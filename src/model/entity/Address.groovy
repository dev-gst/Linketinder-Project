package model.entity

class Address {
    String country
    String state
    String CEP

    String toString() {
        return "Estado: $state, País: $country, CEP: $CEP"
    }
}
