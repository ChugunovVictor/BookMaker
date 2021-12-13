package org;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BarTabController {
  @FXML
  private Label lblText;

  public void handleButton() {
    lblText.setText("Byebye!");
  }
}
