package com.fxcrm.controller;

import com.fxcrm.db.dao.Daos;
import com.fxcrm.utils.AlertUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * @author: LD
 * @date:
 * @description:
 */
public class KhglController {

    private Daos dao = new Daos();

    private static int p = 0;
    private static int p2 = 20;

    @FXML
    private TextField name;
    @FXML
    private TextField dh;
    @FXML
    private TextField dz;
    @FXML
    private Label error;
    @FXML
    private VBox tables;

    @FXML
    private void add() {
        error.setText("");
        String text = name.getText();
        String text1 = dh.getText();
        String text2 = dz.getText();
        if (text.trim().isEmpty() || text1.trim().isEmpty() || text2.trim().isEmpty()) {
            error.setText("名称，电话，地址都不能为空");
        } else {
            List<Map<Integer, String>> list = dao.query("select * from kh_table where name = '" + text + "'", 4);
            if (list != null && list.size() > 0) {
                error.setText("客户名称重复");
            } else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format1 = format.format(System.currentTimeMillis());
                dao.update("insert into kh_table (name,dh,dz,time) values ('" + text + "','" + text1 + "','" + text2 + "','" + format1 + "')");
                error.setText("保存成功");
                name.setText("");
                dh.setText("");
                dz.setText("");
                query(0);
            }
        }
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
        labe2.setPrefWidth(200);
        labe2.setPrefHeight(30);
        labe2.setText("名称");
        labe2.setAlignment(Pos.CENTER);
        box.getChildren().add(labe2);

        Label labe3 = new Label();
        labe3.setPrefWidth(100);
        labe3.setPrefHeight(30);
        labe3.setText("电话");
        labe3.setAlignment(Pos.CENTER);
        box.getChildren().add(labe3);

        Label labe4 = new Label();
        labe4.setPrefWidth(200);
        labe4.setPrefHeight(30);
        labe4.setText("地址");
        labe4.setAlignment(Pos.CENTER);
        box.getChildren().add(labe4);

        Label labe5 = new Label();
        labe5.setPrefWidth(200);
        labe5.setPrefHeight(30);
        labe5.setText("时间");
        labe5.setAlignment(Pos.CENTER);
        box.getChildren().add(labe5);

        Label labe6 = new Label();
        labe6.setPrefWidth(100);
        labe6.setPrefHeight(30);
        labe6.setText("操作");
        labe6.setAlignment(Pos.CENTER);
        box.getChildren().add(labe6);

        children.add(box);

        String s = "";
        if (!name.getText().trim().isEmpty()) {
            s += " and name like '%" + name.getText() + "%' ";
        }
        if (!dh.getText().trim().isEmpty()) {
            s += " and dh like '%" + dh.getText() + "%' ";
        }
        if (!dz.getText().trim().isEmpty()) {
            s += " and dz like '%" + dz.getText() + "%' ";
        }
        List<Map<Integer, String>> list1 = dao.query("select * from kh_table where 1 = 1 " + s + " order by time desc limit " + p + "," + p2, 4);

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
                labe2.setPrefWidth(200);
                labe2.setPrefHeight(30);
                labe2.setText(list1.get(i).get(1));
                labe2.setAlignment(Pos.CENTER);
                box.getChildren().add(labe2);

                Label labe3 = new Label();
                labe3.setPrefWidth(100);
                labe3.setPrefHeight(30);
                labe3.setText(list1.get(i).get(2));
                labe3.setAlignment(Pos.CENTER);
                box.getChildren().add(labe3);

                Label labe4 = new Label();
                labe4.setPrefWidth(200);
                labe4.setPrefHeight(30);
                labe4.setText(list1.get(i).get(3));
                labe4.setAlignment(Pos.CENTER);
                box.getChildren().add(labe4);

                Label labe5 = new Label();
                labe5.setPrefWidth(200);
                labe5.setPrefHeight(30);
                labe5.setText(list1.get(i).get(4));
                labe5.setAlignment(Pos.CENTER);
                box.getChildren().add(labe5);

                Label labe6 = new Label();
                labe6.setPrefWidth(100);
                labe6.setPrefHeight(30);
                labe6.setText("删除");
                labe6.setAlignment(Pos.CENTER);
                String s = list1.get(i).get(1);
                labe6.setOnMouseClicked(event -> {
                    boolean b = new AlertUtils().f_alert_confirmDialog("警告", "确定要删除？");
                    if (b)
                        delete(s);
                });
                box.getChildren().add(labe6);

                Label labe7 = new Label();
                labe7.setPrefWidth(100);
                labe7.setPrefHeight(30);
                labe7.setText("修改");
                labe7.setAlignment(Pos.CENTER);
                labe7.setOnMouseClicked(event -> {
                    update(s);
                });
                box.getChildren().add(labe7);

                children.add(box);

            }
        }
    }

    private void delete(String name) {
        dao.update("delete from kh_table where name = '" + name + "'");
        query(0);
    }

    private void update(String name) {
        // 创建新的stage
        Stage secondStage = new Stage();
        VBox vBox = new VBox();

        Label label = new Label("请输入修改后的地址");
        vBox.getChildren().add(label);
        TextField textField = new TextField();
        vBox.getChildren().add(textField);
        Button button = new Button("确定");
        button.setOnMouseClicked(event -> {
            if (!textField.getText().trim().isEmpty()) {
                dao.update("update kh_table set dz = '" + textField.getText() + "' where name = '" + name + "'");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("成功");
                alert.show();
                query(0);
            }
        });
        vBox.getChildren().add(button);

        Label labe2 = new Label("请输入修改后的电话");
        vBox.getChildren().add(labe2);
        TextField textField2 = new TextField();
        vBox.getChildren().add(textField2);
        Button button2 = new Button("确定");
        button2.setOnMouseClicked(event -> {
            if (!textField2.getText().trim().isEmpty()) {
                dao.update("update kh_table set dh = '" + textField2.getText() + "' where name = '" + name + "'");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("成功");
                alert.show();
                query(0);
            }
        });
        vBox.getChildren().add(button2);

        Scene secondScene = new Scene(vBox, 300, 200);
        secondStage.setScene(secondScene);
        secondStage.show();
    }
}
