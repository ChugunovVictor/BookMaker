package org.chugunov.books;

import org.apache.pdfbox.pdmodel.PDPage;
import org.chugunov.model.Process;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BookPDF extends Book {
  public BookPDF(Process process) throws IOException {
    super(process);
  }

  @Override
  public PDPage createTitlePage() {
    return null;
  }

  @Override
  public List<PDPage> createTableOfContents() {
    return null;
  }

  @Override
  public Object process(Object[] objects) {
    return null;
  }

  @Override
  public List<PDPage> createPages(Elements elements) {
    String indent = "    ";
    for (Element current_tag : elements){
      String text = indent + current_tag.text();
      String url = current_tag.absUrl("src");
      //pt.addImage( encode(url), document );
    }

    return Collections.emptyList();
  }
}
