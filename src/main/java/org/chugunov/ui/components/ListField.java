package org.chugunov.ui.components;

import javafx.collections.FXCollections;

public class ListField extends Field {
    private javafx.scene.control.ComboBox<String> field;

    public ListField(String title, String... values) {
        super(title);
        this.field = new javafx.scene.control.ComboBox<>();
        this.field.setItems(FXCollections.observableArrayList(values));
        super.addChild(field);
    }
}
