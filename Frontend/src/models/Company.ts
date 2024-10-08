import {Person} from "./Person"

export class Company extends Person {            
    
    private _CNPJ: string;

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
        CNPJ: string,
    ) {
        super(id, name, description, email, linkedin, phone, street, number, district, city, state, zip);
        this._CNPJ = CNPJ;
    }
    
    public get CNPJ(): string {
        return this._CNPJ;
    }

    public set CNPJ(CNPJ: string) {
        this._CNPJ = CNPJ;
    }
}