package application.repositories.interfaces

import application.models.dtos.request.AddressDTO
import application.models.entities.Address

interface AddressDAO extends DAO<Address, AddressDTO> {

    Set<Address> getByCompanyId(int companyId)

    Set<Address> getByJobOpeningId(int jobOpeningId)

    Set<Address> getByCandidateId(int candidateId)
}