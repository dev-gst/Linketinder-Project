import { Candidate } from "../models/Candidate";
import { CandidateRepository } from "../repositories/CandidateRepository";

export class CandidateService {

    private _candidateRepository: CandidateRepository;
    private _currentID: bigint;

    constructor(candidateRepository: CandidateRepository) {
        this._candidateRepository = candidateRepository;
        this._currentID = this._candidateRepository.lastCandidateID;
    }

    get candidateRepository(): CandidateRepository {
        return this._candidateRepository;
    }

    public create(
        name: string,
        description: string,
        email: string,
        linkedin: string,
        phone: string,
        street: string,
        number: string,
        district: string,
        city: string,
        state: string,
        zip: string,
        age: number,
        education: string,
        CPF: string,
        skills: string[]
    ): Candidate {

        const candidate: Candidate = new Candidate(
            this._currentID,
            name,
            description,
            email,
            street,
            linkedin,
            phone,
            number,
            district,
            city,
            state,
            zip,
            age,
            education,
            CPF,
            skills
        );

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