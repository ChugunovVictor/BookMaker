package org.chugunov.ui.components;

import javafx.scene.control.SpinnerValueFactory;

public class NumericField extends Field{
    private javafx.scene.control.Spinner<Integer> field;

    public NumericField(String title) {
        super(title);
        this.field = new javafx.scene.control.Spinner();
        this.field.setEditable(true);
        this.field.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        super.addChild(field);
    }

    public NumericField(String title, int value) {
        this(title);
        this.field.getValueFactory().setValue(value);
    }
}
