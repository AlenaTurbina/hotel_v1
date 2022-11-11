package com.hotel_server.exceptionHandler.exception;


import java.util.UUID;

public class ServerEntityNotFoundException extends RuntimeException {

    public ServerEntityNotFoundException(UUID id) {
        super("Entity is not found, id="+id);
    }

}
