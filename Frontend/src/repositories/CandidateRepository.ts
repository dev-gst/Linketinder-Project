import { Candidate } from "../models/Candidate";

export class CandidateRepository {

    private candidateStorage: string;
    private candidateList: Candidate[];

    constructor(candidateList: Candidate[]) {
        this.candidateList = candidateList;
        this.candidateStorage = 'candidateStorage';
    }

    public persist(): void {
        const jsonData: string = JSON.stringify(this.candidateList, (k, v) => {
            return typeof v === 'bigint'? v.toString() :  v;
        });

        localStorage.setItem(this.candidateStorage, jsonData);
    }

    public save(candidate: Candidate) {
        this.candidateList.push(candidate);
    }

    public load(): void {
        const rawCandidateList: string | null =  localStorage.getItem(this.candidateStorage);
        if (rawCandidateList) {
            const parsedCandidateList: Candidate[] = JSON.parse(rawCandidateList, (k, v) => {
                return (typeof k === 'string' && k === 'id') ? BigInt(v) : v;
            }) as Candidate[];
            
            for (let candidate of parsedCandidateList) {
                this.candidateList.push(candidate);
            }
        }
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