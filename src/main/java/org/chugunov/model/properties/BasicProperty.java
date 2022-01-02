package org.chugunov.model.properties;

import javafx.scene.control.TextField;
import org.chugunov.model.Basic;

public class BasicProperty extends Property{

  private Basic value;

  private TextField siteField, authorField, titleField, outputPathField, loginField, passwordPathField;

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

  public BasicProperty login(TextField value) {
    this.loginField = value;
    biDirectionalBindText(this.loginField, this.value.getLoginProperty(), this.value::setLogin_);
    return this;
  }

  public BasicProperty password(TextField value) {
    this.passwordPathField = value;
    biDirectionalBindText(this.passwordPathField, this.value.getPasswordProperty(), this.value::setPassword_);
    return this;
  }

  Basic getValue() {
    return this.value;
  }

  void setValue(Basic value) {
    this.value = value;
  }
}
