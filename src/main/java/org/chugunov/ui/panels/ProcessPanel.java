package org.chugunov.ui.panels;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.chugunov.ui.components.Field;
import org.chugunov.ui.components.PathField;

import java.util.Optional;

public class ProcessPanel extends GridPane {
  public static GridPane gridPane(Optional<Node> additional, Field... components) {
    GridPane content = new GridPane();

    for (Field f : components)
      f.toGridRow(content);

    if (additional.isPresent())
      content.add(additional.get(), 0, components.length, 2, 1);
    content.setHgap(10);
    content.setVgap(10);
    content.setPadding(new Insets(10, 10, 10, 10));

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(25);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(75);
    content.getColumnConstraints().addAll(col1, col2);

    return content;
  }

  public static TitledPane groupPane(String title, Optional<Node> additional, Field... components) {
    TitledPane pane = new TitledPane(title, gridPane(additional, components));
    pane.setCollapsible(false);
    return pane;
  }

  public ProcessPanel(Stage primaryStage) {
    String stylesheet = getClass().getResource("/scan-styles.css").toExternalForm();
    this.getStylesheets().add(stylesheet);

    this.setHgap(10);
    this.setVgap(10);
    this.setPadding(new Insets(10, 10, 10, 10));

    Field typeField = new Field("Type").toComboBox(new String[]{"Novel", "Comic"});
    Field urlField = new Field("URL").toTextField("https://parahumans.wordpress.com/");
    Field authorField = new Field("Author").toTextField("John McCrae");
    Field titleField = new Field("Title").toTextField("Worm");
    Field pathField = new PathField(primaryStage, "Path");

    Field startField = new Field("Start Page").toTextField("https://parahumans.wordpress.com/2011/06/11/1-1/");
    Field selectorContentField = new Field("Selector Content").toTextField("#content .entry-content p:not(p:contains(Next Chapter))");
    Field selectorTitleField = new Field("Selector Title").toTextField("#content .entry-title");
    Field selectorNextField = new Field("Selector Next").toTextField("#content .nav-next a[href]");
    Field examplesField = new Field("Examples").toComboBox(
        new String[]{"Worm (https://parahumans.wordpress.com/)", "Two Guys and Guy (http://www.twogag.com/)"}
    );

    Field numberOfRowsField = new Field("Rows On Page").toSpinner(38);
    Field debugField = new Field("Debug").toCheckBox();

    HBox wrapper = new HBox();
    Button btn = new Button();
    btn.setText("Proceed");
    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("Proceed");
      }
    });

    HBox wrapper2 = new HBox();
    Button btn2 = new Button();
    btn2.setText("Help To Fill");
    btn2.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        String url = (String) urlField.get();
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(url);
        newWindow.setWidth(1200);
        newWindow.setHeight(600);
        newWindow.setScene(WebPanel.scene(url));

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
      }
    });


    wrapper2.getChildren().add(btn2);

    HBox.setHgrow(examplesField, Priority.NEVER);
    HBox.setHgrow(wrapper2, Priority.ALWAYS);

    Node basicPane = groupPane("Basic", Optional.empty(), typeField, urlField, authorField, titleField, pathField);
    Node contentPane = groupPane("Content", Optional.of(wrapper2), startField, selectorContentField, selectorTitleField, selectorNextField, examplesField);
    Node otherPane = groupPane("Other", Optional.empty(), numberOfRowsField, debugField);

    this.add(basicPane, 0, 0, 1, 1);
    this.add(otherPane, 1, 0, 1, 1);
    this.add(contentPane, 0, 1, 2, 1);

    wrapper.getChildren().add(btn);
    wrapper.setAlignment(Pos.CENTER_RIGHT);
    this.add(wrapper, 1, 2, 1, 1);
  }
}
