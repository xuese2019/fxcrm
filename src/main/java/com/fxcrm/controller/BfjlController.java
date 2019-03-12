package com.fxcrm.controller;

import com.fxcrm.db.dao.Daos;
import com.fxcrm.utils.AlertUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author: LD
 * @date:
 * @description:
 */
public class BfjlController {

    private Daos dao = new Daos();
    private static int p = 0;
    private static int p2 = 20;

    @FXML
    private TextField khmc;
    @FXML
    private DatePicker kssj;
    @FXML
    private DatePicker jssj;
    @FXML
    private TextField gjz;
    @FXML
    private VBox tables;

    @FXML
    private void add() {
        // 创建新的stage
        Stage secondStage = new Stage();
        VBox vBox = new VBox();

        Label label = new Label("客户");
        vBox.getChildren().add(label);
        Label label2 = new Label();
        vBox.getChildren().add(label2);
        Button button2 = new Button("选择客户");
        button2.setOnMouseClicked(event -> {
            VBox vBox2 = new VBox();

            TextField textField = new TextField();
            vBox2.getChildren().add(textField);

            VBox hBox = new VBox();
            hBox.setPrefWidth(500);
            Button button = new Button("查询");
            button.setOnMouseClicked(event1 -> {
                List<Map<Integer, String>> list = dao.query("select * from kh_table limit 5", 4);
                if (list != null && list.size() > 0) {
                    list.forEach(k -> {
                        HBox hBox1 = new HBox();
                        Label label1 = new Label(k.get(1));
                        hBox1.getChildren().add(label1);
                        Label label11 = new Label("   ");
                        hBox1.getChildren().add(label11);
                        Label label3 = new Label(k.get(2));
                        hBox1.getChildren().add(label3);
                        Label label12 = new Label("   ");
                        hBox1.getChildren().add(label12);
                        Label label4 = new Label("选择");
                        label4.setOnMouseClicked(event2 -> {
                            label2.setText(k.get(1));
                        });
                        hBox1.getChildren().add(label4);
                        hBox.getChildren().add(hBox1);
                    });
                }
            });
            vBox2.getChildren().add(button);
            vBox2.getChildren().add(hBox);

            Stage secondStage2 = new Stage();
            Scene secondScene2 = new Scene(vBox2, 500, 300);
            secondStage2.setScene(secondScene2);
            secondStage2.show();
        });
        vBox.getChildren().add(button2);

        Label label3 = new Label("拜访时间");
        vBox.getChildren().add(label3);
        DatePicker datePicker = new DatePicker();
        vBox.getChildren().add(datePicker);

        Label label4 = new Label("拜访内容");
        vBox.getChildren().add(label4);
        TextArea textArea = new TextArea();
        vBox.getChildren().add(textArea);

        Button button = new Button();
        button.setText("确定");
        button.setOnMouseClicked(event -> {
            if (!label2.getText().trim().isEmpty() && datePicker.getValue() != null && !textArea.getText().trim().isEmpty()) {
                if (textArea.getText().length() <= 2000) {
                    dao.update("insert into bfjl_table (uuid,khmc,gjsj,gjnr) values ('" + UUID.randomUUID() + "','" + label2.getText() + "','" + datePicker.getValue().toString() + "','" + textArea.getText() + "')");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("成功");
                    alert.show();
                    secondStage.close();
                    page();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("记录不能超过2000个字");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("全部信息不能为空");
                alert.show();
            }
        });
        vBox.getChildren().add(button);

        Scene secondScene = new Scene(vBox, 700, 500);
        secondStage.setScene(secondScene);
        secondStage.show();
    }

    @FXML
    public void page() {
        query(0);
    }

    @FXML
    private void pageUp() {
        p = p - (p2 + 1);
        if (p < 0)
            p = 0;
        query(p);
    }

    @FXML
    private void pageDow() {
        p = p + p2;
        query(p);
    }

    private void query(int p) {
        tables.getChildren().clear();
        ObservableList<Node> children = tables.getChildren();

        HBox box = new HBox();
        box.setPrefWidth(Region.USE_COMPUTED_SIZE);
        box.setPrefHeight(30);

        Label label = new Label();
        label.setPrefWidth(30);
        label.setPrefHeight(30);
        label.setText("序号");
        label.setAlignment(Pos.CENTER);
        box.getChildren().add(label);

        Label labe2 = new Label();
        labe2.setPrefWidth(50);
        labe2.setPrefHeight(30);
        labe2.setText("客户名称");
        labe2.setAlignment(Pos.CENTER);
        box.getChildren().add(labe2);

        Label labe3 = new Label();
        labe3.setPrefWidth(100);
        labe3.setPrefHeight(30);
        labe3.setText("沟通时间");
        labe3.setAlignment(Pos.CENTER);
        box.getChildren().add(labe3);

        Label labe4 = new Label();
        labe4.setPrefWidth(200);
        labe4.setPrefHeight(30);
        labe4.setText("沟通内容");
        labe4.setAlignment(Pos.CENTER);
        box.getChildren().add(labe4);

        children.add(box);

        String s = "";
        if (!khmc.getText().trim().isEmpty()) {
            s += " and khmc like '%" + khmc.getText() + "%' ";
        }
        if (kssj.getValue() != null) {
            s += " and gjsj >= '" + kssj.getValue().toString() + "' ";
        }
        if (jssj.getValue() != null) {
            s += " and gjsj <= '" + jssj.getValue().toString() + "' ";
        }
        if (!gjz.getText().trim().isEmpty()) {
            s += " and gjnr like '%" + gjz.getText() + "%' ";
        }
        List<Map<Integer, String>> list1 = dao.query("select uuid,khmc,gjsj,gjnr from bfjl_table where 1 = 1 " + s + " order by gjsj desc limit " + p + "," + p2, 4);

        data(children, list1);
    }

    private void data(ObservableList<Node> children, List<Map<Integer, String>> list1) {
        if (list1 != null && list1.size() > 0) {
            for (int i = 0; i < list1.size(); i++) {
                HBox box = new HBox();
                box.setPrefWidth(Region.USE_COMPUTED_SIZE);
                box.setPrefHeight(30);
                if (i % 2 == 0) {
                    box.setStyle("-fx-background-color: #0041e3;");
                } else {
                    box.setStyle("-fx-background-color: #285ee4;");
                }

                Label label = new Label();
                label.setPrefWidth(30);
                label.setPrefHeight(30);
                label.setText((i + p) + "");
                label.setAlignment(Pos.CENTER);
                box.getChildren().add(label);

                Label labe2 = new Label();
                labe2.setPrefWidth(50);
                labe2.setPrefHeight(30);
                labe2.setText(list1.get(i).get(2));
                labe2.setAlignment(Pos.CENTER);
                box.getChildren().add(labe2);

                Label labe31 = new Label();
                labe31.setPrefWidth(100);
                labe31.setPrefHeight(30);
                labe31.setText(list1.get(i).get(3));
                labe31.setAlignment(Pos.CENTER);
                box.getChildren().add(labe31);

                Label labe4 = new Label();
                labe4.setPrefHeight(30);
                labe4.setMinWidth(1000);
                labe4.setText(list1.get(i).get(4));
                labe4.setAlignment(Pos.CENTER);
                box.getChildren().add(labe4);

                Label labe5 = new Label();
                labe5.setPrefHeight(30);
                labe5.setText("删除");
                labe5.setAlignment(Pos.CENTER);
                final int j = i;
                labe5.setOnMouseClicked(event -> {
                    boolean b = new AlertUtils().f_alert_confirmDialog("警告", "确定要删除？");
                    if (b)
                        delete(list1.get(j).get(1));
                });
                box.getChildren().add(labe5);

                children.add(box);
            }
        }
    }

    private void delete(String id) {
        dao.update("delete from bfjl_table where uuid = '" + id + "'");
        page();
    }
}
