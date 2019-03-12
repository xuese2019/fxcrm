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

    private static Node node = null;

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

    /**
     * 音乐播放器
     */
    @FXML
    private void yybfq() {
        men("yybfq");
    }

    private void men(String str) {
        try {
//            ObservableList<Stage> stages = FXRobotHelper.getStages();
            if (bodys.getChildren().size() > 0) {
                Node n = bodys.getChildren().get(0);
                String id = n.getId();
                if (id != null && id.equals("yys")) {
                    node = n;
                }
            }
            bodys.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + str + ".fxml"));
            Parent root = fxmlLoader.load();
            bodys.getChildren().add(root);
            Object controller1 = fxmlLoader.getController();
            switch (str) {
                case "khgl":
                    ((KhglController) controller1).page();
                    break;
                case "bfjl":
                    ((BfjlController) controller1).page();
                    break;
                case "llq":
                    ((LlqController) controller1).init();
                    break;
                case "wllj":
                    ((WlljController) controller1).init();
                    break;
                case "yybfq":
                    if (node == null) {
                        ((YybfqController) controller1).init();
                    } else {
                        bodys.getChildren().clear();
                        bodys.getChildren().add(node);
                    }
                    break;
                default:
            }
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
