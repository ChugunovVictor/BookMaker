package org.chugunov.ui.controllers;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.chugunov.BookMaker;
import org.chugunov.model.Process;
import org.chugunov.model.ProcessObservable;
import org.chugunov.model.Scan;
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

  @FXML private ComboBox<Type> typeField;
  @FXML private ComboBox<Process> examplesField;
  @FXML private TextField siteField, addressToStartField,
      authorField, titleField, selectorContentField, selectorTitleField, selectorNavigationNextField,
      outputPathField;
  @FXML private Spinner numberOfBlocksOnPageField, debugDepthField;
  @FXML private CheckBox debugField;
  @FXML private Label debugDepthLabel;

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

    debugField.selectedProperty().addListener((observable, oldValue, newValue) -> {
      debugDepthLabel.setVisible(newValue);
      debugDepthField.setVisible(newValue);
    });

    debugDepthLabel.setVisible(false);
    debugDepthField.setVisible(false);
  }

  public void init() {
    this.targetO = targetO.site(siteField)
        .addressToStart(addressToStartField)
        .author(authorField)
        .debug(debugField)
        .debugDepth(debugDepthField)
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

  public void useHelp(Scan scan) {
    target.setAddressToStart_(scan.getAddressToStart());
    target.setSelectorTitle_(scan.getSelectorTitle());
    target.setSelectorContent_(scan.getSelectorContent());
    target.setSelectorNavigationNext_(scan.getSelectorNavigateNext());
  }

  public void help() throws IOException {
    Stage stage = (Stage) typeField.getScene().getWindow();
    String site = siteField.getText();

    Scene scene = new Scene(new StackPane());

    FXMLLoader loader = new FXMLLoader(ProcessTabController.class.getClassLoader().getResource("fxml/helpPage.fxml"));
    scene.setRoot(loader.load());

    HelpController controller = loader.getController();
    controller.init(site, this::useHelp);

    Stage newWindow = new Stage();
    newWindow.setTitle(site);
    newWindow.setWidth(1200);
    newWindow.setHeight(750);
    newWindow.setScene(scene);
    newWindow.setX(stage.getX() + 100);
    newWindow.setY(stage.getY() + 100);
    newWindow.setResizable(false);

    newWindow.show();
  }

  public void proceed() {
    // todo status
    new BookMaker(target);
  }
}
