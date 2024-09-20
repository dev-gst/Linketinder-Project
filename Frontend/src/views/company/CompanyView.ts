import { Chart, CategoryScale, LinearScale, BarController, BarElement } from 'chart.js';import { Candidate } from "../../models/Candidate";
import { ChartData } from "../../models/ChartData";
import { Company } from "../../models/Company";
import { JobOpening } from "../../models/JobOpening";

export class CompanyView {

    public showProfile(
        company: Company,
        candidateList: Candidate[],
        jobOpeningList: JobOpening[]
    ): void {

        const skills: string[] = [];
        candidateList.forEach(c => {
            c.skills?.forEach(s => {
                skills.push(s);
            })
        })

        const profile = this.buildProfile(
            company,
            candidateList,
            jobOpeningList,
            skills
        )

        document.body = profile;

        Chart.register(CategoryScale, LinearScale, BarController, BarElement);
        
        this.buildChart(skills);
    }

    private buildProfile(
        company: Company,
        candidateList: Candidate[],
        jobOpeningList: JobOpening[],
        skills: string[]
    ): HTMLElement {

        const candidateTable = this.buildCandidateTable(candidateList);
        const jobTable = this.buildJobOpeningTable(jobOpeningList);

        let skillCanvas = '';
        if (skills) {
            skillCanvas = `<canvas id="skill-chart" width="400" height="300"></canvas>`
        }

        let HTMLText: string = `
        <body>
            <h1>Profile</h1>
            <section class="profile" id="company-profile">
                <div class="profile-entry">
                    <div>Nome:</div>
                    <div>${company.name}</div>
                </div>
                <div class="profile-entry">
                    <div>Descrição:</div>
                    <div>${company.description}</div>
                </div>
                <div class="profile-entry">
                    <div>Email:</div>
                    <div>${company.email}</div>
                </div>
                <div class="profile-entry">
                    <div>Linkedin:</div>
                    <div>${company.linkedin}</div>
                </div>
                <div class="profile-entry">
                    <div>Tel:</div>
                    <div>${company.phone}</div>
                </div>
                <div class="profile-entry">
                    <div>Endereço:</div>
                    <div>${company.address}</div>
                </div>
                <div class="profile-entry">
                    <div>CNPJ</div>
                    <div>${company.CNPJ}</div>
                </div>
            </section>

            <section id="display-candidates">
                ${candidateTable}
            </section>

            <section id="display-skill-chart">
                ${skillCanvas}
            </section>

            <section id="display-company-job-openings">
                ${jobTable}
            </section>
            
            <section>
                <form id="create-job-opening-form">
                    <h2>Create Job Opening</h2>
                    <div class="form-content">
                        <div class="form-labels">
                            <label for="job-opening-title">Title: </label>
                            <input id="job-opening-title" type="text">
                        </div>
                        <div class="form-labels">
                            <label for="job-opening-description">Description: </label>
                            <textarea id="job-opening-description" type="text""></textarea>
                        </div>
                        <div class="form-labels">
                            <label for="job-opening-skills">Skills Required: </label>
                            <input id="job-opening-skills" type="text">
                        </div>
                        <div class="form-labels">
                            <label for="job-opening-education">Education: </label>
                            <input id="job-opening-education" type="text">
                        </div>
                        <div class="form-labels">
                            <label for="job-opening-location">Location: </label>
                            <input id="job-opening-location" type="text">
                        </div>
                        <div class="form-labels">
                            <label for="job-opening-salary">Salary: </label>
                            <input id="job-opening-salary" type="text">
                        </div>
                    </div>
                    <button id="create-job-opening-button" type="submit">Create</button>
                </form>
            </section>
        </body>
        `;

        const dom: Document = this.buildDocument(HTMLText);

        return dom.body;
    }

    private buildJobOpeningTable(jobOpeningList: JobOpening[]): string {
        let table = '';

        if (jobOpeningList.length < 1) {
            return table;
        }

        let tableBody = '';
        for (let job of jobOpeningList) {
            let skillsRequired = this.buildSkillsString(job.skillsRequired)
            tableBody +=
                `<tr>
                    <th>${job.title}</th>
                    <th>${job.description}</th>
                    <th>${skillsRequired}</th>
                    <th>${job.educationRequired}</th>
                    <th>${job.location}</th>
                    <th>${job.salary != 0 ? job.salary : 'negotiable'}</th>
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
                    </tr>
                </thead>
                <tbody>
                    ${tableBody}
                </tbody>
            </table>`;

        return table;
    }

    private buildCandidateTable(candidateList: Candidate[]): string {
        let table = '';

        if (candidateList.length < 1) {
            return table;
        }

        let tableBody = '';
        for (let candidate of candidateList) {
            let skills = this.buildSkillsString(candidate.skills);
            tableBody +=
                `<tr>
                    <th>${skills}</th>
                    <th>${candidate.education}</th>
                    <th><button id="choose-button">❤️</button></th>
                </tr>`
        }

        table = `
            <h3>Candidates</h3>
            <table>
                <thead>
                    <tr>
                        <th>Skills</th>
                        <th>Education</th>
                        <th>Choose</th>
                    </tr>
                </thead>
                <tbody>
                    ${tableBody}
                </tbody>
            </table>`;

        return table;
    }

    private buildSkillsString(skills?: string[]): string {
        if (!skills) {
            return '';
        } 

        return skills
            .map(skill => ' ' + skill)
            .toString()
            .trim();
    }

    private buildDocument(text: string): Document {
        const parser = new DOMParser();
        const dom = parser.parseFromString(text, 'text/html');

        return dom;
    }

    private buildChart(skills?: string[]): void {
        if (!skills) return;

        const data: ChartData | null = this.populateChart(skills);

        const canva: HTMLCanvasElement | null = document.querySelector('#skill-chart');
        if (!canva || !data) {
            return;
        }

        const chart = new Chart(canva, {
            type: 'bar',
            data: {
                labels: data.keys,
                datasets: [{
                    label: '# Candidate by competence',
                    data: data.values,
                    backgroundColor: [
                        'rgba(0, 128, 0, 0.6)',
                        'rgba(255, 0, 0, 0.6)',
                        'rgba(255, 255, 0, 0.6)',
                        'rgba(0, 0, 255, 0.6)',
                    ],
                    borderColor: [
                        'rgba(0, 128, 0, 1)',
                        'rgba(255, 0, 0, 1)',
                        'rgba(255, 255, 0, 1)',
                        'rgba(0, 0, 255, 1)',
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        type: 'linear'
                    }
                }
            }
        });
    }

    private populateChart(skills: string[]): ChartData | null {
        if (!skills) return null

        const chartData: ChartData = new ChartData();
        for (let i = 0; i < skills.length; i++) {
            chartData.increment(skills[i]);
        }

        return chartData;
    }
}