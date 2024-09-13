import { JobOpening } from "./JobOpening";
import {Person} from "./Person"

export class Company extends Person {
    
    private _CNPJ?: string;

    constructor(
        id?: bigint,
        name?: string,
        description?: string,
        email?: string,
        address?: string,
        CNPJ?: string,
    ) {
        super(id, name, description, email, address);
        this._CNPJ = CNPJ;
    }

    public get CNPJ(): string | undefined{
        return this._CNPJ;
    }

    public set CNPJ(CNPJ: string | undefined) {
        this._CNPJ = CNPJ;
    }
}