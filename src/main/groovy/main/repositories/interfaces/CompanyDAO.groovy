package main.repositories.interfaces

import main.models.dtos.request.CompanyDTO
import main.models.entities.Company

interface CompanyDAO extends DAO<Company, CompanyDTO> {

    Set<Company> getByJobOpeningId(int jobOpeningId)

    Company authenticate(String email, String password)
}