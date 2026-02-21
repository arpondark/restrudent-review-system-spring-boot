package com.shazan.restrudent.expections;

public class BaseExpection extends RuntimeException {
    public BaseExpection() {
    }

    public BaseExpection(String message) {
        super(message);
    }

    public BaseExpection(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseExpection(Throwable cause) {
        super(cause);
    }


}
