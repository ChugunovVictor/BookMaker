package org.chugunov.ui.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.chugunov.model.Basic;
import org.chugunov.model.BasicObservable;
import org.chugunov.model.Process;
import org.chugunov.model.Type;
import org.chugunov.ui.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BasicController implements Initializable, Controller {
  private javafx.stage.DirectoryChooser path = new javafx.stage.DirectoryChooser();

  private Process process;
  private BasicObservable basicProperty;

  private ObservableList types = FXCollections.observableArrayList(Type.values());

  @FXML private ComboBox<Type> typeField;
  @FXML private ComboBox<Process> examplesField;
  @FXML private TextField siteField, authorField, titleField, outputPathField;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    typeField.setItems(types);
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      URL resource = BasicController.class.getClassLoader().getResource("examples.json");
      Process[] examples = objectMapper.readValue(new File(resource.toURI()), Process[].class);

      examplesField.setItems(FXCollections.observableArrayList(examples));

      examplesField.valueProperty().addListener((observable, oldValue, newValue) -> {
        this.process.copy(newValue);
      });
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public void init(Process target) {
    this.process = target;
    this.basicProperty = new BasicObservable(this.process.getBasic());
    this.basicProperty = basicProperty.site(siteField)
        .author(authorField)
        .title(titleField)
        .outputPath(outputPathField)
        .type(typeField);
  }

  public void chooseDirectory() {
    Stage stage = (Stage) typeField.getScene().getWindow();

    File dir = path.showDialog(stage);
    if (dir != null) {
      this.process.getBasic().setOutputPath_(dir.getAbsolutePath());
    } else {
      this.process.getBasic().setOutputPath_("");
    }
  }


}
