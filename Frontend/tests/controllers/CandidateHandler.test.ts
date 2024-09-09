import { CandidateHandler } from "../../src/controllers/CandidateHandler";
import { Candidate } from "../../src/models/Candidate";
import { CandidateRepository } from "../../src/repositories/CandidateRepository";
import { CandidateService } from "../../src/services/CandidateService";
import { CandidateView } from "../../src/views/candidate/CandidateView";


describe('Test CandidateHandler', () => {
    let candidateList: Candidate[];
    let mockedCandidateService: jest.Mocked<CandidateService>;
    let mockedCandidateRepository: jest.Mocked<CandidateRepository>;
    let mockedCandidateView: jest.Mocked<CandidateView>;
    let candidateHandler: CandidateHandler;
    let mockedCandidate: jest.Mocked<Candidate>;
    let originalLocation = window.location;

    beforeEach(() => {
        originalLocation = window.location;

        Object.defineProperty(window, "location", {
            ...window.location,
            writable: true,
            value: {
                ...window.location,
                assign: jest.fn(), 
            },
        });

        candidateList = [];
        mockedCandidateRepository = new CandidateRepository(candidateList) as jest.Mocked<CandidateRepository>;
        mockedCandidateService = new CandidateService(mockedCandidateRepository) as jest.Mocked<CandidateService>;
        mockedCandidateView = new CandidateView() as jest.Mocked<CandidateView>;

        mockedCandidateService.create = jest.fn();
        mockedCandidateService.save = jest.fn();
        mockedCandidateService.getByEmail = jest.fn();
        mockedCandidateService.getByCPF = jest.fn();

        mockedCandidateView.showProfile = jest.fn();

        candidateHandler = new CandidateHandler(mockedCandidateService, mockedCandidateView);

        mockedCandidate = {
            id: BigInt(1),
            name: 'John Doe',
            email: `johndoe@example.com`,
            description: `good person`,
            address: `Good street, 1`,
            skills: ['JavaScript', 'TypeScript', 'React'],
            education: 'Computer Engineer',
            age: 30,
            CPF: `1234567891`,
        } as jest.Mocked<Candidate>;
    });

    afterEach(() => {
        Object.defineProperty(window, "location", {
            writable: true,
            value: originalLocation,
        });
    });

    test('Receive data, creates and saves a candidate', () => {
        document.body.innerHTML += `
            <form id="candidate-sign-up-form">
                <input id="candidate-name" value="John Doe">
                <input id="candidate-email" value="johndoe@example.com">
                <textarea id="candidate-description">good person</textarea>
                <input id="candidate-address" value="Good street, 1">
                <input id="candidate-skills" value="JavaScript, TypeScript, React">
                <input id="candidate-education" value="Computer Engineer">
                <input id="candidate-age" value="30">
                <input id="candidate-cpf" value="1234567891">
                button id="candidate-sign-up-button" type="submit">Sign Up</button>
            </form>`
        
        const form: HTMLFormElement | null = document.querySelector('#candidate-sign-up-form');
        if (!form) {
            throw new Error('Form not found')
        }

        const submitEvent: Event = new Event('submit', {
            bubbles: true,
            cancelable: true,
        });
        
        candidateHandler.startListeners();
        mockedCandidateService.create.mockReturnValue(mockedCandidate);

        form.dispatchEvent(submitEvent);

        expect(mockedCandidateService.create).toHaveBeenCalledTimes(1);
        expect(mockedCandidateService.create).toHaveBeenCalledWith(
            mockedCandidate.name,
            mockedCandidate.email,
            mockedCandidate.description,
            mockedCandidate.address,
            mockedCandidate.skills,
            mockedCandidate.education,
            mockedCandidate.age,
            mockedCandidate.CPF,
        );

        expect(mockedCandidateService.save).toHaveBeenCalledTimes(1);
        expect(mockedCandidateService.save).toHaveBeenCalledWith(mockedCandidate);
    });

    test('Get candidate by email gets the candidate from db and calls show profile', () => {
        document.body.innerHTML += `
        <form id="candidate-login-form">
            <input id="candidate-email-login" value="johndoe@example.com">
            <button class="login-button" id="candidate-login-button" type="submit">Log In</button>
        </form>`

        const form: HTMLFormElement | null = document.querySelector('#candidate-login-form');
        if (!form) {
            throw new Error('Form not found')
        }

        const submitEvent: Event = new Event('submit', {
            bubbles: true,
            cancelable: true,
        });

        candidateHandler.startListeners();
        mockedCandidateService.getByEmail.mockReturnValue(mockedCandidate);

        form.dispatchEvent(submitEvent);

        expect(mockedCandidateService.getByEmail).toHaveBeenCalledTimes(1);
        expect(mockedCandidateService.getByEmail).toHaveBeenCalledWith('johndoe@example.com');
    
        expect(mockedCandidateView.showProfile).toHaveBeenCalledTimes(1);
        expect(mockedCandidateView.showProfile).toHaveBeenCalledWith(mockedCandidate);
    });

    test('getCandidateByEmail redirects the user to their profile', () => {
        document.body.innerHTML += `
        <form id="candidate-login-form">
            <input id="candidate-email-login" value="johndoe@example.com">
            <button class="login-button" id="candidate-login-button" type="submit">Log In</button>
        </form>`

        const form: HTMLFormElement | null = document.querySelector('#candidate-login-form');
        if (!form) {
            throw new Error('Form not found')
        }

        const submitEvent: Event = new Event('submit', {
            bubbles: true,
            cancelable: true,
        });

        candidateHandler.startListeners();
        mockedCandidateService.getByEmail.mockReturnValue(mockedCandidate);
        
        form.dispatchEvent(submitEvent);
        
        expect(window.location.assign).toHaveBeenCalledTimes(1);
        expect(window.location.assign).toHaveBeenCalledWith("./profile.html");
    });
});