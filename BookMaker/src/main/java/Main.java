
import java.io.IOException;
import org.bloodboneflesh.BookMaker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws IOException{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Application.xml");
        BookMaker bm = (BookMaker)ctx.getBean("bookmaker");
        bm.createBook();
    }
}
