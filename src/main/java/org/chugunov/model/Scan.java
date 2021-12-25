package org.chugunov.model;

public class Scan {
  private String addressToStart, selectorTitle, selectorContent, selectorNavigateNext;

  public Scan(String addressToStart, String selectorTitle, String selectorContent, String selectorNavigateNext) {
    this.addressToStart = addressToStart;
    this.selectorTitle = selectorTitle;
    this.selectorContent = selectorContent;
    this.selectorNavigateNext = selectorNavigateNext;
  }

  public String getAddressToStart() {
    return addressToStart;
  }
  public String getSelectorTitle() {
    return selectorTitle;
  }
  public String getSelectorContent() {
    return selectorContent;
  }
  public String getSelectorNavigateNext() {
    return selectorNavigateNext;
  }
}
