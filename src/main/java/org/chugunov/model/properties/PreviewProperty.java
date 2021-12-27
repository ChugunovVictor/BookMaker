package org.chugunov.model.properties;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import org.chugunov.model.Preview;

public class PreviewProperty extends Property{

  private Preview value;

  private Spinner numberOfBlocksOnPageField, debugDepthField;
  private CheckBox debugField;

  public PreviewProperty(Preview value) {
    this.value = value;
  }

  public PreviewProperty numberOfBlocksOnPage(Spinner value) {
    this.numberOfBlocksOnPageField = value;
    biDirectionalBindNumber(this.numberOfBlocksOnPageField, this.value.getNumberOfBlocksOnPageProperty(), this.value::setNumberOfBlocksOnPage_);
    return this;
  }

  public PreviewProperty debug(CheckBox value) {
    this.debugField = value;
    biDirectionalBindBoolean(this.debugField, this.value.isDebugProperty(), this.value::setDebug_);
    return this;
  }

  public PreviewProperty debugDepth(Spinner value) {
    this.debugDepthField = value;
    biDirectionalBindNumber(this.debugDepthField, this.value.getDebugDepthProperty(), this.value::setDebugDepth_);
    return this;
  }
}
