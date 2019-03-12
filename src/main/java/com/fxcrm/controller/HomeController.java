package com.fxcrm.controller;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class HomeController {

    @FXML
    private VBox bodys;
    @FXML
    private VBox left;
    @FXML
    private Label times;

    /**
     * 关闭程序
     */
    @FXML
    private void closeLogin() {
        Platform.exit();
    }

    /**
     * 最小化程序
     */
    @FXML
    private void setMin() {
        ObservableList<Stage> stages = FXRobotHelper.getStages();
        Stage stage = stages.get(0);
        stage.setIconified(true);
    }

    /**
     * 首页
     */
    @FXML
    private void sy() {
        men("sy");
    }

    /**
     * 个人中心
     */
    @FXML
    private void grzx() {
        men("grzx");
    }

    /**
     * 客户管理
     */
    @FXML
    private void khgl() {
        men("khgl");
    }

    /**
     * 拜访记录
     */
    @FXML
    private void bfjl() {
        men("bfjl");
    }

    /**
     * 常用软件
     */
    @FXML
    private void cyrj() {
        men("cyrj");
    }

    /**
     * 浏览器
     */
    @FXML
    private void llq() {
        men("llq");
    }

    /**
     * 网络邻居
     */
    @FXML
    private void wllj() {
        men("wllj");
    }

    private void men(String str) {
        try {
//            ObservableList<Stage> stages = FXRobotHelper.getStages();
            bodys.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + str + ".fxml"));
            Parent root = fxmlLoader.load();
            if (str.equals("khgl")) {
                KhglController controller = fxmlLoader.getController();
                controller.page();
            }
            if (str.equals("bfjl")) {
                BfjlController controller = fxmlLoader.getController();
                controller.page();
            }
            if (str.equals("llq")) {
                LlqController controller = fxmlLoader.getController();
                controller.init();
            }
            if (str.equals("wllj")) {
                WlljController controller = fxmlLoader.getController();
                controller.init();
            }
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + str + ".fxml"));
            bodys.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menus() {
        ObservableList<Node> children = left.getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (i != 0) {
                children.get(i).setVisible(!children.get(i).isVisible());
            }
        }
    }

    //    时间
    public void dates() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEEE");
                Thread.sleep(1000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String format1 = format.format(System.currentTimeMillis());
                        times.setText(format1);
                        dates();
                    }
                });
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    //    模拟cmd
    private void cmd(String s) {
        try {
            Runtime.getRuntime().exec(s);
//            Runtime.getRuntime().exec("cmd /c "+s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    关闭计算机
    @FXML
    private void stopWindows() {
        cmd("Shutdown -s -t 0");
    }

    @FXML
    private void restWindows() {
        cmd("Shutdown -r -t 0");
    }

    @FXML
    private void qx() {
        cmd("Shutdown -a");
    }

}
