package com.devtiro.restaurant.exceptions;

public class StorageException extends BaseException{
    public StorageException() {
    }

    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
