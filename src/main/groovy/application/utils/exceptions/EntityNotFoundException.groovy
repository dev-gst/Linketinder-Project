package application.utils.exceptions

class EntityNotFoundException extends RuntimeException {

    EntityNotFoundException(String message) {
        super(message)
    }
}
