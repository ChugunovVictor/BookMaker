package org.chugunov.model;

public class Process {

  private Basic basic_ = new Basic();
  private Content content_ = new Content();
  private Preview preview_ = new Preview();

  public Process(){}

  public Basic getBasic() { return basic_; }
  public Content getContent() { return content_; }
  public Preview getPreview() { return preview_; }

  public void setBasic_(Basic value) { this.basic_ = value; }
  public void setContent_(Content value) { this.content_ = value; }
  public void setPreview_(Preview value) { this.preview_ = value; }

  public void copy(Process newValue){
    this.setBasic_(newValue.getBasic());
    this.setContent_(newValue.getContent());
    this.setPreview_(newValue.getPreview());
  }
}
