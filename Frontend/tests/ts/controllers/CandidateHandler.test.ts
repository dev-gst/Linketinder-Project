import { CandidateHandler } from "../../../src/ts/controllers/CandidateHandler"
import { Candidate } from "../../../src/ts/models/Candidate";
import { CandidateService } from "../../../src/ts/services/CandidateService";

describe('Test CandidateHandler', () => {
    let candidateList: Candidate[];
    let mockedCandidateService: jest.Mocked<CandidateService>;
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
        mockedCandidateService = new CandidateService(
            candidateList
        ) as jest.Mocked<CandidateService>;

        mockedCandidateService.createCandidate = jest.fn();
        mockedCandidateService.saveCandidate = jest.fn();

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
        mockedCandidateService.createCandidate.mockReturnValue(mockedCandidate);

        const form: HTMLFormElement | null = document.querySelector('#candidate-sign-up-form');
        if (!form) {
            throw new Error('Form not found')
        }

        const submitEvent: Event = new Event('submit', {
            bubbles: true,
            cancelable: true,
        });

        form.dispatchEvent(submitEvent);

        expect(mockedCandidateService.createCandidate).toHaveBeenCalledTimes(1);
        expect(mockedCandidateService.createCandidate).toHaveBeenCalledWith(
            mockedCandidate.name,
            mockedCandidate.email,
            mockedCandidate.description,
            mockedCandidate.address,
            mockedCandidate.skills,
            mockedCandidate.education,
            mockedCandidate.age,
            mockedCandidate.CPF,
        );

        expect(mockedCandidateService.saveCandidate).toHaveBeenCalledTimes(1);
        expect(mockedCandidateService.saveCandidate).toHaveBeenCalledWith(mockedCandidate);

    });

});