package org.chugunov.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import org.chugunov.BookMaker;
import org.chugunov.books.BookFX;
import org.chugunov.model.Preview;
import org.chugunov.model.Process;
import org.chugunov.ui.BiDirectional;
import org.chugunov.ui.BiDirectionalController;

import java.net.URL;
import java.util.ResourceBundle;

public class PreviewController extends BiDirectionalController implements Initializable {
  @FXML private Spinner paddingTopField, paddingRightField, paddingBottomField, paddingLeftField,
      debugDepthField, fontSizeField;
  @FXML private CheckBox debugField;
  @FXML private Label debugDepthLabel;
  @FXML private VBox previewField;

  private Process target;
  @BiDirectional private Preview preview;

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
    this.preview = this.target.getPreview();
    super.init();
  }

  public void load(){
    this.previewField.getChildren().clear();
    this.preview.setDebug(true);
    new BookFX(this.target, this.previewField).process();
  }

  public void proceed() {
    // todo status
    new BookMaker(target);
  }
}