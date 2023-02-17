package testapp.demo.utils;

import java.util.ArrayList;

public class ResDto {
    private final int status;
    private final String message;
    private final ArrayList<?> data;

    public ResDto(int status, String message, ArrayList<?> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // getters and setters
}