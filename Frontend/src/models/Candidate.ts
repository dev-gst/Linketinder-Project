import { Person } from "./Person"

export class Candidate extends Person {
    
    private _age?: number;
    private _education?: string;
    private _CPF?: string;
    private _skills?: string[];

    constructor(
        id?: bigint,
        name?: string,
        description?: string,
        email?: string,
        address?: string,
        skills?: string[],
        age?: number,
        education?: string,
        CPF?: string,

    ) {
        super(id, name, description, email, address);
        this._age = age;
        this._education = education;
        this._CPF = CPF;
        this._skills = skills;
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

    public get skills(): string[] | undefined {
        return this._skills;
    }

    public set skills(skills: string[] | undefined) {
        this._skills = skills;
    }
}

