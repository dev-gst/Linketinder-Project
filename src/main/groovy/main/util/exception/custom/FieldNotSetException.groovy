package main.util.exception.custom

class FieldNotSetException extends RuntimeException {

    FieldNotSetException(String message) {
        super(message)
    }
}
