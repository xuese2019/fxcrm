package com.fxcrm.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fxcrm.feign.FeignRequestUrl;
import com.fxcrm.feign.FeignUtil;
import com.fxcrm.feign.account.AccountInterface;
import com.fxcrm.utils.AccountUtils;
import com.fxcrm.utils.ResponseResult;
import com.fxcrm.utils.exception.MyExceptionUtils;
import com.fxcrm.utils.jackjsonutils.JackJsonUtils;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class SampleController {

    private AccountInterface accountInterface = FeignUtil.feign()
            .target(AccountInterface.class, new FeignRequestUrl().URL());

    @FXML
    private TextField account;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorText;
    @FXML
    private Button loginButton;

    public void init() {
        account.setText("weihuzu");
        password.setText("123456");
    }

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
        loginButton.setDisable(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                logind2();
                return null;
            }
        };
        new Thread(task).start();
    }

    /**
     * 登陆验证
     */
    private void logind2() throws Exception {
        String accountText = account.getText();
        String passwordText = password.getText();
        JackJsonUtils<HashMap<String, String>> jackJsonUtils = new JackJsonUtils<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("account", accountText);
        map.put("password", passwordText);
        String s = jackJsonUtils.beanToJackJsonString(map);
        ResponseResult<Object> result = null;
        try {
            result = accountInterface.login(new JSONPObject(s, Class.class));
        } catch (Exception e) {
            String myExceptionUtils = new MyExceptionUtils().ex(e);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(myExceptionUtils);
                    alert.show();
                    loginButton.setDisable(false);
                }
            });
        }
        if (result == null) {
            return;
        }
        boolean b = result.isSuccess();
        if (b) {
            AccountUtils.TOKEN = (String) result.getData();
            toHome();
        } else {
            String message = result.getMessage();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(message);
                    alert.show();
                    loginButton.setDisable(false);
                }
            });
        }
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
