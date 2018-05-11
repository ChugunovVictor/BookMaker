package org;

import java.io.IOException;
import org.bloodboneflesh.BookMaker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    static  ApplicationContext ctx;
    
    public class ContextFactory{
        public Object getBeanWithParameters(Class c, Object ... args){
            return ctx.getBean(c, args);
        }
    }

    public static void main(String[] args) throws IOException{
        ctx = new ClassPathXmlApplicationContext("Application.xml");
        BookMaker bm = (BookMaker)ctx.getBean("bookmaker");
        bm.createBook();
    }
}
