package org.chugunov.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class PreviewObservable {

  private Preview value;

  private Spinner numberOfBlocksOnPageField, debugDepthField;
  private CheckBox debugField;

  public PreviewObservable(Preview value) {
    this.value = value;
  }

  public PreviewObservable numberOfBlocksOnPage(Spinner value) {
    this.numberOfBlocksOnPageField = value;
    biDirectionalBindNumber(this.numberOfBlocksOnPageField, this.value.getNumberOfBlocksOnPageProperty(), this.value::setNumberOfBlocksOnPage_);
    return this;
  }

  public PreviewObservable debug(CheckBox value) {
    this.debugField = value;
    biDirectionalBindBoolean(this.debugField, this.value.isDebugProperty(), this.value::setDebug_);
    return this;
  }

  public PreviewObservable debugDepth(Spinner value) {
    this.debugDepthField = value;
    biDirectionalBindNumber(this.debugDepthField, this.value.getDebugDepthProperty(), this.value::setDebugDepth_);
    return this;
  }

  private void biDirectionalBindNumber(Spinner ui, IntegerProperty object, Consumer<Integer> method) {
    ui.getValueFactory().setValue(object.getValue());

    ui.valueProperty().addListener((observable, oldValue, newValue) -> {
      method.accept((int) newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.getValueFactory().setValue(newValue);
    });
  }

  private void biDirectionalBindBoolean(CheckBox ui, BooleanProperty object, Consumer<Boolean> method) {
    ui.selectedProperty().addListener((observable, oldValue, newValue) -> {
      method.accept(newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.selectedProperty().setValue(newValue);
    });
  }

  Preview getValue() {
    return this.value;
  }

  void setValue(Preview value) {
    this.value = value;
  }
}
