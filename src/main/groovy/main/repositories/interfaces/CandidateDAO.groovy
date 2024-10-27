package main.repositories.interfaces

import main.models.dtos.request.CandidateDTO
import main.models.entities.Candidate

interface CandidateDAO extends DAO<Candidate, CandidateDTO> {

    Set<Candidate> getByAddressId(int addressId)
}