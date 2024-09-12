import { Company } from "../../src/models/Company";
import { JobOpening } from "../../src/models/JobOpening";
import { CompanyRepository } from "../../src/repositories/CompanyRepository";

describe('Test CompanyRepository', () => {
    let companyRepository: CompanyRepository;
    let companyList: Company[];
    let mockedCompany1: jest.Mocked<Company>;
    let mockedCompany2: jest.Mocked<Company>;
    let mockedCompany3: jest.Mocked<Company>;

    beforeAll(() => {

        let jobOpening1: JobOpening = new JobOpening(
            BigInt(1),
            "Painter",
            "Best painter job",
            ['Painting'],
            "High School",
            "On site: Good street, 1",
            1000.00,
        );

        let jobOpening2: JobOpening = new JobOpening(
            BigInt(2),
            "Computer Scientist",
            "Best computer science job",
            ['Software Engineering'],
            "High School",
            "Remote",
            1000.00,
        );

        let jobOpening3: JobOpening = new JobOpening(
            BigInt(3),
            "Constructor",
            "Best constructor job",
            ['Construction'],
            "High School",
            "On site: Good street, 3",
            1000.00,
        );

        mockedCompany1 = new Company as jest.Mocked<Company>;
        mockedCompany1.id = BigInt(1);
        mockedCompany1.name = 'Company A';
        mockedCompany1.description = 'good company';
        mockedCompany1.email = 'companya@example.com';
        mockedCompany1.address = 'Good street, 1';
        mockedCompany1.CNPJ = '1234567891';
        mockedCompany1.jobOpenings = [jobOpening1];

        mockedCompany2 = new Company as jest.Mocked<Company>;
        mockedCompany2.id = BigInt(2);
        mockedCompany2.name = 'Company B';
        mockedCompany2.description = 'exceptional company';
        mockedCompany2.email = 'companyb@example.com';
        mockedCompany2.address = 'Good street, 2';
        mockedCompany2.CNPJ = '121234123434567891';
        mockedCompany2.jobOpenings = [jobOpening2];

        mockedCompany3 = new Company as jest.Mocked<Company>;
        mockedCompany3.id = BigInt(3);
        mockedCompany3.name = 'Company A';
        mockedCompany3.description = 'nice company';
        mockedCompany3.email = 'companyc@example.com';
        mockedCompany3.address = 'Good street, 3';
        mockedCompany3.CNPJ = '943506921';
        mockedCompany3.jobOpenings = [jobOpening3];
    })

    beforeEach(() => {
        localStorage.clear();
        companyList = [];
        companyRepository = new CompanyRepository(companyList);
    });

    afterAll(() => {
        localStorage.clear();
    });

    test('persist works with empty list', () => {
        companyRepository.persist();
        let items: string | null = localStorage.getItem(companyRepository.companyStorage);

        expect(items).toBe('[]');
    });

    test('persist works with filled list', () => {
        companyRepository.companyList.push(mockedCompany1);
        companyRepository.persist();
        const items: string | null = localStorage.getItem(companyRepository.companyStorage);

        expect(items).toBe(`[{"_id":"1","_name":"Company A","_description":"good company","_email":"companya@example.com","_address":"Good street, 1","_CNPJ":"1234567891","_jobOpenings":[{"_id":"1","_title":"Painter","_description":"Best painter job","_skillsRequired":["Painting"],"_educationRequired":"High School","_location":"On site: Good street, 1","_salary":1000}]}]`);
    });

    test('save works with correct values', () => {
        companyRepository.save(mockedCompany1);
        expect(companyList.length).toBe(1);
        expect(companyList[0]).toBe(mockedCompany1);

        companyRepository.save(mockedCompany2);
        expect(companyList.length).toBe(2);
        expect(companyList[1]).toBe(mockedCompany2);

        companyRepository.save(mockedCompany3);
        expect(companyList.length).toBe(3);
        expect(companyList[2]).toBe(mockedCompany3);
    });

    test('load does not work with empty localStorage', () => {
        companyRepository.load();
        const spy = jest.spyOn(companyRepository['companyList'], 'push');
        expect(spy).toHaveBeenCalledTimes(0);

        spy.mockRestore();
    });

    test('load works with filled localStorage', () => {
        const newCompanyList: Company[] = [mockedCompany1, mockedCompany2, mockedCompany3];

        const jsonData = JSON.stringify(newCompanyList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem(companyRepository.companyStorage, jsonData);

        companyRepository.load();

        expect(companyRepository.companyList).toContainEqual<Company>(mockedCompany1);
        expect(companyRepository.companyList).toContainEqual<Company>(mockedCompany2);
        expect(companyRepository.companyList).toContainEqual<Company>(mockedCompany3);
    });

    test('Get company by email returns null when the company is not found', () => {
        companyList.push(mockedCompany1);
        companyList.push(mockedCompany2);

        expect(companyRepository.getByEmail(mockedCompany3.email)).toBeNull();
    });

    test('Get company by email returns the correct company', () => {
        companyList.push(mockedCompany1);
        companyList.push(mockedCompany2);

        expect(companyRepository.getByEmail(mockedCompany2.email)).toBe(mockedCompany2);
    });

    test('Get company by CPF returns null when the company is not found', () => {
        companyList.push(mockedCompany1);
        companyList.push(mockedCompany2);

        expect(companyRepository.getByCNPJ(mockedCompany3.CNPJ)).toBeNull();
    });

    test('Get company by CPF returns the correct company', () => {
        companyList.push(mockedCompany1);
        companyList.push(mockedCompany2);

        expect(companyRepository.getByCNPJ(mockedCompany2.CNPJ)).toBe(mockedCompany2);
    });
});