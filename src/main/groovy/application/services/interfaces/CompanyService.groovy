package application.services.interfaces

import application.models.dtos.request.CompanyDTO
import application.models.entities.Company

interface CompanyService extends SearchableService<Company, CompanyDTO> {

    Company authenticate(String email, String password)
}