package org.bloodboneflesh;


import org.bloodboneflesh.Main.ContextFactory;
import org.bloodboneflesh.content.Field;
import org.bloodboneflesh.content.Figure;
import org.bloodboneflesh.content.Point;
import org.bloodboneflesh.engine.Tetris;
import org.bloodboneflesh.graphics.GUI;
import org.bloodboneflesh.graphics.impl.Swing2D;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource( value={"classpath:tetris.properties"}, ignoreResourceNotFound = true)
public class Application {

    @Value("${field_size_x}") int x;
    @Value("${field_size_y}") int y;
    @Value("${field_size_z}") int z;
    @Value("${speed}") int s;
    @Value("${title}") String title;
    
    @Bean @Scope("prototype") public Point point(){ return new Point(); }

    @Bean @Scope("prototype") public Figure figure(){ return new Figure(); }

    @Bean public Tetris tetris(){ return new Tetris(s); }
    
    @Bean public Field field(){ return new Field(x,y,z); }
    
    @Bean public GUI gui(){ return new Swing2D(title); }
    
    @Bean public ContextFactory contextFactory(){ return new ContextFactory(); }
}
