package com.fxcrm.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.File;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class CyrjController {

    @FXML
    private void idea() {
        useAWTDesktop2("D:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2018.2.5\\bin\\idea64.exe");
    }

    @FXML
    private void navicat() {
        useAWTDesktop2("D:\\Program Files\\Navicat Premium\\navicat.exe");
    }

    @FXML
    private void hbuilderx() {
        useAWTDesktop2("D:\\Program Files\\HBuilderX\\HBuilderX.exe");
    }

    @FXML
    private void chrome() {
        useAWTDesktop2("C:\\Users\\liudo\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
    }

    @FXML
    private void sublime() {
        useAWTDesktop2("C:\\Program Files\\Sublime Text 3\\sublime_text.exe");
    }

    @FXML
    private void javafx() {
        useAWTDesktop2("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\JavaFX Scene Builder\\JavaFX Scene Builder 2.0");
    }

    @FXML
    private void tim() {
        useAWTDesktop2("C:\\Program Files (x86)\\Tencent\\TIM\\Bin\\QQScLauncher.exe");
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
}
