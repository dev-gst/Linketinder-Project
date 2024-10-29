package mocks

import main.models.dtos.request.AddressDTO
import main.models.entities.Address
import spock.lang.Specification

class AddressMock extends Specification {

    AddressDTO createAddressDTOMock(int n) {
        AddressDTO addressDTO = Mock(AddressDTO)

        addressDTO.country >> "Brazil $n"
        addressDTO.region >> "SP $n"
        addressDTO.city >> "São Paulo $n"
        addressDTO.neighborhood >> "Centro $n"
        addressDTO.street >> "Rua $n"
        addressDTO.number >> "$n"
        addressDTO.zipCode >> "12345-678$n"

        return addressDTO
    }

    Address createAddressMock(int n) {
        Address address = Mock(Address)

        address.id >> n
        address.country >> "Brazil $n"
        address.region >> "SP $n"
        address.city >> "São Paulo $n"
        address.neighborhood >> "Centro $n"
        address.street >> "Rua $n"
        address.number >> "$n"
        address.zipCode >> "12345-678$n"

        return address
    }
}
