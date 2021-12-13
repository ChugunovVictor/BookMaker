package org.chugunov.ui.components;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Field extends Node {
  Label title;
  Node value;

  public Field(String title) {
    this.title = new Label(title);
    getStyleClass().add("vc-field");
  }

  /* --- forms --- */

  public Node toHBox() {
    HBox h = new HBox();
    h.getChildren().addAll(title, value);
    h.setSpacing(10);
    h.setAlignment(Pos.CENTER);
    return h;
  }

  public Node toVBox() {
    VBox v = new VBox();
    v.getChildren().addAll(title, value);
    v.setSpacing(10);
    v.setAlignment(Pos.CENTER);
    return v;
  }

  public void toGridRow(GridPane gp) {
    try {
      Method method = gp.getClass().getDeclaredMethod("getNumberOfRows");
      method.setAccessible(true);
      Integer rows = (Integer) method.invoke(gp);

      gp.addRow(rows, title, value);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      System.out.println(e.getLocalizedMessage());
      ;
    }
  }

  /* --- getter/setter --- */

  public void update(Object value) {
    if (this.value.getClass() == CheckBox.class)
      ((CheckBox) this.value).setSelected((Boolean) value);
    if (this.value.getClass() == TextField.class)
      ((TextField) this.value).setText((String) value);
    if (this.value.getClass() == Spinner.class)
      ((Spinner) this.value).getValueFactory().setValue((int) value);
    if (this.value.getClass() == ComboBox.class)
      ((ComboBox) this.value).setItems(FXCollections.observableArrayList((String[]) value));
  }

  public Object get() {
    if (this.value.getClass() == CheckBox.class)
      return ((CheckBox) this.value).isSelected();
    if (this.value.getClass() == TextField.class)
      return ((TextField) this.value).getText();
    if (this.value.getClass() == Spinner.class)
      return ((Spinner) this.value).getValueFactory().getValue();
    if (this.value.getClass() == ComboBox.class)
      return ((ComboBox) this.value).getItems();
    else
      return null;
  }

  /* --- to types --- */

  public Field toCheckBox() {
    return toCheckBox(false);
  }

  public Field toCheckBox(Boolean value) {
    this.value = new CheckBox();
    update(value);
    return this;
  }

  public Field toTextField() {
    return toTextField("");
  }

  public Field toTextField(String value) {
    this.value = new TextField();
    update(value);
    return this;
  }

  public Field toSpinner() {
    return toSpinner(0);
  }

  public Field toSpinner(int value) {
    Spinner r = new Spinner();
    r.setEditable(true);
    r.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
    this.value = r;
    update(value);
    return this;
  }

  public Field toComboBox() {
    return toComboBox(new String[]{});
  }

  public Field toComboBox(String[] values) {
    this.value = new ComboBox<>();
    update(values);
    return this;
  }

  @Override
  protected NGNode impl_createPeer() {
    return null;
  }

  @Override
  public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
    return null;
  }

  @Override
  protected boolean impl_computeContains(double localX, double localY) {
    return false;
  }

  @Override
  public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
    return null;
  }
}
