package com.fxcrm.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;

import java.io.IOException;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class WyyyyController {

    @FXML
    private TextField zjdz;

    @FXML
    private WebView webs;

    public void init() {
        zjdz.setText("https://music.163.com/#/artist/album?id=66346");
    }

    @FXML
    private void tz() {
        if (!zjdz.getText().trim().isEmpty()) {
            webs.getEngine().load(zjdz.getText());
        }
    }

    @FXML
    private void ksjx() throws IOException {
        WebEngine engine = webs.getEngine();
        String o = (String) engine.executeScript("document.documentElement.outerHTML");
        System.out.println(o);
    }
}
