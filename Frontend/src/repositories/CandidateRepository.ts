import { Candidate } from "../models/Candidate";

export class CandidateRepository {

    private _candidateStorage: string;
    private _candidateList: Candidate[];
    private _lastCandidateID: bigint;

    constructor(candidateList: Candidate[]) {
        this._candidateList = candidateList;
        this._candidateStorage = 'candidateStorage';
        this._lastCandidateID = BigInt(0);

        this.load();
    }

    public get candidateList(): Candidate[] {
        return this._candidateList;
    }

    public get candidateStorage(): string {
        return this._candidateStorage;
    }

    public get lastCandidateID(): bigint {
        return this._lastCandidateID;
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
                const candidate: Candidate = new Candidate(
                    data._id,
                    data._name,
                    data._description,
                    data._email,
                    data._linkedin,
                    data._phone,
                    data._street,
                    data._number,
                    data._district,
                    data._city,
                    data._state,
                    data._zip,
                    data._age,
                    data._education,
                    data._CPF,
                    data._skills
                );

                this.candidateList.push(candidate);
            }

            if (parsedCandidateList.length > 0 &&
                parsedCandidateList[parsedCandidateList.length - 1]._id > this._lastCandidateID
            ) {
                this._lastCandidateID = parsedCandidateList[parsedCandidateList.length - 1]._id;
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

    public getByID(id: bigint): Candidate | null {
        const candidate: Candidate | undefined = this._candidateList.find(p => p.id === id);

        return candidate ? candidate : null;
    }
}