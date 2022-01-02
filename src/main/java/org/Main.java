package org;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import org.chugunov.Logger;
import org.chugunov.books.Book;
import org.chugunov.books.BookPDF;
import org.chugunov.model.Process;
import org.chugunov.ui.controllers.BasicController;
import org.chugunov.ui.controllers.MainController;
import org.chugunov.ui.controllers.ProcessTabController;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Main extends Application {
  public static void main(String[] args) throws URISyntaxException, IOException {
    launch(args);

//    ObjectMapper objectMapper = new ObjectMapper();
//    URL resource = Main.class.getClassLoader().getResource("examples.json");
//    Process[] examples = objectMapper.readValue(new File(resource.toURI()), Process[].class);
//
//    Book b = new BookPDF(examples[0]);
  }

  //boolean prepare_to_print = true;
  //    if(!prepare_to_print){
  //BookMaker bm = (BookMaker)ctx.getBean("bookmaker");
  //bm.createBook();
  //}else{
  //Print.prepareToPrint("1.PDF","out.pdf");
  //Print.addPageNumbers("1.PDF","out.pdf");
  // }

  //@Override
  public void start(Stage primaryStage) throws IOException {
    Scene scene = new Scene(new StackPane());

    FXMLLoader loader = new FXMLLoader(Main.class.getClassLoader().getResource("fxml/mainPage.fxml"));
    scene.setRoot(loader.load());
    MainController controller = loader.getController();
    controller.init();

    ProcessTabController ptc = (ProcessTabController)controller.getController("Process");
    ptc.init();

    primaryStage.setTitle("Book Maker");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }
}
