import { Company } from "../models/Company";
import { CompanyRepository } from "../repositories/CompanyRepository";

export class CompanyService {

    private _companyRepository: CompanyRepository;
    private _currentID: bigint;

    constructor(companyRepository: CompanyRepository) {
        this._companyRepository = companyRepository;
        this._currentID = this._companyRepository.lastCompanyID + BigInt(1);
    }

    get companyRepository(): CompanyRepository {
        return this._companyRepository;
    }

    public create(name?: string, email?: string, description?: string,
        address?: string, CNPJ?: string): Company {
        const company: Company = new Company();
        company.id = this._currentID;
        company.name = name;
        company.email = email;
        company.description = description;
        company.address = address;
        company.CNPJ = CNPJ;
        
        this._currentID++;

        return company;
    }

    public save(company: Company) {
        if (this.getByEmail(company.email) ||
            this.getByCNPJ(company.CNPJ)) {
            console.error("Company already exists");
            return;
        }

        this._companyRepository.save(company);
        this._companyRepository.persist();
    }

    public getByEmail(email?: string): Company | null {
        return this._companyRepository.getByEmail(email);
    }

    public getByCNPJ(CNPJ?: string): Company | null {
        return this._companyRepository.getByCNPJ(CNPJ);
    }

    public getByID(id: bigint): Company | null {
        return this._companyRepository.getByID(id);
    }

    public getALL(): Company[] {
        return [... this._companyRepository.companyList];
    }
}