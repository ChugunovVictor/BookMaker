package org.chugunov.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.chugunov.model.Process;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class BiDirectionalController {

  private Map<String, Field> controllerFields = new HashMap<>();
  private Map<String, Pair<Field, Field>> bindFields = new HashMap<>();

  public BiDirectionalController() {
  }

  public void init() {
    try {
      this.processBinding();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void processBinding() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    final Field[] fields = this.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; ++i) {
      final Field field = fields[i];
      field.setAccessible(true);
      if (field.getAnnotation(BiDirectional.class) != null) {
        Field[] bindClassFields = field.getType().getDeclaredFields();
        for (int j = 0; j < bindClassFields.length; ++j) {
          Field bindClassField = bindClassFields[j];
          bindFields.put(bindClassField.getName(), new Pair(bindClassField, field));
        }
      } else if (field.getAnnotation(FXML.class) != null) {
        controllerFields.put(field.getName(), field);
      } else {
        continue;
      }
    }

    if (!bindFields.isEmpty()) {
      for (Map.Entry<String, Pair<Field, Field>> e : bindFields.entrySet()) {
        Field target = controllerFields.get(e.getKey() + "Field");
        if (target != null) {
          Field source = e.getValue().getKey();
          source.setAccessible(true);
          Object object = e.getValue().getValue().get(this);
          bind(source, target, object);
        }
      }
    }
  }

  private void bind(Field a, Field b, Object obj) throws IllegalAccessException {
    if(b.getType() == TextField.class){
      StringProperty bProp = ((TextField) b.get(this)).textProperty();
      StringProperty aProp = (StringProperty) a.get(obj);
      aProp.bindBidirectional(bProp);
    }
    if(b.getType() == Spinner.class){
      ObjectProperty bProp = ((Spinner) b.get(this)).getValueFactory().valueProperty();
      IntegerProperty aProp = (IntegerProperty) a.get(obj);
      aProp.bindBidirectional(bProp);
    }
    if(b.getType() == CheckBox.class){
      BooleanProperty bProp = ((CheckBox) b.get(this)).selectedProperty();
      BooleanProperty aProp = (BooleanProperty) a.get(obj);
      aProp.bindBidirectional(bProp);
    }
  }

}
