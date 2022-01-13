package org.chugunov.books.contents;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public interface Content {
  static float WIDTH_OF_PAGE_A4 = PDRectangle.A4.getWidth();
  static float HEIGHT_OF_PAGE_A4 = PDRectangle.A4.getHeight();

  static PDFont font_standart = PDType1Font.TIMES_ROMAN;    //PDType0Font.load(document, new File("times.ttf"));
  static PDFont font_italic = PDType1Font.TIMES_ITALIC;   //PDType0Font.load(document, new File("timesi.ttf"));
  static PDFont font_bold = PDType1Font.TIMES_BOLD;     //PDType0Font.load(document, new File("timesbd.ttf"));

}
