package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePageDto<T> extends PageImpl<T> {
   private final PageMetada page;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponsePageDto(@JsonProperty("content") List<T> content,
                           @JsonProperty("page") PageMetada page) {
        super(content, PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
        this.page = page;
    }
    public PageMetada getPage(){
        return page;
   }
}
