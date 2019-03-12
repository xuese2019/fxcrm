package com.fxcrm.controller;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class SyController {

    private static String p = null;
    private Stage stage = null;
    @FXML
    private HBox datas;
    @FXML
    private Label mac;

    @FXML
    private void getZm() {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        p = desktopPath;
        mac.setText("当前地址:" + desktopPath);
        findFileByDisk(desktopPath);
    }

    @FXML
    private void getMac() {
        String sn = getSerialNumber("C");
        mac.setText(sn);
    }

    private static String getSerialNumber(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("damn", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\""
                    + drive
                    + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;

            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    private List<String> getPC() {
        List<String> list = new ArrayList<>();
        // 当前文件系统类
//        FileSystemView fsv = FileSystemView.getFileSystemView();
        // 列出所有windows 磁盘
        File[] fs = File.listRoots();
        // 显示磁盘卷标
        for (int i = 0; i < fs.length; i++) {
            list.add(fs[i].toString());
        }
        return list;
    }

    private void findFileByDisk(String character) {
        mac.setText("当前地址:" + character);
        datas.getChildren().clear();
        File file = new File(character);
        File[] tempList = file.listFiles();
//        滚动面板
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("edge-to-edge");
        ObservableList<Stage> stages = FXRobotHelper.getStages();
        Stage stage = stages.get(0);
        Scene scene = stage.getScene();
        scrollPane.setPrefWidth(scene.getWidth() - 150);
        scrollPane.setMinViewportHeight(scene.getHeight() - 55);
//        隐藏底部滚动条
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color:#1E90FF;");
        hBox.setPrefWidth(scene.getWidth() - 165);
        hBox.setMinHeight(scrollPane.getMinViewportHeight());
        scrollPane.setContent(hBox);
        datas.getChildren().add(scrollPane);
        if (tempList != null && tempList.length > 0) {
            VBox vBoxa = new VBox();
            vBoxa.setPrefHeight(Region.USE_COMPUTED_SIZE);
            VBox vBoxb = new VBox();
            vBoxb.setPrefHeight(Region.USE_COMPUTED_SIZE);
            VBox vBoxc = new VBox();
            vBoxc.setPrefHeight(Region.USE_COMPUTED_SIZE);
            vBoxc.setPrefWidth(100);
            if (!p.equals(character)) {
                Button button = new Button();
                button.setText("返回");
                button.setStyle("-fx-background-color: #0000CD;-fx-cursor: hand;");
                button.setPrefWidth(100);
                button.setOnMouseClicked(event -> {
                    mous1(character);
                });
                vBoxc.getChildren().add(button);
            }

            TextField textField = new TextField();
            textField.setStyle("-fx-text-fill: black;");
            vBoxc.getChildren().add(textField);
            Button button = new Button();
            button.setText("搜索");
            button.setStyle("-fx-background-color: #0000CD;-fx-cursor: hand;");
            button.setPrefWidth(100);
            button.setOnMouseClicked(event -> {
                if (!textField.getText().trim().isEmpty()) {
                    seach(textField.getText(), character);
                }
            });
            vBoxc.getChildren().add(button);

            Button button5 = new Button();
            button5.setText("进度条");
            button5.setStyle("-fx-background-color: #0000CD;-fx-cursor: hand;");
            button5.setPrefWidth(100);
            button5.setOnMouseClicked(event -> {
                jdt();
            });
            vBoxc.getChildren().add(button5);

            List<String> list = getPC();
            if (list.size() > 0) {
                list.forEach(j -> {
                    Button button2 = new Button();
                    button2.setText(j);
                    button2.setStyle("-fx-background-color: #0000CD;-fx-cursor: hand;");
                    button2.setPrefWidth(100);
                    button2.setOnMouseClicked(event -> {
                        findFileByDisk(j);
                    });
                    vBoxc.getChildren().add(button2);
                });
            }

            hBox.getChildren().add(vBoxc);
            hBox.getChildren().add(vBoxa);
            hBox.getChildren().add(vBoxb);
            for (int i = 0; i < tempList.length; i++) {
                HBox pane = new HBox();
                pane.setStyle("-fx-cursor: hand;");
                pane.setPrefHeight(30);
                pane.setPrefWidth(300);
                ImageView imageView = new ImageView();
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                if (tempList[i].isFile()) {
                    vBoxb.getChildren().add(pane);
                    String path = tempList[i].getPath();
                    pane.setOnMouseClicked(event -> {
                        useAWTDesktop(event, path);
                    });
                }
//                是否是文件夹
                if (tempList[i].isDirectory()) {
                    Image image = new Image(getClass().getResource("/img/wjj.jpg").toString());
                    imageView.setImage(image);
                    pane.getChildren().add(imageView);
                    String path = tempList[i].getPath();
                    pane.setOnMouseClicked(event -> {
                        findFileByDisk(path);
                    });
                    vBoxa.getChildren().add(pane);
                }
                Label label = new Label(tempList[i].getName());
                label.setPrefHeight(30);
                pane.getChildren().add(label);
            }
        } else {
            Button button = new Button();
            button.setText("返回");
            button.setStyle("-fx-background-color: #0000CD;-fx-cursor: hand;");
            button.setPrefWidth(100);
            button.setOnMouseClicked(event -> {
                mous1(character);
            });
            hBox.getChildren().add(button);
        }
    }

    private void useAWTDesktop(MouseEvent event, String path) {
        MouseButton button = event.getButton();
        double x = event.getSceneX();
        double y = event.getSceneY();
        switch (button.name()) {
//            左键
            case "PRIMARY":
                useAWTDesktop2(path);
                break;
//                右键
            case "SECONDARY":
                if (stage != null) {
                    stage.close();
                }
                stage = new Stage();
                stage.setWidth(100);
                stage.setHeight(200);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setX(x);
                stage.setY(y);
                VBox vBox = new VBox();
                vBox.setPrefWidth(100);
                vBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
                Scene scene = new Scene(vBox);
                stage.setScene(scene);
                stage.show();

                Label label = new Label("关闭");
                label.setPrefWidth(100);
                label.setPrefHeight(30);
                label.setStyle("-fx-border-color: #0000CD;-fx-cursor: hand;-fx-text-fill: #0000CD;-fx-alignment: center;");
                label.setOnMouseClicked(event1 -> {
                    stage.close();
                });
                vBox.getChildren().add(label);

                Label label2 = new Label("属性");
                label2.setPrefWidth(100);
                label2.setPrefHeight(30);
                label2.setStyle("-fx-border-color: #0000CD;-fx-cursor: hand;-fx-text-fill: #0000CD;-fx-alignment: center;");
                label2.setOnMouseClicked(event1 -> {
                    stage.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("通知");
                    alert.setContentText("属性开发中");
                    alert.show();
                });
                vBox.getChildren().add(label2);

                break;
//                中建
            case "MIDDLE":
                break;
            default:
        }
    }

    //    打开文件
    private static void useAWTDesktop2(String path) {
        try {
            java.awt.Desktop.getDesktop().open(new File(path));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("程序打开出错");
            alert.show();
        }
    }

    //    搜索
    private void seach(String str, String path) {
        if (str != null && !str.trim().isEmpty()) {
//            datas.getChildren().clear();
//            Label label = new Label("搜索中...");
//            datas.getChildren().add(label);
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    seach2(path, str);
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();

            datas.getChildren().clear();
            Label label = new Label();
            label.setText("搜索中...");
            datas.getChildren().add(label);
//            非主线程中更新ui
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    task.run();
//                }
//            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("查询条件不能为空");
            alert.show();
        }
    }

    private void seach2(String path, String str) {
        List<String> list = new ArrayList<>();
        list.add(path);
        List<String> dg = dg(list, path, str);
//        滚动面板
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("edge-to-edge");
        ObservableList<Stage> stages = FXRobotHelper.getStages();
        Stage stage = stages.get(0);
        Scene scene = stage.getScene();
        scrollPane.setPrefWidth(scene.getWidth() - 150);
        scrollPane.setMinViewportHeight(scene.getHeight() - 55);
//        隐藏底部滚动条
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color:#1E90FF;");
        vBox.setPrefWidth(scene.getWidth() - 165);
        vBox.setMinHeight(scrollPane.getMinViewportHeight());
        scrollPane.setContent(vBox);
        if (dg.size() > 0) {
            dg.forEach(k -> {
                HBox hBox = new HBox();
                hBox.setPrefWidth(vBox.getPrefWidth());
                hBox.setPrefHeight(30);
                Label label = new Label();
                label.setText(k);
                label.setOnMouseClicked(event -> {
                    if (path.equals(k)) {
                        findFileByDisk(path);
                    } else {
                        useAWTDesktop(event, k);
                    }
                });
                hBox.getChildren().add(label);
                vBox.getChildren().add(hBox);
            });
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                datas.getChildren().clear();
                datas.getChildren().add(scrollPane);
            }
        });
    }

    private List<String> dg(List<String> list, String path, String str) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        if (tempList != null && tempList.length > 0) {
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isDirectory()) {
                    dg(list, tempList[i].getPath(), str);
                } else {
                    String name = tempList[i].getName();
                    if (name.contains(str)) {
                        list.add(tempList[i].getPath());
                    }
                }
            }
        }
        return list;
    }

    private void mous1(String character) {
        int m = 0;
        for (int n = 0; n < character.length(); n++) {
            if (character.charAt(n) == '\\') {
                m++;
            }
        }
        if (m > 0) {
            int i = character.lastIndexOf("\\");
            String substring = character.substring(0, i);
            if (substring.contains("\\")) {
                findFileByDisk(substring);
            } else {
                if (substring.contains(":") && substring.lastIndexOf(":") < substring.length()) {
                    findFileByDisk(substring + "\\");
                } else {
                    findFileByDisk(p);
                }
            }
        } else {
            findFileByDisk(p);
        }
    }

    private void jdt() {
        datas.getChildren().clear();
        Label label = new Label();
        label.setStyle("-fx-background-color: blue;");
        label.setPrefHeight(15);
        label.setPrefWidth(0);
        datas.getChildren().add(label);
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                for (double i = 0.0; i < datas.getWidth(); i++) {
                    Thread.sleep(50);
                    final double j = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            label.setPrefWidth(j);
                        }
                    });
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
