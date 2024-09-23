import { Candidate } from "../models/Candidate";
import { Company } from "../models/Company";
import { JobOpening } from "../models/JobOpening";
import { CandidateService } from "../services/CandidateService";
import { CompanyService } from "../services/CompanyService";
import { JobOpeningService } from "../services/JobOpeningService";
import { CandidateView } from "../views/candidate/CandidateView";
import { ShowMessage } from "../views/common/ShowMessage";
import { CompanyView } from "../views/company/CompanyView";
import { Validation } from "./Validation";

export class Handler {
    private companyService: CompanyService;
    private candidateService: CandidateService;
    private jobOpeningService: JobOpeningService;

    private companyView: CompanyView;
    private candidateView: CandidateView;

    private loggedInCompanyID: bigint;

    constructor(
        companyService: CompanyService,
        candidateService: CandidateService,
        jobOpeningService: JobOpeningService,
        companyView: CompanyView,
        candidateView: CandidateView
    ) {
        this.companyService = companyService;
        this.candidateService = candidateService;
        this.jobOpeningService = jobOpeningService;
        this.companyView = companyView;
        this.loggedInCompanyID = BigInt(0);
        this.jobOpeningService = jobOpeningService;
        this.candidateView = candidateView;
    }

    public startListeners() {
        const companySignUpForm: HTMLFormElement | null = document.querySelector('#company-sign-up-form');
        companySignUpForm?.addEventListener('submit', this.createCompany.bind(this));

        const companyLoginForm: HTMLFormElement | null = document.querySelector('#company-login-form');
        companyLoginForm?.addEventListener('submit', this.getCompanyProfile.bind(this));

        const candidateSignUpForm: HTMLFormElement | null = document.querySelector('#candidate-sign-up-form');
        candidateSignUpForm?.addEventListener('submit', this.createCandidate.bind(this));

        const candidateLoginForm: HTMLFormElement | null = document.querySelector('#candidate-login-form');
        candidateLoginForm?.addEventListener('submit', this.getCandidateProfile.bind(this));

        const createJobOpeningForm: HTMLFormElement | null = document.querySelector('#create-job-opening-form');
        createJobOpeningForm?.addEventListener('submit', this.createJobOpening.bind(this));
    }

    private createCompany(e: Event): void {
        e.preventDefault();

        const companySignUpForm: HTMLFormElement = e.target as HTMLFormElement;
        let company: Company;

        try {
            const name: string = Validation.parseName((companySignUpForm.querySelector('#company-name') as HTMLInputElement).value);
            const email: string = Validation.parseEmail((companySignUpForm.querySelector('#company-email') as HTMLInputElement).value);
            const description: string = Validation.parseCommonString((companySignUpForm.querySelector('#company-description') as HTMLTextAreaElement).value, 'Description');
            const linkedin: string = Validation.parseLinkedin((companySignUpForm.querySelector('#company-linkedin') as HTMLInputElement).value);
            const phone: string = Validation.parsePhone((companySignUpForm.querySelector('#company-phone') as HTMLInputElement).value);
            const street: string = Validation.parseCommonString((companySignUpForm.querySelector('#company-address-street') as HTMLInputElement).value, 'Street');
            const number: string = Validation.parseNumber((companySignUpForm.querySelector('#company-address-number') as HTMLInputElement).value);
            const district: string = Validation.parseCommonString((companySignUpForm.querySelector('#company-address-district') as HTMLInputElement).value, 'District');
            const city: string = Validation.parseCommonString((companySignUpForm.querySelector('#company-address-city') as HTMLInputElement).value, 'City');
            const state: string = Validation.parseCommonString((companySignUpForm.querySelector('#company-address-state') as HTMLInputElement).value, 'State');
            const zip: string = Validation.parseZip((companySignUpForm.querySelector('#company-address-zip') as HTMLInputElement).value);
            const CNPJ: string = Validation.parseCNPJ((companySignUpForm.querySelector('#company-cnpj') as HTMLInputElement).value);

            company = this.companyService.create(
                name,
                description,
                email,
                linkedin,
                phone,
                street,
                number,
                district,
                city,
                state,
                zip,
                CNPJ,
            )

            this.companyService.save(company);

        } catch (error: any) {
            ShowMessage.showErrorMessage(
                error.message,
                'company-sign-up-form',
            );

            return;
        }

        ShowMessage.showSuccessMessage(
            'Company signed up successfully',
            'company-sign-up-form',
        );
    }

