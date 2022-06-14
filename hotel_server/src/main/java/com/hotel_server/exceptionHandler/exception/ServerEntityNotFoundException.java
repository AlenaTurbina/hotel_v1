package com.hotel_server.exceptionHandler.exception;


public class ServerEntityNotFoundException extends RuntimeException {

    public ServerEntityNotFoundException(Integer id) {
        super("Entity is not found, id="+id);
    }

}
