package org.chugunov.books;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.chugunov.books.contents.Content;
import org.chugunov.model.Process;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BookPDF extends Book <PDPage> {
  public BookPDF(Process process) {
    super(process);
  }

  @Override
  PDPage createPage(Collection<? extends Content> content) {
    return null;
  }

  public PDPage createTitlePage() {
    return null;
  }

  public List<PDPage> createTableOfContents() {
    return null;
  }

  public void process() {
    try(PDDocument document = new PDDocument()){
      for (List<PDPage> pages : this.pages.values()){
        for (PDPage page : pages){
          document.addPage(page);
        }
      }

      document.save(new File(
          (process.getBasic().getOutputPath() == null || process.getBasic().getOutputPath().isEmpty())
              ?  process.getBasic().getTitle() + ".pdf"
              : process.getBasic().getOutputPath() + "/" +  process.getBasic().getTitle() + ".pdf"
      ));
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

}
