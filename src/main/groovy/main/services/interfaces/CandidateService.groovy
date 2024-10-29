package main.services.interfaces

import main.models.dtos.request.CandidateDTO
import main.models.entities.Candidate

interface CandidateService extends SearchableService<Candidate, CandidateDTO> {
    Candidate authenticate(String email, String password)
}