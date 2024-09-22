import { Candidate } from '../../src/models/Candidate';
import { CandidateRepository } from '../../src/repositories/CandidateRepository';
import { CandidateService } from '../../src/services/CandidateService';

jest.mock('../../src/repositories/CandidateRepository', () => {
    return {
        CandidateRepository: jest.fn().mockImplementation(() => {
            return {
                _candidateStorage: 'candidateStorage',
                _candidateList: [],
                lastCandidateID: BigInt(1),
                save: jest.fn(),
                persist: jest.fn(),
                getByEmail: jest.fn(),
                getByCPF: jest.fn(),
            };
        })
    }
});

describe('Test CandidateService', () => {
    let candidate: Candidate;
    let mockCandidateRepository: jest.Mocked<CandidateRepository>;
    let candidateService: CandidateService;
    
    beforeEach(() => {
        jest.clearAllMocks();

        candidate = new Candidate(
            BigInt(1),
            'John Doe',
            'good person',
            'johndoe@example.com',
            'linkedin.com/johndoe',
            '1234567890',
            'Good street',
            '1',
            'District 1',
            'City 1',
            'State 1',
            '12345',
            30,
            'Computer Engineering',
            '1234567891',
            ['JavaScript', 'TypeScript', 'React'],
        );
    
        mockCandidateRepository = new CandidateRepository([]) as jest.Mocked<CandidateRepository>;
        candidateService = new CandidateService(mockCandidateRepository);
    });

    test('Creates a new Candidate', () => {
        const newCandidate: Candidate = candidateService.create(
            candidate.name,
            candidate.description,
            candidate.email,
            candidate.linkedin,
            candidate.phone,
            candidate.street,
            candidate.number,
            candidate.district,
            candidate.city,
            candidate.state,
            candidate.zip,
            candidate.age,
            candidate.education,
            candidate.CPF,
            candidate.skills,
        );

        expect(newCandidate.id.valueOf()).toEqual(candidate.id.valueOf());
        expect(newCandidate.name).toEqual(candidate.name);
        expect(newCandidate.description).toEqual(candidate.description);
        expect(newCandidate.email).toEqual(candidate.email);
        expect(newCandidate.address).toEqual(
            `${candidate.street}, n° ${candidate.number} - Bairro ${candidate.district} - ` +
            `${candidate.city}, ${candidate.state} - CEP: ${candidate.zip}`
        );
        expect(newCandidate.skills).toEqual(candidate.skills);
        expect(newCandidate.education).toEqual(candidate.education);
        expect(newCandidate.age).toEqual(candidate.age);
        expect(newCandidate.CPF).toEqual(candidate.CPF);
    });

    test('Saves a Candidate to the "Database"', () => {
        const spy1 = jest.spyOn(candidateService['candidateRepository'], 'save');
        const spy2 = jest.spyOn(candidateService['candidateRepository'], 'persist');

        candidateService.save(candidate);

        expect(spy1).toHaveBeenCalledTimes(1)
        expect(spy1).toHaveBeenCalledWith(expect.objectContaining({
            id: candidate.id,
            name: candidate.name,
            description: candidate.description,
            email: candidate.email,
            linkedin: candidate.linkedin,
            phone: candidate.phone,
            street: candidate.street,
            number: candidate.number,
            district: candidate.district,
            city: candidate.city,
            skills: candidate.skills,
            education: candidate.education,
            age: candidate.age,
            CPF: candidate.CPF,
        }));

        expect(spy2).toHaveBeenCalledTimes(1);
        expect(spy2).toHaveBeenCalledWith();

        spy1.mockReset();
        spy2.mockReset();
    });

    test('Get by email returns null when the candidate is not present', () => {
        mockCandidateRepository.getByEmail.mockReturnValue(null);
        expect(candidateService.getByEmail(candidate.email)).toBeNull();
    });

    test('Get by email returns candidate when the candidate is present', () => {
        mockCandidateRepository.getByEmail.mockReturnValue(candidate);
        expect(candidateService.getByEmail(candidate.email)).toBe(candidate);
    });

    test('Get by CPF returns null when the candidate is not present', () => {
        mockCandidateRepository.getByCPF.mockReturnValue(null);
        expect(candidateService.getByCPF(candidate.email)).toBeNull();
    });

    test('Get by CPF returns candidate when the candidate is present', () => {
        mockCandidateRepository.getByCPF.mockReturnValue(candidate);
        expect(candidateService.getByCPF(candidate.CPF)).toBe(candidate);
    });   
});
