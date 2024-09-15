import { JobOpening } from "../models/JobOpening";

export class JobOpeningRepository {

    private _jobOpeningStorage: string;
    private _jobOpeningList: JobOpening[];
    private _lastJobOpeningID: bigint;

    constructor(jobOpeningList: JobOpening[]) {
        this._jobOpeningList = jobOpeningList;
        this._jobOpeningStorage = 'jobOpeningStorage';
        this._lastJobOpeningID = BigInt(0);

        this.load();
    }

    public get jobOpeningList(): JobOpening[] {
        return this._jobOpeningList;
    }

    public get jobOpeningStorage(): string {
        return this._jobOpeningStorage;
    }

    public get lastJobOpeningID(): bigint {
        return this._lastJobOpeningID;
    }

    public persist(): void {
        const jsonData: string = JSON.stringify(this._jobOpeningList, (k, v) => {
            return typeof v === 'bigint' ? v.toString() : v;
        });

        localStorage.setItem(this._jobOpeningStorage, jsonData);
    }

    public save(jobOpening: JobOpening) {
        this._jobOpeningList.push(jobOpening);
    }

    public load(): void {
        const rawJobOpeningList: string | null = localStorage.getItem(this._jobOpeningStorage);

        if (rawJobOpeningList) {
            let parsedJobOpeningList = JSON.parse(rawJobOpeningList, (k, v) => {
                return k.toLowerCase().includes('_id') || k.toLowerCase().includes('id') ? BigInt(v) : v;
            }) as any[];

            for (let data of parsedJobOpeningList) {
                const jobOpening: JobOpening = new JobOpening(
                    data._id,
                    data._title,
                    data._description,
                    data._skillsRequired,
                    data._educationRequired,
                    data._location,
                    data._companyID,
                    data._salary
                );

                this.jobOpeningList.push(jobOpening);
            }

            if (parsedJobOpeningList.length > 0) {
                this._lastJobOpeningID = parsedJobOpeningList[parsedJobOpeningList.length - 1]._id;
            }
        }
    }

    public getByID(id: bigint): JobOpening | null {
        const jobOpening: JobOpening | undefined = this._jobOpeningList.find(p => p.id === id);

        return jobOpening ? jobOpening : null;
    }

    public getByCompanyID(companyID: bigint): JobOpening[] | [] {
        const jobOpening: JobOpening[] = this._jobOpeningList.filter(p => p.companyID === companyID);

        return jobOpening;
    }
}