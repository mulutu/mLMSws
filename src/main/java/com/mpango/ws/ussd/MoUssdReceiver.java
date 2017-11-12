/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.ussd;

import com.google.gson.Gson;
import com.mpango.ws.ussd.MoUssdReq;
import com.mpango.ws.ussd.MoUssdResp;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author jmulutu
 */
@WebServlet("/UssdHandler2")
public class MoUssdReceiver extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MoUssdReceiver.class.getName());
    private List<MoUssdListener> moListenerList = new ArrayList();
    private ExecutorService executorService;
    private static int sdpMoReceiverThreadCount;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String receiverClassName = config.getInitParameter("ussdReceiver");
        initializeListeners(receiverClassName);
        initializeReceivingThreadPool();
    }

    private void initializeReceivingThreadPool() {
        this.executorService = Executors.newCachedThreadPool(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r, "sdp-mo-receiver-thread-" + MoUssdReceiver.class);
            }
        });
    }

    private void initializeListeners(String receiverClassName) {
        try {
            if (receiverClassName != null) {
                Class listener = Class.forName(receiverClassName);
                Constructor constructor = listener.getConstructor(new Class[0]);
                Object object = constructor.newInstance(new Object[0]);
                if ((object instanceof MoUssdListener)) {
                    MoUssdListener moUssdListener = (MoUssdListener) object;
                    moUssdListener.init();
                    this.moListenerList.add(moUssdListener);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Exception occurred while initializing listener", e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        if ((contentType == null) || (!contentType.equals("application/json"))) {
            resp.setStatus(415);
            resp.getWriter().println("Only application/json is supporting");
            return;
        }
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        Gson gson = new Gson();
        try {
            String readContent = readStringContent(req);
            MoUssdReq moUssdReq = (MoUssdReq) gson.fromJson(readContent, MoUssdReq.class);
            for (MoUssdListener moUssdListener : this.moListenerList) {
                fireMoEvent(moUssdListener, moUssdReq);
            }
            MoUssdResp moUssdResp = new MoUssdResp();
            moUssdResp.setStatusCode("S1000");
            moUssdResp.setStatusDetail("Success");
            resp.getWriter().print(gson.toJson(moUssdResp));
        } catch (Exception e) {
            MoUssdResp moUssdResp = new MoUssdResp();
            moUssdResp.setStatusCode("E1601");
            moUssdResp.setStatusDetail("System error occurred");
            try {
                resp.getWriter().print(gson.toJson(moUssdResp));
            } catch (IOException e2) {
                LOGGER.log(Level.INFO, "Unexpected error occurred", e);
            }
            LOGGER.log(Level.INFO, "Unexpected exception occurred", e);
        }
    }

    private void fireMoEvent(final MoUssdListener moUssdListener, final MoUssdReq moUssdReq) {
        this.executorService.submit(new Runnable() {
            public void run() {
                try {
                    moUssdListener.onReceivedUssd(moUssdReq);
                } catch (Exception e) {
                    MoUssdReceiver.LOGGER.log(Level.INFO, "Unexpected error occurred ", e);
                }
            }
        });
    }

    private String readStringContent(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("SDP Application is Running");
    }
}
