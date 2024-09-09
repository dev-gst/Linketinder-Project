import { CandidateHandler } from "./controllers/CandidateHandler";
import { Candidate } from "./models/Candidate";
import { CandidateRepository } from "./repositories/CandidateRepository";
import { CandidateService } from "./services/CandidateService";
import { CandidateView } from "./views/candidate/CandidateView";

class Main {

    static start() {
        const candidateList: Candidate[] = [];
        const candidateRepository = new CandidateRepository(candidateList);
        const candidateView = new CandidateView();
        const candidateService: CandidateService = new CandidateService(candidateRepository)
        const candidateHandler: CandidateHandler = new CandidateHandler(candidateService, candidateView);

        candidateHandler.startListeners();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    Main.start();
});