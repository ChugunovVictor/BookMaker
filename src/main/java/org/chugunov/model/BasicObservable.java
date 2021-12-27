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

public class BasicObservable {

  private Basic value;

  private TextField siteField, authorField, titleField, outputPathField;
  private ComboBox<Type> typeField;

  public BasicObservable(Basic value) {
    this.value = value;
  }

  public BasicObservable site(TextField value) {
    this.siteField = value;
    biDirectionalBindText(this.siteField, this.value.getSiteProperty(), this.value::setSite_);
    return this;
  }

  public BasicObservable author(TextField value) {
    this.authorField = value;
    biDirectionalBindText(this.authorField, this.value.getAuthorProperty(), this.value::setAuthor_);
    return this;
  }

  public BasicObservable title(TextField value) {
    this.titleField = value;
    biDirectionalBindText(this.titleField, this.value.getTitleProperty(), this.value::setTitle_);
    return this;
  }

  public BasicObservable outputPath(TextField value) {
    this.outputPathField = value;
    biDirectionalBindText(this.outputPathField, this.value.getOutputPathProperty(), this.value::setOutputPath_);
    return this;
  }

  public BasicObservable type(ComboBox<Type> value) {
    this.typeField = value;
    biDirectionalBindList(this.typeField, this.value.getTypeProperty(), this.value::setType_);
    return this;
  }

  private <T> void biDirectionalBindList(ComboBox<T> ui, ObjectProperty object, Consumer<T> method) {
    ui.valueProperty().addListener((observable, oldValue, newValue) -> {
      method.accept(newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.setValue((T) newValue);
    });
  }

  private void biDirectionalBindText(TextField ui, StringProperty object, Consumer<String> method) {
    ui.textProperty().addListener((observable, oldValue, newValue) -> {
      method.accept(newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.setText(newValue);
    });
  }

  Basic getValue() {
    return this.value;
  }

  void setValue(Basic value) {
    this.value = value;
  }
}
