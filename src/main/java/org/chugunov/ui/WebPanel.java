package org.chugunov.ui;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebPanel {
    public static Scene scene(String url){

        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();

        webEngine.load(url);

        browser.getEngine().getLoadWorker().stateProperty().addListener(new HyperLinkRedirectListener(browser));

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(browser);

        return new Scene(secondaryLayout);
    }
}
