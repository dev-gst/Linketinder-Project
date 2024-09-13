import { Company } from '../../src/models/Company';
import { JobOpening } from '../../src/models/JobOpening';
import { CompanyRepository } from '../../src/repositories/CompanyRepository';
import { CompanyService } from '../../src/services/CompanyService';

describe('Test CompanyService', () => {
    let companyList: Company[];
    let mockedCompanyRepository: jest.Mocked<CompanyRepository>
    let mockedCompany: jest.Mocked<Company>
    let companyService: CompanyService;

    beforeEach(() => {
        companyList = [];
        mockedCompanyRepository = new CompanyRepository(companyList) as jest.Mocked<CompanyRepository>

        mockedCompanyRepository.getByEmail = jest.fn();
        mockedCompanyRepository.getByCNPJ = jest.fn();
        
        mockedCompany = {
            id: BigInt(1),
            name: 'John Doe',
            email: `johndoe@example.com`,
            description: `good person`,
            address: `Good street, 1`,
            CNPJ: `1234567891`,
        }

        companyService = new CompanyService(mockedCompanyRepository);
    });

    test('Creates a new Company', () => {
        const company: Company = companyService.create (
            mockedCompany.name,
            mockedCompany.email,
            mockedCompany.description,
            mockedCompany.address,
            mockedCompany.CNPJ,
        );

        expect(company.id).toEqual(mockedCompany.id);
        expect(company.name).toEqual(mockedCompany.name);
        expect(company.description).toEqual(mockedCompany.description);
        expect(company.email).toEqual(mockedCompany.email);
        expect(company.address).toEqual(mockedCompany.address);
        expect(company.CNPJ).toEqual(mockedCompany.CNPJ);
    });

    test('Saves a Company to the "Database"', () => {
        const spy1 = jest.spyOn(companyService['companyRepository'], 'save');
        const spy2 = jest.spyOn(companyService['companyRepository'], 'persist');

        companyService.save(mockedCompany);

        expect(spy1).toHaveBeenCalledTimes(1)
        expect(spy1).toHaveBeenCalledWith(expect.objectContaining({
            id: mockedCompany.id,
            name: mockedCompany.name,
            description: mockedCompany.description,
            email: mockedCompany.email,
            address: mockedCompany.address,
            CNPJ: mockedCompany.CNPJ,
        }));

        expect(spy2).toHaveBeenCalledTimes(1);
        expect(spy2).toHaveBeenCalledWith();

        spy1.mockReset();
        spy2.mockReset();
    });

    test('Get by email returns null when the company is not present', () => {
        mockedCompanyRepository.getByEmail.mockReturnValue(null);
        expect(companyService.getByEmail(mockedCompany.email)).toBeNull();
    });

    test('Get by email returns company when present', () => {
        mockedCompanyRepository.getByEmail.mockReturnValue(mockedCompany);
        expect(companyService.getByEmail(mockedCompany.email)).toBe(mockedCompany);
    });

    test('Get by CNPJ returns null when the company is not present', () => {
        mockedCompanyRepository.getByCNPJ.mockReturnValue(null);
        expect(companyService.getByCNPJ(mockedCompany.email)).toBeNull();
    });

    test('Get by CNPJ returns company when present', () => {
        mockedCompanyRepository.getByCNPJ.mockReturnValue(mockedCompany);
        expect(companyService.getByCNPJ(mockedCompany.CNPJ)).toBe(mockedCompany);
    });   
});
