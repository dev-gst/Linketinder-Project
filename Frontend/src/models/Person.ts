export class Person {

    private _id?: bigint;
    private _name?: string;
    private _description?: string;
    private _email?: string;
    private _address?: string;
    private _skills?: string[];

    constructor(
        id?: bigint,
        name?: string,
        description?: string,
        email?: string,
        address?: string,
        skills?: string[]
    ) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._email = email;
        this._address = address;
        this.skills = skills;
    }
    
    public get id(): bigint | undefined {
        return this._id;
    }

    public set id(id : bigint | undefined) {
        this._id = id;
    }

    public get name(): string | undefined {
        return this._name;
    }

    public set name(name:string | undefined) {
        this._name = name;
    }

    public get description(): string | undefined {
        return this._description;
    }

    public set description(description: string | undefined) {
        this._description = description;
    }

    public get email(): string | undefined {
        return this._email;
    }

    public set email(email: string | undefined) {
        this._email = email;
    }
    
    public get address(): string | undefined {
        return this._address;
    }

    public set address(address: string | undefined) {
        this._address = address;
    }

    public get skills(): string[] | undefined {
        return this._skills;
    }

    public set skills(skills: string[] | undefined) {
        this._skills = skills;
    }
}