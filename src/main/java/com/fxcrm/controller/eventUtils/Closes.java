package com.fxcrm.controller.eventUtils;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class Closes implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        Platform.exit();
    }

}
