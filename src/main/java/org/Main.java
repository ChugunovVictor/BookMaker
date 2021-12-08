package org;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.application.Application;
import org.chugunov.ui.*;

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

    public void addTab(TabPane tabPane, String title, Node content) {
        Tab t = new Tab(title, content);
        t.setClosable(false);
        tabPane.getTabs().add(t);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Maker");

        TabPane tabPane = new TabPane();

        addTab(tabPane, "Scanning", new ScanPanel(primaryStage));
        addTab(tabPane, "Utility", new UtilityPanel());
        addTab(tabPane, "About", new AboutPanel());

        primaryStage.setScene(new Scene(tabPane, 800, 600));
        primaryStage.show();
    }
}
