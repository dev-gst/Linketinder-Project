package application.repositories.interfaces

import application.models.dtos.request.CandidateDTO
import application.models.entities.Candidate

interface CandidateDAO extends DAO<Candidate, CandidateDTO> {

    Set<Candidate> getByAddressId(int addressId)

    Candidate authenticate(String email, String password)
}