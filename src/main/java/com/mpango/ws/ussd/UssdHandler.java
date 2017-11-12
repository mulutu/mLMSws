package com.mpango.ws.ussd;

import com.google.gson.Gson;
import com.mpango.ws.bus.CustomerBUS;
import com.mpango.ws.bus.CustomerDTO;
import com.mpango.ws.dao.lms.DataProvider;

import com.mpango.ws.ussd.PropertyLoader;
import com.mpango.ws.ussd.MoUssdReq;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UssdHandler -
 * http://localhost:8080/mpangoLMSWebService/UssdHandler //
 * http://localhost:8080/mLMSws-1.0-SNAPSHOT/UssdHandler
 */
@WebServlet("/UssdHandler")
public class UssdHandler extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CustomerDTO result;
    private static String customerMSISDN = "";
    private static String PIN = "";

    //hardcoded values
    private static final String EXIT_SERVICE_CODE = "000";
    private static final String BACK_SERVICE_CODE = "999";
    private static final String INIT_SERVICE_CODE = "#678*";
    //private static final String REQUEST_SENDER_SERVICE = "http://localhost:7000/ussd/send";
    private static final String PROPERTY_KEY_PREFIX = "menu.level.";
    private static final String USSD_OPERATION_MT_CONT = "mt-cont";
    private static final String USSD_OPERATION_MT_FIN = "mt-fin";

    //menu state saving for back button
    private List<Byte> menuState = new ArrayList<Byte>();

    /*
     * Not needed by USSD API.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        response.getWriter().println("Hello USSD!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");        
        PrintWriter out = response.getWriter(); // Set the output printer

        try {
            // Variable holding the ussd response
            UssdResponse ussdResponse = new UssdResponse();

            // Receive the Ussd request raw data
            StringBuilder requestBuffer = new StringBuilder();
            String lineRead;
            while ((lineRead = request.getReader().readLine()) != null) {
                requestBuffer.append(lineRead);
            }

            // Parse the request raw data into the UssdRequest Object for better manipulation
            // since the request raw data comes in a json format.
            Gson gson = new Gson();
            UssdRequest ussdRequest = gson.fromJson(requestBuffer.toString(), UssdRequest.class);
            //MoUssdReq ussdRequest = gson.fromJson(requestBuffer.toString(), MoUssdReq.class);
            
            //onReceivedUssd(MoUssdReq ussdRequest);

            // Process the Ussd request
            if (ussdRequest != null) {

                System.out.println("Ussd Request " + ussdRequest.toString());
                // check the various request type
                if (ussdRequest.getType().equals("Initiation")) {

                    Connection con = DataProvider.getConnection();
                    customerMSISDN = ussdRequest.getMobile();
                    result = CustomerBUS.getCustomerByMSISDN(customerMSISDN, con);

                    ussdResponse.setType("Response");

                    if (result.getMsisdn().equals(customerMSISDN)) {
                        String customerFName = result.getFirstName();
                        ussdResponse.setMessage("Dear " + customerFName + ",\n Welcome to Mpango.\n Please enter your PIN to proceed:");
                        ussdResponse.setClientState("L1");
                    } else {
                        ussdResponse.setMessage("Welcome to Mpango.\n 1. Register\n 2. Ask question");
                        ussdResponse.setClientState("L0");
                    }

                    con.close();

                } else if (ussdRequest.getType().equals("Response")) {
                    // response case studies
                    if (!ussdRequest.getMessage().isEmpty()) {

                        // check whether the lcient state is sent
                        if (ussdRequest.getClientState() == null) {

                            // LOGIN PART
                            // CAPTURE THE PIN
                            if (ussdRequest.getMessage().equals("1")) {
                                ussdResponse.setMessage("Are you sure you want free food?\n1. Yes\n2. No");
                                ussdResponse.setType("Response");
                                ussdResponse.setClientState("FF");
                            } // Free drink is chosen
                            else if (ussdRequest.getMessage().equals("2")) {
                                ussdResponse.setMessage("Are you sure you want free drink?\n1. Yes\n2. No");
                                ussdResponse.setType("Response");
                                ussdResponse.setClientState("FD");
                            } // Free airtime is chosen
                            else if (ussdRequest.getMessage().equals("3")) {
                                ussdResponse.setMessage("Are you sure you want free airtime?\n1. Yes\n2. No");
                                ussdResponse.setType("Response");
                                ussdResponse.setClientState("FA");
                            } else {
                                ussdResponse.setMessage("Invalid Option");
                                ussdResponse.setType("Release");
                            }
                        } else {
                            // The client state is resent back to us
                            if (ussdRequest.getClientState().equals("L1")) {
                                //ussdResponse.setMessage("Thank you. You will receive your free food shortly.");
                                //ussdResponse.setType("Release");

                                Connection con = DataProvider.getConnection();

                                PIN = ussdRequest.getMessage();
                                customerMSISDN = ussdRequest.getMobile();
                                result = CustomerBUS.customerLogin(customerMSISDN, PIN, con);

                                if (result.getMsisdn().equals(customerMSISDN)) {
                                    ussdResponse.setMessage("Main menu?\n1. Get Loan\n2. Pay Loan");
                                    ussdResponse.setType("Response");
                                    ussdResponse.setClientState("L2");
                                } else {
                                    ussdResponse.setMessage("Wrong PIN\n1. Please try again:");
                                    ussdResponse.setType("Response");
                                    ussdResponse.setClientState("L1");
                                }
                                con.close();

                            } else if (ussdRequest.getClientState().equals("L2") && ussdRequest.getMessage().equals("1")) {
                                // check limit/existing loan
                                ussdResponse.setMessage("GET LOAN");
                                ussdResponse.setType("Response");
                                ussdResponse.setClientState("LL2");
                            } else if (ussdRequest.getClientState().equals("L2") && ussdRequest.getMessage().equals("2")) {
                                ussdResponse.setMessage("PAY LOAN");
                                ussdResponse.setType("Response");
                                ussdResponse.setClientState("LL3");
                            } else if (ussdRequest.getClientState().equals("FA") && ussdRequest.getMessage().equals("1")) {
                                ussdResponse.setMessage("Thank you. You will receive your free airtime shortly.");
                                ussdResponse.setType("Release");
                            } else if (ussdRequest.getMessage().equals("2")) {
                                ussdResponse.setMessage("Order Cancelled.");
                                ussdResponse.setType("Release");
                            } else {
                                ussdResponse.setMessage("Unexpected Response.");
                                ussdResponse.setType("Release");
                            }
                        }
                    } else {
                        ussdResponse.setMessage("Invalid option.");
                        ussdResponse.setType("Release");
                    }
                }
            } else {
                ussdResponse.setMessage("Duh.");
                ussdResponse.setType("Release");
            }

            System.out.println("Ussd response : " + ussdResponse.toString());
            String responseJson = gson.toJson(ussdResponse);
            out.print(responseJson);
        } catch (Exception e) {
            // Send at least a message to the user in case of errors
            try {
                Gson gson = new Gson();
                UssdResponse ussdResponse = new UssdResponse();
                ussdResponse.setMessage("Invalid request");
                ussdResponse.setType("Release");
                String responseJson = gson.toJson(ussdResponse);
                out.print(responseJson);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void onReceivedUssd(MoUssdReq ussdrequest) {
        processRequest(ussdrequest);
    }

    public void processRequest(MoUssdReq ussdrequest) {
        //exit request - session destroy
        if (ussdrequest.getMessage().equals(EXIT_SERVICE_CODE)) {
            terminateSession(ussdrequest);
            return;//completed work and return
        }

        //back button handling
        if (ussdrequest.getMessage().equals(BACK_SERVICE_CODE)) {
            backButtonHandle(ussdrequest);
            return;//completed work and return
        }

        //get current service code
        byte serviceCode;
        if (ussdrequest.getMessage().equals(INIT_SERVICE_CODE)) {
            serviceCode = 0;
            clearMenuState();
        } else {
            serviceCode = getServiceCode(ussdrequest);
        }
        //create request to display user
        final MtUssdReq response = createRequest(ussdrequest, buildNextMenuContent(serviceCode), USSD_OPERATION_MT_CONT);
        //sendRequest(request);
        //record menu state
        menuState.add(serviceCode);
    }

    private MtUssdReq createRequest(MoUssdReq moUssdReq, String menuContent, String ussdOperation) {
        final MtUssdReq request = new MtUssdReq();
        request.setApplicationId(moUssdReq.getApplicationId());
        request.setEncoding(moUssdReq.getEncoding());
        request.setMessage(menuContent);
        request.setPassword("password");
        request.setSessionId(moUssdReq.getSessionId());
        request.setUssdOperation(ussdOperation);
        request.setVersion(moUssdReq.getVersion());
        request.setDestinationAddress(moUssdReq.getSourceAddress());
        return request;
    }

    private String buildNextMenuContent(byte selection) {
        String menuContent;
        try {
            //build menu contents
            menuContent = getText(selection);
        } catch (MissingResourceException e) {
            //back to main menu
            menuContent = getText((byte) 0);
        }
        return menuContent;
    }

    private String getText(byte key) {
        return PropertyLoader.getInstance().getText(PROPERTY_KEY_PREFIX + key);
    }

    private byte getServiceCode(MoUssdReq ussdrequest) {
        byte serviceCode = 0;
        try {
            serviceCode = Byte.parseByte(ussdrequest.getMessage());
        } catch (NumberFormatException e) {
            return serviceCode;
        }

        //create service codes for child menus based on the main menu codes
        if (menuState.size() > 0 && menuState.get(menuState.size() - 1) != 0) {
            String generatedChildServiceCode = "" + menuState.get(menuState.size() - 1) + serviceCode;
            serviceCode = Byte.parseByte(generatedChildServiceCode);
        }

        return serviceCode;
    }

    private void clearMenuState() {
        //LOGGER.info("clear history List");
        menuState.clear();
    }

    private void terminateSession(MoUssdReq ussdrequest) {
        //final MtUssdReq request = createRequest(ussdrequest, "", USSD_OPERATION_MT_FIN);
        //sendRequest(request);
    }

    private void backButtonHandle(MoUssdReq ussdrequest) {
        byte lastMenuVisited = 0;

        //remove last menu when back
        /*if (menuState.size() > 0) {
         menuState.remove(menuState.size() - 1);
         lastMenuVisited = menuState.get(menuState.size() - 1);
         }*/
        //create request and send
        //final MtUssdReq request = createRequest(moUssdReq, buildBackMenuContent(lastMenuVisited), USSD_OPERATION_MT_CONT);
        //sendRequest(request);
        //clear menu status
        /*if (lastMenuVisited == 0) {
         clearMenuState();
         //add 0 to menu state ,finally its in main menu
         menuState.add((byte) 0);
         }*/
    }

}
