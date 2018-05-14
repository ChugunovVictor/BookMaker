package org.bloodboneflesh.content;

import org.bloodboneflesh.engine.scan.Scanner;
import org.springframework.beans.factory.annotation.Autowired;

// Two figures at once
// Dr Mario type of scanning field

public class Field {
    int size_x, size_y, size_z;
    Point[][][] field;

    public Point[][][] getField() {
        return field;
    }
    //@Autowired 
    Scanner scanner;
    
    public Field(int x, int y, int z){
        this.size_x = x;
        this.size_y = y;
        this.size_z = z;
        field = new Point[x][y][z];
    }
    
    public void scan(){
        scanner.scan(field);
    }

    @Override
    public String toString() {
        return "Field{" + "x=" + size_x + ", y=" + size_y + ", z=" + size_z + '}';
    }
}
