package org.chugunov.ui.controllers;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.chugunov.ui.Controller;

public class MainController {

  @FXML
  private TabPane tabPane;

  @FXML
  private Tab aboutTab;
  @FXML
  private AboutTabController aboutTabPageController;

  @FXML
  private Tab processTab;
  @FXML
  private ProcessTabController processTabPageController;

  @FXML
  private Tab utilityTab;
  @FXML
  private UtilityTabController utilityTabPageController;

  public void init() {
    tabPane.getSelectionModel().selectedItemProperty().addListener(
        (ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
          if (newValue == aboutTab) {
            System.out.println("About Tab page");
          } else if (newValue == processTab) {
            System.out.println("Process Tab page");
          } else if (newValue == utilityTab) {
            System.out.println("Utility Tab page");
          }
        });
  }

  public Controller getController(String type) {
    switch (type) {
      case "Process":
        return processTabPageController;
      case "Utility":
        return utilityTabPageController;
      case "About":
      default:
        return aboutTabPageController;
    }
  }
}