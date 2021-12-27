package org.chugunov.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Basic {

  private SimpleStringProperty site_ = new SimpleStringProperty();
  private SimpleStringProperty author_ = new SimpleStringProperty();
  private SimpleStringProperty title_ = new SimpleStringProperty();
  private SimpleStringProperty outputPath_ = new SimpleStringProperty();
  private SimpleObjectProperty<Type> type_ = new SimpleObjectProperty(Type.NOVEL);

  public Basic(){}

  public SimpleStringProperty getSiteProperty() { return site_; }
  public SimpleStringProperty getAuthorProperty() { return author_; }
  public SimpleStringProperty getTitleProperty() { return title_; }
  public SimpleStringProperty getOutputPathProperty() { return outputPath_; }
  public SimpleObjectProperty getTypeProperty() { return type_; }

  public String getSite() { return site_.getValue(); }
  public String getAuthor() { return author_.getValue(); }
  public String getTitle() { return title_.getValue(); }
  public Type getType() { return type_.getValue(); }
  public String getOutputPath() { return outputPath_.getValue(); }

  public Basic site(String value) { this.site_.setValue(value); return this; }
  public Basic author(String value) { this.author_.setValue(value); return this; }
  public Basic title(String value) { this.title_.setValue(value); return this; }
  public Basic type(Type value) { this.type_.setValue(value); return this; }
  public Basic outputPath(String value) { this.outputPath_.setValue(value); return this; }

  public void setSite_(String value) { this.site_.setValue(value); }
  public void setAuthor_(String value) { this.author_.setValue(value); }
  public void setTitle_(String value) { this.title_.setValue(value); }
  public void setType_(Type value) { this.type_.setValue(value); }
  public void setOutputPath_(String value) { this.outputPath_.setValue(value); }

  public void copy(Basic newValue){
    this.setAuthor_(newValue.getAuthor());
    this.setTitle_(newValue.getTitle());
    this.setType_(newValue.getType());
    this.setSite_(newValue.getSite());
    this.setOutputPath_(newValue.getOutputPath());
  }

  @Override
  public String toString() {
    return title_.getValue() + " (" + site_.getValue() + ")";
  }

  public String toExtendedString() {
    return "Process{" +
        "site_=" + site_.getValue() +
        ", author_='" + author_.getValue() + '\'' +
        ", title_='" + title_.getValue() + '\'' +
        ", type_=" + type_.getValue() +
        ", outputPath_=" + outputPath_.getValue() +
        '}';
  }
}
