package org.chugunov.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.chugunov.utility.BiDirectional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller {

  private Map<String, Field> controllerFields = new HashMap<>();
  private Map<String, Pair<Field, Field>> bindFields = new HashMap<>();

  public Controller() {

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

//  void biDirectionalBindText(TextField ui, StringProperty object, Consumer<String> method) {
//    ui.textProperty().addListener((observable, oldValue, newValue) -> {method.accept(newValue);});
//    object.addListener((observable, oldValue, newValue) -> {ui.setText(newValue);});
//  }

  private void bind(Field a, Field b, Object obj) throws IllegalAccessException {
    StringProperty bProp = ((TextField) b.get(this)).textProperty();
    bProp.addListener(
        (observable, oldValue, newValue) -> {
          try { a.set(obj, newValue); }
          catch (IllegalAccessException e) { e.printStackTrace(); }
        }
    );
    StringProperty aProp = new SimpleStringProperty((String) a.get(obj));
    aProp.bindBidirectional(bProp);
  }

}
