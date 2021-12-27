package org.chugunov.model.properties;

import javafx.scene.control.TextField;
import org.chugunov.model.Content;

public class ContentProperty extends Property{

  private Content value;

  private TextField addressToStartField, selectorContentField, selectorTitleField, selectorNavigationNextField;

  public ContentProperty(Content value) {
    this.value = value;
  }

  public ContentProperty addressToStart(TextField value) {
    this.addressToStartField = value;
    biDirectionalBindText(this.addressToStartField, this.value.getAddressToStartProperty(), this.value::setAddressToStart_);
    return this;
  }

  public ContentProperty selectorContent(TextField value) {
    this.selectorContentField = value;
    biDirectionalBindText(this.selectorContentField, this.value.getSelectorContentProperty(), this.value::setSelectorContent_);
    return this;
  }

  public ContentProperty selectorTitle(TextField value) {
    this.selectorTitleField = value;
    biDirectionalBindText(this.selectorTitleField, this.value.getSelectorTitleProperty(), this.value::setSelectorTitle_);
    return this;
  }

  public ContentProperty selectorNavigationNext(TextField value) {
    this.selectorNavigationNextField = value;
    biDirectionalBindText(this.selectorNavigationNextField, this.value.getSelectorNavigationNextProperty(), this.value::setSelectorNavigationNext_);
    return this;
  }
}
