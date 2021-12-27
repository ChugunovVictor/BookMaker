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

// bidirectional bindings in fxml?
// automatic observables
// it is strange to do it by hands in end 2021
// rx, react/angular, even in android this problem was solved

public class ContentObservable {

  private Content value;

  private TextField addressToStartField, selectorContentField, selectorTitleField, selectorNavigationNextField;

  public ContentObservable(Content value) {
    this.value = value;
  }

  public ContentObservable addressToStart(TextField value) {
    this.addressToStartField = value;
    biDirectionalBindText(this.addressToStartField, this.value.getAddressToStartProperty(), this.value::setAddressToStart_);
    return this;
  }

  public ContentObservable selectorContent(TextField value) {
    this.selectorContentField = value;
    biDirectionalBindText(this.selectorContentField, this.value.getSelectorContentProperty(), this.value::setSelectorContent_);
    return this;
  }

  public ContentObservable selectorTitle(TextField value) {
    this.selectorTitleField = value;
    biDirectionalBindText(this.selectorTitleField, this.value.getSelectorTitleProperty(), this.value::setSelectorTitle_);
    return this;
  }

  public ContentObservable selectorNavigationNext(TextField value) {
    this.selectorNavigationNextField = value;
    biDirectionalBindText(this.selectorNavigationNextField, this.value.getSelectorNavigationNextProperty(), this.value::setSelectorNavigationNext_);
    return this;
  }

  private void biDirectionalBindText(TextField ui, StringProperty object, Consumer<String> method) {
    ui.textProperty().addListener((observable, oldValue, newValue) -> {
      method.accept(newValue);
    });
    object.addListener((observable, oldValue, newValue) -> {
      ui.setText(newValue);
    });
  }

  Content getValue() {
    return this.value;
  }

  void setValue(Content value) {
    this.value = value;
  }
}
