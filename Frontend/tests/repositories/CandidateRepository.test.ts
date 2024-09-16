import { Candidate } from "../../src/models/Candidate";
import { CandidateRepository } from "../../src/repositories/CandidateRepository";

describe('Test CandidateRepository', () => {
    let candidateRepository: CandidateRepository;
    let candidateList: Candidate[];
    let mockedCandidate1: jest.Mocked<Candidate>;
    let mockedCandidate2: jest.Mocked<Candidate>;
    let mockedCandidate3: jest.Mocked<Candidate>;

    beforeAll(() => {
        mockedCandidate1 = new Candidate as jest.Mocked<Candidate>;
        mockedCandidate1.id = BigInt(1);
        mockedCandidate1.name = 'John Doe';
        mockedCandidate1.description = 'good person';
        mockedCandidate1.email = 'johndoe@example.com';
        mockedCandidate1.address = 'Good street, 1';
        mockedCandidate1.skills = ['JavaScript', 'TypeScript', 'React'];
        mockedCandidate1.education = 'Computer Engineering';
        mockedCandidate1.age = 30;
        mockedCandidate1.CPF = '1234567891';

        mockedCandidate2 = new Candidate as jest.Mocked<Candidate>;
        mockedCandidate2.id = BigInt(2);
        mockedCandidate2.name = 'Tom Doe';
        mockedCandidate2.description = 'exceptional person';
        mockedCandidate2.email = 'tomdoe@example.com';
        mockedCandidate2.address = 'Good street, 2';
        mockedCandidate2.skills = ['Python', 'AI'];
        mockedCandidate2.education = 'Data Science';
        mockedCandidate2.age = 98;
        mockedCandidate2.CPF = '121234123434567891';

        mockedCandidate3 = new Candidate as jest.Mocked<Candidate>;
        mockedCandidate3.id = BigInt(3);
        mockedCandidate3.name = 'Marcos Doe';
        mockedCandidate3.description = 'nice person';
        mockedCandidate3.email = 'marcosdoe@example.com';
        mockedCandidate3.address = 'Good street, 3';
        mockedCandidate3.skills = ['Medicines'];
        mockedCandidate3.education = 'Pharmacy';
        mockedCandidate3.age = 24;
        mockedCandidate3.CPF = '943506921';
    })

    beforeEach(() => {
        localStorage.clear();
        candidateList = [];
        candidateRepository = new CandidateRepository(candidateList);
    });

    afterAll(() => {
        localStorage.clear();
    });

    test('persist works with empty list', () => {
        candidateRepository.persist();
        let items: string | null = localStorage.getItem(candidateRepository.candidateStorage);

        expect(items).toBe('[]');
    });

    test('persist works with filled list', () => {
        candidateList.push(mockedCandidate1);
        candidateRepository.persist();
        const items: string | null = localStorage.getItem(candidateRepository.candidateStorage);

        expect(items).toBe(
            `[{"_id":"1","_name":"John Doe","_description":"good person",` + 
            `"_email":"johndoe@example.com","_address":"Good street, 1","_age":30,` + 
            `"_education":"Computer Engineering","_CPF":"1234567891",` +
            `"_skills":["JavaScript","TypeScript","React"]}]`
        );
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

        spy.mockRestore();
    });

    test('load works with filled localStorage', () => {
        const newCandidateList: Candidate[] = [mockedCandidate1, mockedCandidate2, mockedCandidate3];

        const jsonData = JSON.stringify(newCandidateList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem(candidateRepository.candidateStorage, jsonData);

        candidateRepository.load();

        expect(candidateRepository.candidateList).toContainEqual<Candidate>(mockedCandidate1);
        expect(candidateRepository.candidateList).toContainEqual<Candidate>(mockedCandidate2);
        expect(candidateRepository.candidateList).toContainEqual<Candidate>(mockedCandidate3);
    });

    test('Get candidate by email returns null when the candidate is not found', () => {
        candidateList.push(mockedCandidate1);
        candidateList.push(mockedCandidate2);

        expect(candidateRepository.getByEmail(mockedCandidate3.email)).toBeNull();
    });

    test('Get candidate by email returns the correct value', () => {
        candidateList.push(mockedCandidate1);
        candidateList.push(mockedCandidate2);

        expect(candidateRepository.getByEmail(mockedCandidate2.email)).toBe(mockedCandidate2);
    });

    test('Get candidate by CPF returns null when the candidate is not found', () => {
        candidateList.push(mockedCandidate1);
        candidateList.push(mockedCandidate2);

        expect(candidateRepository.getByCPF(mockedCandidate3.CPF)).toBeNull();
    });

    test('Get candidate by CPF returns the correct value', () => {
        candidateList.push(mockedCandidate1);
        candidateList.push(mockedCandidate2);

        expect(candidateRepository.getByCPF(mockedCandidate2.CPF)).toBe(mockedCandidate2);
    });
});