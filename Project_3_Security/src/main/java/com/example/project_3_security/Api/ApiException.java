package com.example.project_3_security.Api;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
