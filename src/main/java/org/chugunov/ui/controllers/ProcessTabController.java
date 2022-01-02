package org.chugunov.ui.controllers;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.TitledPane;
import org.chugunov.ui.Controller;
import org.jsoup.internal.StringUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class ProcessTabController extends Controller implements Initializable {
  @FXML
  private Accordion accordion;
  @FXML
  private TitledPane preview, content, basic;

  // Names of controller variables should be <fx:id>Controller
  @FXML
  private PreviewController previewPageController;
  @FXML
  private ContentController contentPageController;
  @FXML
  private BasicController basicPageController;

  private TitledPane active;

  private org.chugunov.model.Process process = new org.chugunov.model.Process();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setResizable(false);
    alert.setTitle("Attention");
    alert.setHeaderText(null);
    alert.setContentText("Not enough data:\n" + message);
    alert.showAndWait();
  }

  public void init() {
    this.basicPageController.init(process);
    this.contentPageController.init(process);
    this.previewPageController.init(process);

    active = basic;

    accordion.expandedPaneProperty().addListener((ObservableValue<? extends TitledPane> observable, TitledPane oldPane, TitledPane newPane) -> {
      boolean expand = accordion.getPanes().stream().map(c -> c.isExpanded()).reduce((a, c) -> a || c).orElse(true);
      boolean contentCheck = newPane == content && StringUtil.isBlank(process.getBasic().getSite());
      boolean previewCheck = newPane == preview && (
          StringUtil.isBlank(process.getContent().getAddressToStart())
              || StringUtil.isBlank(process.getContent().getSelectorContent())
              || StringUtil.isBlank(process.getContent().getSelectorNavigationNext())
              || StringUtil.isBlank(process.getContent().getSelectorTitle())
      );

      if (!expand || contentCheck || previewCheck) {
        if (contentCheck) showAlert("Please fill the URL field");
        if (previewCheck) showAlert("Please fill selectors fields and start page");
        Platform.runLater(() -> {
          accordion.setExpandedPane(active);
        });
      } else {
        if (newPane != null) active = newPane;
        if (newPane == content) contentPageController.load();
      }
    });
  }
}
