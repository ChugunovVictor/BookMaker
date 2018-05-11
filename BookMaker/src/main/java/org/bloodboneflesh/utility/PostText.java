package org.bloodboneflesh.utility;

import java.util.List;

public class PostText extends Text{
    public int page_number;
    
    public PostText(int page_number, String title, List<String> strings) {
        this.page_number = page_number;
        this.title = title;
        this.context = strings;
    }
}
