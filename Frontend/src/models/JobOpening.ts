export class JobOpening {
    
    private _id: bigint;
    private _title: string;
    private _description: string;
    private _skillsRequired: string[];
    private _educationRequired: string;
    private _location: string;
    private _companyID: bigint;
    private _salary: number | undefined;

    constructor(
        id: bigint,
        title: string,
        description: string,
        skillsRequired: string[],
        educationRequired: string,
        location: string,
        companyID: bigint,
        salary?: number,
    ) {
        this._id = id;
        this._title = title;
        this._description = description;
        this._skillsRequired = skillsRequired;
        this._educationRequired = educationRequired;
        this._location = location;
        this._companyID = companyID;
        this._salary = salary;
    }

    get id(): bigint {
        return this._id;
    }

    set id(id: bigint) {
        this._id = id;
    }

    get title(): string {
        return this._title;
    }

    set title(title: string) {
        this._title = title;
    }
    
    get description(): string {
        return this._description;
    }

    set description(description: string) {
        this._description = description;
    }

    get skillsRequired(): string[] {
        return this._skillsRequired;
    }

    set skillsRequired(skillsRequired: string[]) {
        this._skillsRequired = skillsRequired;
    }

    get educationRequired(): string {
        return this._educationRequired;
    }

    set educationRequired(educationRequired: string) {
        this._educationRequired= educationRequired;
    }

    get location(): string {
        return this._location;
    }

    set location(location: string) {
        this._location = location;
    }

    get companyID(): bigint {
        return this._companyID;
    }

    set companyID(companyID: bigint) {
        this._companyID = companyID;
    }


    get salary(): number | undefined {
        return this._salary;
    }

    set salary(salary: number | undefined) {
        this._salary = salary;
    }
}