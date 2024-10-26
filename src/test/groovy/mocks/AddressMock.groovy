package mocks

import main.models.dtos.request.address.AddressDTO
import main.models.dtos.request.address.DetailedAddressDTO
import main.models.entities.address.Address
import main.models.entities.address.DetailedAddress
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

    Address createAddressMock(int n) {
        Address address = Mock(Address)

        address.id >> n
        address.country >> "Brazil $n"
        address.region >> "SP $n"
        address.city >> "São Paulo $n"
        address.detailedAddress >> createDetailedAddressMock(n)

        return address
    }

    DetailedAddress createDetailedAddressMock(int n) {
        DetailedAddress detailedAddress = Mock(DetailedAddress)

        detailedAddress.id >> n
        detailedAddress.neighborhood >> "Centro $n"
        detailedAddress.street >> "Rua $n"
        detailedAddress.number >> "$n"
        detailedAddress.zipCode >> "12345-678$n"

        return detailedAddress
    }
}
