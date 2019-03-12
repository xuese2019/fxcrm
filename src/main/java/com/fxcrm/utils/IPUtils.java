package com.fxcrm.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class IPUtils {

    public List<String> run() {
        List<String> list = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec("arp -a");
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            boolean b = true;
            int i = 0;
            while (b) {
                String line = input.readLine();
                if (line != null) {
                    if (i > 2) {
                        list.add(line.substring(0, 17).trim());
                    }
                } else {
                    b = false;
                }
                i++;
            }
        } catch (IOException e) {
        }
        return list;
    }
}
