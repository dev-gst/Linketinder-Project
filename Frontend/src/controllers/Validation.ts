export class Validation {

    public static parseName(name?: string): string {
        if (!name) {
            throw new Error('Name is required');
        }

        const nameLenRegex = /^[\s\dA-Za-zÀ-ÖØ-öø-ÿ]{2,50}$/;
        if (!nameLenRegex.test(name)) {
            throw new Error('Name needs to be between 2 and 50 characters');
        }

        const trimmedRegex = /[\S]+[\s\S]*[\S]+/;
        const trimmedName = name.match(trimmedRegex);
        if (!trimmedName) {
            throw new Error('Could not parse name');
        }

        return trimmedName[0];
    }

    public static parseSkills(skills?: string): string[] {
        if (!skills) {
            throw new Error('Skills are required');
        }

        const splitRegex = /,/g;
        const skillList = skills.split(splitRegex);

        const trimmedRegex = /[\S]+[\s\S]*[\S]+/;
        for (let i = 0; i < skillList.length; i++) {
            let parsedSkill: string | undefined = skillList[i].match(trimmedRegex)?.[0];
            if (!parsedSkill) {
                throw new Error('Error parsing skill: ' + skillList[i]);
            }

            skillList[i] = parsedSkill;
        }

        return skillList.map(skill => {
            if (skill.length === 1) return skill.toUpperCase();

            const firstLetter: string = skill.charAt(0).toUpperCase();
            const restOfSkill: string = skill.slice(1).toLowerCase()

            return firstLetter + restOfSkill;
        });
    }

    public static parseSalary(salary?: string): number {
        if (!salary) return 0

        const numberRegex = /^[0-9]+(\.[0-9]+)?$/;
        if (salary.match(numberRegex) === null) {
            throw new Error('Could not parse salary');
        }

        return parseFloat(salary);
    }


    public static parseAge(ageString?: string): number {
        if (!ageString) {
            throw new Error('Age is required');
        }
        
        const intRegex = /^((1[6-9])|([2-9][0-9])|(1[0-2][0-9])|130)$/;
        if (!intRegex.test(ageString)) {
            throw new Error('Invalid age');
        }

        return parseInt(ageString, 10);
    }

    public static parseEmail(email?: string): string {
        if (!email) {
            throw new Error('Email is required');
        }

        const emailRegex = /^[\w\d.-]+@[\d\w.-]+\.[\w\d.-]+$/;
        if (!emailRegex.test(email)) {
            throw new Error('Invalid email');
        }
        
        return email;
    }

    public static parsePhone(phone?: string): string {
        if (!phone) {
            throw new Error('Phone is required');
        }

        const phoneRegex = /^((\(?[0-9]{2}\)?\s?)|[0-9]{2}\s?)?[0-9]{4,5}-?[0-9]{4}$/
        if (!phoneRegex.test(phone)) {
            throw new Error('Invalid phone');
        }

        phone = phone.replace(/\D/g, '');

        return phone;
    }

    public static parseLinkedin(linkedin?: string): string {
        if (!linkedin) return '';

        const linkedinRegex = /^(https:\/\/)?www.linkedin.com\/in\/\S+$/i
        if (!linkedinRegex.test(linkedin)) {
            throw new Error('Invalid Linkedin url');
        }

        const optinalHttpsRegex = /^https:\/\//i;
        if (!optinalHttpsRegex.test(linkedin)) {
            linkedin = 'https://' + linkedin;
        }

        return linkedin;
    }

    public static parseCommonString(commonString?: string, fieldName?: string): string {
        if (!commonString) {
            throw new Error(
                fieldName ?
                'Please fill the required fields: ' + fieldName :
                'Please fill the required fields'
            );
        }

        const commonStringRegex = /^([\S]+[\s\S\n]*[\S]+)$/
        if (!commonStringRegex.test(commonString)) {
            throw new Error(fieldName ? 'Invalid field: ' + fieldName : 'Invalid field');
        }

        return commonString;
    }

    public static parseNumber(number?: string): string {
        if (!number) {
            throw new Error('Number is required');
        }

        const numberRegex = /^[0-9]+$/;
        if (!numberRegex.test(number)) {
            throw new Error('Invalid number');
        }

        return number;
    }

    public static parseZip(zip?: string): string {
        if (!zip) {
            throw new Error('Zip is required');
        }

        const zipRegex = /^[0-9]{5}-?[0-9]{3}$/;
        if (!zipRegex.test(zip)) {
            throw new Error('Invalid zip');
        }

        zip = zip.replace(/[^0-9]/g, '');

        return zip;
    }

    public static parseCPF(cpf?: string): string {
        if (!cpf) {
            throw new Error('CPF is required');
        }

        const cpfRegex = /^[0-9]{3}\.?[0-9]{3}\.?[0-9]{3}-?[0-9]{2}$/;
        if (!cpfRegex.test(cpf)) {
            throw new Error('Invalid CPF');
        }

        cpf = cpf.replace(/[^0-9]/g, '');

        return cpf;
    }

    public static parseCNPJ(cnpj?: string): string {
        if (!cnpj) {
            throw new Error('CNPJ is required');
        }

        const cnpjRegex = /^[A-z0-9]{2}\.?[A-z0-9]{3}\.?[A-z0-9]{3}\/?[A-z0-9]{4}-?[0-9]{2}$/;
        if (!cnpjRegex.test(cnpj)) {
            throw new Error('Invalid CNPJ');
        }

        cnpj = cnpj.replace(/[^0-9]/g, '');
        
        return cnpj;
    }
}