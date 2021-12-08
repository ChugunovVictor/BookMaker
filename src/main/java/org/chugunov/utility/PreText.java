package org.chugunov.utility;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
    /* Want to put table of contents before all paragraphes. 
       to calculate it, need to save info in someplace before print */
public class PreText extends Text{
    
        public interface PreTextFactory{
            public PreText createPreText();
        }
    
        public PreText(){
            this.context = new ArrayList<>();
        }
    
        public int page_for_table_of_content;
        
        public List<PDImageXObject> contextImages = new ArrayList<>();
        
        private PDDocument pd_document;

        public void addImage(String source){
            PDImageXObject result = getImage(source);
            if (result != null) contextImages.add(result);
        }

        public void addText(String source){
            context.add(source);
        }

        private PDImageXObject getImage(String src){
            try (InputStream in = new URL(src).openStream()) {
                //Image result = ImageIO.read(in);
                return JPEGFactory.createFromStream(pd_document, in);
            } catch (IOException ex) {
                return null;
            }
        }

        /// TODO better name generation
        public void copyImageToFile(String src) throws Exception{
            try ( InputStream in = new URL(src).openStream(); 
                  OutputStream out = new BufferedOutputStream(
                      new FileOutputStream( String.valueOf(new java.util.Random().nextInt() ))) 
            ) {
                for (int b; (b = in.read()) != -1;) {
                    out.write(b);
                }
            }
        }
        
        public void setTitle(String title) {
            this.title = title;
        }        

        
        
        public List<String> lines;
        public List<PDPage> pages = new ArrayList<PDPage>();
        
        public PreText(String title, String context, List <PDImageXObject> contextImages, List<String> lines) throws IOException {
            this.title = title;
            this.context = new ArrayList<>(); this.context.add(context);
            this.contextImages = contextImages;
            this.lines = lines;
        }
        
        public PreText(String title, List<String> lines, int page) {
            this.title = title;
            this.lines = lines;
            this.page_for_table_of_content = page;
        }
}
    