    private createCandidate(e: Event): void {
        e.preventDefault();

        const candidateSignUpForm: HTMLFormElement = (document.querySelector('#candidate-sign-up-form')) as HTMLFormElement;
        let candidate: Candidate;

        try {
            const name: string = Validation.parseName((candidateSignUpForm.querySelector('#candidate-name') as HTMLInputElement).value);
            const email: string = Validation.parseEmail((candidateSignUpForm.querySelector('#candidate-email') as HTMLInputElement).value);
            const description: string = Validation.parseCommonString((candidateSignUpForm.querySelector('#candidate-description') as HTMLTextAreaElement).value);
            const linkedin: string = Validation.parseLinkedin((candidateSignUpForm.querySelector('#candidate-linkedin') as HTMLInputElement).value);
            const phone: string = Validation.parsePhone((candidateSignUpForm.querySelector('#candidate-phone') as HTMLInputElement).value);
            const street: string = Validation.parseCommonString((candidateSignUpForm.querySelector('#candidate-address-street') as HTMLInputElement).value);
            const number: string = Validation.parseNumber((candidateSignUpForm.querySelector('#candidate-address-number') as HTMLInputElement).value);
            const district: string = Validation.parseCommonString((candidateSignUpForm.querySelector('#candidate-address-district') as HTMLInputElement).value);
            const city: string = Validation.parseCommonString((candidateSignUpForm.querySelector('#candidate-address-city') as HTMLInputElement).value);
            const state: string = Validation.parseCommonString((candidateSignUpForm.querySelector('#candidate-address-state') as HTMLInputElement).value);
            const zip: string = Validation.parseZip((candidateSignUpForm.querySelector('#candidate-address-zip') as HTMLInputElement).value);
            const skills: string[] = Validation.parseSkills((candidateSignUpForm.querySelector('#candidate-skills') as HTMLInputElement).value);
            const age: number = Validation.parseAge((candidateSignUpForm.querySelector('#candidate-age') as HTMLInputElement).value);
            const education: string = Validation.parseCommonString((candidateSignUpForm.querySelector('#candidate-education') as HTMLInputElement).value);
            const CPF: string = Validation.parseCPF((candidateSignUpForm.querySelector('#candidate-cpf') as HTMLInputElement).value);

            candidate = this.candidateService.create(
                name,
                description,
                email,
                linkedin,
                phone,
                street,
                number,
                district,
                city,
                state,
                zip,
                age,
                education,
                CPF,
                skills,
            );

            this.candidateService.save(candidate);

        } catch (error: any) {
            ShowMessage.showErrorMessage(
                error.message,
                'candidate-sign-up-form',
            );
            
            return;
        }

        ShowMessage.showSuccessMessage(
            'Candidate signed up successfully',
            'candidate-sign-up-form',
        );
    }

    private createJobOpening(e: Event) {
        e.preventDefault();

        const createJobOpeningForm: HTMLFormElement = e.target as HTMLFormElement;
        let jobOpening: JobOpening;
        let companyID: bigint;

        try {
            const title: string = Validation.parseCommonString((createJobOpeningForm.querySelector('#job-opening-title') as HTMLInputElement).value, 'Title');
            const description: string = Validation.parseCommonString((createJobOpeningForm.querySelector('#job-opening-description') as HTMLInputElement).value, 'Description');
            const skillsRequired: string[] = Validation.parseSkills((createJobOpeningForm.querySelector('#job-opening-skills') as HTMLInputElement).value);
            const educationRequired: string = Validation.parseCommonString((createJobOpeningForm.querySelector('#job-opening-education') as HTMLInputElement).value, 'Education');
            const location: string = Validation.parseCommonString((createJobOpeningForm.querySelector('#job-opening-location') as HTMLInputElement).value, 'Location');
            const salary: number = Validation.parseSalary((createJobOpeningForm.querySelector('#job-opening-salary') as HTMLInputElement).value);
            companyID = this.loggedInCompanyID;

            jobOpening = this.jobOpeningService.create(
                title,
                description,
                skillsRequired,
                educationRequired,
                location,
                companyID,
                salary,
            );

            this.jobOpeningService.save(jobOpening);

        } catch (error: any) {
            ShowMessage.showErrorMessage(
                error.message,
                'create-job-opening-form',
            );

            return;
        }

        const jobOpeningList: JobOpening[] = this.jobOpeningService.getByCompanyID(companyID);
        const company: Company | null = this.companyService.getByID(companyID);
        const candidates: Candidate[] = this.candidateService.getALL();

        if (company) {
            this.companyView.showProfile(company, candidates, jobOpeningList)
        }

        this.startListeners()

        ShowMessage.showSuccessMessage(
            'Opening created successfully',
            'create-job-opening-form',
        );
    }

    private getCandidateProfile(e: Event) {
        e.preventDefault()

        const candidateLoginForm: HTMLFormElement = (document.querySelector('#candidate-login-form')) as HTMLFormElement;
        const email: string = (candidateLoginForm.querySelector('#candidate-email-login') as HTMLInputElement).value;

        const candidate: Candidate | null = this.candidateService.getByEmail(email);
        if (!candidate) {
            // TODO: improve error handling
            console.error("handler: candidate not found")
            return;
        }

        const jobOpenings: JobOpening[] = this.jobOpeningService.getAll();

        this.candidateView.showProfile(candidate, jobOpenings);
    }

    private getCompanyProfile(e: Event) {
        e.preventDefault();

        const companyLoginForm: HTMLFormElement = (document.querySelector('#company-login-form')) as HTMLFormElement;
        const email: string = (companyLoginForm.querySelector('#company-email-login') as HTMLInputElement).value;

        const company: Company | null = this.companyService.getByEmail(email);
        if (!company || !company.id) {
            // TODO: improve error handling
            console.error("handler: company not found")
            return;
        }

        this.loggedInCompanyID = company.id;
        const jobOpeningList: JobOpening[] = this.jobOpeningService.getByCompanyID(company.id);
        const candidates: Candidate[] = this.candidateService.getALL();

        this.companyView.showProfile(company, candidates, jobOpeningList);

        this.startListeners()
    }
}