import { Candidate } from "../models/Candidate";

export class CandidateService {
    
    private currentID: bigint;
    private candidateList: Candidate[];

    constructor(candidateList: Candidate[]) {
        this.currentID = BigInt(1);
        this.candidateList = candidateList;
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
        this.candidateList.push(candidate);
    }

    public getByEmail(email?: string): Candidate | null {
        if (!email) return null;
        
        const candidate: Candidate | undefined = this.candidateList.find(p => p.email === email);
        
        return candidate ? candidate : null;
    }

    public getByCPF(CPF?: string): Candidate | null {
        if (!CPF) return null;
        
        const candidate: Candidate | undefined = this.candidateList.find(p => p.CPF === CPF);
        
        return candidate ? candidate : null;
    }

}