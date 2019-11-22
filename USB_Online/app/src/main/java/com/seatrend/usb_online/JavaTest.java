package com.seatrend.usb_online;

import com.seatrend.usb_online.util.GsonUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2019/11/19 20:23
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class JavaTest {
    public static boolean isLoop = true;

    public static void main(String[] args) throws Exception {
//1

//        while (true) {
//            ServerSocket ss = new ServerSocket(9998);// 监听9998端口
//            Socket socket = ss.accept();
//            //等待客户端连上，并等待接收数据
//            DataInputStream dis = new DataInputStream(socket.getInputStream());
//            System.out.println(dis.readUTF()); //打印出客户端发来的数据
//            //回复消息给客户端
//            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//            Data.LList l = new Data.LList();
//            l.setId("111"); Data.LList l1 = new Data.LList();
//            l1.setId("222"); Data.LList l2 = new Data.LList();
//            l2.setId("333"); Data.LList l3 = new Data.LList();
//            l3.setId("444");
//            ArrayList<Data.LList> lists = new ArrayList<>();
//            lists.add(l);
//            lists.add(l1);
//            lists.add(l2);
//            lists.add(l3);
//
//            Data D = new Data();
//            D.setCode(0);
//            D.setMessage("成功");
//            D.setStatus(true);
//            D.setList(lists);
//
//            String result = GsonUtils.toJson(D);
//            dos.writeUTF("你好LY");
//
//
//            ss.close();//通信完之后要关闭，不然下次会报错
//            dos.close();
//            dis.close();
//        }

//2
//        resetConnect();


//        sendMsg("111",socket);
//        Thread.sleep(1000);
//        sendMsg("222", socket);
//        Thread.sleep(1000);
//        sendMsg("333", socket);
//        Thread.sleep(1000);
//        sendMsg("444", socket);
//        Thread.sleep(1000);
//        sendMsg("555", socket);
//        Thread.sleep(1000);
        sendMsg("665665556555.00");

        showRecv();
    }

    private static void sendMsg(String s) throws Exception {
        resetConnect();
        Socket socket = new Socket("127.0.0.1", 8000);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(s);
//        Thread.sleep(1000);
//        dos.close();
//        socket.close();
    }

    private static void resetConnect() {
        try {
            //避免重复开启service所以在转发端口前先stop一下
            Runtime.getRuntime().exec("adb shell am broadcast -a NotifyServiceStop");
            //转发的关键代码 只执行这两句命令也可以实现转发
            Runtime.getRuntime().exec("adb forward tcp:8000 tcp:9000");//端口号根据自己的需求
            Runtime.getRuntime().exec("adb shell am broadcast -a NotifyServiceStart");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRecv() throws IOException {
        resetConnect();
        while (true) {
            Socket socket = new Socket("127.0.0.1", 8000);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String textShow = dis.readUTF();
            System.out.println(textShow);
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

            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

        }
    }

    public static String readMsgFromSocket(InputStream in) {
        String msg = "";
        byte[] tempbuffer = new byte[1024];
        try {
            int numReadedBytes = in.read(tempbuffer, 0, tempbuffer.length);
            msg = new String(tempbuffer, 0, numReadedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    private static void showLog(String msg) {
        System.out.println(msg);
    }
//        try {
//            Runtime.getRuntime().exec("adb root");
////            Runtime.getRuntime().exec("adb pull sdcard/laiyu/11.txt D:\\lyzy\\caogao");
//            Runtime.getRuntime().exec("adb push D:\\lyzy\\11.txt sdcard/laiyu");
//            System.out.println("ggooggo");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}
