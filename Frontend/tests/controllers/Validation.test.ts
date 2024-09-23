import { Validation } from "../../src/controllers/Validation";

describe('Test Validation Controller', () => {


    // parseName
    test('parseName should throw error if name is empty', () => {
        expect(() => Validation.parseName('')).toThrow('Name is required');
    });

    test('parseName should throw error if name < 2 or > 50 characters', () => {
        expect(() => Validation.parseName('a')).toThrow('Name needs to be between 2 and 50 characters');
        expect(() => Validation.parseName('a'.repeat(51))).toThrow('Name needs to be between 2 and 50 characters');
    });

    test('parseName should trim', () => {
        expect(Validation.parseName(' Jonh Doe ')).toBe('Jonh Doe');
        expect(Validation.parseName(' Marie Lili ')).toBe('Marie Lili');
    });

    test('parseName should work if a valid name is passed', () => {
        expect(Validation.parseName('Mark Doe')).toBe('Mark Doe');
        expect(Validation.parseName('John Kay')).toBe('John Kay');
        expect(Validation.parseName('John Doe Doe Doe')).toBe('John Doe Doe Doe');
        expect(Validation.parseName('Zé Ninguém da Rocha')).toBe('Zé Ninguém da Rocha');
    });


    // parseSkills
    test('parseSkills should throw error if skills is empty or too badly formated', () => {
        expect(() => Validation.parseSkills('')).toThrow('Skills are required');
        expect(() => Validation.parseSkills(',Javascript,Typescript,')).toThrow('Error parsing skill: ');
    });

    test('parseSkills should return a array of skills', () => {
        expect(Validation.parseSkills('Java, Javascript, Typescript, Python')).toEqual(['Java', 'Javascript', 'Typescript', 'Python']);
    });

    test('parseSkills should map first letter to uppercase and remaining to lowercase', () => {
        expect(Validation.parseSkills('java, javascript, typeScript, pytHon')).toEqual(['Java', 'Javascript', 'Typescript', 'Python']);
    });


    // parseSalary
    test('parseSalary should throw if a badly formated number was provided', () => {
        expect(() => Validation.parseSalary('adfasd234234')).toThrow('Could not parse salary');
        expect(() => Validation.parseSalary('1.,234234')).toThrow('Could not parse salary');
    });

    test('parseSalary should return 0 if a salary was not provided', () => {
        expect(Validation.parseSalary('')).toBe(0);
    });

    test('parseSalary should return the salary as a number', () => {
        expect(Validation.parseSalary('1234')).toBe(1234);
        expect(Validation.parseSalary('1234.56')).toBe(1234.56);
    });


    // parseAge
    test('parseAge should throw if a badly formated number was provided', () => {
        expect(() => Validation.parseAge('adfasd234234')).toThrow('Invalid age');
        expect(() => Validation.parseAge('1.,234234')).toThrow('Invalid age');
        expect(() => Validation.parseAge('1.23')).toThrow('Invalid age');
    });

    test('parseAge should throw if age was not provided', () => {
        expect(() => Validation.parseAge('')).toThrow('Age is required');
        expect(() => Validation.parseAge(undefined)).toThrow('Age is required');
    });

    test('parseAge should throw if age < 16 or > 130', () => {
        expect(() => Validation.parseAge('15')).toThrow('Invalid age');
        expect(() => Validation.parseAge('131')).toThrow('Invalid age');
        expect(() => Validation.parseAge('0')).toThrow('Invalid age');
    });

    test('parseAge should work if age >= 16 and <= 130', () => {
        expect(Validation.parseAge('16')).toBe(16);
        expect(Validation.parseAge('130')).toBe(130);
        expect(Validation.parseAge('25')).toBe(25);
        expect(Validation.parseAge('99')).toBe(99);
        expect(Validation.parseAge('100')).toBe(100);
        expect(Validation.parseAge('129')).toBe(129);
        expect(Validation.parseAge('17')).toBe(17);
        expect(Validation.parseAge('38')).toBe(38);
        expect(Validation.parseAge('82')).toBe(82);
        expect(Validation.parseAge('129')).toBe(129);
    });


    // parsePhone
    test('parsePhone should throw if a badly formated number was provided', () => {
        expect(() => Validation.parsePhone('adfasd234234')).toThrow('Invalid phone');
        expect(() => Validation.parsePhone('1.,234234')).toThrow('Invalid phone');
        expect(() => Validation.parsePhone('1.23')).toThrow('Invalid phone');
    });

    test('parsePhone should throw if no phone number was provided', () => {
        expect(() => Validation.parsePhone('')).toThrow('Phone is required');
    });

    test('parsePhone returns valid numbers', () => {
        expect(Validation.parsePhone('(21)98765-4321')).toBe('21987654321');
        expect(Validation.parsePhone('(11)1234-1234')).toBe('1112341234');
        expect(Validation.parsePhone('(11) 1234-1234')).toBe('1112341234');
        expect(Validation.parsePhone('(11 1234-1234')).toBe('1112341234');
        expect(Validation.parsePhone('1112341234')).toBe('1112341234');
    });


    // parseCommonString
    test('parseCommonString should throw error if commonString is empty', () => {
        expect(() => Validation.parseCommonString('')).toThrow('Please fill the required fields');
        expect(() => Validation.parseCommonString('', 'Description')).toThrow('Please fill the required fields: Description');
    });

    test('parseCommonString should throw error if commonString is badly formatted', () => {
        expect(() => Validation.parseCommonString(' ')).toThrow('Invalid field');
        expect(() => Validation.parseCommonString(' ', 'Description')).toThrow('Invalid field: Description');
    });

    test('parseCommonString should work with valid strings', () => {
        expect(Validation.parseCommonString('This is a description.')).toBe('This is a description.');
        expect(Validation.parseCommonString('123 Main St')).toBe('123 Main St');
        expect(Validation.parseCommonString('California')).toBe('California');
        expect(Validation.parseCommonString('United States')).toBe('United States');
    });


    // parseEmail
    test('parseEmail should throw error if email is empty', () => {
        expect(() => Validation.parseEmail('')).toThrow('Email is required');
    });

    test('parseEmail should throw error if email is invalid', () => {
        expect(() => Validation.parseEmail('invalid-email')).toThrow('Invalid email');
        expect(() => Validation.parseEmail('invalid@')).toThrow('Invalid email');
        expect(() => Validation.parseEmail('invalid@domain')).toThrow('Invalid email');
    });

    test('parseEmail should work with valid email', () => {
        expect(Validation.parseEmail('test@example.com')).toBe('test@example.com');
        expect(Validation.parseEmail('user.name@domain.co')).toBe('user.name@domain.co');
    });


    // parseLinkedin
    test('parseLinkedin should return empty string if linkedin is not provided', () => {
        expect(Validation.parseLinkedin('')).toBe('');
        expect(Validation.parseLinkedin(undefined)).toBe('');
    });

    test('parseLinkedin should throw error if linkedin url is invalid', () => {
        expect(() => Validation.parseLinkedin('invalid-linkedin')).toThrow('Invalid Linkedin url');
        expect(() => Validation.parseLinkedin('www.linkedin.com/off/username')).toThrow('Invalid Linkedin url');
    });

    test('parseLinkedin should work with valid linkedin url', () => {
        expect(Validation.parseLinkedin('https://www.linkedin.com/in/username')).toBe('https://www.linkedin.com/in/username');
        expect(Validation.parseLinkedin('www.linkedin.com/in/username')).toBe('https://www.linkedin.com/in/username');
    });


    // parseNumber
    test('parseNumber should throw error if number is empty', () => {
        expect(() => Validation.parseNumber('')).toThrow('Number is required');
    });

    test('parseNumber should throw error if number is invalid', () => {
        expect(() => Validation.parseNumber('invalid-number')).toThrow('Invalid number');
    });

    test('parseNumber should work with valid number', () => {
        expect(Validation.parseNumber('123456')).toBe('123456');
    });


    // parseZip
    test('parseZip should throw error if zip is empty', () => {
        expect(() => Validation.parseZip('')).toThrow('Zip is required');
    });

    test('parseZip should throw error if zip is invalid', () => {
        expect(() => Validation.parseZip('invalid-zip')).toThrow('Invalid zip');
    });

    test('parseZip should work with valid zip', () => {
        expect(Validation.parseZip('12345-678')).toBe('12345678');
        expect(Validation.parseZip('12345678')).toBe('12345678');
    });


    // parseCPF
    test('should throw error if CPF is not provided', () => {
        expect(() => Validation.parseCPF()).toThrow('CPF is required');
    });

    test('should throw error if CPF is invalid', () => {
        expect(() => Validation.parseCPF('123.4569.789-00')).toThrow('Invalid CPF');
        expect(() => Validation.parseCPF('123456787900')).toThrow('Invalid CPF');
    });

    test('should return cleaned CPF if CPF is valid', () => {
        expect(Validation.parseCPF('123.456.789-09')).toBe('12345678909');
        expect(Validation.parseCPF('12345678909')).toBe('12345678909');
    });


    // parseCNPJ
    test('should throw error if CNPJ is not provided', () => {
        expect(() => Validation.parseCNPJ()).toThrow('CNPJ is required');
    });

    test('should throw error if CNPJ is invalid', () => {
        expect(() => Validation.parseCNPJ('12.3450.678/0001-00')).toThrow('Invalid CNPJ');
        expect(() => Validation.parseCNPJ('123456748000100')).toThrow('Invalid CNPJ');
    });

    test('should return cleaned CNPJ if CNPJ is valid', () => {
        expect(Validation.parseCNPJ('12.345.678/0001-95')).toBe('12345678000195');
        expect(Validation.parseCNPJ('12345678000195')).toBe('12345678000195');
    });
});