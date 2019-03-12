package com.fxcrm.controller;

import com.fxcrm.utils.AlertUtils;
import com.fxcrm.utils.IPUtils;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class WlljController {

    @FXML
    private VBox grids;

    public void init() {
        grids.getChildren().clear();
        grids.getChildren().add(new Label("正在检测局域网内的连接设备"));
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                getPcs();
                return null;
            }
        };
        new Thread(task).start();
    }

    private void getPcs() {
        IPUtils ipUtils = new IPUtils();
        VBox vBox = new VBox();
        try {
            List<String> list = ipUtils.run();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    HBox hBox = new HBox();
                    hBox.setPrefHeight(30);
                    hBox.setOnMouseClicked(event -> {
//                        addres(list.get(j));
                    });
                    Label label4 = new Label(list.get(i));
                    label4.setPrefHeight(30);
                    hBox.getChildren().add(label4);
                    vBox.getChildren().add(hBox);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        grids.getChildren().clear();
                        grids.getChildren().add(vBox);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addres(String ip) {
        InetAddress addr = null;
        try {
            byte[] bytes = new byte[4];
            String[] split = ip.split("\\.");
            bytes[0] = (byte) Short.parseShort(split[0]);
            bytes[1] = (byte) Short.parseShort(split[1]);
            bytes[2] = (byte) Short.parseShort(split[2]);
            bytes[3] = (byte) Short.parseShort(split[3]);
            addr = InetAddress.getByAddress(bytes);
//            主机名
            String canonicalHostName = addr.getCanonicalHostName();
//            主机别名
            String hostName = addr.getHostName();
            AlertUtils alertUtils = new AlertUtils();
            alertUtils.f_alert_informationDialog("", "主机名称：" + canonicalHostName + "    主机别名：" + hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            AlertUtils alertUtils = new AlertUtils();
            alertUtils.f_alert_informationDialog("警告", "主机信息获取失败");
        }
    }
}
