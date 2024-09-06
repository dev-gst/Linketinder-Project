import { Candidate } from "../../../src/ts/models/Candidate";
import { CandidateService } from "../../../src/ts/services/CandidateService";

export function mockCandidate(n: number): jest.Mocked<Candidate> {
    return {
        id: BigInt(n),
        name: `John Doe ${n}`,
        description: `good person`,
        email: `johndoe${n}@example.com`,
        address: `Good street, ${n}`,
        skills: ['JavaScript', 'TypeScript', 'React'],
        education: `Computer Engineer`,
        age: 30,
        CPF: `123456789${n}`,
    } as jest.Mocked<Candidate>;
};

export function mockCandidateList(n: number): jest.Mocked<Candidate>[] {
    const mockedCandidates: Candidate[] = []
    for (let i = 0; i < n; i++) {
        mockedCandidates.push(mockCandidate(i));
    }

    return mockedCandidates as jest.Mocked<Candidate>[];
}

export function mockCandidateService(n: number): jest.Mocked<CandidateService> {
    return new CandidateService(
        mockCandidateList(n)
    ) as jest.Mocked<CandidateService>;
}