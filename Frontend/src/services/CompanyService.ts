import { Company } from "../models/Company";
import { CompanyRepository } from "../repositories/CompanyRepository";

export class CompanyService {

    private companyRepository: CompanyRepository;
    private currentID: bigint;

    constructor(companyRepository: CompanyRepository) {
        this.currentID = BigInt(1);
        this.companyRepository = companyRepository;
    }

    public create(name?: string, email?: string, description?: string,
        address?: string, CNPJ?: string): Company {
        const company: Company = new Company();
        company.id = this.currentID;
        company.name = name;
        company.email = email;
        company.description = description;
        company.address = address;
        company.CNPJ = CNPJ;
        
        this.currentID++;

        return company;
    }

    public save(company: Company) {
        if (this.getByEmail(company.email) ||
            this.getByCNPJ(company.CNPJ)) {
            console.error("Company already exists");
            return;
        }

        this.companyRepository.save(company);
        this.companyRepository.persist();
    }

    public getByEmail(email?: string): Company | null {
        return this.companyRepository.getByEmail(email);
    }

    public getByCNPJ(CNPJ?: string): Company | null {
        return this.companyRepository.getByCNPJ(CNPJ);
    }
}