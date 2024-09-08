import { CandidateHandler } from "./controllers/CandidateHandler";
import { Candidate } from "./models/Candidate";
import { CandidateRepository } from "./repositories/CandidateRepository";
import { CandidateService } from "./services/CandidateService";

class Main {

    static start() {
        const candidateList: Candidate[] = [];
        const candidateRepository = new CandidateRepository(candidateList);
        const candidateService: CandidateService = new CandidateService(candidateRepository)
        const candidateHandler: CandidateHandler = new CandidateHandler(candidateService);

        candidateHandler.startListeners();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    Main.start();
});