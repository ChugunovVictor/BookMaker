package org.chugunov.books;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.chugunov.books.contents.ContentGenerator;
import org.chugunov.books.contents.Content;
import org.chugunov.books.contents.Text;
import org.chugunov.model.Process;
import javafx.util.Pair;
import org.chugunov.utility.ArabicToRoman;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;

public abstract class Book<Page> {
  public TreeMap<String, List<Page>> pages;
  TreeMap<String, Pair<Integer, Page>> tableOfContents = new TreeMap<>();
  int pageNumber = 2;

  Process process;

  public Book(Process process){
    this.process = process;
    this.pages = parseHTTP(process);
  }

  abstract Page createPage();
  abstract void printDecor(Page page, String title, int pageNumber);
  abstract void printText(Page page, String text, int pageNumber);

  Page createPage(String title, int pageNumber, Collection<? extends Content> content){
    Page current = createPage();
    printDecor(current, title, pageNumber);

//    content.forEach((Text c) -> printText(current, c.getLines(), c.getFont(), c.getFontSize());
//    printText(strings.toArray(new String[]{}), font_standart, fontSize,
//        mediabox.getLowerLeftX() + margin,
//        mediabox.getUpperRightY() - margin * 1.4f - (is_Head_Start ?  leading*2 : 0),
//        -leading, contentStream);
    return null;
  }

  List<Page> createChapter(String title, org.jsoup.select.Elements elements) {
    ContentGenerator cg = ContentGenerator.parse(title, elements,
        this.process.getPreview().getFontSize(),
        this.process.getPreview().getPadding());
    return Collections.EMPTY_LIST;//cg.make(this::createPage);
  }

  private Optional<String> getNavigationNext(Document doc, String selector) {
    return doc.select(selector).stream()
        .map(c -> Optional.ofNullable(c.attr("href")))
        .reduce(Optional.empty(), (a,c) -> c);
  }

  private Optional<String> getTitle(Document doc, String selector) {
    return doc.select(selector).stream()
        .map(c -> Optional.ofNullable(c.text()))
        .reduce(Optional.empty(), (a,c) ->
            a.flatMap(aa -> c.map(cc -> aa + cc))
        );
  }

  private void addParagraphToTableOfContents(String title, List<Page> pages) {
    this.tableOfContents.put(title, new Pair(
        pageNumber,
        pages.get(0)
    ));
    this.pageNumber += pages.size();
  }

  public TreeMap<String, List<Page>> parseHTTP(Process process) {
    Document doc = null;
    try {
      doc = Jsoup.connect(process.getContent().getAddressToStart()).timeout(0).get();
    } catch (IOException e) {
      e.printStackTrace();
    }

    int debug_link_counter = 0;

    TreeMap<String, List<Page>> materialForBook = new TreeMap<>();

    while (doc != null && debug_link_counter < process.getPreview().getDebugDepth()) {
      Optional<String> new_url = getNavigationNext(doc, process.getContent().getSelectorNavigationNext());

      String title = getTitle(doc, process.getContent().getSelectorTitle()).orElse("");
      List<Page> pages = Collections.EMPTY_LIST;//createChapter(doc.select(process.getContent().getSelectorContent()));
      addParagraphToTableOfContents(title, pages);

      materialForBook.put(title, pages);

      if (new_url.isPresent()) {
        doc = null;
        int reconnect_attempt_count = 0;
        while (doc == null && reconnect_attempt_count < 5) {
          try {
            //doc = Jsoup.parse(new URL(new_url).openStream(), "UTF-8", new_url);
            doc = Jsoup.connect(new_url.get()).timeout(0).get();
          } catch (Exception ex) {
            reconnect_attempt_count++;
          }
        }
      } else break;

      if (process.getPreview().isDebug()) debug_link_counter++;
    }

    return materialForBook;
  }
}
