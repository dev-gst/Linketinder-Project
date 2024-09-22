import { Company } from "../../src/models/Company";
import { CompanyRepository } from "../../src/repositories/CompanyRepository";

describe('Test CompanyRepository', () => {
    let companyRepository: CompanyRepository;
    let companyList: Company[];
    let company1: Company;
    let company2: Company;
    let company3: Company;

    beforeAll(() => {
        company1 = new Company(
            BigInt(1),
            'Company A',
            'good company',
            'companya@example.com',
            'linkedin.com/companya',
            '1234567890',
            'Good street',
            '1',
            'District 1',
            'City 1',
            'State 1',
            '12345',
            '1234567891'
        );

        company2 = new Company(
            BigInt(2),
            'Company B',
            'another good company',
            'companyb@example.com',
            'linkedin.com/companyb',
            '0987654321',
            'Great street',
            '2',
            'District 2',
            'City 2',
            'State 2',
            '54321',
            '9876543210'
        );

        company3 = new Company(
            BigInt(3),
            'Company C',
            'yet another good company',
            'companyc@example.com',
            'linkedin.com/companyc',
            '1357924680',
            'Awesome street',
            '3',
            'District 3',
            'City 3',
            'State 3',
            '67890',
            '2468135790'
        );
    });

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
        companyRepository.companyList.push(company1);
        companyRepository.persist();
        const items: string | null = localStorage.getItem(companyRepository.companyStorage);

        expect(items).toBe(
            `[{\"_id\":\"1\",\"_name\":\"Company A\",\"_description\":\"good company\",\"_email\":\"companya@example.com\",\"_street\":\"Good street\",\"_linkedin\":\"linkedin.com/companya\",\"_phone\":\"1234567890\",\"_number\":\"1\",\"_district\":\"District 1\",\"_city\":\"City 1\",\"_state\":\"State 1\",\"_zip\":\"12345\",\"_CNPJ\":\"1234567891\"}]`
        );
    });

    test('save works with correct values', () => {
        companyRepository.save(company1);
        expect(companyList.length).toBe(1);
        expect(companyList[0]).toBe(company1);

        companyRepository.save(company2);
        expect(companyList.length).toBe(2);
        expect(companyList[1]).toBe(company2);

        companyRepository.save(company3);
        expect(companyList.length).toBe(3);
        expect(companyList[2]).toBe(company3);
    });

    test('load works with empty localStorage', () => {
        companyRepository.load();
        const spy = jest.spyOn(companyRepository['companyList'], 'push');
        expect(spy).toHaveBeenCalledTimes(0);

        spy.mockRestore();
    });

    test('load works with filled localStorage', () => {
        const newCompanyList: Company[] = [company1, company2, company3];

        const jsonData = JSON.stringify(newCompanyList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem(companyRepository.companyStorage, jsonData);

        companyRepository.load();

        expect(companyRepository.companyList).toContainEqual<Company>(company1);
        expect(companyRepository.companyList).toContainEqual<Company>(company2);
        expect(companyRepository.companyList).toContainEqual<Company>(company3);
    });

    test('Get company by email returns null when the company is not found', () => {
        companyList.push(company1);
        companyList.push(company2);

        expect(companyRepository.getByEmail(company3.email)).toBeNull();
    });

    test('Get company by email returns the correct value', () => {
        companyList.push(company1);
        companyList.push(company2);

        expect(companyRepository.getByEmail(company2.email)).toBe(company2);
    });

    test('Get company by CPF returns null when the company is not found', () => {
        companyList.push(company1);
        companyList.push(company2);

        expect(companyRepository.getByCNPJ(company3.CNPJ)).toBeNull();
    });

    test('Get company by CPF returns the correct value', () => {
        companyList.push(company1);
        companyList.push(company2);

        expect(companyRepository.getByCNPJ(company2.CNPJ)).toBe(company2);
    });
});