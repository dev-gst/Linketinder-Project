import { Company } from "../models/Company";

export class CompanyRepository {

    private _companyStorage: string;
    private _companyList: Company[];
    private _lastCompanyID: bigint;

    constructor(companyList: Company[]) {
        this._companyList = companyList;
        this._companyStorage = 'companyStorage';
        this._lastCompanyID = BigInt(0);

        this.load();
    }

    public get companyList(): Company[] {
        return this._companyList;
    }

    public get companyStorage(): string {
        return this._companyStorage;
    }

    public get lastCompanyID(): bigint {
        return this._lastCompanyID;
    }

    public persist(): void {
        const jsonData: string = JSON.stringify(this._companyList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem(this._companyStorage, jsonData);
    }

    public save(company: Company) {
        this._companyList.push(company);
    }

    public load(): void {
        const rawCompanyList: string | null = localStorage.getItem(this._companyStorage);

        if (rawCompanyList) {
            let parsedCompanyList = JSON.parse(rawCompanyList, (k, v) => {
                return (k === '_id' || k === 'id') ? BigInt(v) : v;
            }) as any[];

            for (let data of parsedCompanyList) {
                const company: Company = new Company();
                company.id = data._id;
                company.name = data._name;
                company.description = data._description;
                company.email = data._email;
                company.address = data._address;
                company.CNPJ = data._CNPJ;

                this.companyList.push(company);
            }

            if (parsedCompanyList.length > 0) {
                this._lastCompanyID = parsedCompanyList[parsedCompanyList.length - 1]._id;
            }
        }
    }

    public getByEmail(email?: string): Company | null {
        if (!email) return null;

        const company: Company | undefined = this._companyList.find(p => p.email === email);

        return company ? company : null;
    }

    public getByCNPJ(CNPJ?: string): Company | null {
        if (!CNPJ) return null;

        const company: Company | undefined = this._companyList.find(p => p.CNPJ === CNPJ);

        return company ? company : null;
    }

    public getByID(id: bigint): Company | null {
        const company: Company | undefined = this._companyList.find(p => p.id === id);

        return company ? company : null;
    }
}