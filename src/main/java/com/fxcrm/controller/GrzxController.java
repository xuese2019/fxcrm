package com.fxcrm.controller;

import com.fxcrm.db.dao.AccountDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * @author: LD
 * @date:
 * @description:
 */
public class GrzxController {

    private AccountDao dao = new AccountDao();

    @FXML
    private TextField password;
    @FXML
    private TextField userName;
    @FXML
    private Label pwderror;
    @FXML
    private Label nameerror;

    @FXML
    private void updatePWD() {
        dao.update("update account_table set password = " + password.getText());
        pwderror.setText("修改成功");
    }

    @FXML
    private void updateName() {
        dao.update("update account_table set name = " + userName.getText());
        nameerror.setText("修改成功");
    }
}
