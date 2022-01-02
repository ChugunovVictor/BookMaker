package org.chugunov.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Basic {

  private SimpleStringProperty site_ = new SimpleStringProperty();
  private SimpleStringProperty author_ = new SimpleStringProperty();
  private SimpleStringProperty title_ = new SimpleStringProperty();
  private SimpleStringProperty outputPath_ = new SimpleStringProperty();
  private SimpleStringProperty login_ = new SimpleStringProperty();
  private SimpleStringProperty password_ = new SimpleStringProperty();

  public Basic(){}

  public SimpleStringProperty getSiteProperty() { return site_; }
  public SimpleStringProperty getAuthorProperty() { return author_; }
  public SimpleStringProperty getTitleProperty() { return title_; }
  public SimpleStringProperty getOutputPathProperty() { return outputPath_; }
  public SimpleStringProperty getLoginProperty() { return login_; }
  public SimpleStringProperty getPasswordProperty() { return password_; }

  public String getSite() { return site_.getValue(); }
  public String getAuthor() { return author_.getValue(); }
  public String getTitle() { return title_.getValue(); }
  public String getOutputPath() { return outputPath_.getValue(); }
  public String getLogin() { return login_.getValue(); }
  public String getPassword() { return password_.getValue(); }

  public void setSite_(String value) { this.site_.setValue(value); }
  public void setAuthor_(String value) { this.author_.setValue(value); }
  public void setTitle_(String value) { this.title_.setValue(value); }
  public void setOutputPath_(String value) { this.outputPath_.setValue(value); }
  public void setLogin_(String value) { this.login_.setValue(value); }
  public void setPassword_(String value) { this.password_.setValue(value); }

  public void copy(Basic newValue){
    this.setAuthor_(newValue.getAuthor());
    this.setTitle_(newValue.getTitle());
    this.setSite_(newValue.getSite());
    this.setOutputPath_(newValue.getOutputPath());
    this.setLogin_(newValue.getLogin());
    this.setPassword_(newValue.getPassword());
  }
}
