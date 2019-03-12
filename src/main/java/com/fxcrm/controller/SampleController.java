package com.fxcrm.controller;

import com.fxcrm.db.dao.AccountDao;
import com.fxcrm.utils.AccountUtils;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SampleController {

    private AccountDao dao = new AccountDao();

    @FXML
    private TextField account;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorText;
    @FXML
    private Button loginButton;

    /**
     * 关闭程序
     */
    @FXML
    private void closeLogin() {
        Platform.exit();
    }

    /**
     * 登陆
     */
    @FXML
    private void login() {
        errorText.setText("");
        loginButton.setVisible(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                logind2();
            }
        });
    }

    /**
     * 登陆验证
     */
    private void logind2() {
        String accountText = account.getText();
        String passwordText = password.getText();
        loginButton.setDisable(true);
//        if (accountText.equals("admin") && passwordText.equals("admin")) {
//            loginButton.setDisable(true);
////            跳转
//            toHome();
//        } else {
        boolean b = dao.login("select * from account_table where account = '" + accountText + "' and password = '" + passwordText + "'");
        if (b) {
            toHome();
        } else {
            loginButton.setDisable(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("账号或密码错误");
            alert.show();
        }
//        }
    }

    /**
     * 跳转至首页
     */
    private void toHome() {
        try {
            //        获取stage
            AccountUtils.ACC = account.getText();
            ObservableList<Stage> stages = FXRobotHelper.getStages();
            Stage stage = stages.get(0);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent root = fxmlLoader.load();
            HomeController controller = fxmlLoader.getController();
            controller.dates();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/home.css").toExternalForm());
            stage.setScene(scene);
            //            窗口最大化
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
