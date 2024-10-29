package main.services.interfaces

import main.models.dtos.request.CompanyDTO
import main.models.entities.Company

interface CompanyService extends SearchableService<Company, CompanyDTO> {

}