package org.chugunov.model;

import javafx.beans.property.*;

public class Preview {

  private BooleanProperty debug = new SimpleBooleanProperty(false);
  private IntegerProperty debugDepth = new SimpleIntegerProperty(3);
  private IntegerProperty numberOfBlocksOnPage = new SimpleIntegerProperty(40);

  public Preview(){}

  public boolean isDebug() { return debug.getValue(); }
  public int getDebugDepth() { return debugDepth.getValue(); }
  public int getNumberOfBlocksOnPage() { return numberOfBlocksOnPage.getValue(); }

  public void setDebug(boolean value) { this.debug.setValue(value); }
  public void setDebugDepth(int value) { this.debugDepth.setValue(value); }
  public void setNumberOfBlocksOnPage(int value) { this.numberOfBlocksOnPage.setValue(value); }

  public void copy(Preview newValue){
    this.setDebug(newValue.isDebug());
    this.setNumberOfBlocksOnPage(newValue.getNumberOfBlocksOnPage());
    this.setDebugDepth(newValue.getDebugDepth());
  }
}
