export class Person {

    private _id: bigint;
    private _name: string;
    private _description: string;
    private _email: string;
    private _linkedin: string;
    private _phone: string;
    private _street: string;
    private _number: string;
    private _district: string;
    private _city: string;
    private _state: string;
    private _zip: string;

    constructor(
        id: bigint,
        name: string,
        description: string,
        email: string,
        street: string,
        linkedin: string,
        phone: string,
        number: string,
        district: string,
        city: string,
        state: string,
        zip: string,
    ) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._email = email;
        this._street = street;
        this._linkedin = linkedin;
        this._phone = phone;
        this._number = number;
        this._district = district;
        this._city = city;
        this._state = state;
        this._zip = zip;
    }

    public get id(): bigint {
        return this._id;
    }

    public set id(id: bigint) {
        this._id = id;
    }

    public get name(): string {
        return this._name;
    }

    public set name(name: string) {
        this._name = name;
    }

    public get description(): string {
        return this._description;
    }

    public set description(description: string) {
        this._description = description;
    }

    public get email(): string {
        return this._email;
    }

    public set email(email: string) {
        this._email = email;
    }
    
    public get street(): string {
        return this._street;
    }

    public set street(street: string) {
        this._street = street;
    }

    public get linkedin(): string {
        return this._linkedin;
    }

    public set linkedin(linkedin: string) {
        this._linkedin = linkedin;
    }

    public get phone(): string {
        return this._phone;
    }

    public set phone(phone: string) {
        this._phone = phone;
    }

    public get number(): string {
        return this._number;
    }

    public set number(number: string) {
        this._number = number;
    }

    public get district(): string {
        return this._district;
    }

    public set district(district: string) {
        this._district = district;
    }

    public get city(): string {
        return this._city;
    }

    public set city(city: string) {
        this._city = city;
    }

    public get state(): string {
        return this._state;
    }

    public set state(state: string) {
        this._state = state;
    }

    public get zip(): string {
        return this._zip;
    }

    public set zip(zip: string) {
        this._zip = zip;
    }

    public get address(): string {
        return `${this.street}, n° ${this.number} - Bairro ${this.district} - ${this.city}, ${this.state} - CEP: ${this.zip}`;
    }
}