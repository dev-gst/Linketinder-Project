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
        const address: string = (candidateSignUpForm.querySelector('#candidate-address') as HTMLInputElement).value;
        const skills: string[] = this.parseSkills((candidateSignUpForm.querySelector('#candidate-skills') as HTMLInputElement).value);
        const age: number = validAge;
        const education: string = (candidateSignUpForm.querySelector('#candidate-education') as HTMLInputElement).value;
        const CPF: string = (candidateSignUpForm.querySelector('#candidate-cpf') as HTMLInputElement).value;

        const candidate: Candidate = this.candidateService.create(
            name,
            email,
            description,
            address,
            skills,
            education,
            age,
            CPF
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

        this.companyView.showProfile(company, jobOpeningList);

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

        if (company) {
            this.companyView.showProfile(company, jobOpeningList)
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

        this.candidateView.showProfile(candidate);
    }

    private parseSkills(skills: string): string[] {
        const skillList: string[] = skills
                                        .trim()
                                        .split(",")
                                        .map(skill => {
                                            let firstLetter = skill.trim().charAt(0).toUpperCase();
                                            skill = firstLetter + skill.substring(1).toLowerCase();

                                            return skill;
                                        }) 
        

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