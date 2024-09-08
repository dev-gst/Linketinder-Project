import { CandidateHandler } from "../../src/controllers/CandidateHandler";
import { Candidate } from "../../src/models/Candidate";
import { CandidateRepository } from "../../src/repositories/CandidateRepository";
import { CandidateService } from "../../src/services/CandidateService";


describe('Test CandidateHandler', () => {
    let candidateList: Candidate[];
    let mockedCandidateService: jest.Mocked<CandidateService>;
    let mockedCandidateRepository: jest.Mocked<CandidateRepository>;
    let candidateHandler: CandidateHandler;
    let mockedCandidate: Candidate;

    beforeEach(() => {
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

        candidateList = [];
        mockedCandidateRepository = new CandidateRepository(candidateList) as jest.Mocked<CandidateRepository>;
        mockedCandidateService = new CandidateService(mockedCandidateRepository) as jest.Mocked<CandidateService>;

        mockedCandidateService.create = jest.fn();
        mockedCandidateService.save = jest.fn();

        candidateHandler = new CandidateHandler(mockedCandidateService);
        candidateHandler.startListeners();

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
        } as Candidate;
    })

    test('Receive data, creates and saves a candidate', () => {
        mockedCandidateService.create.mockReturnValue(mockedCandidate);

        const form: HTMLFormElement | null = document.querySelector('#candidate-sign-up-form');
        if (!form) {
            throw new Error('Form not found')
        }

        const submitEvent: Event = new Event('submit', {
            bubbles: true,
            cancelable: true,
        });

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
});