package com.seatrend.usb_online;

import android.annotation.TargetApi;
import android.os.Build;
import com.seatrend.usb_online.enity.DataEnity;
import com.seatrend.usb_online.util.GsonUtils;
import kotlin.text.Charsets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JavaTest {
    public static boolean isLoop = true;

    public static void main(String[] args) throws Exception {


//
        resetConnect();

        ArrayList<DataEnity.DATA> list = new ArrayList<>();
        DataEnity.DATA data1 = new DataEnity.DATA();
        data1.setSEAL_NO("11111111");
        data1.setSEAL_TYPE("X");
        list.add(data1);
        DataEnity.DATA data2 = new DataEnity.DATA();
        data1.setSEAL_NO("22222222");
        data1.setSEAL_TYPE("X");
        list.add(data1);

        DataEnity enity = new DataEnity();
        enity.setDATA(list);
        System.out.print("send ok");
        sendMsg("{\"APPOINT_NO\":\"123456\",\"STATUS\":\"0\",\"VEH_ID\":\"111\",\"CHASSIS_NO\":\"987654\",\"STATION\":\"A\",\"LEAN_NO\":\"4\",\"DATA\":[{\"SEAL_TYPE\":\"N\",\"SEAL_NO\":\"AA0001\",\"INSP_ITEM\":\"\",\"SPECIAL_TYPE\":\"2\"},{\"SEAL_TYPE\":\"X\",\"SEAL_NO\":\"AA0002\",\"INSP_ITEM\":\"\",\"SPECIAL_TYPE\":\"3\"}\n" +
                ",{\"SEAL_TYPE\":\"R\",\"SEAL_NO\":\"AA0003\",\"INSP_ITEM\":\"\",\"SPECIAL_TYPE\":\"3\"}\n" +
                ",{\"SEAL_TYPE\":\"Y\",\"SEAL_NO\":\"AA0004\",\"INSP_ITEM\":\"\",\"SPECIAL_TYPE\":\"3\"}\n" +
                ",{\"SEAL_TYPE\":\"X\",\"SEAL_NO\":\"AA0005\",\"INSP_ITEM\":\"\",\"SPECIAL_TYPE\":\"3\"}]}");
        showRecv();

    }

    private static void sendMsg(String s) throws Exception {
//        resetConnect();
        Socket socket = new Socket("127.0.0.1", 8000);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(s);
//        Thread.sleep(1000);
//        dos.close();
//        socket.close();

//        Socket socket = new Socket("127.0.0.1",8000);
//        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
//        out.write(s.getBytes(Charsets.UTF_8));
    }

    //建立端口
    private static void resetConnect() {
        try {

            Runtime.getRuntime().exec("adb forward tcp:8000 tcp:9000");//端口号根据自己的需求
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRecv() throws IOException {
//        resetConnect();
        while (true) {
//            Socket socket = new Socket("127.0.0.1", 8000);
//            DataInputStream dis = new DataInputStream(socket.getInputStream());
//            String textShow = dis.readUTF();
//            System.out.println(textShow);
            Socket socket = new Socket("127.0.0.1", 8000);
            BufferedInputStream br = new BufferedInputStream(socket.getInputStream());
            System.out.println(readMsgFromSocket(br));
        }
    }


    private static String createSocket() {

        Data.LList l = new Data.LList();
        l.setId("111");
        Data.LList l1 = new Data.LList();
        l1.setId("222");
        Data.LList l2 = new Data.LList();
        l2.setId("333");
        Data.LList l3 = new Data.LList();
        l3.setId("444");
        ArrayList<Data.LList> lists = new ArrayList<>();
        lists.add(l);
        lists.add(l1);
        lists.add(l2);
        lists.add(l3);

        Data D = new Data();
        D.setCode(0);
        D.setMessage("成功");
        D.setStatus(true);
        D.setList(lists);

        return GsonUtils.toJson(D);
    }

    static class Data {
        private boolean status;
        private int code;
        private String message;
        private List<LList> list;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<LList> getList() {
            return list;
        }

        public void setList(List<LList> list) {
            this.list = list;
        }

        static class LList {

            private String ID;

            public String getId() {
                return ID;
            }

            public void setId(String id) {
                this.ID = id;
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String readMsgFromSocket(InputStream in) {
        String msg = "";
        byte[] tempbuffer = new byte[1024];
        try {
            int numReadedBytes = in.read(tempbuffer, 0, tempbuffer.length);
            msg = new String(tempbuffer, 0, numReadedBytes, Charsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
}
