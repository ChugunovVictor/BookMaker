package org.chugunov.ui.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.chugunov.model.Basic;
import org.chugunov.model.Process;
import org.chugunov.ui.BiDirectional;
import org.chugunov.ui.BiDirectionalController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BasicController extends BiDirectionalController implements Initializable {
  private javafx.stage.DirectoryChooser path = new javafx.stage.DirectoryChooser();

  private Process process;

  @BiDirectional
  private Basic basic;

  @FXML private ComboBox<Process> examplesField;
  @FXML private TextField siteField, authorField, titleField, outputPathField, loginField, passwordField;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      URL resource = BasicController.class.getClassLoader().getResource("examples.json");
      Process[] examples = objectMapper.readValue(new File(resource.toURI()), Process[].class);

      examplesField.setItems(FXCollections.observableArrayList(examples));

      examplesField.valueProperty().addListener((observable, oldValue, newValue) -> {
        this.process.copy(newValue);
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void init(Process target) {
    this.process = target;
    this.basic = this.process.getBasic();
    super.init();
  }

  public void chooseDirectory() {
    File dir = path.showDialog((Stage) examplesField.getScene().getWindow());
    this.process.getBasic().setOutputPath(dir != null ? dir.getAbsolutePath() : "");
  }
}
