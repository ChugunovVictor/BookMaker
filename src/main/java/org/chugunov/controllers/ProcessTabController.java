package org.chugunov.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.chugunov.model.Process;
import org.chugunov.model.ProcessObservable;
import org.chugunov.model.Type;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProcessTabController implements Initializable {
  private javafx.stage.DirectoryChooser path = new javafx.stage.DirectoryChooser();
  private Process target = new Process();
  private ObservableList types = FXCollections.observableArrayList(Type.values());
  private ProcessObservable targetO = new ProcessObservable(target);

  @FXML
  private ComboBox<Type> typeField;
  @FXML
  private TextField siteField, addressToStartField,
      authorField, titleField, selectorContentField, selectorTitleField, selectorNavigationNextField,
      outputPathField;
  @FXML
  private Spinner numberOfBlocksOnPageField;
  @FXML
  private CheckBox debugField;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    typeField.setItems(types);

    targetO = targetO.site(siteField)
        .addressToStart(addressToStartField)
        .author(authorField)
        .debug(debugField)
        .title(titleField)
        .numberOfBlocksOnPage(numberOfBlocksOnPageField)
        .outputPath(outputPathField)
        .selectorContent(selectorContentField)
        .selectorNavigationNext(selectorNavigationNextField)
        .selectorTitle(selectorTitleField)
        .type(typeField);
  }

  public void chooseDirectory() {
    Stage stage = (Stage) typeField.getScene().getWindow();

    File dir = path.showDialog(stage);
    if (dir != null) {
      target.setOutputPath(dir.getAbsolutePath());
    } else {
      target.setOutputPath("");;
    }
    System.out.println(target);
  }
}
