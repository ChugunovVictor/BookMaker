package org.chugunov.books;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.chugunov.utility.PreText;
import org.chugunov.utility.ArabicToRoman;
import org.chugunov.utility.PostText;
import org.chugunov.utility.Text;
import org.chugunov.model.Process;

public class Novel extends Book2 {

    List<PostText> paragraph;
    List<PostText> toc;
    
    List<PDPage> content_pages;

    public Novel(PDDocument document, Process process) {
        super(document, process);
    }

    private List<PDPage> createTableOfContents(){ 
        ArrayList<String> strings = new ArrayList<>();
        for(PostText pt : paragraph){
            float max_width = etalonPage.getMediaBox().getWidth() - margin * 3;
            float still_free = max_width - getStringWidth(font_standart, String.valueOf(pt.title + pt.page_number)) / 1000 * fontSize;
            float point_width = getStringWidth(font_standart, ".")  / 1000 * fontSize;

            String points = "";
            for (int i =0 ; i < still_free / point_width; i++){
                points += ".";
            }
            strings.add(pt.title + points + pt.page_number);
        }
        toc = prepare( Arrays.asList(new PostText[]{new PostText(1, "Table of Contents", strings)}));
        return createPages(true);
    }
    
    private List<PDPage> createPages(boolean is_toc){
        List<PDPage> result = new ArrayList<>();
        for(PostText p: is_toc ? toc : paragraph)
            result.addAll(createParagraph(p, is_toc));
        return result;
    }
    
    private List<PDPage> createParagraph(PostText p, boolean is_toc){
        List<PDPage> result = new ArrayList<>();
        int page_counter = p.page_number;
        
        int place_for_title = 2;
        int strings_left = p.context.size() + place_for_title;
        int from = 0;
        int to = process.getPreview().getNumberOfBlocksOnPage() - place_for_title;
        
        while( strings_left > process.getPreview().getNumberOfBlocksOnPage() ){
            result.add(createPage( p.title, page_counter, p.context.subList(from, to),
                (process.getPreview().getNumberOfBlocksOnPage() - 2) == (to - from), is_toc
            ));
            from = to;
            to += process.getPreview().getNumberOfBlocksOnPage();
            strings_left -= process.getPreview().getNumberOfBlocksOnPage();
            page_counter ++;
        }
        if (strings_left != 0)
            result.add(createPage( p.title, page_counter, p.context.subList(from, p.context.size()),
                strings_left == (p.context.size() + place_for_title), is_toc    
            ));
            
        return result;
    }
     
    private PDPage createPage(String title, int page_number, List<String> strings, 
            boolean is_Head_Start, boolean is_toc){
        PDPage page = new PDPage();
        try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
        
            PDRectangle mediabox = page.getMediaBox();
                    
            printDecor(contentStream, mediabox, title, 
                    is_toc ? ArabicToRoman.convert(page_number - 1) : String.valueOf(page_number));
        
            /* print title if it is necessary */
            if (is_Head_Start) {
                printText(new String[]{ title }, font_bold, 20, mediabox.getLowerLeftX() + margin, 
                    mediabox.getUpperRightY() - margin * 1.4f , 0, contentStream);
            }

            /* print content text */
            float leading = 1.5f * fontSize;
              
            if(is_toc)
                printLink(strings.toArray(new String[]{}), font_standart, fontSize, 
                    mediabox.getLowerLeftX() + margin, 
                    mediabox.getUpperRightY() - margin * 1.4f - (is_Head_Start ?  leading*2 : 0),
                    -leading, contentStream, page, content_pages);
            else    
                printText(strings.toArray(new String[]{}), font_standart, fontSize, 
                    mediabox.getLowerLeftX() + margin, 
                    mediabox.getUpperRightY() - margin * 1.4f - (is_Head_Start ?  leading*2 : 0),
                    -leading, contentStream);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return page;
    }
    
    @Override
    public List<PDPage> createContent(ArrayList <PreText> materialForBook) {
        List<PDPage> result = new ArrayList<>();
        paragraph = prepare(materialForBook);
        content_pages = createPages(false);
        result.addAll(createTableOfContents());
        result.addAll(content_pages);
        return result;
    }

    private void drawLine(PDPageContentStream contentStream, float startX, float startY, float endX, float endY) throws IOException {
        contentStream.moveTo(startX, startY);
        contentStream.lineTo(endX, endY);
        contentStream.stroke();
    }

    public void printDecor(PDPageContentStream contentStream, PDRectangle mediabox, 
            String title, String page_number) throws IOException{
        /* draw decor top line with paragraph title */
        int decor_text_font_size = 9;
        contentStream.setLineWidth(0.5f);

        drawLine(contentStream,margin / 2, mediabox.getHeight() - margin * 0.8f,
                mediabox.getWidth() - margin / 2, mediabox.getHeight() - margin * 0.8f);


        
        printText(new String[]{ title }, font_italic, decor_text_font_size, 
                mediabox.getWidth() - margin - getStringWidth(font_italic, title) / 1000 * decor_text_font_size, 
                mediabox.getHeight() - margin * 0.8f + 5, 0, contentStream);
        
        /* print page number */
        printPageNumber(contentStream, mediabox, page_number);
    }

    public static void printPageNumber(PDPageContentStream contentStream, PDRectangle mediabox, String page_number) throws IOException{
        printText(new String[]{ String.valueOf(page_number) }, font_standart, fontSize,
                mediabox.getWidth() / 2, margin * 0.2f, 0, contentStream);
    }

    
    public List<PostText> prepare(List <? extends Text> materialForBook){
        int page_counter = 2;
        //int pages_to_toc = calculatePages(materialForBook.size(), number_of_rows_on_page);
        List<PostText> prepare_paragraph = new ArrayList<>();
        
        for(Text p : materialForBook){
            List<String> strings = new ArrayList<>();
            for(String c : p.context){
                strings.addAll(calculateLines(font_standart, c));
            }
            prepare_paragraph.add(new PostText(page_counter, p.title, strings));
            page_counter += calculatePages(strings.size(), process.getPreview().getNumberOfBlocksOnPage());
        }
        
        return prepare_paragraph;
    }
        
    public int calculatePages(int rows_count, int number_of_rows_on_page){
        float current_value = (float)( rows_count + 2 ) / number_of_rows_on_page; /* +2 for title which takes 2 rows*/
        int pages_to_chapter = Math.round(current_value) ;
        if (pages_to_chapter > current_value) return pages_to_chapter;
        else return pages_to_chapter + 1;
    }

}
