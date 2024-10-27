package main.repositories.interfaces

import main.models.dtos.request.address.AddressDTO
import main.models.entities.address.Address

interface AddressDAO extends DAO<Address, AddressDTO> {

    Set<Address> getByCompanyId(int companyId)

    Set<Address> getByJobOpeningId(int jobOpeningId)

    Set<Address> getByCandidateId(int candidateId)
}