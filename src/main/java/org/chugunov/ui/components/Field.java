package org.chugunov.ui.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public abstract class Field extends HBox {
    Label titleLabel;

    public Field(String title){
        titleLabel = new Label(title);
        getChildren().addAll(titleLabel);
        setSpacing(10);
        setAlignment(Pos.CENTER);

        getStyleClass().add("vc-field");
    }

    public void addChild(Node value){
        getChildren().add(value);
    }

    // We are able to use this kind of components as a separate and as a whole
    public Label getTitle(){
        return this.titleLabel;
    }

    public Node getValue(){
        return this.getChildren().get(0);
    }
}
