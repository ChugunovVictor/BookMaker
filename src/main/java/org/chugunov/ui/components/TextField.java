package org.chugunov.ui.components;

public class TextField extends Field{
    private javafx.scene.control.TextField field;

    public TextField(String title) {
        super(title);
        this.field = new javafx.scene.control.TextField();
        super.addChild(field);
    }

    public TextField(String title, String value) {
        this(title);
        this.field.setText(value);
    }

    public String getTextValue(){
        return this.field.getText();
    }
}
