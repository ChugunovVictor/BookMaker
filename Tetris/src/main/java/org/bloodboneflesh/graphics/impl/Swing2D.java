package org.bloodboneflesh.graphics.impl;

import javax.swing.JFrame;
import org.bloodboneflesh.content.Point;
import org.bloodboneflesh.graphics.GUI;

public class Swing2D extends JFrame implements GUI{

    int default_heigth = 600;
    int default_width = 480;
    
    public Point[][][] field;
        
    public Swing2D(String title){
        this.setTitle(title);
        this.setSize(default_width, default_heigth);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void setField(Point[][][] f) {
        this.field = f;
    }
}
