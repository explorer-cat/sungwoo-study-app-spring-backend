package testapp.demo.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResDto {
    private final int status;
    private final String message;
    private final List<?> data;


    public ResDto(int status, String message, List<?> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // getters and setters
}