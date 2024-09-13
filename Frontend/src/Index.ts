import { CandidateHandler } from "./controllers/CandidateHandler";
import { CompanyHandler } from "./controllers/CompanyHandler";
import { Candidate } from "./models/Candidate";
import { Company } from "./models/Company";
import { CandidateRepository } from "./repositories/CandidateRepository";
import { CompanyRepository } from "./repositories/CompanyRepository";
import { CandidateService } from "./services/CandidateService";
import { CompanyService } from "./services/CompanyService";
import { CandidateView } from "./views/candidate/CandidateView";
import { CompanyView } from "./views/company/CompanyView";

class Main {

    static start() {
        const candidateList: Candidate[] = [];
        const candidateRepository = new CandidateRepository(candidateList);
        const candidateView = new CandidateView();
        const candidateService: CandidateService = new CandidateService(candidateRepository)
        const candidateHandler: CandidateHandler = new CandidateHandler(candidateService, candidateView);

        const companyList: Company[] = [];
        const companyRepository = new CompanyRepository(companyList);
        const companyView = new CompanyView();
        const companyService: CompanyService = new CompanyService(companyRepository)
        const companyHandler: CompanyHandler = new CompanyHandler(companyService, companyView);

        candidateHandler.startListeners();
        companyHandler.startListeners();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    Main.start();
});