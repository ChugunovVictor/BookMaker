package org.chugunov.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import javafx.beans.property.SimpleStringProperty;

public class Content {
  public Content(){}

  @JsonRawValue private SimpleStringProperty addressToStart = new SimpleStringProperty();
  @JsonRawValue private SimpleStringProperty selectorContent = new SimpleStringProperty();
  @JsonRawValue private SimpleStringProperty selectorTitle = new SimpleStringProperty();
  @JsonRawValue private SimpleStringProperty selectorNavigationNext = new SimpleStringProperty();

  public String getAddressToStart() { return addressToStart.getValue(); }
  public String getSelectorContent() { return selectorContent.getValue(); }
  public String getSelectorTitle() { return selectorTitle.getValue(); }
  public String getSelectorNavigationNext() { return selectorNavigationNext.getValue(); }

  public void setAddressToStart(String value) { this.addressToStart.setValue(value); }
  public void setSelectorContent(String value) { this.selectorContent.setValue(value); }
  public void setSelectorTitle(String value) { this.selectorTitle.setValue(value); }
  public void setSelectorNavigationNext(String value) { this.selectorNavigationNext.setValue(value); }

  public void copy(Content newValue){
    this.setSelectorContent(newValue.getSelectorContent());
    this.setSelectorTitle(newValue.getSelectorTitle());
    this.setSelectorNavigationNext(newValue.getSelectorNavigationNext());
    this.setAddressToStart(newValue.getAddressToStart());
  }
}
