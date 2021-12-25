package org.chugunov.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.chugunov.books.*;

public enum Type {
  NOVEL, COMICS;

  public Book createBook(PDDocument document, Process process){
    switch (this){
      case COMICS: return new Comics(document, process);
      case NOVEL:
      default: return new Novel(document, process);
    }
  }
}
