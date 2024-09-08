import { Candidate } from '../../src/models/Candidate';
import { CandidateService } from '../../src/services/CandidateService';

describe('Test CandidateService', () => {
    let candidateList: Candidate[];
    let mockedCandidate: jest.Mocked<Candidate>
    let candidateService: CandidateService;

    beforeEach(() => {
        candidateList = [];
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
        }

        candidateService = new CandidateService(candidateList);
    });

    test('Creates a new Candidate', () => {
        const candidate: Candidate = candidateService.create (
            mockedCandidate.name,
            mockedCandidate.email,
            mockedCandidate.description,
            mockedCandidate.address,
            mockedCandidate.skills,
            mockedCandidate.education,
            mockedCandidate.age,
            mockedCandidate.CPF,
        );

        expect(candidate.id).toEqual(mockedCandidate.id);
        expect(candidate.name).toEqual(mockedCandidate.name);
        expect(candidate.description).toEqual(mockedCandidate.description);
        expect(candidate.email).toEqual(mockedCandidate.email);
        expect(candidate.address).toEqual(mockedCandidate.address);
        expect(candidate.skills).toEqual(mockedCandidate.skills);
        expect(candidate.education).toEqual(mockedCandidate.education);
        expect(candidate.age).toEqual(mockedCandidate.age);
        expect(candidate.CPF).toEqual(mockedCandidate.CPF);
    });

    test('Saves a Candidate to the "Database"', () => {
        const spy = jest.spyOn(candidateService['candidateList'], 'push');

        candidateService.save(mockedCandidate);

        expect(spy).toHaveBeenCalledTimes(1)
        expect(spy).toHaveBeenCalledWith(expect.objectContaining({
            id: mockedCandidate.id,
            name: mockedCandidate.name,
            description: mockedCandidate.description,
            email: mockedCandidate.email,
            address: mockedCandidate.address,
            skills: mockedCandidate.skills,
            education: mockedCandidate.education,
            age: mockedCandidate.age,
            CPF: mockedCandidate.CPF,
        }));

        spy.mockReset();
        spy.mockRestore();
    });

    test('Get by email returns null when the candidate is not present', () => {
        expect(candidateService.getByEmail(mockedCandidate.email)).toBeNull();
    });

    test('Get by email returns candidate when the candidate is present', () => {
        candidateList.push(mockedCandidate);
        expect(candidateService.getByEmail(mockedCandidate.email)).toBe(mockedCandidate);
    });

    test('Get by CPF returns null when the candidate is not present', () => {
        expect(candidateService.getByCPF(mockedCandidate.CPF)).toBeNull();
    });

    test('Get by CPF returns candidate when the candidate is present', () => {
        candidateList.push(mockedCandidate);
        expect(candidateService.getByCPF(mockedCandidate.CPF)).toBe(mockedCandidate);
    });   
});
