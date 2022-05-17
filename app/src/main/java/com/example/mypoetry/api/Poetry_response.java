package com.example.mypoetry.api;

import com.example.mypoetry.modelclass.poetry_class;

import java.util.List;

public class Poetry_response {
    String status;
    String message;

    List<poetry_class> data;

    public Poetry_response(String status, String message, List<poetry_class> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<poetry_class> getData() {
        return data;
    }

    public void setData(List<poetry_class> data) {
        this.data = data;
    }
}
