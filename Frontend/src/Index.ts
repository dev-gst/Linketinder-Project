import { CandidateHandler } from "./controllers/CandidateHandler";
import { CompanyHandler } from "./controllers/CompanyHandler";
import { Candidate } from "./models/Candidate";
import { Company } from "./models/Company";
import { JobOpening } from "./models/JobOpening";
import { CandidateRepository } from "./repositories/CandidateRepository";
import { CompanyRepository } from "./repositories/CompanyRepository";
import { JobOpeningRepository } from "./repositories/JobOpeningRepository";
import { CandidateService } from "./services/CandidateService";
import { CompanyService } from "./services/CompanyService";
import { JobOpeningService } from "./services/JobOpeningService";
import { CandidateView } from "./views/candidate/CandidateView";
import { CompanyView } from "./views/company/CompanyView";

class Main {

    static start() {
        const jobOpeningList: JobOpening[] = [];
        const jobOpeningRepository: JobOpeningRepository = new JobOpeningRepository(jobOpeningList);
        const jobOpeningService: JobOpeningService = new JobOpeningService(jobOpeningRepository);

        const candidateList: Candidate[] = [];
        const candidateRepository = new CandidateRepository(candidateList);
        const candidateView = new CandidateView();
        const candidateService: CandidateService = new CandidateService(candidateRepository)
        const candidateHandler: CandidateHandler = new CandidateHandler(candidateService, jobOpeningService, candidateView);

        const companyList: Company[] = [];
        const companyRepository = new CompanyRepository(companyList);
        const companyView = new CompanyView();
        const companyService: CompanyService = new CompanyService(companyRepository)
        const companyHandler: CompanyHandler = new CompanyHandler(companyService, jobOpeningService, companyView);

        candidateHandler.startListeners();
        companyHandler.startListeners();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    Main.start();
});