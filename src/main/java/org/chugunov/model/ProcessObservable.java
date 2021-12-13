package org.chugunov.model;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Optional;

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
  private Spinner numberOfBlocksOnPageField;
  private CheckBox debugField;

  //private URL site_, addressToStart_;
  //private String author_, title_, selectorContent_, selectorTitle_, selectorNavigationNext_;
  //private Type type_ = Type.NOVEL;
  //private boolean debug_ = false;
  //private int numberOfBlocksOnPage_ = 40;
  //private Optional<String> outputPath_ = Optional.empty();

  public ProcessObservable(Process value){ this.value = value; }

  public ProcessObservable site(TextField value) { this.siteField = value; return this; }
  public ProcessObservable addressToStart(TextField value) { this.addressToStartField = value;return this; }
  public ProcessObservable author(TextField value) { this.authorField = value; return this; }
  public ProcessObservable title(TextField value) { this.titleField = value; return this; }
  public ProcessObservable selectorContent(TextField value) { this.selectorContentField = value; return this; }
  public ProcessObservable selectorTitle(TextField value) { this.selectorTitleField = value; return this; }
  public ProcessObservable selectorNavigationNext(TextField value) { this.selectorNavigationNextField = value; return this; }
  public ProcessObservable type(ComboBox<Type> value) { this.typeField = value; return this; }
  public ProcessObservable debug(CheckBox value) { this.debugField = value; return this; }
  public ProcessObservable numberOfBlocksOnPage(Spinner value) { this.numberOfBlocksOnPageField = value; return this; }

  public ProcessObservable outputPath(TextField value) {
    if(value != null){
      this.outputPathField = value;
      this.outputPathField.textProperty().addListener((observable, oldValue, newValue) -> {
        this.value.setOutputPath( newValue );
      });
      this.value.getOutputPathProperty().addListener((observable, oldValue, newValue) -> {
        outputPathField.setText( newValue );
      });
    }
    return this;
  }

  Process getValue(){ return this.value; }
  void setValue(Process value){ this.value = value; }
}
