import { Person } from "./Person"

export class Candidate extends Person {
    
    private _age: number;
    private _education: string;
    private _CPF: string;
    private _skills: string[];

    constructor(
        id: bigint,
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
    ) {
        super(id, name, description, email, linkedin, phone, street, number, district, city, state, zip);
        this._age = age;
        this._education = education;
        this._CPF = CPF;
        this._skills = skills;
    }

    public get age(): number {
        return this._age;
    }

    public set age(value: number) {
        this._age = value;
    }

    public get education(): string {
        return this._education;
    }

    public set education(value: string) {
        this._education = value;
    }

    public get CPF(): string {
        return this._CPF;
    }

    public set CPF(value: string) {
        this._CPF = value;
    }

    public get skills(): string[] {
        return this._skills;
    }

    public set skills(value: string[]) {
        this._skills = value;
    }
}

