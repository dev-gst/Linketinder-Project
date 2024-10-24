package main.services

import main.models.dtos.anonresponse.AnonAddressDTO
import main.models.dtos.request.address.AddressDTO
import main.models.dtos.request.address.DetailedAddressDTO
import main.models.entities.address.Address
import main.models.entities.address.DetailedAddress
import main.repositories.AddressDAO
import main.util.exception.custom.EntityNotFoundException
import spock.lang.Specification

class AddressServiceTest extends Specification {

    Address address1
    Address address2
    AddressDTO addressDTO1
    AddressDTO addressDTO2
    AddressDAO addressDAO
    AddressService addressService

    void setup() {
        address1 = new Address(
                1,
                "Brasil",
                "SP",
                "São Paulo",
                new DetailedAddress(
                        "Vila Mariana",
                        "Rua Domingos de Morais",
                        "123",
                        "04010-000"
                )
        )

        address2 = new Address(
                2,
                "Brasil",
                "SP",
                "São Paulo",
                new DetailedAddress(
                        "Vila Mariana",
                        "Rua Domingos de Morais",
                        "123",
                        "04010-000"
                )
        )

        addressDTO1 = new AddressDTO(
                "Brasil",
                "SP",
                "São Paulo",
                new DetailedAddressDTO(
                        "Vila Mariana",
                        "Rua Domingos de Morais",
                        "123",
                        "04010-000"
                )
        )

        addressDTO2 = new AddressDTO(
                "Brasil",
                "RJ",
                "Rio de Janeiro",
                new DetailedAddressDTO(
                        "Centro",
                        "Rua da Carioca",
                        "321",
                        "20011-000"
                )
        )

        addressDAO = Mock(AddressDAO)
        addressService = new AddressService(addressDAO)
    }


    def "get by id returns address"() {
        given:
        addressDAO.getById(1) >> address1

        when:
        Address result = addressService.getById(1)

        then:
        result == address1
        1 * addressDAO.getById(1) >> address1
    }

    def "get by id throws exception when id is less than 1"() {
        when:
        addressService.getById(0)

        then:
        thrown(IllegalArgumentException)
    }

    def "get anon by id returns anon address"() {
        given:
        addressDAO.getById(1) >> address1

        when:
        AnonAddressDTO result = addressService.getAnonById(1)

        then:
        result.country == "Brasil"
        result.region == "SP"
        result.city == "São Paulo"
        1 * addressDAO.getById(1) >> address1
    }

    def "get anon by id throws exception when id is less than 1"() {
        when:
        addressService.getAnonById(0)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field returns addresses"() {
        given:
        Set<Address> addresses = new LinkedHashSet<>()
        addresses.add(address1)
        addresses.add(address2)
        addressDAO.getByField("country", "Brasil") >> addresses

        when:
        Set<Address> result = addressService.getByField("country", "Brasil")

        then:
        result.size() == 2
        result.contains(address1)
        result.contains(address2)
        1 * addressDAO.getByField("country", "Brasil") >> addresses
    }

    def "get by field throws exception when field name is null"() {
        when:
        addressService.getByField(null, "Brasil")

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field throws exception when field name is blank"() {
        when:
        addressService.getByField("", "Brasil")

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field throws exception when field value is null"() {
        when:
        addressService.getByField("country", null)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field throws exception when field value is blank"() {
        when:
        addressService.getByField("country", "")

        then:
        thrown(IllegalArgumentException)
    }

    def "get all returns all addresses"() {
        given:
        addressDAO.getAll() >> [address1, address2]

        when:
        Set<Address> result = addressService.getAll()

        then:
        result.size() == 2
        result.contains(address1)
        result.contains(address2)
        1 * addressDAO.getAll() >> [address1, address2]
    }

    def "get all returns empty set when there are no addresses"() {
        given:
        addressDAO.getAll() >> []

        when:
        Set<Address> result = addressService.getAll()

        then:
        result.size() == 0
        1 * addressDAO.getAll() >> []
    }

    def "get all anon returns all anon addresses"() {
        given:
        Set<Address> addresses = new LinkedHashSet<>()
        addresses.add(address1)
        addresses.add(address2)
        addressDAO.getAll() >> addresses

        when:
        Set<AnonAddressDTO> result = addressService.getAllAnon()

        then:
        result.size() == 2
        result[0].country == "Brasil"
        result[0].region == "SP"
        result[0].city == "São Paulo"

        result[1].country == "Brasil"
        result[1].region == "SP"
        result[1].city == "São Paulo"

        1 * addressDAO.getAll() >> addresses
    }

    def "get all anon returns empty set when there are no addresses"() {
        given:
        addressDAO.getAll() >> []

        when:
        Set<AnonAddressDTO> result = addressService.getAllAnon()

        then:
        result.size() == 0
        1 * addressDAO.getAll() >> []
    }

    def "save returns id"() {
        given:
        addressDAO.save(addressDTO1) >> 1

        when:
        int id = addressService.save(addressDTO1)

        then:
        id == 1
        1 * addressDAO.save(addressDTO1) >> 1
    }

    def "save all returns all addresses id"() {
        given:
        addressDAO.save(addressDTO1) >> 1
        addressDAO.save(addressDTO2) >> 2
        Set<AddressDTO> addressDTOSet = [addressDTO1, addressDTO2] as Set

        when:
        Set<Integer> result = addressService.saveAll(addressDTOSet)

        then:
        result.contains(1)
        result.contains(2)
        1 * addressDAO.save(addressDTO1) >> 1
        1 * addressDAO.save(addressDTO2) >> 2
    }

    def "save throws exception when addressDTO set is null"() {
        when:
        addressService.save(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "save all throws exception when addressDTO set is empty"() {
        when:
        addressService.saveAll([] as Set)

        then:
        thrown(IllegalArgumentException)
    }

    def "save all throws exception when addressDTO set is null"() {
        when:
        addressService.saveAll(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "update by id returns updated address"() {
        given:
        addressDAO.updateById(1, addressDTO2) >> address2

        when:
        Address result = addressService.updateById(1, addressDTO2)

        then:
        result == address2
        1 * addressDAO.updateById(1, addressDTO2) >> address2
    }

    def "update by id throws exception when addressDTO is null"() {
        when:
        addressService.updateById(1, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "update by id throws exception when id is less than 1"() {
        when:
        addressService.updateById(0, addressDTO1)

        then:
        thrown(IllegalArgumentException)
    }

    def "update by id throws exception when address is not found"() {
        given:
        addressDAO.updateById(1, addressDTO1) >> null

        when:
        addressService.updateById(1, addressDTO1)

        then:
        thrown(EntityNotFoundException)
        1 * addressDAO.updateById(1, addressDTO1) >> null
    }

    def "delete by id returns void"() {
        when:
        Address result = addressService.deleteById(1)

        then:
        result == null
        1 * addressDAO.deleteById(1)
    }

    def "delete by id throws exception when id is less than 1"() {
        when:
        addressService.deleteById(0)

        then:
        thrown(IllegalArgumentException)
    }
}
