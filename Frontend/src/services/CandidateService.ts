import { Candidate } from "../models/Candidate";
import { CandidateRepository } from "../repositories/CandidateRepository";

export class CandidateService {

    private _candidateRepository: CandidateRepository;
    private _currentID: bigint;

    constructor(candidateRepository: CandidateRepository) {
        this._candidateRepository = candidateRepository;
        this._currentID = this._candidateRepository.lastCandidateID + BigInt(1);
    }

    get candidateRepository(): CandidateRepository {
        return this._candidateRepository;
    }

    public create(name?: string, email?: string, description?: string, address?: string, skills?: string[],
        education?: string, age?: number, CPF?: string): Candidate {

        const candidate: Candidate = new Candidate();
        candidate.id = this._currentID;
        candidate.name = name;
        candidate.email = email;
        candidate.description = description;
        candidate.address = address;
        candidate.skills = skills;
        candidate.education = education;
        candidate.age = age;
        candidate.CPF = CPF;

        this._currentID++;

        return candidate;
    }

    public save(candidate: Candidate) {
        if (this.getByEmail(candidate.email) ||
            this.getByCPF(candidate.CPF)) {
            console.error("Candidate already exists");
            return;
        }

        this._candidateRepository.save(candidate);
        this._candidateRepository.persist();
    }

    public getByEmail(email?: string): Candidate | null {
        return this._candidateRepository.getByEmail(email);
    }

    public getByCPF(CPF?: string): Candidate | null {
        return this._candidateRepository.getByCPF(CPF);
    }

    public getALL(): Candidate[] {
        return [... this._candidateRepository.candidateList];
    }
}