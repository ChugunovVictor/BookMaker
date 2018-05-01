package bloodboneflesh.code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BookMaker {
    // int link_counter = 5;
    // https://parahumans.wordpress.com/
    // http://www.twogag.com/
    
    String book_author = "John McCrae";
    String book_title = "Worm";
    String book_file = book_author + " - " + book_title + ".pdf";
    
    String adress_to_start  = "https://parahumans.wordpress.com/2011/06/11/1-1/";
    String selector_content = "#content .entry-content p:not(p:contains(Next Chapter))"; //"#content .entry-content p:not(p:has(a[title=Next Chapter]))";
    String selector_title   = "#content .entry-title";
    String selector_navigation_next = "#content .nav-next a[href]";
        
    int number_of_rows_on_page = 38;
    float fontSize = 12;
    float margin = 50;
    float width = 620 - 2*margin;

    PDFont font_standart = PDType1Font.TIMES_ROMAN;    //PDType0Font.load(document, new File("times.ttf")); 
    PDFont font_italic   = PDType1Font.TIMES_ITALIC;   //PDType0Font.load(document, new File("timesi.ttf")); 
    PDFont font_bold     = PDType1Font.TIMES_BOLD;     //PDType0Font.load(document, new File("timesbd.ttf"));
    
    /* Want to put table of contents before all paragraphes. 
       to calculate it, need to save info in someplace before print */
    class PreText{
        String title;
        String context;
        List<String> lines;
        List<PDPage> pages = new ArrayList<PDPage>();
        int page_for_table_of_content;

        public PreText(String title, String context, PDFont font) throws IOException {
            this.title = title;
            this.context = context;
            this.lines = calculateLines(font, this.context);
        }
        
        public PreText(String title, List<String> lines, int page) {
            this.title = title;
            this.lines = lines;
            this.page_for_table_of_content = page;
        }
    }
    
    public void addHiperLink(PDDocument target_document, PDPage target_page, float offset_y, String link_text, int page_number, PDPage link_to) throws IOException{
        List<PDAnnotation> annotations = target_page.getAnnotations();
        
        // First add some text, two lines we'll add some annotations to this later
        PDFont font = PDType1Font.TIMES_ROMAN;
        PDPageContentStream contents = new PDPageContentStream(target_document, target_page, true, true);
       
        contents.beginText();
        contents.setFont(font, fontSize);
        contents.newLineAtOffset(margin, offset_y);
        try{
            contents.showText(link_text);
        }catch(java.lang.IllegalArgumentException ex){
            contents.showText(excludeUnsupportedSymbols(font, link_text)); 
        }
        contents.endText();
        
        contents.beginText();
        contents.setFont(font, fontSize);
        contents.newLineAtOffset(target_page.getMediaBox().getWidth() - margin - getStringWidth(font, String.valueOf(page_number)) / 1000 * fontSize, offset_y);
        contents.showText(String.valueOf(page_number));
        contents.endText();
        
        float max_width = target_page.getMediaBox().getWidth() - margin * 2;
        float still_free = max_width - getStringWidth(font, String.valueOf(page_number)) / 1000 * fontSize - getStringWidth(font, link_text) / 1000 * fontSize;
        float point_width = getStringWidth(font, ".")  / 1000 * fontSize;
        
        String points = "";
        for (int i =0 ; i < still_free / point_width; i++){
            points += ".";
        }
        
        contents.beginText();
        contents.setFont(font, fontSize);
        contents.newLineAtOffset(margin + getStringWidth(font, link_text) / 1000 * fontSize, offset_y);
        contents.showText(points);
        contents.endText();
        contents.close();
        
        // Now add the link annotation, so the click on "Jump to page three" works
        PDAnnotationLink pageLink = new PDAnnotationLink();

        // Set the rectangle containing the link
        float textWidth = getStringWidth(font, link_text) / 1000 * fontSize;
        PDRectangle position = new PDRectangle();
        position.setLowerLeftX(margin);
        position.setLowerLeftY(offset_y);  // down a couple of points
        position.setUpperRightX(target_page.getMediaBox().getWidth() - margin);
        position.setUpperRightY(offset_y + 10);
        pageLink.setRectangle(position);
        // add the GoTo action
        PDActionGoTo actionGoto = new PDActionGoTo();
        // see javadoc for other types of PDPageDestination
        //PDPageDestination dest = new PDPageFitWidthDestination();
        PDPageXYZDestination dest = new PDPageXYZDestination();
        // do not use setPageNumber(), this is for external destinations only
        dest.setPage(link_to);
        dest.setLeft(0);
        dest.setTop((int)link_to.getMediaBox().getHeight());
        
        actionGoto.setDestination(dest);
        pageLink.setAction(actionGoto);
        
        PDBorderStyleDictionary border = new PDBorderStyleDictionary();
        border.setWidth( 0f );
        pageLink.setBorderStyle( border );

        annotations.add(pageLink);      
    }
        
    public BookMaker() throws IOException{
        
        //int counter = 4;
        Document doc = Jsoup.connect(adress_to_start).timeout(0).get();
        //Document doc = Jsoup.parse(new URL(adress_to_start).openStream(), "UTF-8", adress_to_start);
        
        PDDocument document = new PDDocument();
        
        ArrayList <PreText> materialForBook = new ArrayList<>();
            
        while ( doc!= null  /*&& link_counter != 0*/ ){
            
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
            
            materialForBook.add(new PreText(title, content_text, font_standart));
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
                //counter--;
            }else break;
            //link_counter--;
        }
        
        document.addPage( createTitlePage(document, book_author, book_title) );
        
        
        prepare(materialForBook, document);
        
        // print table of contents 
        PreText TOC = new PreText ("Table Of Contents", new ArrayList<String>(){}, 2 );
        printParagraph(TOC, document, true, materialForBook);
        
        for(PDPage a : TOC.pages){
            document.addPage(a);
        }
        
        for (PreText b: materialForBook)
            for(PDPage a : b.pages){
                document.addPage(a);
            }
        document.save(new File(book_file));
        document.close();
    }
    
    public void prepare(ArrayList <PreText> materialForBook, PDDocument doc) throws IOException{
        int page_counter = 1;
        int pages_to_toc = calculatePages(materialForBook.size(), number_of_rows_on_page);
        
        page_counter += pages_to_toc;
        
        for(PreText a : materialForBook) {
            a.page_for_table_of_content =  page_counter + 1; /* next after last page before */
            int pages_to_chapter = calculatePages(a.lines.size(), number_of_rows_on_page); 
            page_counter += pages_to_chapter;
            printParagraph(a, doc, false, null);
        }
    }

    public void printParagraph( PreText current, PDDocument doc, boolean isTOC, ArrayList<PreText> material) throws IOException {
        int page_counter = current.page_for_table_of_content;
        
        if(isTOC){
            for (int i=0; i< material.size();  )
            {
                if ( i == 0 ) // add string with paragraph title 
                {
                    /* -2 because title has x2 font */
                    int finalDestination = i + number_of_rows_on_page - 1;
                    if ( finalDestination > material.size() ) finalDestination = material.size();

                    current.pages.add( printTOCPage(doc, material.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, true));
                    
                    page_counter++;

                    i+= number_of_rows_on_page - 1;
                }else{
                    int finalDestination = i + number_of_rows_on_page;
                    if ( finalDestination > material.size() ) finalDestination = material.size();

                    current.pages.add(printTOCPage(doc, material.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, false));
                    
                    page_counter++;

                    i+= number_of_rows_on_page;
                }
            }
        }else{
            for (int i=0; i< current.lines.size();  )
            {
                if ( i == 0 ) // add string with paragraph title 
                {
                    /* -2 because title has x2 font */
                    int finalDestination = i + number_of_rows_on_page - 2;
                    if ( finalDestination > current.lines.size() ) finalDestination = current.lines.size();

                    current.pages.add(addPage( doc, current.lines.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, true));
                    
                    page_counter++;

                    i+= number_of_rows_on_page - 2;
                }else{
                    int finalDestination = i + number_of_rows_on_page;
                    if ( finalDestination > current.lines.size() ) finalDestination = current.lines.size();

                    current.pages.add(addPage( doc, current.lines.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, false));
                    page_counter++;

                    i+= number_of_rows_on_page;
                }
            }
        }
    }
    
    public PDPage createTitlePage(PDDocument doc, String author, String title) throws IOException{
        PDPage page = new PDPage();
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        PDRectangle mediabox = page.getMediaBox();
        
        /* print author */
        contentStream.beginText();
        contentStream.setFont(font_standart, fontSize);
        float authorWidth = font_standart.getStringWidth(author) / 1000 * fontSize;
        contentStream.newLineAtOffset(mediabox.getUpperRightX() - margin - authorWidth, 
                mediabox.getUpperRightY() - margin );
        contentStream.showText(author);
        contentStream.endText();
        
        /* print title */
        contentStream.beginText();
        contentStream.setFont(font_bold, fontSize * 5);
        float titleWidth = font_bold.getStringWidth(title) / 1000 * fontSize * 5;
        contentStream.newLineAtOffset( (mediabox.getWidth() - titleWidth)/2 , mediabox.getHeight()/2);
        contentStream.showText(title);
        contentStream.endText();
        
        /* print notes */
        contentStream.beginText();
        contentStream.setFont(font_italic, fontSize);
        contentStream.newLineAtOffset(margin + mediabox.getLowerLeftX() , 26 + margin + mediabox.getLowerLeftY());
        contentStream.showText("Made with https://jsoup.org/");
        contentStream.newLineAtOffset(0, -13);
        contentStream.showText("Made with https://pdfbox.apache.org/");
        contentStream.newLineAtOffset(0, -13);
        contentStream.showText("Using materials from https://parahumans.wordpress.com/");
        contentStream.endText();
        
        contentStream.close();
        return page;
    }
    
    public PDPage printTOCPage(PDDocument doc, List<PreText> lines, float fontSize, float margin, String title, int number, 
            boolean is_Head_Start) throws IOException{
        PDPage page = new PDPage();
        //doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        float leading = 1.5f * fontSize;
        
        PDRectangle mediabox = page.getMediaBox();
        
        /* draw decor top line with paragraph title */
        int decor_text_font_size = 9;
        contentStream.setLineWidth(0.5f);
        contentStream.drawLine(margin / 2, mediabox.getHeight() - margin * 0.8f, mediabox.getWidth() - margin / 2, mediabox.getHeight() - margin * 0.8f);
        
        contentStream.beginText();
        contentStream.setFont(font_italic, decor_text_font_size);
        contentStream.newLineAtOffset(mediabox.getWidth() - margin - font_italic.getStringWidth(title) / 1000 * decor_text_font_size, mediabox.getHeight() - margin * 0.8f + 5);
        contentStream.showText(title);
        contentStream.endText();
        
        /* print title if it is necessary */
        
        if (is_Head_Start) {
            contentStream.beginText();
            contentStream.setFont(font_bold, 20);
            contentStream.newLineAtOffset(mediabox.getLowerLeftX() + margin, mediabox.getUpperRightY() - margin * 1.4f );
            contentStream.showText(title);
            contentStream.endText(); 
        }
        /* print content text */

        float offset_y;
        if (!is_Head_Start) {        
            offset_y = mediabox.getUpperRightY() - margin * 1.4f;
        }else {
            offset_y = mediabox.getUpperRightY() - margin * 1.4f - leading*1.5f ;
        }
        
        /* print page number */
        contentStream.beginText();
        contentStream.setFont(font_standart, fontSize);
        contentStream.newLineAtOffset(mediabox.getWidth() / 2, margin * 0.5f );
        contentStream.showText(String.valueOf(number));
        contentStream.endText();
        
        contentStream.close();
        
        for (PreText alpha: lines)
        {
            addHiperLink(doc, page, offset_y, alpha.title , alpha.page_for_table_of_content, alpha.pages.get(0));
            offset_y -= leading;
        }
        
        return page;
    }
    
    public int calculatePages(int rows_count, int number_of_rows_on_page){
        float current_value = (float)( rows_count + 2 ) / number_of_rows_on_page; /* +2 for title which takes 2 rows*/
        int pages_to_chapter = Math.round(current_value) ;
        if (pages_to_chapter > current_value) return pages_to_chapter;
        else return pages_to_chapter + 1;
    }
    
    public static void main(String[] args) throws IOException {
        new BookMaker();
    }
   
