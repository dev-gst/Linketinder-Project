import { Candidate } from "../models/Candidate";
import { CandidateRepository } from "../repositories/CandidateRepository";

export class CandidateService {
    
    private candidateRepository: CandidateRepository;
    private currentID: bigint;

    constructor(candidateRepository: CandidateRepository) {
        this.currentID = BigInt(1);
        this.candidateRepository = candidateRepository;
    }

    public create(name?: string, email?: string, description?: string, address?: string, skills?: string[],
        education?: string, age?: number, CPF?: string): Candidate {

        const candidate: Candidate = new Candidate();
        candidate.id = this.currentID;
        candidate.name = name;
        candidate.email = email;
        candidate.description = description;
        candidate.address = address;
        candidate.skills = skills;
        candidate.education = education;
        candidate.age = age;
        candidate.CPF = CPF;

        this.currentID++;

        return candidate;
    }

    public save(candidate: Candidate) {
        this.candidateRepository.save(candidate);
        this.candidateRepository.persist();
    }

    public getByEmail(email?: string): Candidate | null {
        return this.candidateRepository.getByEmail(email);
    }

    public getByCPF(CPF?: string): Candidate | null {
       return this.candidateRepository.getByCPF(CPF);
    }

}