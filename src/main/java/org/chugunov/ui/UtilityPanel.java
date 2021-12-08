package org.chugunov.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

public class UtilityPanel extends GridPane {
    public UtilityPanel() {
        this.add(pageNumbersPane(), 0, 0, 1, 1);
        this.add(prepareToPrintPane(), 1, 0, 1, 1);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10, 10, 10, 10));
    }

    public Node pageNumbersPane() {
        String title = "Add Page Numbers";
        Node content = new Label(title);
        TitledPane pane = new TitledPane(title, content);
        pane.setCollapsible(false);
        return pane;
    }

    public Node prepareToPrintPane() {
        String title = "Prepare To Print";
        Node content = new Label(title);
        TitledPane pane = new TitledPane(title, content);
        pane.setCollapsible(false);
        return pane;
    }
}
