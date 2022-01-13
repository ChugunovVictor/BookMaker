package org.chugunov.books.contents;

import javafx.geometry.Insets;
import org.jsoup.nodes.Element;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ContentGenerator {
  Queue<Text> texts;
  Queue<Image> images;
  int rowsOnPage;

  public ContentGenerator(Queue<Text> texts, Queue<Image> images, int rowsOnPage){
    this.texts = texts;
    this.images = images;
    this.rowsOnPage = rowsOnPage;
  }

  public <Page> List<Page> make(Function<Collection<? extends Content>, Page> createPageFn){
    return Arrays.asList(createPageFn.apply(texts));
  }

  public static ContentGenerator parse(String title, org.jsoup.select.Elements elements, float fontSize, Insets padding){
    String indent = "    ";

    Queue<Text> texts = new PriorityQueue<>();
    Queue<Image> images = new PriorityQueue<>();

    texts.add(Text.parse(-1,
        title,
        Content.font_bold,
        fontSize * 2,
        padding));

    for (int i = 0; i < elements.size(); i++) {
      Element element = elements.get(i);
      if (element.tagName().equalsIgnoreCase("img")) {
        String providedWidth = element.attr("width");
        String providedHeight = element.attr("height");

        images.add(Image.parse(i,
            encode(element.absUrl("src")),
            providedWidth.isEmpty() ? Optional.empty() : Optional.of(Integer.valueOf(providedWidth)),
            providedHeight.isEmpty() ? Optional.empty() : Optional.of(Integer.valueOf(providedHeight)),
            fontSize,
            padding));
      } else {
        texts.add(Text.parse(i,
            indent + element.text(),
            Content.font_standart,
            fontSize,
            padding));
      }
    }

    return new ContentGenerator(texts,
        images,
        (int)((Content.HEIGHT_OF_PAGE_A4 - padding.getTop() - padding.getBottom()) / fontSize));
  }

  private static String encode(String str) {
    return str.replaceAll(" ", "%20");
  }
}
