
package com.myproject.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// record: 데이터를 담기 위한 클래스를 간결하게 작성하는 문법
// JsonIgnoreProperties(ignoreUnknown = true)
//  -> 우리가 정의하지 않은 필드를 Api가 반환해도 무시하고 필요한 값만 읽음
@JsonIgnoreProperties(ignoreUnknown = true)
public record Yes24BookResponse(
        boolean success,
        String message,
        String errorCode,
        Data data
) 
{
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(
            List<BookItem> items
    ) 
    
    {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record BookItem(
            Long itemId,
            String title,
            String author,
            String publisher,
            String isbn13,
            String publishDate,
            String cover,
            String link,
            ContentDetail contentDetail
    ) 
    
    {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ContentDetail(
            String bookIntroduction
    ) 
    
    {
    }
}
