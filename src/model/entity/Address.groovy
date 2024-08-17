package model.entity

class Address {
    String country
    String state
    String zipCode

    String toString() {
        return "Estado: $state, País: $country, CEP: $zipCode"
    }
}
