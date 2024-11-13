package application.services.interfaces

import application.models.dtos.request.CandidateDTO
import application.models.entities.Candidate

interface CandidateService extends SearchableService<Candidate, CandidateDTO> {
    Candidate authenticate(String email, String password)
}