package org;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import org.chugunov.controllers.MainController;
import org.chugunov.controllers.ProcessTabController;
import org.chugunov.ui.panels.AboutPanel;
import org.chugunov.ui.panels.ProcessPanel;
import org.chugunov.ui.panels.UtilityPanel;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class Main extends Application {
  public static void main(String[] args) throws IOException, URISyntaxException {
//    ObjectMapper objectMapper = new ObjectMapper();
//    URL resource = Main.class.getClassLoader().getResource("examples.json");
//    org.chugunov.model.Process[] p = objectMapper.readValue(new File(resource.toURI()), org.chugunov.model.Process[].class);
//    System.out.println(Arrays.toString(p));
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

  public void addTab(TabPane tabPane, String title, Node content) {
    Tab t = new Tab(title, content);
    t.setClosable(false);
    tabPane.getTabs().add(t);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
//    primaryStage.setTitle("Book Maker");
//
//    TabPane tabPane = new TabPane();
//
//    addTab(tabPane, "Process", new ProcessPanel(primaryStage));
//    addTab(tabPane, "Utility", new UtilityPanel());
//    addTab(tabPane, "About", new AboutPanel());
//
//    primaryStage.setScene(new Scene(tabPane, 800, 600));
//    primaryStage.show();



    Scene scene = new Scene(new StackPane());

    FXMLLoader loader = new FXMLLoader(Main.class.getClassLoader().getResource("fxml/ui.fxml"));
    scene.setRoot(loader.load());
    MainController controller = loader.getController();
    controller.init();

    ProcessTabController ptc = (ProcessTabController)controller.getController("Process");
    ptc.init();

    primaryStage.setTitle("Book Maker");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
