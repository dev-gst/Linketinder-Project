import { Candidate } from "../../../src/models/Candidate";
import { CandidateView } from "../../../src/views/candidate/CandidateView";

describe('Test CandidateView', () => {
    let candidate: Candidate;
    let HTMLText: string;
    let candidateView: CandidateView;

    beforeEach(() => {
        candidateView = new CandidateView();

        candidate = new Candidate()
        candidate.id = BigInt(1);
        candidate.name = 'John Doe';
        candidate.description = 'good person';
        candidate.email = 'johndoe@example.com';
        candidate.address = 'Good street, 1';
        candidate.skills = ['JavaScript', 'TypeScript', 'React'];
        candidate.education = 'Computer Engineering';
        candidate.age = 30;
        candidate.CPF = '1234567891';

        HTMLText = `
        <body>
            <h1>Profile</h1>
            <section class="profile" id="candidate-profile">
                <div class="profile-entry">
                    <div>Name:</div>
                    <div>${candidate.name}</div>
                </div>
                <div class="profile-entry">
                    <div>Email:</div>
                    <div>${candidate.email}</div>
                </div>
                <div class="profile-entry">
                    <div>Bio:</div>
                    <div>${candidate.description}</div>
                </div>
                <div class="profile-entry">
                    <div>Adress:</div>
                    <div>${candidate.address}</div>
                </div>
                <div class="profile-entry">
                    <div>Skills:</div>
                    <div>${candidate.skills}</div>
                </div>
                <div class="profile-entry">
                    <div>Education:</div>
                    <div>${candidate.education}</div>
                </div>
                <div class="profile-entry">
                    <div>Age:</div>
                    <div>${candidate.age}</div>
                </div>
                <div class="profile-entry">
                    <div>CPF</div>
                    <div>${candidate.CPF}</div>
                </div>
            </section>
            <section>
            </section>
        </body>
        `;
    })
    
    test('showProfiles builds and display the candidate profile', () => {
        candidateView.showProfile(candidate);

        const parsedHTML = new DOMParser().parseFromString(HTMLText, "text/html");

        expect(document.body.innerHTML).toEqual(parsedHTML.body.innerHTML);
    })
});