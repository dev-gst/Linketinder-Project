package application.repositories.interfaces

import application.models.dtos.request.CompanyDTO
import application.models.entities.Company

interface CompanyDAO extends DAO<Company, CompanyDTO> {

    Set<Company> getByJobOpeningId(int jobOpeningId)

    Company authenticate(String email, String password)
}