import {Person} from "./Person"

export class Company extends Person {
    
    private _CNPJ?: string;

    constructor() {
        super()
    }

    public get CNPJ(): string | undefined{
        return this._CNPJ;
    }

    public set CNPJ(CNPJ: string | undefined) {
        this._CNPJ = CNPJ;
    }
}