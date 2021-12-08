package org.chugunov.ui.components;

public class BooleanField extends Field{
    private javafx.scene.control.CheckBox field;

    public BooleanField(String title) {
        super(title);
        this.field = new javafx.scene.control.CheckBox();
        super.addChild(field);
    }

    public BooleanField(String title, Boolean value) {
        this(title);
        this.field.setSelected(value);
    }
}
