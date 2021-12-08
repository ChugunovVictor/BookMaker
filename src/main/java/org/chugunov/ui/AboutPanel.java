package org.chugunov.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class AboutPanel extends StackPane {
    public AboutPanel() {
        this.getChildren().add(new Label("Scan Web"));
    }
}
