package com.seatrend.usb_online.enity;

import java.util.ArrayList;
import java.util.List;

/**
 * {"STATUS":"0"}
 */
public class SocketEnity {

    private String STATUS;
    private MESSAGE MESSAGE;

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setMESSAGE(MESSAGE MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public MESSAGE getMESSAGE() {
        return MESSAGE;
    }

    public static class MESSAGE {

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
}


