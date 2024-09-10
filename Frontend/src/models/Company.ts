import { JobOpening } from "./JobOpening";
import {Person} from "./Person"

export class Company extends Person {
    
    private _CNPJ?: string;
    private _jobOpenings?: JobOpening[];

    constructor(
        id?: bigint,
        name?: string,
        description?: string,
        email?: string,
        address?: string,
        CNPJ?: string,
        jobOpenings?: JobOpening[]

    ) {
        super(id, name, description, email, address);
        this._CNPJ = CNPJ;
        this._jobOpenings = jobOpenings;
    }

    public get CNPJ(): string | undefined{
        return this._CNPJ;
    }

    public set CNPJ(CNPJ: string | undefined) {
        this._CNPJ = CNPJ;
    }

    public get jobOpenings(): JobOpening[] | undefined{
        return this._jobOpenings;
    }

    public set jobOpenings(jobOpenings: JobOpening[] | undefined) {
        this._jobOpenings = jobOpenings;
    }
}