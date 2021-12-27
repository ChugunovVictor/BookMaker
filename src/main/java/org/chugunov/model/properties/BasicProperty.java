package org.chugunov.model.properties;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.chugunov.model.Basic;
import org.chugunov.model.Type;

public class BasicProperty extends Property{

  private Basic value;

  private TextField siteField, authorField, titleField, outputPathField;
  private ComboBox<Type> typeField;

  public BasicProperty(Basic value) {
    this.value = value;
  }

  public BasicProperty site(TextField value) {
    this.siteField = value;
    biDirectionalBindText(this.siteField, this.value.getSiteProperty(), this.value::setSite_);
    return this;
  }

  public BasicProperty author(TextField value) {
    this.authorField = value;
    biDirectionalBindText(this.authorField, this.value.getAuthorProperty(), this.value::setAuthor_);
    return this;
  }

  public BasicProperty title(TextField value) {
    this.titleField = value;
    biDirectionalBindText(this.titleField, this.value.getTitleProperty(), this.value::setTitle_);
    return this;
  }

  public BasicProperty outputPath(TextField value) {
    this.outputPathField = value;
    biDirectionalBindText(this.outputPathField, this.value.getOutputPathProperty(), this.value::setOutputPath_);
    return this;
  }

  public BasicProperty type(ComboBox<Type> value) {
    this.typeField = value;
    biDirectionalBindList(this.typeField, this.value.getTypeProperty(), this.value::setType_);
    return this;
  }

  Basic getValue() {
    return this.value;
  }

  void setValue(Basic value) {
    this.value = value;
  }
}
