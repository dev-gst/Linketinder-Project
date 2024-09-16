import { Candidate } from '../../src/models/Candidate';
import { CandidateRepository } from '../../src/repositories/CandidateRepository';
import { CandidateService } from '../../src/services/CandidateService';

describe('Test CandidateService', () => {
    let candidateList: Candidate[];
    let mockedCandidateRepository: jest.Mocked<CandidateRepository>
    let mockedCandidate: jest.Mocked<Candidate>
    let candidateService: CandidateService;

    beforeEach(() => {
        candidateList = [];
        mockedCandidateRepository = new CandidateRepository(candidateList) as jest.Mocked<CandidateRepository>

        mockedCandidateRepository.getByEmail = jest.fn();
        mockedCandidateRepository.getByCPF = jest.fn();
        
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

        candidateService = new CandidateService(mockedCandidateRepository);
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
        const spy1 = jest.spyOn(candidateService['candidateRepository'], 'save');
        const spy2 = jest.spyOn(candidateService['candidateRepository'], 'persist');

        candidateService.save(mockedCandidate);

        expect(spy1).toHaveBeenCalledTimes(1)
        expect(spy1).toHaveBeenCalledWith(expect.objectContaining({
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

        expect(spy2).toHaveBeenCalledTimes(1);
        expect(spy2).toHaveBeenCalledWith();

        spy1.mockReset();
        spy2.mockReset();
    });

    test('Get by email returns null when the candidate is not present', () => {
        mockedCandidateRepository.getByEmail.mockReturnValue(null);
        expect(candidateService.getByEmail(mockedCandidate.email)).toBeNull();
    });

    test('Get by email returns candidate when the candidate is present', () => {
        mockedCandidateRepository.getByEmail.mockReturnValue(mockedCandidate);
        expect(candidateService.getByEmail(mockedCandidate.email)).toBe(mockedCandidate);
    });

    test('Get by CPF returns null when the candidate is not present', () => {
        mockedCandidateRepository.getByCPF.mockReturnValue(null);
        expect(candidateService.getByCPF(mockedCandidate.email)).toBeNull();
    });

    test('Get by CPF returns candidate when the candidate is present', () => {
        mockedCandidateRepository.getByCPF.mockReturnValue(mockedCandidate);
        expect(candidateService.getByCPF(mockedCandidate.CPF)).toBe(mockedCandidate);
    });   
});
