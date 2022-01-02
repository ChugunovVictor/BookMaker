package org.chugunov.ui.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.chugunov.model.Basic;
import org.chugunov.model.Basic2;
import org.chugunov.model.properties.BasicProperty;
import org.chugunov.model.Process;
import org.chugunov.ui.Controller;
import org.chugunov.utility.BiDirectional;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class BasicController extends Controller implements Initializable {
  private javafx.stage.DirectoryChooser path = new javafx.stage.DirectoryChooser();

  private Process process;

  private BasicProperty basicProperty;

  @BiDirectional
  private Basic2 basic;

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
    this.basic = new Basic2();
    this.basicProperty = new BasicProperty(this.process.getBasic());
    this.basicProperty = basicProperty.site(siteField)
        .author(authorField)
        .title(titleField)
        .outputPath(outputPathField)
        .login(loginField)
        .password(passwordField);

    try {
      this.processBinding();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void chooseDirectory() {
    File dir = path.showDialog((Stage) examplesField.getScene().getWindow());
    this.process.getBasic().setOutputPath_(dir != null ? dir.getAbsolutePath() : "");
  }
}
