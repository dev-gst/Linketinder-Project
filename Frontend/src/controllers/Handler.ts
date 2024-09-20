import { Candidate } from "../models/Candidate";
import { Company } from "../models/Company";
import { JobOpening } from "../models/JobOpening";
import { CandidateService } from "../services/CandidateService";
import { CompanyService } from "../services/CompanyService";
import { JobOpeningService } from "../services/JobOpeningService";
import { CandidateView } from "../views/candidate/CandidateView";
import { CompanyView } from "../views/company/CompanyView";

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
        
        const name: string = (companySignUpForm.querySelector('#company-name') as HTMLInputElement).value;
        const email: string = (companySignUpForm.querySelector('#company-email') as HTMLInputElement).value;
        const description: string = (companySignUpForm.querySelector('#company-description') as HTMLTextAreaElement).value;
        const linkedin: string = (companySignUpForm.querySelector('#company-linkedin') as HTMLInputElement).value;
        const phone: string = (companySignUpForm.querySelector('#company-phone') as HTMLInputElement).value;
        const street: string = (companySignUpForm.querySelector('#company-address-street') as HTMLInputElement).value;
        const number: string = (companySignUpForm.querySelector('#company-address-number') as HTMLInputElement).value;
        const district: string = (companySignUpForm.querySelector('#company-address-district') as HTMLInputElement).value;
        const city: string = (companySignUpForm.querySelector('#company-address-city') as HTMLInputElement).value;
        const state: string = (companySignUpForm.querySelector('#company-address-state') as HTMLInputElement).value;
        const zip: string = (companySignUpForm.querySelector('#company-address-zip') as HTMLInputElement).value;
        const CNPJ: string = (companySignUpForm.querySelector('#company-cnpj') as HTMLInputElement).value;

        const company: Company = this.companyService.create(
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
    }

    private createCandidate(e: Event): void {
        e.preventDefault();

        const candidateSignUpForm: HTMLFormElement = (document.querySelector('#candidate-sign-up-form')) as HTMLFormElement;        
        
        const validAge: number | null = this.validateAge((candidateSignUpForm.querySelector('#candidate-age') as HTMLInputElement).value);
        if (!validAge) {
            return;
        }

        const name: string = (candidateSignUpForm.querySelector('#candidate-name') as HTMLInputElement).value;
        const email: string = (candidateSignUpForm.querySelector('#candidate-email') as HTMLInputElement).value;
        const description: string = (candidateSignUpForm.querySelector('#candidate-description') as HTMLTextAreaElement).value;
        const linkedin: string = (candidateSignUpForm.querySelector('#candidate-linkedin') as HTMLInputElement).value;
        const phone: string = (candidateSignUpForm.querySelector('#candidate-phone') as HTMLInputElement).value;
        const street: string = (candidateSignUpForm.querySelector('#candidate-address-street') as HTMLInputElement).value;
        const number: string = (candidateSignUpForm.querySelector('#candidate-address-number') as HTMLInputElement).value;
        const district: string = (candidateSignUpForm.querySelector('#candidate-address-district') as HTMLInputElement).value;
        const city: string = (candidateSignUpForm.querySelector('#candidate-address-city') as HTMLInputElement).value;
        const state: string = (candidateSignUpForm.querySelector('#candidate-address-state') as HTMLInputElement).value;
        const zip: string = (candidateSignUpForm.querySelector('#candidate-address-zip') as HTMLInputElement).value;
        const skills: string[] = this.parseSkills((candidateSignUpForm.querySelector('#candidate-skills') as HTMLInputElement).value);
        const age: number = validAge;
        const education: string = (candidateSignUpForm.querySelector('#candidate-education') as HTMLInputElement).value;
        const CPF: string = (candidateSignUpForm.querySelector('#candidate-cpf') as HTMLInputElement).value;

        const candidate: Candidate = this.candidateService.create(
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
        )
        
        this.candidateService.save(candidate);
    }

    private validateAge(ageString: string): number | null {
        let age: number;
        try {
            age = parseInt(ageString, 10);

            if (isNaN(age)) {
                throw new Error('Age needs to be a integer');
            }

            if (age < 16 || age > 130) {
                throw new Error('Invalid age');
            }

        } catch (error: any) {
            console.error(`Error: ${error.message}`);
            return null;
        }

        return age;
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

    private createJobOpening(e: Event) {
        e.preventDefault();

        const createJobOpeningForm: HTMLFormElement = e.target as HTMLFormElement;
        
        const title: string = (createJobOpeningForm.querySelector('#job-opening-title') as HTMLInputElement).value;
        const description: string = (createJobOpeningForm.querySelector('#job-opening-description') as HTMLInputElement).value;
        const skillsRequired: string[] = this.parseSkills((createJobOpeningForm.querySelector('#job-opening-skills') as HTMLInputElement).value);
        const educationRequired: string = (createJobOpeningForm.querySelector('#job-opening-education') as HTMLInputElement).value;
        const location: string = (createJobOpeningForm.querySelector('#job-opening-location') as HTMLInputElement).value;
        const salary: number = this.parseSalary((createJobOpeningForm.querySelector('#job-opening-salary') as HTMLInputElement).value);
        const companyID: bigint = this.loggedInCompanyID;
        
        if (salary < 0) {
            return;
        }

        const jobOpening: JobOpening = this.jobOpeningService.create(
            title,
            description,
            skillsRequired,
            educationRequired,
            location,
            companyID,
            salary,
        );

        this.jobOpeningService.save(jobOpening);

        const jobOpeningList: JobOpening[] = this.jobOpeningService.getByCompanyID(companyID);
        const company: Company | null = this.companyService.getByID(companyID);
        const candidates: Candidate[] = this.candidateService.getALL();

        if (company) {
            this.companyView.showProfile(company, candidates, jobOpeningList)
        }

        this.startListeners()
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

    private parseSkills(skills: string): string[] {
        const skillList: string[] = skills
                                        .trim()
                                        .split(",")
                                        .map(skill => {
                                            const trimmedSkill: string = skill.trim();
                                            if (!trimmedSkill) return '';

                                            const firstLetter = trimmedSkill.charAt(0).toUpperCase();
                                            const restOfSkill = trimmedSkill.slice(1).toLowerCase()
                                            
                                            if (trimmedSkill.length === 1) return firstLetter;

                                            return firstLetter + restOfSkill;
                                        })
                                        .filter(skill => skill !== '' && skill !== ',')
        
        return skillList;
    }

    private parseSalary(salary?: string): number {
        if (!salary) return -1

        let numberSalary: number = parseFloat(salary);
        if(isNaN(numberSalary)){
            console.error('Could not parse salary');
            return -1;
        }

        return numberSalary;
    }
}