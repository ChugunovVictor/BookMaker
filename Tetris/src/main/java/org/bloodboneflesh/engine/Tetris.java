package org.bloodboneflesh.engine;

import org.bloodboneflesh.content.Field;
import org.springframework.beans.factory.annotation.Autowired;

public class Tetris {
    @Autowired Field field;
    public static int score;
    
    int speed;
    
    public Tetris(int s){
        this.speed = s;
    }
}
