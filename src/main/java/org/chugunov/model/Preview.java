package org.chugunov.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Preview {

  private SimpleBooleanProperty debug_ = new SimpleBooleanProperty(false);
  private SimpleIntegerProperty debugDepth_ = new SimpleIntegerProperty(3);
  private SimpleIntegerProperty numberOfBlocksOnPage_ = new SimpleIntegerProperty(40);

  public Preview(){}

  public SimpleBooleanProperty isDebugProperty() { return debug_; }
  public SimpleIntegerProperty getDebugDepthProperty() { return debugDepth_; }
  public SimpleIntegerProperty getNumberOfBlocksOnPageProperty() { return numberOfBlocksOnPage_; }

  public boolean isDebug() { return debug_.getValue(); }
  public int getDebugDepth() { return debugDepth_.getValue(); }
  public int getNumberOfBlocksOnPage() { return numberOfBlocksOnPage_.getValue(); }

  public Preview debug(boolean value) { this.debug_.setValue(value); return this; }
  public Preview debugDepth(int value) { this.debugDepth_.setValue(value); return this; }
  public Preview numberOfBlocksOnPage(int value) { this.numberOfBlocksOnPage_.setValue(value); return this; }

  public void setDebug_(boolean value) { this.debug_.setValue(value); }
  public void setDebugDepth_(int value) { this.debugDepth_.setValue(value); }
  public void setNumberOfBlocksOnPage_(int value) { this.numberOfBlocksOnPage_.setValue(value); }

  public void copy(Preview newValue){
    this.setDebug_(newValue.isDebug());
    this.setNumberOfBlocksOnPage_(newValue.getNumberOfBlocksOnPage());
    this.setDebugDepth_(newValue.getDebugDepth());
  }
}
