import { JobOpening } from "../models/JobOpening";
import { JobOpeningRepository } from "../repositories/JobOpeningRepository";

export class JobOpeningService {

    private _jobOpeningRepository: JobOpeningRepository;
    private _currentID: bigint;

    constructor(jobOpeningRepository: JobOpeningRepository) {
        this._jobOpeningRepository = jobOpeningRepository;
        this._currentID = this._jobOpeningRepository.lastJobOpeningID + BigInt(1);
    }

    get jobOpeningRepository(): JobOpeningRepository {
        return this._jobOpeningRepository;
    }

    public create(
        title: string,
        description: string,
        skillsRequired: string[],
        educationRequired: string,
        location: string,
        companyID: bigint,
        salary?: number
    ): JobOpening {

        const jobOpening: JobOpening = new JobOpening(
            this._currentID,
            title,
            description,
            skillsRequired,
            educationRequired,
            location,
            companyID,
            salary,
        );

        this._currentID++;

        return jobOpening;
    }

    public save(jobOpening: JobOpening) {
        this._jobOpeningRepository.save(jobOpening);
        this._jobOpeningRepository.persist();
    }

    public getByID(id: bigint): JobOpening | null {
        return this._jobOpeningRepository.getByID(id);
    }

    public getByCompanyID(companyID?: bigint): JobOpening[] {
        if (!companyID) return []

        return this._jobOpeningRepository.getByCompanyID(companyID);
    }
}