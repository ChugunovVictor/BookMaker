package org.chugunov.model;

import javafx.beans.property.SimpleStringProperty;

public class Basic2 {

  private String site;
  private String author;
  private String title;
  private String outputPath;
  private String login;
  private String password;

  public Basic2(){}

  public String getSite() {return site;}
  public String getAuthor() {return author;}
  public String getTitle() {return title;}
  public String getOutputPath() {return outputPath;}
  public String getLogin() {return login;}
  public String getPassword() {return password;}

  public void setSite(String site) {this.site = site;}
  public void setAuthor(String author) {this.author = author;}
  public void setTitle(String title) {this.title = title;}
  public void setOutputPath(String outputPath) {this.outputPath = outputPath;}
  public void setLogin(String login) {this.login = login;}
  public void setPassword(String password) {this.password = password;}
}
