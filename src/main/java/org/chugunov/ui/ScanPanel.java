package org.chugunov.ui;

import com.sun.tools.hat.internal.parser.PositionDataInputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.chugunov.ui.components.*;

public class ScanPanel extends GridPane {
    public TitledPane groupPane(String title, Node additional, Field... components) {
        GridPane content = new GridPane();
        for (int i = 0; i < components.length; i++){
            Field f = components[i];
            content.add(f.getTitle(), 0, i, 1, 1);
            content.add(f.getValue(), 1, i, 1, 1);
        }
        if(additional != null)
            content.add(additional, 0, components.length, 2, 1);
        content.setHgap(10);
        content.setVgap(10);
        content.setPadding(new Insets(10, 10, 10, 10));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        content.getColumnConstraints().addAll(col1, col2);

        TitledPane pane = new TitledPane(title, content);
        pane.setCollapsible(false);

        return pane;
    }

    public ScanPanel(Stage primaryStage) {
        String stylesheet = getClass().getResource("/scan-styles.css").toExternalForm();
        this.getStylesheets().add(stylesheet);

        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10, 10, 10, 10));

        Field typeField = new ListField("Type", new String[]{"Novel", "Comic"});
        Field urlField = new TextField("URL", "https://parahumans.wordpress.com/");
        Field authorField = new TextField("Author", "John McCrae");
        Field titleField = new TextField("Title", "Worm");
        Field pathField = new PathField(primaryStage, "Path");

        Field startField = new TextField("Start Page", "https://parahumans.wordpress.com/2011/06/11/1-1/");
        Field selectorContentField = new TextField("Selector Content", "#content .entry-content p:not(p:contains(Next Chapter))");
        Field selectorTitleField = new TextField("Selector Title", "#content .entry-title");
        Field selectorNextField = new TextField("Selector Next", "#content .nav-next a[href]");

        Field numberOfRowsField = new NumericField("Rows On Page", 38);
        Field debugField = new BooleanField("Debug", false);

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
                String url = ((TextField)urlField).getTextValue();
                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle(url);
                newWindow.setWidth(800);
                newWindow.setHeight(600);
                newWindow.setScene(WebPanel.scene(url));

                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() + 200);
                newWindow.setY(primaryStage.getY() + 100);

                newWindow.show();
            }
        });

        wrapper2.getChildren().add(btn2);
        wrapper2.setAlignment(Pos.CENTER_RIGHT);

        Node basicPane = groupPane("Basic", null, typeField, urlField, authorField, titleField, pathField);
        Node contentPane = groupPane("Content", wrapper2, startField, selectorContentField, selectorTitleField, selectorNextField);
        Node otherPane = groupPane("Other", null, numberOfRowsField, debugField);

        this.add(basicPane, 0, 0, 1, 1);
        this.add(otherPane, 1, 0, 1, 1);
        this.add(contentPane, 0, 1, 2, 1);

        wrapper.getChildren().add(btn);
        wrapper.setAlignment(Pos.CENTER_RIGHT);
        this.add(wrapper, 1, 2, 1, 1);
    }
}
