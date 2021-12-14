package org.chugunov.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.chugunov.ui.ClickListener;
import org.chugunov.ui.Controller;

public class HelpController implements Controller {
  @FXML
  private WebView browser;
  @FXML
  private FlowPane treePane;
  @FXML
  private TextField addressToStartField, selectorContentField, selectorTitleField, selectorNavigationNextField;

  public void init(String site) {
    WebEngine webEngine = browser.getEngine();
    webEngine.load(site);
    browser.getEngine().getLoadWorker().stateProperty().addListener(new ClickListener(browser, treePane));
  }
}