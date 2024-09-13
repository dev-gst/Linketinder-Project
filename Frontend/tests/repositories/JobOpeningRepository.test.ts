import { JobOpening } from "../../src/models/JobOpening";
import { JobOpeningRepository } from "../../src/repositories/JobOpeningRepository";

describe('Test JobOpeningRepository', () => {

    let jobOpeningRepository: JobOpeningRepository;
    let jobOpeningList: JobOpening[];
    let jobOpening1: JobOpening;
    let jobOpening2: JobOpening
    let jobOpening3: JobOpening

    beforeAll(() => {
        jobOpening1 = new JobOpening(
            BigInt(1),
            "Painter",
            "Best painter job",
            ['Painting'],
            "High School",
            "On site: Good street, 1",
            BigInt(1),
            1000.00,
        );

        jobOpening2 = new JobOpening(
            BigInt(2),
            "Computer Scientist",
            "Best computer science job",
            ['Software Engineering'],
            "High School",
            "Remote",
            BigInt(2),
            1000.00,
        );

        jobOpening3 = new JobOpening(
            BigInt(3),
            "Constructor",
            "Best constructor job",
            ['Construction'],
            "High School",
            "On site: Good street, 3",
            BigInt(1),
            1000.00,
        );
    })

    beforeEach(() => {
        localStorage.clear();
        jobOpeningList = []
        jobOpeningRepository = new JobOpeningRepository(jobOpeningList);
    })


    afterAll(() => {
        localStorage.clear();
    });

    test('persist works with empty list', () => {
        jobOpeningRepository.persist();
        let items: string | null = localStorage.getItem(jobOpeningRepository.jobOpeningStorage);

        expect(items).toBe('[]');
    });

    test('persist works with filled list', () => {
        jobOpeningRepository.jobOpeningList.push(jobOpening1);
        jobOpeningRepository.persist();
        const items: string | null = localStorage.getItem(jobOpeningRepository.jobOpeningStorage);

        expect(items).toBe(
            `[{"_id":"1","_title":"Painter","_description":"Best painter job",` +
            `"_skillsRequired":["Painting"],"_educationRequired":"High School",` +
            `"_location":"On site: Good street, 1","_companyID":"1","_salary":1000}]`
        );
    });

    test('save works with correct values', () => {
        jobOpeningRepository.save(jobOpening1);
        expect(jobOpeningList.length).toBe(1);
        expect(jobOpeningList[0]).toBe(jobOpening1);

        jobOpeningRepository.save(jobOpening2);
        expect(jobOpeningList.length).toBe(2);
        expect(jobOpeningList[1]).toBe(jobOpening2);

        jobOpeningRepository.save(jobOpening3);
        expect(jobOpeningList.length).toBe(3);
        expect(jobOpeningList[2]).toBe(jobOpening3);
    });

    test('load does not work with empty localStorage', () => {
        jobOpeningRepository.load();
        const spy = jest.spyOn(jobOpeningRepository['jobOpeningList'], 'push');
        expect(spy).toHaveBeenCalledTimes(0);

        spy.mockRestore();
    });

    test('load works with filled localStorage', () => {
        const newjobOpeningList: JobOpening[] = [jobOpening1, jobOpening2, jobOpening3];

        const jsonData = JSON.stringify(newjobOpeningList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem(jobOpeningRepository.jobOpeningStorage, jsonData);

        jobOpeningRepository.load();

        expect(jobOpeningRepository.jobOpeningList).toContainEqual<JobOpening>(jobOpening1);
        expect(jobOpeningRepository.jobOpeningList).toContainEqual<JobOpening>(jobOpening2);
        expect(jobOpeningRepository.jobOpeningList).toContainEqual<JobOpening>(jobOpening3);
    });

    test('Get jobOpening by ID returns null when it is not found', () => {
        jobOpeningList.push(jobOpening1);
        jobOpeningList.push(jobOpening2);

         expect(jobOpeningRepository.getByID(jobOpening3.id)).toBeNull();
    });

    test('Get jobOpening by ID returns the correct value', () => {
        jobOpeningList.push(jobOpening1);
        jobOpeningList.push(jobOpening2);

        expect(jobOpeningRepository.getByID(jobOpening2.id)).toBe(jobOpening2);
    });

    test('Get jobOpening by company ID returns null when the value is not found', () => {
        jobOpeningList.push(jobOpening1);
        jobOpeningList.push(jobOpening2);

        expect(jobOpeningRepository.getByCompanyID(BigInt(200))).toEqual([]);
    });

    test('Get jobOpening by company ID returns the correct values', () => {
        jobOpeningList.push(jobOpening1);
        jobOpeningList.push(jobOpening2);
        jobOpeningList.push(jobOpening3)

        expect(jobOpeningRepository.getByCompanyID(BigInt(2))).toContain(jobOpening2);
        expect(jobOpeningRepository.getByCompanyID(BigInt(1))).toContain(jobOpening1);
        expect(jobOpeningRepository.getByCompanyID(BigInt(1))).toContain(jobOpening3);
    });
});


