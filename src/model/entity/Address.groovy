package model.entity

class Address {
    String country
    String state
    String zipCode

    String toString() {
        return "País: $country, " +
                "\nEstado: $state, " +
                "\nCEP: $zipCode"
    }
}
