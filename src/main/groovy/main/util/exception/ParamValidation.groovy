package main.util.exception

class ParamValidation {

    static void requireNonNull(Object obj, String message) {
        if (obj == null) throw new IllegalArgumentException(message)
    }
}
