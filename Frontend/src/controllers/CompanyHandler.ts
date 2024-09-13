import { Company } from "../models/Company";
import { CompanyService } from "../services/CompanyService";
import { CompanyView } from "../views/company/CompanyView";

export class CompanyHandler {
    private companyService: CompanyService;
    private companyView: CompanyView;

    constructor(companyService: CompanyService, companyView: CompanyView) {
        this.companyService = companyService;
        this.companyView = companyView;
    }

    public startListeners() {
        const companySignUpForm: HTMLFormElement | null = document.querySelector('#company-sign-up-form');        
        companySignUpForm?.addEventListener('submit', this.createCompany.bind(this));
    
        const companyLoginForm: HTMLFormElement | null = document.querySelector('#company-login-form');        
        companyLoginForm?.addEventListener('submit', this.getCompanyByEmail.bind(this));
    }

    public createCompany(e: Event): void {
        e.preventDefault();

        const companySignUpForm: HTMLFormElement = e.target as HTMLFormElement;        
        
        const name: string = (companySignUpForm.querySelector('#company-name') as HTMLInputElement).value;
        const email: string = (companySignUpForm.querySelector('#company-email') as HTMLInputElement).value;
        const description: string = (companySignUpForm.querySelector('#company-description') as HTMLTextAreaElement).value;
        const address: string = (companySignUpForm.querySelector('#company-address') as HTMLInputElement).value;
        const CNPJ: string = (companySignUpForm.querySelector('#company-cnpj') as HTMLInputElement).value;

        const company: Company = this.companyService.create(
            name,
            email,
            description,
            address,
            CNPJ,
        )
        
        this.companyService.save(company);
    }

    private getCompanyByEmail(e: Event) {
        e.preventDefault()

        const companyLoginForm: HTMLFormElement = (document.querySelector('#company-login-form')) as HTMLFormElement;
        const email: string = (companyLoginForm.querySelector('#company-email-login') as HTMLInputElement).value;

        const company: Company | null = this.companyService.getByEmail(email);
        if (!company) {
            // TODO: improve error handling
            console.error("handler: company not found")
                return;
        }

        this.companyView.showProfile(company);
    }
}