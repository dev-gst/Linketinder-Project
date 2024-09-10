import { Candidate } from "../../models/Candidate";

export class CandidateView {

    public showProfile(candidate: Candidate): void {
        const profile = this.buildProfile(candidate)
        document.body = profile;
    }

    private buildProfile(candidate: Candidate): HTMLElement {
        let HTMLText: string = `
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

        const dom: Document = this.buildDocument(HTMLText);

        return dom.body;
    }

    private buildDocument(text: string): Document {
        const parser = new DOMParser();
        const dom = parser.parseFromString(text, 'text/html');

        return dom;
    }
}