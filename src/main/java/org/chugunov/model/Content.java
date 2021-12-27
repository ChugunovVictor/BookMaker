package org.chugunov.model;

import javafx.beans.property.SimpleStringProperty;

public class Content {
  public Content(){}

  private SimpleStringProperty addressToStart_ = new SimpleStringProperty();
  private SimpleStringProperty selectorContent_ = new SimpleStringProperty();
  private SimpleStringProperty selectorTitle_ = new SimpleStringProperty();
  private SimpleStringProperty selectorNavigationNext_ = new SimpleStringProperty();

  public SimpleStringProperty getAddressToStartProperty() { return addressToStart_; }
  public SimpleStringProperty getSelectorContentProperty() { return selectorContent_; }
  public SimpleStringProperty getSelectorTitleProperty() { return selectorTitle_; }
  public SimpleStringProperty getSelectorNavigationNextProperty() { return selectorNavigationNext_; }

  public String getAddressToStart() { return addressToStart_.getValue(); }
  public String getSelectorContent() { return selectorContent_.getValue(); }
  public String getSelectorTitle() { return selectorTitle_.getValue(); }
  public String getSelectorNavigationNext() { return selectorNavigationNext_.getValue(); }

  public Content addressToStart(String value) { this.addressToStart_.setValue(value); return this; }
  public Content selectorContent(String value) { this.selectorContent_.setValue(value); return this; }
  public Content selectorTitle(String value) { this.selectorTitle_.setValue(value); return this; }
  public Content selectorNavigationNext(String value) { this.selectorNavigationNext_.setValue(value); return this; }

  public void setAddressToStart_(String value) { this.addressToStart_.setValue(value); }
  public void setSelectorContent_(String value) { this.selectorContent_.setValue(value); }
  public void setSelectorTitle_(String value) { this.selectorTitle_.setValue(value); }
  public void setSelectorNavigationNext_(String value) { this.selectorNavigationNext_.setValue(value); }

  public void copy(Content newValue){
    this.setSelectorContent_(newValue.getSelectorContent());
    this.setSelectorTitle_(newValue.getSelectorTitle());
    this.setSelectorNavigationNext_(newValue.getSelectorNavigationNext());
    this.setAddressToStart_(newValue.getAddressToStart());
  }
}
