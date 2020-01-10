package com.seatrend.usb_online.enity;

import java.io.Serializable;
import java.util.List;

/**
 * {"APPOINT_NO":"202001041135","VEH_Reg_Mark":"132144854","STATUS":"0","DATA_CHECK":[{"SEAL_NO":"AA0001","INSP_ITEM":"TA"}],"DATA_NEW":[{"SEAL_NO":"AA0001","INSP_ITEM":"TA"}]}
 */
public class NewData  implements Serializable {


    private String APPOINT_NO;
    private String VEH_Reg_Mark;
    private String STATUS;
    private List<DATA_CHECK> DATA_CHECK;
    private List<DATA_NEW> DATA_NEW;

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

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setDATA_CHECK(List<DATA_CHECK> DATA_CHECK) {
        this.DATA_CHECK = DATA_CHECK;
    }

    public List<DATA_CHECK> getDATA_CHECK() {
        return DATA_CHECK;
    }

    public void setDATA_NEW(List<DATA_NEW> DATA_NEW) {
        this.DATA_NEW = DATA_NEW;
    }

    public List<DATA_NEW> getDATA_NEW() {
        return DATA_NEW;
    }


    public static class DATA_CHECK  implements Serializable {

        private String SEAL_NO;
        private String INSP_ITEM;
        public void setSEAL_NO(String SEAL_NO) {
            this.SEAL_NO = SEAL_NO;
        }
        public String getSEAL_NO() {
            return SEAL_NO;
        }

        public void setINSP_ITEM(String INSP_ITEM) {
            this.INSP_ITEM = INSP_ITEM;
        }
        public String getINSP_ITEM() {
            return INSP_ITEM;
        }

    }

    public static class DATA_NEW  implements Serializable {

        private String SEAL_NO;
        private String INSP_ITEM;
        public void setSEAL_NO(String SEAL_NO) {
            this.SEAL_NO = SEAL_NO;
        }
        public String getSEAL_NO() {
            return SEAL_NO;
        }

        public void setINSP_ITEM(String INSP_ITEM) {
            this.INSP_ITEM = INSP_ITEM;
        }
        public String getINSP_ITEM() {
            return INSP_ITEM;
        }

    }

}

