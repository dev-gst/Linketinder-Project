package main.services.interfaces

import main.models.dtos.request.company.CompanyDTO
import main.models.entities.company.Company

interface CompanyService extends SearchableService<Company, CompanyDTO> {

}