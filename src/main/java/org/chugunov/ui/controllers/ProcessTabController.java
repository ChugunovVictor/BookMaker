package org.chugunov.ui.controllers;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.chugunov.model.Process;
import org.chugunov.model.ProcessObservable;
import org.chugunov.model.Type;
import org.chugunov.ui.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProcessTabController implements Initializable, Controller {
  private javafx.stage.DirectoryChooser path = new javafx.stage.DirectoryChooser();
  private Process target = new Process();
  private ObservableList types = FXCollections.observableArrayList(Type.values());
  private ProcessObservable targetO = new ProcessObservable(target);

  @FXML
  private ComboBox<Type> typeField;
  @FXML
  private ComboBox<Process> examplesField;
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
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      URL resource = ProcessTabController.class.getClassLoader().getResource("examples.json");
      Process[] examples = objectMapper.readValue(new File(resource.toURI()), Process[].class);

      examplesField.setItems(FXCollections.observableArrayList(examples));

      examplesField.valueProperty().addListener((observable, oldValue, newValue) -> {
        target.copy(newValue);
      });
    } catch (StreamReadException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public void init() {
    this.targetO = targetO.site(siteField)
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
      target.setOutputPath_(dir.getAbsolutePath());
    } else {
      target.setOutputPath_("");
    }
  }

  public void help() throws IOException {
    Stage stage = (Stage) typeField.getScene().getWindow();
    String site = siteField.getText();

    Scene scene = new Scene(new StackPane());

    FXMLLoader loader = new FXMLLoader(ProcessTabController.class.getClassLoader().getResource("fxml/helpPage.fxml"));
    scene.setRoot(loader.load());
    HelpController controller = loader.getController();
    controller.init(site);

    Stage newWindow = new Stage();
    newWindow.setTitle(site);
    newWindow.setWidth(1200);
    newWindow.setHeight(600);
    newWindow.setScene(scene);

    // Set position of second window, related to primary window.
    newWindow.setX(stage.getX() + 100);
    newWindow.setY(stage.getY() + 100);

    newWindow.show();
  }

  public void proceed() {
    System.out.println(target);
  }
}
