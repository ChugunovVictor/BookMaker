package org.bloodboneflesh;

import org.bloodboneflesh.engine.Tetris;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static ConfigurableApplicationContext ac;
    
    public static class ContextFactory{
        ContextFactory(){}
        Object getBeanWithParameters(Class c, Object ... args){
            return ac.getBean(c, args);
        }
    }
        
    public static void main(String[] args) {
        ac = new AnnotationConfigApplicationContext(org.bloodboneflesh.Application.class);
        Tetris t = ac.getBean(Tetris.class);
        System.out.println(t);
    }
}
