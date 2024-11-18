package application.services.interfaces

import application.models.dtos.request.AddressDTO
import application.models.entities.Address

interface AddressService extends SearchableService<Address, AddressDTO> {

}