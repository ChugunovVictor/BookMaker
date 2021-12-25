package org.chugunov.utility;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.File;
import java.util.Arrays;

import static org.chugunov.books.Novel.printPageNumber;

public class Print {

    public static int devide (int pages){
        int result_pages = 0;
        if( pages % 4 == 0 ) result_pages = pages;
        if( (pages + 1) % 4 == 0 ) result_pages = pages + 1;
        if( (pages + 2) % 4 == 0 ) result_pages = pages + 2;
        if( (pages + 3) % 4 == 0 ) result_pages = pages + 3;

        // I think that is the best to group in ~40 pages -> 10 lists
        if( result_pages < 45 ) // if we have less - than no point to devide it
            return pages;

        for( int i = 44; i > 22; i-=4){
            if( result_pages % i == 0 ) return i;
        }

        // not always is possible to devide on equal parts
        // for example "Worm" has 3451 pages
        // 3451 -> 3452 as 3452 % 4 == 0
        // however 3452 / 4 = 863 -> its a prime number - cannot be devided more
        // so it would be impossible to devide 3452 pages into pieces like ~40 pages

        for( int i = 44; i > 20; i-=4){
            if( result_pages % i >= (i-5) && result_pages % i <= (i+5)
            ) return i;
        }

        // or return default
        return 40;
    }

    public static int[] sort(int start, int end){
        // 4 pages on each list of a4
        // -1 will symbolized empty page

        int pieces_of_paper = (( end - start + 1 ) / 4 ) + (( end - start + 1 ) % 4 == 0 ? 0 : 1) ;
        if( start == end ) pieces_of_paper = 1;

        int sorted[] = new int[ pieces_of_paper * 4 ];
        int end_pointer = start + pieces_of_paper * 4 - 1  , start_pointer = start;

        for( int i = 0; i < pieces_of_paper; i++ ){ // fill
            sorted[ i * 4 ]     = end_pointer--;
            sorted[ i * 4 + 1 ] = start_pointer++;
            sorted[ i * 4 + 2 ] = start_pointer++;
            sorted[ i * 4 + 3 ] = end_pointer--;
        }
        for ( int i=0; i < sorted.length; i++ ){    // mark empties
            if( sorted[i] > end ) sorted[i] = -1;
        }
        return sorted;
    }

    public static void addPageNumbers( String in, String out  ){
        try {
            PDDocument document = Loader.loadPDF(new File(in));

            for( int i = 7; i < document.getNumberOfPages() - 3; i++ ){
                PDPage page = document.getPage(i);
                PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
                printPageNumber(contentStream, page.getMediaBox(), String.valueOf(i+1));
                contentStream.close();
            }
            document.save(new File(out));
            document.close();
        }catch (Exception ex){

        }
    }

    public static void prepareToPrint( String in, String out ){
        try {
            PDDocument old_document = Loader.loadPDF(new File(in));
            PDDocument new_document = new PDDocument();

            int interval = Print.devide(old_document.getNumberOfPages());
            for (int start = 1; start < old_document.getNumberOfPages(); start += interval){
                int end = (start + interval - 1)<= old_document.getNumberOfPages() ? (start + interval - 1) : old_document.getNumberOfPages();
                int[]pages = Print.sort(start, end);

                System.out.println(Arrays.toString(pages));

                for( int i = 0; i < pages.length; i++ ){
                    if( pages[i] != -1)
                        new_document.addPage( old_document.getPage( pages[i]-1 ) );
                    else
                        new_document.addPage(new PDPage());
                }
            }

            new_document.save(new File(out));
            new_document.close();

            old_document.close();

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
