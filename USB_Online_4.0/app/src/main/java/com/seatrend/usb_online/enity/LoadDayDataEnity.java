package com.seatrend.usb_online.enity;

import java.util.ArrayList;

/**
 * {"DATE":"2020-02-18 0:00:00","DATA_LIST":[{"APPOINT_NO":"1111","VEH_Reg_Mark":"123123"},{"APPOINT_NO":"1111","VEH_Reg_Mark":"123123"}]}
 */
public class LoadDayDataEnity {

    private String DATE;
    private ArrayList<DATA_LIST> DATA_LIST;

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATA_LIST(ArrayList<DATA_LIST> DATA_LIST) {
        this.DATA_LIST = DATA_LIST;
    }

    public ArrayList<DATA_LIST> getDATA_LIST() {
        return DATA_LIST;
    }

    public static class DATA_LIST {

        private String APPOINT_NO;
        private String VEH_Reg_Mark;

        public void setAPPOINT_NO(String APPOINT_NO) {
            this.APPOINT_NO = APPOINT_NO;
        }

        public String getAPPOINT_NO() {
            return APPOINT_NO;
        }

        public void setVEH_Reg_Mark(String VEH_Reg_Mark) {
            this.VEH_Reg_Mark = VEH_Reg_Mark;
        }

        public String getVEH_Reg_Mark() {
            return VEH_Reg_Mark;
        }
    }
}
