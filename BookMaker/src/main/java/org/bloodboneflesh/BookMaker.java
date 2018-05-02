package org.bloodboneflesh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.bloodboneflesh.books.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BookMaker {
    Book draft;
    String book_file;
    String address_to_start;
    String selector_content;
    String selector_title;
    String selector_navigation_next;
    
    int link_counter = 5;        
    
    
    public void createBook() throws IOException{
        Document doc = Jsoup.connect(address_to_start).timeout(0).get();
        //Document doc = Jsoup.parse(new URL(adress_to_start).openStream(), "UTF-8", adress_to_start);
        PDDocument document = new PDDocument();
        
        ArrayList <PreText> materialForBook = new ArrayList<>();
            
        while ( doc!= null  && link_counter != 0 ){
            
            Elements selected_url_tags = doc.select(selector_navigation_next);
            String new_url = "";
            for (Element current_tag : selected_url_tags){
                 new_url = current_tag.attr("href");
                 System.out.println(new_url);
            }
            
            Elements selected_title_tags = doc.select(selector_title);
              
            String title = "";
            for (Element current_tag : selected_title_tags){
                title += current_tag.text();
            }

            String content_text = "";
            Elements selected_tags = doc.select(selector_content);
            for (Element current_tag : selected_tags){
                content_text += "    " + current_tag.text() + "\n";
            }
            
            materialForBook.add(new PreText(title, content_text, draft.calculateLines(Book.font_standart, content_text)));
            // addParagraph(document, title , content_text, standart_font, courcive_font, bold_font);
            
            if( new_url != "" ){ 
                doc = null;
                while(doc == null){
                    try{
                        //doc = Jsoup.parse(new URL(new_url).openStream(), "UTF-8", new_url);
                        doc = Jsoup.connect(new_url).timeout(0).get(); 
                    }catch(Exception ex){
                        // try connect again and again until success    
                    }
                }
            }else break;
            link_counter--;
        }
        try {
            draft.createBook(document, materialForBook);
        } catch (Exception ex) {
            ex.printStackTrace();
        }    
        
        document.save(new File(book_file));
        document.close();
    }
    
    public void setDraft(Book draft) {
        this.draft = draft;
    }
    
    public void setBook_file(String book_file) {
        this.book_file = book_file;
    }
    
        public void setAddress_to_start(String address_to_start) {
        this.address_to_start = address_to_start;
    }

    public void setSelector_content(String selector_content) {
        this.selector_content = selector_content;
    }

    public void setSelector_title(String selector_title) {
        this.selector_title = selector_title;
    }

    public void setSelector_navigation_next(String selector_navigation_next) {
        this.selector_navigation_next = selector_navigation_next;
    }
}
