package main.services.interfaces

import main.models.dtos.request.address.AddressDTO
import main.models.entities.address.Address

interface AddressService extends SearchableService<Address, AddressDTO> {

}