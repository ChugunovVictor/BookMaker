package org.chugunov.model;

public class Process {

  private Basic basic = new Basic();
  private Content content = new Content();
  private Preview preview = new Preview();

  public Process(){}

  public Basic getBasic() { return basic; }
  public Content getContent() { return content; }
  public Preview getPreview() { return preview; }

  // for unmarshalling
  public void setBasic(Basic value) { this.basic = value; }
  public void setContent(Content value) { this.content = value; }
  public void setPreview(Preview value) { this.preview = value; }

  public void copy(Process newValue){
    this.getBasic().copy(newValue.getBasic());
    this.getContent().copy(newValue.getContent());
    this.getPreview().copy(newValue.getPreview());
  }

  @Override
  public String toString() {
    return basic.getTitle() + " (" + basic.getSite() + ")";
  }
}
