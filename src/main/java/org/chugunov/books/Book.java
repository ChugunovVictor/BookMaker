package org.chugunov.books;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.chugunov.model.Process;
import org.chugunov.utility.PostText;
import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Book <Page, Container> {
  static float fontSize = 12;
  static float margin = 50;
  float width = 620 - 2*margin;

  public static PDFont font_standart = PDType1Font.TIMES_ROMAN;    //PDType0Font.load(document, new File("times.ttf"));
  public static PDFont font_italic   = PDType1Font.TIMES_ITALIC;   //PDType0Font.load(document, new File("timesi.ttf"));
  public static PDFont font_bold     = PDType1Font.TIMES_BOLD;     //PDType0Font.load(document, new File("timesbd.ttf"));

  private TreeMap<String, List<Page>> pages;
  private Page title;
  private List<Page> toc;

  public Book(Process process) throws IOException {
    this.pages = parseHTTP(process);
    this.title = createTitlePage();
    this.toc = createTableOfContents();
  }

  abstract Page createTitlePage();
  abstract List<Page> createTableOfContents();
  abstract Container process(Page... pages);

  List<Page> createPages(org.jsoup.select.Elements elements){
    String indent = "    ";

    List<Pair<List<String>, String>> content = new ArrayList<>();

    for (Element element : elements){
      if(element.tagName().equalsIgnoreCase("img")){
        content.add(new Pair(null, encode(element.absUrl("src"))));
      } else {
        content.add(new Pair(calculateLines(font_standart, indent + element.text()), null ));
      }
    }

    return Collections.emptyList();

  }

  private String encode(String str){
    return str.replaceAll(" ", "%20");
  }

  private String getNavigationNext(Document doc, String selector){
    String newUrl = "";
    for (Element current_tag : doc.select(selector)){
      newUrl = current_tag.attr("href");
    }
    return newUrl;
  }

  private String getTitle(Document doc, String selector){
    String title = "";
    for (Element current_tag : doc.select(selector)){
      title += current_tag.text();
    }
    return title;
  }

  public TreeMap<String, List<Page>> parseHTTP(Process process) throws IOException {
    Document doc = Jsoup.connect(process.getContent().getAddressToStart()).timeout(0).get();

    int debug_link_counter = 0;

    TreeMap <String, List<Page>> materialForBook = new TreeMap<>();

    while ( doc!= null  && debug_link_counter < process.getPreview().getDebugDepth()){
      String new_url = getNavigationNext(doc, process.getContent().getSelectorNavigationNext());
      String title = getTitle(doc, process.getContent().getSelectorTitle());
      List<Page> pages = createPages(doc.select(process.getContent().getSelectorContent()));

      materialForBook.put(title, pages);

      if( new_url != "" ){
        doc = null;
        int reconnect_attempt_count = 0;
        while(doc == null && reconnect_attempt_count < 5){
          try{
            //doc = Jsoup.parse(new URL(new_url).openStream(), "UTF-8", new_url);
            doc = Jsoup.connect(new_url).timeout(0).get();
          }catch(Exception ex){
            reconnect_attempt_count++;
          }
        }
      } else break;

      if (process.getPreview().isDebug()) debug_link_counter++;
    }

    return materialForBook;
  }

  public static float getStringWidth(PDFont font, String text){
    try{
      try{
        return font.getStringWidth(text);
      } catch (java.lang.IllegalArgumentException ex){
        return font.getStringWidth(excludeUnsupportedSymbols(font, text));
      }
    } catch (IOException s){
      s.printStackTrace();
      return 1;
    }
  }

  public List<String> calculateLines(PDFont font, String input){
    List<String> lines = new ArrayList<String>();
    int lastSpace = -1;

    String [] newLines = input.split("\n");
    for(String text : newLines){
      text = "    " + text; /* new line indent */
      while (text.length() > 0) {
        int spaceIndex = text.indexOf(' ', lastSpace + 1);
        if (spaceIndex < 0)
          spaceIndex = text.length();
        String subString = text.substring(0, spaceIndex);

        float size = fontSize * getStringWidth(font, subString) / 1000;

        if (size > width){
          if (lastSpace < 0)
            lastSpace = spaceIndex;

          if (lastSpace < text.length()) {
            subString = text.substring(0, lastSpace);
            lines.add(subString);
            text = text.substring(lastSpace).trim();
            lastSpace = -1;
          } else {
            subString = text.trim();
            lines.add(subString);
            text = text.trim();
            lastSpace = -1;
          }
        }
        else if (spaceIndex == text.length()) {
          lines.add(text);
          text = "";
        } else {
          lastSpace = spaceIndex;
        }
      }
    }
    return lines;
  }

  /**
   * If there were an exception about symbol w/o glyph - exclude this symbol
   * @param font - target font
   * @param text - text with problem symbol
   * @return new text w/o corrupted symbol
   * @throws IOException
   */
  public static String excludeUnsupportedSymbols(PDFont font, String text) throws IOException{
    String new_text = "";
    char[] a = text.toCharArray();

    for (char b : a) {
      try{
        font.encode(String.valueOf(b));
        new_text += String.valueOf(b);
      } catch(java.lang.IllegalArgumentException e){
        // just pass
      }
    }
    return new_text;
  }
}
