package org.chugunov.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.chugunov.ui.Controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ProcessTabController implements Initializable, Controller {
  @FXML
  private TitledPane settings, content, basic;

  // Names of controller variables should be <fx:id>Controller
  @FXML
  private PreviewController previewPageController;
  @FXML
  private ContentController contentPageController;
  @FXML
  private BasicController basicPageController;

  private org.chugunov.model.Process process = new org.chugunov.model.Process();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    settings.setExpanded(false);
    content.setExpanded(false);

    basic.setPrefHeight(1000);
    settings.setPrefHeight(10);
    content.setPrefHeight(10);

    VBox.setVgrow(basic, Priority.ALWAYS);
    VBox.setVgrow(settings, Priority.NEVER);
    VBox.setVgrow(content, Priority.NEVER);

    addExpandListener(basic, settings, content);
    addExpandListener(settings, basic, content);
    addExpandListener(content, basic, settings);
  }

  private void addExpandListener(TitledPane subject, TitledPane... rest) {
    subject.setPrefHeight(1000);
    VBox.setVgrow(subject, Priority.ALWAYS);
    subject.expandedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        Arrays.stream(rest).forEach(c -> {
          c.setExpanded(false);
          c.setPrefHeight(10);
          VBox.setVgrow(c, Priority.NEVER);
        });
      }
    });
  }

  public void init() {
    this.basicPageController.init(process);
    this.contentPageController.init(process);
    this.previewPageController.init(process);
  }
}
