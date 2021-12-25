package org;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import org.chugunov.ui.controllers.MainController;
import org.chugunov.ui.controllers.ProcessTabController;
import java.io.IOException;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  //boolean prepare_to_print = true;
  //    if(!prepare_to_print){
  //BookMaker bm = (BookMaker)ctx.getBean("bookmaker");
  //bm.createBook();
  //}else{
  //Print.prepareToPrint("1.PDF","out.pdf");
  //Print.addPageNumbers("1.PDF","out.pdf");
  // }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Scene scene = new Scene(new StackPane());

    FXMLLoader loader = new FXMLLoader(Main.class.getClassLoader().getResource("fxml/index.fxml"));
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
