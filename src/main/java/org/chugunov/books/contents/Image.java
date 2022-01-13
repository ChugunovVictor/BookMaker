package org.chugunov.books.contents;

import javafx.geometry.Insets;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Image implements Content, Comparable{
  byte[] image;
  int position;
  int lines;
  double width;
  double height;

  public Image(byte[] image, int position, int lines, double width, double height) {
    this.image = image;
    this.position = position;
    this.lines = lines;
    this.width = width;
    this.height = height;
  }

  public static Image parse(int position, String url, Optional<Integer> width, Optional<Integer> height, float fontSize, Insets padding){

    try (ByteArrayOutputStream bao = new ByteArrayOutputStream();
         InputStream in = new URL(url).openStream()) {
        int bytesRead = 0;
        byte[] buff = new byte[8000];
        double pageWidth = Content.WIDTH_OF_PAGE_A4 - padding.getLeft() - padding.getRight();

        while((bytesRead = in.read(buff)) != -1) {
          bao.write(buff, 0, bytesRead);
        }

        byte[] data = bao.toByteArray();

        javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(data));

        double providedWidth = width.isPresent() ? width.get() : (img.getWidth() > 0 ? img.getWidth() : pageWidth);
        double providedHeight = height.isPresent() ? height.get() : img.getHeight();
        double scale = providedWidth / pageWidth;

        int lines = calculateLines( fontSize,providedHeight * scale );
      // graphicsContext.drawImage(img, x, y, width, height);

        return new Image(data, position, lines, providedWidth * scale, providedHeight * scale);
    } catch (IOException e) {
      e.printStackTrace();
    }return null;
  }

  private static int calculateLines(float fontSize, double height){
    return (int)(height / fontSize);
  }

  private PDImageXObject getImage(String src, PDDocument document){
    try (InputStream in = new URL(src).openStream()) {
      return JPEGFactory.createFromStream(document, in);
    } catch (IOException ex) {
      return null;
    }
  }

  @Override
  public int compareTo(Object o) {
    if(o.getClass() == Text.class)
      return ((Image)o).position > this.position ? -1 : 1;
    return 0;
  }
}
