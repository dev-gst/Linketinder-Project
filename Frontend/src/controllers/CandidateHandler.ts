import { Candidate } from "../models/Candidate";
import { CandidateService } from "../services/CandidateService";
import { CandidateView } from "../views/candidate/CandidateView";

export class CandidateHandler {
    private candidateService: CandidateService;
    private candidateView: CandidateView;

    constructor(candidateService: CandidateService, candidateView: CandidateView) {
        this.candidateService = candidateService;
        this.candidateView = candidateView;
    }

    public startListeners() {
        const candidateSignUpForm: HTMLFormElement | null = document.querySelector('#candidate-sign-up-form');        
        candidateSignUpForm?.addEventListener('submit', this.createCandidate.bind(this));
    
        const candidateLoginForm: HTMLFormElement | null = document.querySelector('#candidate-login-form');        
        candidateLoginForm?.addEventListener('submit', this.getCandidateByEmail.bind(this));
    }

    public createCandidate(e: Event): void {
        e.preventDefault();

        const candidateSignUpForm: HTMLFormElement = (document.querySelector('#candidate-sign-up-form')) as HTMLFormElement;        
        
        const validAge: number | null = this.validateAge((candidateSignUpForm.querySelector('#candidate-age') as HTMLInputElement).value);
        if (!validAge) {
            return;
        }

        const name: string = (candidateSignUpForm.querySelector('#candidate-name') as HTMLInputElement).value;
        const email: string = (candidateSignUpForm.querySelector('#candidate-email') as HTMLInputElement).value;
        const description: string = (candidateSignUpForm.querySelector('#candidate-description') as HTMLInputElement).value;
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

    private parseSkills(skillsString: string): string[] {
        return skillsString.trim().split(',').map(skill => skill.trim());
    }

    private getCandidateByEmail(e: Event) {
        e.preventDefault()

        const candidateLoginForm: HTMLFormElement = (document.querySelector('#candidate-login-form')) as HTMLFormElement;
        const email: string = (candidateLoginForm.querySelector('#candidate-email-login') as HTMLInputElement).value;

        const candidate: Candidate | null = this.candidateService.getByEmail(email);
        if (!candidate) {
            // TODO: improve error handling
            console.error("handler: candidate not found")
            return;
        }

        window.location.assign('./profile.html');
        
        this.candidateView.showProfile(candidate);
    }
}