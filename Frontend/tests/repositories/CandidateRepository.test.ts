import { Candidate } from "../../src/models/Candidate";
import { CandidateRepository } from "../../src/repositories/CandidateRepository";

describe('Test CandidateRepository', () => {
    let candidateRepository: CandidateRepository;
    let candidateList: Candidate[];
    let candidate1: Candidate;
    let candidate2: Candidate;
    let candidate3: Candidate;

    beforeAll(() => {
        candidate1 = new Candidate (
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

        candidate2 = new Candidate (
            BigInt(2),
            'Jane Smith',
            'experienced professional',
            'janesmith@example.com',
            'linkedin.com/janesmith',
            '0987654321',
            'Great street',
            '2',
            'District 2',
            'City 2',
            'State 2',
            '54321',
            35,
            'Software Engineering',
            '9876543210',
            ['Java', 'Python', 'C++'],
        );

        candidate3 = new Candidate (
            BigInt(3),
            'Bob Johnson',
            'enthusiastic learner',
            'bobjohnson@example.com',
            'linkedin.com/bobjohnson',
            '5678901234',
            'Awesome street',
            '3',
            'District 3',
            'City 3',
            'State 3',
            '67890',
            25,
            'Data Science',
            '0123456789',
            ['Python', 'R', 'SQL'],
        );

    });

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
        candidateList.push(candidate1);
        candidateRepository.persist();
        const items: string | null = localStorage.getItem(candidateRepository.candidateStorage);

        expect(items).toBe(
            `[{\"_id\":\"1\",\"_name\":\"John Doe\",\"_description\":\"good person\",` +
            `\"_email\":\"johndoe@example.com\",\"_street\":\"Good street\",` +
            `\"_linkedin\":\"linkedin.com/johndoe\",\"_phone\":\"1234567890\",\"_number\":\"1\",` +
            `\"_district\":\"District 1\",\"_city\":\"City 1\",\"_state\":\"State 1\",` +
            `\"_zip\":\"12345\",\"_age\":30,\"_education\":\"Computer Engineering\",` +
            `\"_CPF\":\"1234567891\",\"_skills\":[\"JavaScript\",\"TypeScript\",\"React\"]}]`
        );
    });

    test('save works with correct values', () => {
        candidateRepository.save(candidate1);
        expect(candidateList.length).toBe(1);
        expect(candidateList[0]).toBe(candidate1);

        candidateRepository.save(candidate2);
        expect(candidateList.length).toBe(2);
        expect(candidateList[1]).toBe(candidate2);

        candidateRepository.save(candidate3);
        expect(candidateList.length).toBe(3);
        expect(candidateList[2]).toBe(candidate3);
    });

    test('load works with empty localStorage', () => {
        candidateRepository.load();
        const spy = jest.spyOn(candidateRepository['candidateList'], 'push');
        expect(spy).toHaveBeenCalledTimes(0);

        spy.mockRestore();
    });

    test('load works with filled localStorage', () => {
        const newCandidateList: Candidate[] = [candidate1, candidate2, candidate3];

        const jsonData = JSON.stringify(newCandidateList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem(candidateRepository.candidateStorage, jsonData);

        candidateRepository.load();

        expect(candidateRepository.candidateList).toContainEqual<Candidate>(candidate1);
        expect(candidateRepository.candidateList).toContainEqual<Candidate>(candidate2);
        expect(candidateRepository.candidateList).toContainEqual<Candidate>(candidate3);
    });

    test('Get candidate by email returns null when the candidate is not found', () => {
        candidateList.push(candidate1);
        candidateList.push(candidate2);

        expect(candidateRepository.getByEmail(candidate3.email)).toBeNull();
    });

    test('Get candidate by email returns the correct value', () => {
        candidateList.push(candidate1);
        candidateList.push(candidate2);

        expect(candidateRepository.getByEmail(candidate2.email)).toBe(candidate2);
    });

    test('Get candidate by CPF returns null when the candidate is not found', () => {
        candidateList.push(candidate1);
        candidateList.push(candidate2);

        expect(candidateRepository.getByCPF(candidate3.CPF)).toBeNull();
    });

    test('Get candidate by CPF returns the correct value', () => {
        candidateList.push(candidate1);
        candidateList.push(candidate2);

        expect(candidateRepository.getByCPF(candidate2.CPF)).toBe(candidate2);
    });
});