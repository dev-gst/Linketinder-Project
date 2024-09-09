import { Candidate } from "../models/Candidate";

export class CandidateRepository {

    private _candidateStorage: string;
    private _candidateList: Candidate[];

    constructor(candidateList: Candidate[]) {
        this._candidateList = candidateList;
        this._candidateStorage = 'candidateStorage';

        this.load();
    }

    public get candidateList(): Candidate[] {
        return this._candidateList;
    }

    public get candidateStorage(): string {
        return this._candidateStorage;
    }

    public persist(): void {
        const jsonData: string = JSON.stringify(this._candidateList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem(this._candidateStorage, jsonData);
    }

    public save(candidate: Candidate) {
        this._candidateList.push(candidate);
    }

    public load(): void {
        const rawCandidateList: string | null = localStorage.getItem(this._candidateStorage);

        if (rawCandidateList) {
            let parsedCandidateList = JSON.parse(rawCandidateList, (k, v) => {
                return (k === '_id' || k === 'id') ? BigInt(v) : v;
            }) as any[];

            for (let data of parsedCandidateList) {
                const candidate: Candidate = new Candidate();
                candidate.id = data._id;
                candidate.name = data._name;
                candidate.description = data._description;
                candidate.email = data._email;
                candidate.address = data._address;
                candidate.skills = data._skills;
                candidate.education = data._education;
                candidate.age = data._age;
                candidate.CPF = data._CPF;

                this.candidateList.push(candidate);
            }
        }
    }

    public getByEmail(email?: string): Candidate | null {
        if (!email) return null;

        const candidate: Candidate | undefined = this._candidateList.find(p => p.email === email);

        return candidate ? candidate : null;
    }

    public getByCPF(CPF?: string): Candidate | null {
        if (!CPF) return null;

        const candidate: Candidate | undefined = this._candidateList.find(p => p.CPF === CPF);

        return candidate ? candidate : null;
    }
}