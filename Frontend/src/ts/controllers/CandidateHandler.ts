import { Candidate } from "../models/Candidate";
import { CandidateService } from "../services/CandidateService";

export class CandidateHandler {
    private candidateService: CandidateService;

    constructor(candidateService: CandidateService) {
        this.candidateService = candidateService;
    }

    public startListeners() {
        const candidateSignUpForm: HTMLFormElement | null = document.querySelector('#candidate-sign-up-form');        
        candidateSignUpForm?.addEventListener('submit', this.createCandidate.bind(this));
    }

    public createCandidate(e: Event) {
        e.preventDefault();

        const candidateSignUpForm: HTMLFormElement = (document.querySelector('#candidate-sign-up-form')) as HTMLFormElement;        
        
        const validAge: number | null = this.validateAge((candidateSignUpForm.querySelector('#candidate-age') as HTMLInputElement).value);
        if (!validAge) {
            return;
        }

        let name: string = (candidateSignUpForm.querySelector('#candidate-name') as HTMLInputElement).value;
        let email: string = (candidateSignUpForm.querySelector('#candidate-email') as HTMLInputElement).value;
        let description: string = (candidateSignUpForm.querySelector('#candidate-description') as HTMLInputElement).value;
        let address: string = (candidateSignUpForm.querySelector('#candidate-address') as HTMLInputElement).value;
        let skills: string[] = this.parseSkills((candidateSignUpForm.querySelector('#candidate-skills') as HTMLInputElement).value);
        let age: number = validAge;
        let education: string = (candidateSignUpForm.querySelector('#candidate-education') as HTMLInputElement).value;
        let CPF: string = (candidateSignUpForm.querySelector('#candidate-cpf') as HTMLInputElement).value;

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

        console.log(candidate)

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
}