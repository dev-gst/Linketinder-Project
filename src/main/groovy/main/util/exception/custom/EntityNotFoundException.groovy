package main.util.exception.custom

class EntityNotFoundException extends RuntimeException {

    EntityNotFoundException(String message) {
        super(message)
    }
}
