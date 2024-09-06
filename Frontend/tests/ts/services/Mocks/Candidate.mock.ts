import { Candidate } from "../../../../src/ts/models/Candidate";

export function mockCandidate(n: number): Candidate {
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
    }
};

export function mockCandidateList(n: number): Candidate[] {
    const mockedCandidates: Candidate[] = []

    for (let i = 0; i < n; i++) {
        mockedCandidates.push(mockCandidate(i));
    }

    return mockedCandidates;
}