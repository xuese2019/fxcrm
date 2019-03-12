package com.fxcrm;

import com.fxcrm.controller.SampleController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author LD
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/sample.fxml"));
        Parent root = fxmlLoader.load();
        SampleController sampleController = fxmlLoader.getController();
        sampleController.init();
        ObservableList<Node> childrenUnmodifiable = root.getChildrenUnmodifiable();
        if (!childrenUnmodifiable.isEmpty()) {
            VBox node = (VBox) childrenUnmodifiable.get(0);
            HBox node1 = (HBox) node.getChildrenUnmodifiable().get(0);
            Label node2 = (Label) node1.getChildrenUnmodifiable().get(0);
            node2.setText("客户跟进");
        }
//        primaryStage.setTitle("CRM");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        primaryStage.setScene(scene);
//        禁止窗口缩放大小
        primaryStage.setResizable(false);
//        设置窗口类型
//        StageStyle.DECORATED - 具有纯白色背景和平台装饰的舞台。
//        StageStyle.UNDECORATED - 具有纯白色背景且无装饰的舞台。
//        StageStyle.TRANSPARENT - 具有透明背景且无装饰的舞台
//        StageStyle.UTILITY - 具有纯白色背景和最小平台装饰的舞台。//慎用
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
