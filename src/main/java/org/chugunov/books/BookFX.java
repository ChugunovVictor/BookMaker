package org.chugunov.books;

import javafx.scene.Node;
import org.chugunov.model.Process;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class BookFX extends Book {

  public BookFX(Process process) throws IOException {
    super(process);
  }

  @Override
  public Node createTitlePage() {
    return null;
  }

  @Override
  public List<Node> createTableOfContents() {
    return null;
  }

  @Override
  public Node process(Object[] objects) {
    return null;
  }

  @Override
  public List<Node> createPages(Elements elements) {
    return null;
  }
}
