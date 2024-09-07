import { CandidateHandler } from "./controllers/CandidateHandler";
import { Candidate } from "./models/Candidate";
import { CandidateService } from "./services/CandidateService";

class Main {

    static start() {
        const candidateList: Candidate[] = [];
        const candidateService: CandidateService = new CandidateService(candidateList)
        const candidateHandler: CandidateHandler = new CandidateHandler(candidateService);

        candidateHandler.startListeners();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    Main.start();
});