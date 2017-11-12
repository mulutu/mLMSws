/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.ussd;

import com.mpango.ws.ussd.MoUssdReq;

/**
 *
 * @author jmulutu
 */
public abstract interface MoUssdListener {

    public abstract void init();

    public abstract void onReceivedUssd(MoUssdReq paramMoUssdReq);
}
