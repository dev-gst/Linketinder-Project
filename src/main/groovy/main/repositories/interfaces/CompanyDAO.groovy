package main.repositories.interfaces

import main.models.dtos.request.company.CompanyDTO
import main.models.entities.company.Company

interface CompanyDAO extends DAO<Company, CompanyDTO> {

}