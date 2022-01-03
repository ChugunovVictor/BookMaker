package org.chugunov.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Basic {

  @JsonRawValue private StringProperty site = new SimpleStringProperty();
  @JsonRawValue private StringProperty author = new SimpleStringProperty();
  @JsonRawValue private StringProperty title = new SimpleStringProperty();
  @JsonRawValue private StringProperty outputPath = new SimpleStringProperty();
  @JsonRawValue private StringProperty login = new SimpleStringProperty();
  @JsonRawValue private StringProperty password = new SimpleStringProperty();

  public Basic(){}

  public String getSite() {return site.getValue();}
  public String getAuthor() {return author.getValue();}
  public String getTitle() {return title.getValue();}
  public String getOutputPath() {return outputPath.getValue();}
  public String getLogin() {return login.getValue();}
  public String getPassword() {return password.getValue();}

  public void setSite(String site) {this.site.setValue(site);}
  public void setAuthor(String author) {this.author.setValue(author);}
  public void setTitle(String title) {this.title.setValue(title);}
  public void setOutputPath(String outputPath) {this.outputPath.setValue(outputPath);}
  public void setLogin(String login) {this.login.setValue(login);}
  public void setPassword(String password) {this.password.setValue(password);}

  public void copy(Basic newValue){
    setSite(newValue.getSite());
    setAuthor(newValue.getAuthor());
    setTitle(newValue.getTitle());
    setOutputPath(newValue.getOutputPath());
    setLogin(newValue.getLogin());
    setPassword(newValue.getPassword());
  }
}
