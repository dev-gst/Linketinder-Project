package mocks

import main.models.dtos.request.address.AddressDTO
import main.models.dtos.request.address.DetailedAddressDTO
import spock.lang.Specification

class AddressMock extends Specification {

    AddressDTO createAddressDTOMock(int n) {
        AddressDTO addressDTO = Mock(AddressDTO)

        addressDTO.country >> "Brazil $n"
        addressDTO.region >> "SP $n"
        addressDTO.city >> "São Paulo $n"
        addressDTO.detailedAddressDTO >> createDetailedAddressDTOMock(n)

        return addressDTO
    }

    DetailedAddressDTO createDetailedAddressDTOMock(int n) {
        DetailedAddressDTO detailedAddressDTO = Mock(DetailedAddressDTO)

        detailedAddressDTO.neighborhood >> "Centro $n"
        detailedAddressDTO.street >> "Rua $n"
        detailedAddressDTO.number >> "$n"
        detailedAddressDTO.zipCode >> "12345-678$n"

        return detailedAddressDTO
    }
}
