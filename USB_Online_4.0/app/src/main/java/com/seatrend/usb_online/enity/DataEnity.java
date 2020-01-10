package com.seatrend.usb_online.enity;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 数据结构
 * <p>
 * {"APPOINT_NO":"111111","VEH_ID":null,"CHASSIS_NO":null,"STATION":null,"LEAN_NO":"111","DATA":[{"SEAL_TYPE":"X","SEAL_NO":"AA0001","INSP_ITEM":"","SPECIAL_TYPE":"2"},{"SEAL_TYPE":"X","SEAL_NO":"AA0004","INSP_ITEM":"","SPECIAL_TYPE":"3"},{"SEAL_TYPE":"X","SEAL_NO":"AA0005","INSP_ITEM":"","SPECIAL_TYPE":"3"}]}
 * ]
 * }
 */
public class DataEnity implements Serializable{
    private String APPOINT_NO;
    private String VEH_ID;
    private String CHASSIS_NO;
    private String STATION;
    private String LEAN_NO;
    private ArrayList<DATA> DATA;
    private String STATUS;  //增加状态 0 是初始状态 1 是通知发送

    public String getSTATUS() { return STATUS; }

    public void setSTATUS(String STATUS) { this.STATUS = STATUS; }

    public void setAPPOINT_NO(String APPOINT_NO) {
        this.APPOINT_NO = APPOINT_NO;
    }

    public String getAPPOINT_NO() {
        return APPOINT_NO;
    }

    public void setVEH_ID(String VEH_ID) {
        this.VEH_ID = VEH_ID;
    }

    public String getVEH_ID() {
        return VEH_ID;
    }

    public void setCHASSIS_NO(String CHASSIS_NO) {
        this.CHASSIS_NO = CHASSIS_NO;
    }

    public String getCHASSIS_NO() {
        return CHASSIS_NO;
    }

    public void setSTATION(String STATION) {
        this.STATION = STATION;
    }

    public String getSTATION() {
        return STATION;
    }

    public void setLEAN_NO(String LEAN_NO) {
        this.LEAN_NO = LEAN_NO;
    }

    public String getLEAN_NO() {
        return LEAN_NO;
    }

    public void setDATA(ArrayList<DATA> DATA) {
        this.DATA = DATA;
    }

    public ArrayList<DATA> getDATA() {
        return DATA;
    }

    public static class DATA implements Serializable {

        private String SEAL_TYPE; //标签的标记类型
        private String SEAL_NO; //标签的num
        private String INSP_ITEM; //标签的位置
        private String SPECIAL_TYPE;//标签的种类类型

        public void setSEAL_TYPE(String SEAL_TYPE) {
            this.SEAL_TYPE = SEAL_TYPE;
        }

        public String getSEAL_TYPE() {
            return SEAL_TYPE;
        }

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

        public void setSPECIAL_TYPE(String SPECIAL_TYPE) {
            this.SPECIAL_TYPE = SPECIAL_TYPE;
        }

        public String getSPECIAL_TYPE() {
            return SPECIAL_TYPE;
        }
    }
}
