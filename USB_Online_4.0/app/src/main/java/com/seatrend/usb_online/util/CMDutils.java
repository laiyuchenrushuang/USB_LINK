package com.seatrend.usb_online.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class CMDutils {

    public void execCommand(String command) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);
        String a = "";
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line).append(" ");
            }
            System.out.println(stringBuffer.toString());

        } catch (InterruptedException e) {
            System.err.println(e);
        }finally{
            try {
                proc.destroy();
            } catch (Exception ignored) {
            }
        }
    }
}
