package main.services

import main.models.dtos.anonresponse.AnonAddressDTO
import main.models.dtos.request.address.AddressDTO
import main.models.entities.address.Address
import main.repositories.AddressDAO
import main.services.interfaces.AnonService
import main.services.interfaces.SearchableService
import main.util.exception.ParamValidation
import main.util.exception.custom.EntityNotFoundException

class AddressService implements SearchableService<Address, AddressDTO>,
        AnonService<AnonAddressDTO> {

    AddressDAO addressDAO

    AddressService(AddressDAO addressDAO) {
        ParamValidation.requireNonNull(addressDAO, "AddressDAO cannot be null")

        this.addressDAO = addressDAO
    }

    @Override
    Address getById(int id) {
        ParamValidation.requirePositive(id, "Address ID must be greater than 0")

        return addressDAO.getById(id)
    }

    @Override
    AnonAddressDTO getAnonById(int id) {
        ParamValidation.requirePositive(id, "Address ID must be greater than 0")

        Address address = getById(id)
        AnonAddressDTO anonAddressDTO = new AnonAddressDTO(
                address.getCountry(),
                address.getRegion(),
                address.getCity()
        )

        return anonAddressDTO
    }

    @Override
    Set<Address> findByField(String fieldName, String fieldValue) {
        ParamValidation.requireNonBlank(fieldName, "Field name cannot be blank or null")
        ParamValidation.requireNonBlank(fieldValue, "Field value cannot be blank or null")

        return addressDAO.findByField(fieldName, fieldValue)
    }

    @Override
    Set<Address> getAll() {
        return addressDAO.getAll()
    }

    @Override
    Set<AnonAddressDTO> getAllAnon() {
        Set<Address> addresses = getAll()
        Set<AnonAddressDTO> anonAddresses = new LinkedHashSet<>()

        for (Address address : addresses) {
            AnonAddressDTO anonAddressDTO = new AnonAddressDTO(
                    address.getCountry(),
                    address.getRegion(),
                    address.getCity()
            )

            anonAddresses.add(anonAddressDTO)
        }

        return anonAddresses
    }

    @Override
    int save(AddressDTO addressDTO) {
        ParamValidation.requireNonNull(addressDTO, "AddressDTO cannot be null")

        return addressDAO.save(addressDTO)
    }

    @Override
    Set<Integer> saveAll(Set<AddressDTO> addressDTOS) {
        ParamValidation.requireNonEmpty(addressDTOS, "AddressDTO set cannot be null or empty")

        Set<Integer> addresses = new LinkedHashSet<>()
        for (AddressDTO addressDTO : addressDTOS) {
            addresses.add(Integer.valueOf(save(addressDTO)))
        }

        return addresses
    }

    @Override
    Address updateById(int id, AddressDTO addressDTO) {
        ParamValidation.requirePositive(id, "Address ID must be greater than 0")
        ParamValidation.requireNonNull(addressDTO, "AddressDTO cannot be null")

        Address address = addressDAO.updateById(id, addressDTO)
        if (address == null) throw new EntityNotFoundException("Address with ID ${id} not found")

        return address
    }

    @Override
    void deleteById(int id) {
        ParamValidation.requirePositive(id, "Skill ID must be greater than 0")

        addressDAO.deleteById(id)
    }
}
