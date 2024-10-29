package main.util.exception.custom

class ClassNotFoundException extends RuntimeException {

    ClassNotFoundException(String message) {
        super(message)
    }
}
