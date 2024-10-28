package main.services.interfaces

import main.models.dtos.request.AddressDTO
import main.models.entities.Address

interface AddressService extends SearchableService<Address, AddressDTO> {

}