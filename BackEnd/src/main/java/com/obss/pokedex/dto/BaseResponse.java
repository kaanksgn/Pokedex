package com.obss.pokedex.dto;

import com.obss.pokedex.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


@Data
public class BaseResponse<T> {
    private Long code;

    private String message;
    private T payload;

    public BaseResponse(Long code, T payload) {
        this.code = code;
        this.payload = payload;
    }

    public BaseResponse(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(Long code) {
        this.code = code;
    }

    public BaseResponse(T payload) {
        this.code = 200L;
        this.payload = payload;
    }

    public BaseResponse(String message) {
        this.code = 200L;
        this.message = message;
    }
    

}
