import { Candidate } from "../../src/models/Candidate";
import { CandidateRepository } from "../../src/repositories/CandidateRepository";

describe('Test CandidateRepository', () => {
    let candidateRepository: CandidateRepository;
    let candidateList: Candidate[];
    let mockedCandidate1: jest.Mocked<Candidate>;
    let mockedCandidate2: jest.Mocked<Candidate>;
    let mockedCandidate3: jest.Mocked<Candidate>;


    beforeAll(() => {
        mockedCandidate1 = {
            id: BigInt(1),
            name: 'John Doe',
            email: `johndoe@example.com`,
            description: `good person`,
            address: `Good street, 1`,
            skills: ['JavaScript', 'TypeScript', 'React'],
            education: 'Computer Engineering',
            age: 30,
            CPF: `1234567891`,
        }

        mockedCandidate2 = {
            id: BigInt(2),
            name: 'Tom Doe',
            email: `tomdoe@example.com`,
            description: `exceptional person`,
            address: `Good street, 2`,
            skills: ['Python', 'AI'],
            education: 'Data Science',
            age: 98,
            CPF: `121234123434567891`,
        }

        mockedCandidate3 = {
            id: BigInt(3),
            name: 'Marcos Doe',
            email: `marcosdoe@example.com`,
            description: `nice person`,
            address: `Good street, 3`,
            skills: ['Medicines'],
            education: 'Pharmacy',
            age: 24,
            CPF: `94350692`,
        }
    })

    beforeEach(() => {
        candidateList = [];
        candidateRepository = new CandidateRepository(candidateList);
        localStorage.clear();
    });

    test('persist works with empty list', () => {
        candidateRepository.persist();
        let items: string | null = localStorage.getItem('candidateStorage');

        expect(items).toBe('[]');
    });

    test('persist works with filled list', () => {
        candidateList.push(mockedCandidate1);
        candidateRepository.persist();
        const items: string | null = localStorage.getItem('candidateStorage');

        expect(items).toBe(`[{"id":"1","name":"John Doe","email":"johndoe@example.com","description":"good person","address":"Good street, 1","skills":["JavaScript","TypeScript","React"],"education":"Computer Engineering","age":30,"CPF":"1234567891"}]`);
    });

    test('save works with correct values', () => {
        candidateRepository.save(mockedCandidate1);
        expect(candidateList.length).toBe(1);
        expect(candidateList[0]).toBe(mockedCandidate1);

        candidateRepository.save(mockedCandidate2);
        expect(candidateList.length).toBe(2);
        expect(candidateList[1]).toBe(mockedCandidate2);

        candidateRepository.save(mockedCandidate3);
        expect(candidateList.length).toBe(3);
        expect(candidateList[2]).toBe(mockedCandidate3);
    });

    test('load does not work with empty localStorage', () => {
        candidateRepository.load();
        const spy = jest.spyOn(candidateRepository['candidateList'], 'push');
        expect(spy).toHaveBeenCalledTimes(0);

        spy.mockReset();
    });

    test('load works with filled localStorage', () => {
        const newCandidateList: Candidate[] = [];
        newCandidateList.push(mockedCandidate1);
        newCandidateList.push(mockedCandidate2);
        newCandidateList.push(mockedCandidate3);

        const jsonData: string | null = JSON.stringify(newCandidateList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem('candidateStorage', jsonData);

        candidateRepository.load();

        expect(candidateList).toContainEqual(mockedCandidate1);
        expect(candidateList).toContainEqual(mockedCandidate2);
        expect(candidateList).toContainEqual(mockedCandidate3);
    });

    test('Get candidate by email returns null when the candidate is not found', () => {
        candidateList.push(mockedCandidate1);
        candidateList.push(mockedCandidate2);

        expect(candidateRepository.getByEmail(mockedCandidate3.email)).toBeNull();
    });

    test('Get candidate by email returns the correct candidate', () => {
        candidateList.push(mockedCandidate1);
        candidateList.push(mockedCandidate2);

        expect(candidateRepository.getByEmail(mockedCandidate2.email)).toBe(mockedCandidate2);
    });

    test('Get candidate by CPF returns null when the candidate is not found', () => {
        candidateList.push(mockedCandidate1);
        candidateList.push(mockedCandidate2);

        expect(candidateRepository.getByCPF(mockedCandidate3.CPF)).toBeNull();
    });

    test('Get candidate by CPF returns the correct candidate', () => {
        candidateList.push(mockedCandidate1);
        candidateList.push(mockedCandidate2);

        expect(candidateRepository.getByCPF(mockedCandidate2.CPF)).toBe(mockedCandidate2);
    });
});