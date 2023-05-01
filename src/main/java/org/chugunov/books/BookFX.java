package org.chugunov.books;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.chugunov.books.contents.Content;
import org.chugunov.books.contents.Image;
import org.chugunov.books.contents.Text;
import org.chugunov.model.Process;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BookFX extends Book <Node> {

  Node root;

  public BookFX(Process process, Node root){
    super(process);
    this.root = root;
  }

  public void process(){
    this.pages.values().stream().forEach(c ->
        ((VBox)this.root).getChildren().addAll(c)
    );
  }

  public Node createTitlePage() {
    return null;
  }


  public List<Node> createTableOfContents() {
    return null;
  }


  public Node process(Object[] objects) {
    return null;
  }

  Node createNode(Content content){
    if(content instanceof Text){
      return new Label(((Text)content).getLines().stream().collect(Collectors.joining("\\n")));
    } else
      return new Label("image");
  }

  Node createPage(Collection<? extends Content> content){
    Pane page = new Pane();
    String cssLayout = "-fx-border-color: grey; " +
        "-fx-border-style: solid; " +
        "-fx-border-width: 2.5; " +
        "-fx-border-insets: 10; " +
        "-fx-pref-width: " + Content.WIDTH_OF_PAGE_A4 + "; "+
        "-fx-pref-height: " + Content.HEIGHT_OF_PAGE_A4 + "; "+
        "";
    page.setStyle(cssLayout);
    content.stream().map(c -> createNode(c)).forEach(c -> page.getChildren().add(c));
    return page;

//    return content.stream().map(c -> createNode(c)).reduce(new VBox(), (a, c) -> {
//      ((VBox)a).getChildren().add(c); return c;
//    });
  }

  @Override
  Node createPage() {
    return null;
  }

  @Override
  void printDecor(Node node, String title, int pageNumber) {

  }

  @Override
  void printText(Node node, String text, int pageNumber) {

  }
}
