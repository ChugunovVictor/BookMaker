package org.chugunov.books.contents;

import javafx.geometry.Insets;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Text implements Content, Comparable{
  private List<String> lines;
  private int position;
  private PDFont font;
  private float fontSize;

  public List<String> getLines() { return lines; }
  public void setLines(List<String> lines) { this.lines = lines; }
  public int getPosition() { return position; }
  public void setPosition(int position) { this.position = position; }
  public PDFont getFont() {return font;}
  public void setFont(PDFont font) {this.font = font;}
  public float getFontSize() {return fontSize;}
  public void setFontSize(float fontSize) {this.fontSize = fontSize;}

  public Text(int position, List<String> lines, PDFont font, float fontSize){
    this.position = position;
    this.lines = lines;
  }

  public static Text parse(int position, String text, PDFont font, float fontSize, Insets padding){
    List<String> lines = calculateLines(font, text, fontSize, padding);
    return new Text(position, lines, font, fontSize);
  }

  private static String excludeUnsupportedSymbols(PDFont font, String text) throws IOException{
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

  private static float getStringWidth(PDFont font, String text){
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

  private static List<String> calculateLines(PDFont font, String input, float fontSize, Insets padding){
    List<String> lines = new ArrayList<String>();
    int lastSpace = -1;
    double pageWidth = Content.WIDTH_OF_PAGE_A4 - padding.getLeft() - padding.getRight();

    String [] newLines = input.split("\n");
    for(String text : newLines){
      text = "    " + text; /* new line indent */
      while (text.length() > 0) {
        int spaceIndex = text.indexOf(' ', lastSpace + 1);
        if (spaceIndex < 0)
          spaceIndex = text.length();
        String subString = text.substring(0, spaceIndex);

        float size = fontSize * getStringWidth(font, subString) / 1000;

        if (size > pageWidth){
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

  @Override
  public int compareTo(Object o) {
    if(o.getClass() == Text.class)
      return ((Text)o).position > this.position ? -1 : 1;
    return 0;
  }
}
