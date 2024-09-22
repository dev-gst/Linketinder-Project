import { Company } from '../../src/models/Company';
import { CompanyRepository } from '../../src/repositories/CompanyRepository';
import { CompanyService } from '../../src/services/CompanyService';

jest.mock('../../src/repositories/CompanyRepository', () => {
    return {
        CompanyRepository: jest.fn().mockImplementation(() => {
            return {
                _companyStorage: 'companyStorage',
                _companyList: [],
                lastCompanyID: BigInt(1),
                save: jest.fn(),
                persist: jest.fn(),
                getByEmail: jest.fn(),
                getByCNPJ: jest.fn(),
                getByID: jest.fn(),
            };
        })
    }
});


describe('Test CompanyService', () => {
    let company: jest.Mocked<Company>;
    let mockedCompanyRepository: jest.Mocked<CompanyRepository>;
    let companyService: CompanyService;

    beforeEach(() => {
        jest.clearAllMocks();

        company = new Company(
            BigInt(1),
            'Company A',
            'good company',
            'companya@example.com',
            'Good street',
            'linkedin.com/companya',
            '1234567890',
            '1',
            'District 1',
            'City 1',
            'State 1',
            '12345',
            '1234567891'
        );

        mockedCompanyRepository = new CompanyRepository([]) as jest.Mocked<CompanyRepository>;
        companyService = new CompanyService(mockedCompanyRepository);
    });

    test('Creates a new Company', () => {
        const newCompany: Company = companyService.create (
            company.name,
            company.description,
            company.email,
            company.linkedin,
            company.phone,
            company.street,
            company.number,
            company.district,
            company.city,
            company.state,
            company.zip,
            company.CNPJ,
        );

        expect(newCompany.id).toEqual(company.id);
        expect(newCompany.name).toEqual(company.name);
        expect(newCompany.description).toEqual(company.description);
        expect(newCompany.email).toEqual(company.email);
        expect(newCompany.address).toEqual(company.address);
        expect(newCompany.CNPJ).toEqual(company.CNPJ);
    });

    test('Saves a Company to the "Database"', () => {
        const spy1 = jest.spyOn(companyService['companyRepository'], 'save');
        const spy2 = jest.spyOn(companyService['companyRepository'], 'persist');

        companyService.save(company);

        expect(spy1).toHaveBeenCalledTimes(1)
        expect(spy1).toHaveBeenCalledWith(expect.objectContaining({
            id: company.id,
            name: company.name,
            description: company.description,
            email: company.email,
            address: company.address,
            CNPJ: company.CNPJ,
        }));

        expect(spy2).toHaveBeenCalledTimes(1);
        expect(spy2).toHaveBeenCalledWith();

        spy1.mockReset();
        spy2.mockReset();
    });

    test('Get by email returns null when the company is not present', () => {
        mockedCompanyRepository.getByEmail.mockReturnValue(null);
        expect(companyService.getByEmail(company.email)).toBeNull();
    });

    test('Get by email returns company when present', () => {
        mockedCompanyRepository.getByEmail.mockReturnValue(company);
        expect(companyService.getByEmail(company.email)).toBe(company);
    });

    test('Get by CNPJ returns null when the company is not present', () => {
        mockedCompanyRepository.getByCNPJ.mockReturnValue(null);
        expect(companyService.getByCNPJ(company.email)).toBeNull();
    });

    test('Get by CNPJ returns company when present', () => {
        mockedCompanyRepository.getByCNPJ.mockReturnValue(company);
        expect(companyService.getByCNPJ(company.CNPJ)).toBe(company);
    });   
});
