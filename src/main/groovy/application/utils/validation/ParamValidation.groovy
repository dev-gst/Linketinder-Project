package application.utils.validation

class ParamValidation {

    static void requireNonNull(Object obj, String message) {
        if (obj == null) throw new IllegalArgumentException(message)
    }

    static void requireNonBlank(String str, String message) {
        if (str == null) throw new IllegalArgumentException(message)
        if (str.isBlank()) throw new IllegalArgumentException(message)
    }

    static void requirePositive(Integer num, String message) {
        if (num == null) throw new IllegalArgumentException(message)
        if (num <= 0) throw new IllegalArgumentException(message)
    }

    static void requireNonEmpty(Collection<?> collection, String message) {
        if (collection == null) throw new IllegalArgumentException(message)
        if (collection.isEmpty()) throw new IllegalArgumentException(message)
    }
}
