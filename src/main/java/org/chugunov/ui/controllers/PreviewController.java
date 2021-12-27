package org.chugunov.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import org.chugunov.BookMaker;
import org.chugunov.model.properties.PreviewProperty;
import org.chugunov.model.Process;
import org.chugunov.ui.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class PreviewController implements Initializable, Controller {
  @FXML private Spinner numberOfBlocksOnPageField, debugDepthField;
  @FXML private CheckBox debugField;
  @FXML private Label debugDepthLabel;

  private Process target;
  private PreviewProperty previewProperty;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    debugField.selectedProperty().addListener((observable, oldValue, newValue) -> {
      debugDepthLabel.setVisible(newValue);
      debugDepthField.setVisible(newValue);
    });

    debugDepthLabel.setVisible(false);
    debugDepthField.setVisible(false);
  }

  public void init(Process target){
    this.target = target;
    this.previewProperty = new PreviewProperty(this.target.getPreview());
    this.previewProperty = this.previewProperty
        .debug(debugField)
        .debugDepth(debugDepthField)
        .numberOfBlocksOnPage(numberOfBlocksOnPageField);
  }

  public void proceed() {
    // todo status
    new BookMaker(target);
  }
}