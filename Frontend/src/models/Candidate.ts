import { Person } from "./Person"

export class Candidate extends Person {
    
    private _age?: number;
    private _education?: string;
    private _CPF?: string;

    constructor() {
        super()
    }

    public get age(): number | undefined{
        return this._age;
    }

    public set age(age: number | undefined) {
        this._age = age;
    }

    public get education(): string | undefined{
        return this._education;
    }

    public set education(education: string | undefined) {
        this._education = education;
    }

    public get CPF(): string | undefined{
        return this._CPF;
    }

    public set CPF(CPF: string | undefined) {
        this._CPF = CPF;
    }
}

