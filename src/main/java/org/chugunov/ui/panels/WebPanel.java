package org.chugunov.ui.panels;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.chugunov.ui.ClickListener;
import org.chugunov.ui.components.Field;

public class WebPanel {
  public static Scene scene(String url) {
    WebView browser = new WebView();
    WebEngine webEngine = browser.getEngine();
    webEngine.load(url);

    VBox content = new VBox();
    content.getChildren().addAll(
        new Field("Start Page").toTextField().toVBox(),
        new Field("Selector Content").toTextField().toVBox(),
        new Field("Selector Title").toTextField().toVBox(),
        new Field("Selector Next").toTextField().toVBox()
    );
    content.setPadding(new Insets(10, 10, 10, 10));
    content.setPrefWidth(400);

    HBox secondaryLayout = new HBox();
    secondaryLayout.getChildren().add(browser);

    FlowPane tagsPanel = new FlowPane(Orientation.VERTICAL, 10, 10);

    VBox panel = new VBox();
    panel.getChildren().addAll(tagsPanel, content);

    secondaryLayout.getChildren().add(panel);
    browser.getEngine().getLoadWorker().stateProperty().addListener(new ClickListener(browser, tagsPanel));

    return new Scene(secondaryLayout);
  }
}
