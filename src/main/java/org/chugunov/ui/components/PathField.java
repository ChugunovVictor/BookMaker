package org.chugunov.ui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

public class PathField extends Field {
  private javafx.stage.DirectoryChooser path;

  public PathField(Stage primaryStage, String title) {
    super(title);

    javafx.scene.control.TextField text = new javafx.scene.control.TextField();
    Button button = new Button("...");
    this.path = new javafx.stage.DirectoryChooser();

    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        File dir = path.showDialog(primaryStage);
        if (dir != null) {
          text.setText(dir.getAbsolutePath());
        } else {
          text.setText(null);
        }
      }
    });

    HBox h = new HBox();
    h.getChildren().addAll(text, button);

    this.value = h;
  }

  @Override
  public Object get() {
    return this.path.getTitle();
  }
}
