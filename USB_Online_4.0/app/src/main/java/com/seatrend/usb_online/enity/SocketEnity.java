package com.seatrend.usb_online.enity;

import java.util.ArrayList;
import java.util.List;

/**
 * {"STATUS":"3","MESSAGE":"{\"DATE\":\"2020/2/18 0:00:00\",\"DATA_LIST\":[{\"APPOINT_NO\":\"1111\",\"VEH_Reg_Mark\":\"123123\"},{\"APPOINT_NO\":\"1111\",\"VEH_Reg_Mark\":\"123123\"}]}"}
 */
public class SocketEnity {

    private String STATUS;
    private String MESSAGE;

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}