/**
 * Add new page with content to document 
 * only print calculated before text
 *
 * @param doc  target document
 * @param lines  text content
 * @param fontSize  font size for text content
 * @param margin  margin from right / left / top / bottom
 * @param title  name of the paragraph - to use it with decor and for titling
 * @param number  page number
 * @param is_Head_Start  show if paragraph start with this page - so if we need to print it or not
 */
    public PDPage addPage(PDDocument doc, List<String> lines, float fontSize, float margin, String title, int number, 
            boolean is_Head_Start) throws IOException{
        
        PDPage page = new PDPage();
        //doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        float leading = 1.5f * fontSize;
        
        PDRectangle mediabox = page.getMediaBox();
        
        /* draw decor top line with paragraph title */
        int decor_text_font_size = 9;
        contentStream.setLineWidth(0.5f);
        contentStream.drawLine(margin / 2, mediabox.getHeight() - margin * 0.8f, mediabox.getWidth() - margin / 2, mediabox.getHeight() - margin * 0.8f);
        
        contentStream.beginText();
        contentStream.setFont(font_italic, decor_text_font_size);
        contentStream.newLineAtOffset(mediabox.getWidth() - margin - getStringWidth(font_italic, title) / 1000 * decor_text_font_size, mediabox.getHeight() - margin * 0.8f + 5);
        try{
            contentStream.showText(title);
        }catch(java.lang.IllegalArgumentException ex){
            contentStream.showText(excludeUnsupportedSymbols(font_italic, title)); 
        }
        contentStream.endText();
        
        /* print title if it is necessary */
        
        if (is_Head_Start) {
            contentStream.beginText();
            contentStream.setFont(font_bold, 20);
            contentStream.newLineAtOffset(mediabox.getLowerLeftX() + margin, mediabox.getUpperRightY() - margin * 1.4f );
            try{
                contentStream.showText(title);
            }catch(java.lang.IllegalArgumentException ex){
                contentStream.showText(excludeUnsupportedSymbols(font_bold, title)); 
            }
            contentStream.endText(); 
        }
        /* print content text */
        
        contentStream.beginText();
        contentStream.setFont(font_standart, fontSize);
        
        if (!is_Head_Start) {        
            contentStream.newLineAtOffset(mediabox.getLowerLeftX() + margin, mediabox.getUpperRightY() - margin * 1.4f );
        }else {
            contentStream.newLineAtOffset(mediabox.getLowerLeftX() + margin, mediabox.getUpperRightY() - margin * 1.4f - leading*2 );
        }
        for (String line: lines)
        {
            try{
                contentStream.showText(line);
            }catch(java.lang.IllegalArgumentException ex){
                contentStream.showText(excludeUnsupportedSymbols(font_standart, line)); 
            }
            if (line.trim().isEmpty()) 
                contentStream.newLineAtOffset(0, -leading/2);
            else    
                contentStream.newLineAtOffset(0, -leading);
        }
        contentStream.endText(); 
        
        /* print page number */
        contentStream.beginText();
        contentStream.setFont(font_standart, fontSize);
        contentStream.newLineAtOffset(mediabox.getWidth() / 2, margin * 0.5f );
        contentStream.showText(String.valueOf(number));
        contentStream.endText();
        
        contentStream.close();
        
        return page;
    }
    
    /**
     * If there were an exception about symbol w/o glyph - exclude this symbol
     * @param font - target font 
     * @param text - text with problem symbol
     * @return new text w/o corrupted symbol
     * @throws IOException 
     */
    public String excludeUnsupportedSymbols(PDFont font, String text) throws IOException{
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
        
        //System.out.println(text);
        //System.out.println(new_text);
        
        return new_text;
    }
    
    public float getStringWidth(PDFont font, String text) throws IOException{
        try{
            return font.getStringWidth(text);
        } catch (java.lang.IllegalArgumentException ex){
            return font.getStringWidth(excludeUnsupportedSymbols(font, text));
        }
    }
    
    public List<String> calculateLines(PDFont font, String input) throws IOException{
        List<String> lines = new ArrayList<String>();
        int lastSpace = -1;

        String [] newLines = input.split("\n");
        for(String text : newLines){
            text = "    " + text; /* new line indent */
            while (text.length() > 0)
            {
                int spaceIndex = text.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0)
                    spaceIndex = text.length();
                String subString = text.substring(0, spaceIndex);
                
                float size = fontSize * getStringWidth(font, subString) / 1000;

                if (size > width)
                {
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
                else if (spaceIndex == text.length())
                {
                    lines.add(text);
                    text = "";
                }
                else
                {
                    lastSpace = spaceIndex;
                }
            }
        }
        return lines;
    }
    
}


