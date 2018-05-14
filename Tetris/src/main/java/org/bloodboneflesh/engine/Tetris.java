package org.bloodboneflesh.engine;

import org.bloodboneflesh.content.Field;
import org.bloodboneflesh.graphics.GUI;
import org.springframework.beans.factory.annotation.Autowired;

public class Tetris {
    @Autowired Field field;
    @Autowired GUI user_interface;
    public static int score;
    
    int speed;
    
    public Tetris(int s){
        this.speed = s;
    }
    
    public void process(){
        user_interface.setField(field.getField());
    }
}
