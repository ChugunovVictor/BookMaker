package org.chugunov.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import javafx.beans.property.*;
import javafx.geometry.Insets;

public class Preview {

  @JsonRawValue private BooleanProperty debug = new SimpleBooleanProperty(false);
  @JsonRawValue private IntegerProperty debugDepth = new SimpleIntegerProperty(3);
  @JsonRawValue private IntegerProperty fontSize = new SimpleIntegerProperty(12);

  @JsonRawValue private IntegerProperty paddingTop = new SimpleIntegerProperty(50);
  @JsonRawValue private IntegerProperty paddingRight = new SimpleIntegerProperty(50);
  @JsonRawValue private IntegerProperty paddingBottom = new SimpleIntegerProperty(50);
  @JsonRawValue private IntegerProperty paddingLeft = new SimpleIntegerProperty(50);

  public Preview(){}

  public boolean isDebug() { return debug.getValue(); }
  public int getDebugDepth() { return debugDepth.getValue(); }
  public int getFontSize() { return fontSize.get(); }
  public int getPaddingTop() {return paddingTop.getValue();}
  public int getPaddingRight() {return paddingRight.getValue();}
  public int getPaddingBottom() {return paddingBottom.getValue();}
  public int getPaddingLeft() { return paddingLeft.getValue();}
  public Insets getPadding(){
    return new Insets(paddingTop.getValue(), paddingRight.getValue(), paddingBottom.getValue(), paddingLeft.getValue());
  }

  public void setDebug(boolean value) { this.debug.setValue(value); }
  public void setDebugDepth(int value) { this.debugDepth.setValue(value); }
  public void setFontSize(int fontSize) { this.fontSize.set(fontSize); }
  public void setPaddingTop(int paddingTop) {this.paddingTop.setValue(paddingTop);}
  public void setPaddingRight(int paddingRight) {this.paddingRight.setValue(paddingRight);}
  public void setPaddingBottom(int paddingBottom) {this.paddingBottom.setValue(paddingBottom);}
  public void setPaddingLeft(int paddingLeft) {this.paddingLeft.setValue(paddingLeft);}

  public void copy(Preview newValue){
    this.setDebug(newValue.isDebug());
    this.setDebugDepth(newValue.getDebugDepth());
    this.setFontSize(newValue.getFontSize());
    this.setPaddingTop(newValue.getPaddingTop());
    this.setPaddingLeft(newValue.getPaddingLeft());
    this.setPaddingBottom(newValue.getPaddingBottom());
    this.setPaddingRight(newValue.getPaddingRight());
  }
}
