import { CompanyHandler } from "../../src/controllers/CompanyHandler";
import { Company } from "../../src/models/Company";
import { JobOpening } from "../../src/models/JobOpening";
import { CompanyRepository } from "../../src/repositories/CompanyRepository";
import { JobOpeningRepository } from "../../src/repositories/JobOpeningRepository";
import { CompanyService } from "../../src/services/CompanyService";
import { JobOpeningService } from "../../src/services/JobOpeningService";
import { CompanyView } from "../../src/views/company/CompanyView";


describe('Test CompanyHandler', () => {
    let companyList: Company[];
    let mockedCompanyService: jest.Mocked<CompanyService>;
    let mockedCompanyRepository: jest.Mocked<CompanyRepository>;
    let mockedCompanyView: jest.Mocked<CompanyView>;
    let mockedCompany: jest.Mocked<Company>;

    let jobOpeningList: JobOpening[];
    let mockedJobOpeningRepository: jest.Mocked<JobOpeningRepository>;
    let mockedJobOpeningService: jest.Mocked<JobOpeningService>;

    let companyHandler: CompanyHandler;
    

    beforeEach(() => {
        companyList = [];
        mockedCompanyRepository = new CompanyRepository(companyList) as jest.Mocked<CompanyRepository>;
        mockedCompanyService = new CompanyService(mockedCompanyRepository) as jest.Mocked<CompanyService>;
        mockedCompanyView = new CompanyView() as jest.Mocked<CompanyView>;

        jobOpeningList = [];
        mockedJobOpeningRepository = new JobOpeningRepository(jobOpeningList) as jest.Mocked<JobOpeningRepository>;
        mockedJobOpeningService = new JobOpeningService(mockedJobOpeningRepository) as jest.Mocked<JobOpeningService>;

        mockedCompanyService.create = jest.fn();
        mockedCompanyService.save = jest.fn();
        mockedCompanyService.getByEmail = jest.fn();
        mockedCompanyService.getByCNPJ = jest.fn();

        mockedCompanyView.showProfile = jest.fn();

        companyHandler = new CompanyHandler(mockedCompanyService, mockedJobOpeningService, mockedCompanyView);

        mockedCompany = new Company as jest.Mocked<Company>;
        mockedCompany.id = BigInt(1);
        mockedCompany.name = 'Company A';
        mockedCompany.description = 'good company';
        mockedCompany.email = 'companya@example.com';
        mockedCompany.address = 'Good street, 1';
        mockedCompany.CNPJ = '1234567891';
        
    });

    test('Receive data, creates and saves a company', () => {
        document.body.innerHTML += `
            <form id="company-sign-up-form">
                <input id="company-name" value="Company A">
                <input id="company-email" value="companya@example.com">
                <textarea id="company-description">good company</textarea>
                <input id="company-address" value="Good street, 1">
                <input id="company-cnpj" value="1234567891">
                <button id="company-sign-up-button" type="submit">Sign Up</button>
            </form>`
        
        const form: HTMLFormElement | null = document.querySelector('#company-sign-up-form');
        if (!form) {
            throw new Error('Form not found')
        }

        const submitEvent: Event = new Event('submit', {
            bubbles: true,
            cancelable: true,
        });
        
        companyHandler.startListeners();
        mockedCompanyService.create.mockReturnValue(mockedCompany);

        form.dispatchEvent(submitEvent);

        expect(mockedCompanyService.create).toHaveBeenCalledTimes(1);
        expect(mockedCompanyService.create).toHaveBeenCalledWith(
            mockedCompany.name,
            mockedCompany.email,
            mockedCompany.description,
            mockedCompany.address,
            mockedCompany.CNPJ,
        );

        expect(mockedCompanyService.save).toHaveBeenCalledTimes(1);
        expect(mockedCompanyService.save).toHaveBeenCalledWith(mockedCompany);
    });

    test('Get company profile gets the company from db and calls show profile', () => {
        document.body.innerHTML += `
        <form id="company-login-form">
            <input id="company-email-login" value="company@example.com">
            <button class="login-button" id="company-login-button" type="submit">Log In</button>
        </form>`

        const form: HTMLFormElement | null = document.querySelector('#company-login-form');
        if (!form) {
            throw new Error('Form not found')
        }

        const submitEvent: Event = new Event('submit', {
            bubbles: true,
            cancelable: true,
        });

        companyHandler.startListeners();
        mockedCompanyService.getByEmail.mockReturnValue(mockedCompany);

        form.dispatchEvent(submitEvent);

        expect(mockedCompanyService.getByEmail).toHaveBeenCalledTimes(1);
        expect(mockedCompanyService.getByEmail).toHaveBeenCalledWith('company@example.com');
    
        expect(mockedCompanyView.showProfile).toHaveBeenCalledTimes(1);
        expect(mockedCompanyView.showProfile).toHaveBeenCalledWith(mockedCompany, []);
    });
});