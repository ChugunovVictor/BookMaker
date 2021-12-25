package org.chugunov.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Optional;
import java.util.function.Consumer;

// bidirectional bindings in fxml?
// automatic observables
// it is strange to do it by hands in end 2021
// rx, react/angular, even in android this problem was solved

public class ProcessObservable {

  private Process value;

  private TextField siteField, addressToStartField,
      authorField, titleField, selectorContentField, selectorTitleField, selectorNavigationNextField,
      outputPathField;
  private ComboBox<Type> typeField;
  private Spinner numberOfBlocksOnPageField, debugDepthField;
  private CheckBox debugField;

  public ProcessObservable(Process value) {
    this.value = value;
  }

  public ProcessObservable site(TextField value) {
    this.siteField = value;
    biDirectionalBindText(this.siteField, this.value.getSiteProperty(), this.value::setSite_);
    return this;
  }

  public ProcessObservable addressToStart(TextField value) {
    this.addressToStartField = value;
    biDirectionalBindText(this.addressToStartField, this.value.getAddressToStartProperty(), this.value::setAddressToStart_);
    return this;
  }

  public ProcessObservable author(TextField value) {
    this.authorField = value;
    biDirectionalBindText(this.authorField, this.value.getAuthorProperty(), this.value::setAuthor_);
    return this;
  }

  public ProcessObservable title(TextField value) {
    this.titleField = value;
    biDirectionalBindText(this.titleField, this.value.getTitleProperty(), this.value::setTitle_);
    return this;
  }

  public ProcessObservable selectorContent(TextField value) {
    this.selectorContentField = value;
    biDirectionalBindText(this.selectorContentField, this.value.getSelectorContentProperty(), this.value::setSelectorContent_);
    return this;
  }

  public ProcessObservable selectorTitle(TextField value) {
    this.selectorTitleField = value;
    biDirectionalBindText(this.selectorTitleField, this.value.getSelectorTitleProperty(), this.value::setSelectorTitle_);
    return this;
  }

  public ProcessObservable selectorNavigationNext(TextField value) {
    this.selectorNavigationNextField = value;
    biDirectionalBindText(this.selectorNavigationNextField, this.value.getSelectorNavigationNextProperty(), this.value::setSelectorNavigationNext_);
    return this;
  }

  public ProcessObservable outputPath(TextField value) {
    this.outputPathField = value;
    biDirectionalBindText(this.outputPathField, this.value.getOutputPathProperty(), this.value::setOutputPath_);
    return this;
  }

  public ProcessObservable numberOfBlocksOnPage(Spinner value) {
    this.numberOfBlocksOnPageField = value;
    biDirectionalBindNumber(this.numberOfBlocksOnPageField, this.value.getNumberOfBlocksOnPageProperty(), this.value::setNumberOfBlocksOnPage_);
    return this;
  }

  public ProcessObservable debug(CheckBox value) {
    this.debugField = value;
    biDirectionalBindBoolean(this.debugField, this.value.isDebugProperty(), this.value::setDebug_);
    return this;
  }

  public ProcessObservable debugDepth(Spinner value) {
    this.debugDepthField = value;
    biDirectionalBindNumber(this.debugDepthField, this.value.getDebugDepthProperty(), this.value::setDebugDepth_);
    return this;
  }

  public ProcessObservable type(ComboBox<Type> value) {
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

  Process getValue() {
    return this.value;
  }

  void setValue(Process value) {
    this.value = value;
  }
}
