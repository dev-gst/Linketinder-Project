package application.mocks

import application.models.dtos.request.AddressDTO
import application.models.entities.Address
import spock.lang.Specification

class AddressMockFactory extends Specification {

    AddressDTO createAddressDTOMock(int n) {
        AddressDTO addressDTO = Mock(AddressDTO)

        addressDTO.country >> "Brazil $n"
        addressDTO.region >> "SP $n"
        addressDTO.city >> "São Paulo $n"
        addressDTO.neighborhood >> "Centro $n"
        addressDTO.street >> "Rua $n"
        addressDTO.number >> "$n"
        addressDTO.zipCode >> "12345-678$n"

        addressDTO.toString() >> "${addressDTO.street}, ${addressDTO.number} - ${addressDTO.neighborhood}," +
                " ${addressDTO.city}, ${addressDTO.region}, ${addressDTO.country}, ${addressDTO.zipCode}"

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

        address.toString() >> "${address.street}, ${address.number} - ${address.neighborhood}," +
                " ${address.city}, ${address.region}, ${address.country}, ${address.zipCode}"

        return address
    }
}
