import { Candidate } from "../../models/Candidate";
import { JobOpening } from "../../models/JobOpening";

export class CandidateView {

    public showProfile(candidate: Candidate, jobOPeningList: JobOpening[]): void {
        const profile = this.buildProfile(candidate, jobOPeningList)
        document.body = profile;
    }

    private buildProfile(candidate: Candidate, jobOPeningList: JobOpening[]): HTMLElement {

        const skills: string = this.buildSkillsString(candidate.skills);
        const jobOpeningTable: string = this.buildJobOpeningTable(jobOPeningList, candidate.skills);

        let HTMLText: string = `
        <body>
            <h1>Profile</h1>
            <section class="profile" id="candidate-profile">
                <div class="profile-entry">
                    <div>Nome:</div>
                    <div>${candidate.name}</div>
                </div>
                <div class="profile-entry">
                    <div>Email:</div>
                    <div>${candidate.email}</div>
                </div>
                 <div class="profile-entry">
                    <div>Linkedin:</div>
                    <div>${candidate.linkedin}</div>
                </div>
                <div class="profile-entry">
                    <div>Tel:</div>
                    <div>${candidate.phone}</div>
                </div>
                <div class="profile-entry">
                    <div>Bio:</div>
                    <div>${candidate.description}</div>
                </div>
                <div class="profile-entry">
                    <div>Endereço:</div>
                    <div>${candidate.address}</div>
                </div>
                <div class="profile-entry">
                    <div>Habilidades:</div>
                    <div>${skills}</div>
                </div>
                <div class="profile-entry">
                    <div>Educação:</div>
                    <div>${candidate.education}</div>
                </div>
                <div class="profile-entry">
                    <div>Idade:</div>
                    <div>${candidate.age}</div>
                </div>
                <div class="profile-entry">
                    <div>CPF</div>
                    <div>${candidate.CPF}</div>
                </div>
            </section>
            <section id="display-job-openings">
                ${jobOpeningTable}
            </section>
        </body>
        `;

        const dom: Document = this.buildDocument(HTMLText);

        return dom.body;
    }

    private buildJobOpeningTable(
        jobOpeningList: JobOpening[],
        candidateSkills?: string[]
    ): string {
        let table = '';

        if (jobOpeningList.length < 1) {
            return table;
        }

        let tableBody = '';
        for (let job of jobOpeningList) {
            let affinity: number = candidateSkills && candidateSkills.length >= 1 ?
                this.buildAffinityField(candidateSkills, job.skillsRequired) : 0.00;

            let skillsRequired = this.buildSkillsString(job.skillsRequired)
            tableBody +=
                `<tr>
                    <th>${job.title}</th>
                    <th>${job.description}</th>
                    <th>${skillsRequired}</th>
                    <th>${job.educationRequired}</th>
                    <th>${job.location}</th>
                    <th>${job.salary != 0 ? job.salary : 'negotiable'}</th>
                    <th>${affinity.toFixed(2)}%</th>
                </tr>`
        }

        table = `
            <h3>Company Open Positions</h3>
            <table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Skills</th>
                        <th>Education</th>
                        <th>Location</th>
                        <th>Salary</th>
                        <th>Affinity</th>
                    </tr>
                </thead>
                <tbody>
                    ${tableBody}
                </tbody>
            </table>`;

        return table;
    }

    private buildSkillsString(skills?: string[]): string {
        if (!skills) throw new Error('Skill cannot be empty');

        return skills
            .map(skill => ' ' + skill)
            .toString()
            .trim();
    }

    private buildAffinityField(
        candidateSkills: string[],
        jobOpeningSkils: string[]
    ): number {

        let counter = 0;
        for (let cSkill of candidateSkills) {
            for (let jSkill of jobOpeningSkils) {
                if (cSkill.toLowerCase().trim() === jSkill.toLowerCase().trim()) {
                    counter++;
                }
            }
        }

        return (counter / jobOpeningSkils.length) * 100;
    }

    private buildDocument(text: string): Document {
        const parser = new DOMParser();
        const dom = parser.parseFromString(text, 'text/html');

        return dom;
    }
}