package org.bloodboneflesh;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.bloodboneflesh.books.Book;
    /* Want to put table of contents before all paragraphes. 
       to calculate it, need to save info in someplace before print */
public class PreText{
        public String title;
        public String context;
        public List<PDImageXObject> contextImages;
        public List<String> lines;
        public List<PDPage> pages = new ArrayList<PDPage>();
        public int page_for_table_of_content;

        public PreText(String title, String context, List <PDImageXObject> contextImages, List<String> lines) throws IOException {
            this.title = title;
            this.context = context;
            this.contextImages = contextImages;
            this.lines = lines;
        }
        
        public PreText(String title, List<String> lines, int page) {
            this.title = title;
            this.lines = lines;
            this.page_for_table_of_content = page;
        }
}
    