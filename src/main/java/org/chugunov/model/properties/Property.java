package org.chugunov.model.properties;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public abstract class Property {
  <T> void biDirectionalBindList(ComboBox<T> ui, ObjectProperty object, Consumer<T> method) {
    ui.valueProperty().addListener((observable, oldValue, newValue) -> {
      method.accept(newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.setValue((T) newValue);
    });
  }

  void biDirectionalBindText(TextField ui, StringProperty object, Consumer<String> method) {
    ui.textProperty().addListener((observable, oldValue, newValue) -> {
      method.accept(newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.setText(newValue);
    });
  }

  void biDirectionalBindNumber(Spinner ui, IntegerProperty object, Consumer<Integer> method) {
    ui.getValueFactory().setValue(object.getValue());

    ui.valueProperty().addListener((observable, oldValue, newValue) -> {
      method.accept((int) newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.getValueFactory().setValue(newValue);
    });
  }

  void biDirectionalBindBoolean(CheckBox ui, BooleanProperty object, Consumer<Boolean> method) {
    ui.selectedProperty().addListener((observable, oldValue, newValue) -> {
      method.accept(newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.selectedProperty().setValue(newValue);
    });
  }
}
