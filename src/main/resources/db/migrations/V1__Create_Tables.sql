CREATE TABLE addresses (
	id SERIAL PRIMARY KEY,
	country VARCHAR(100) NOT NULL,
	region VARCHAR(100) NOT NULL,
	city VARCHAR(100) NOT NULL,
	neighborhood VARCHAR(255) NOT NULL,
	street VARCHAR(255) NOT NULL,
	number VARCHAR(100) NOT NULL,
	zip_code varchar(25) NOT NULL
);

CREATE TABLE skills (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE candidates (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	birth_date DATE NOT NULL,
	cpf VARCHAR(100) NOT NULL,
	education VARCHAR(255) NOT NULL,
	address_id INT REFERENCES addresses(id) NOT NULL
);

CREATE TABLE candidate_skills (
	id SERIAL PRIMARY KEY,
	skill_id INT REFERENCES skills(id) NOT NULL,
	candidate_id INT REFERENCES candidates(id) NOT NULL
);

CREATE TABLE companies (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	cnpj VARCHAR(255) NOT NULL,
	address_id INT REFERENCES addresses(id) NOT NULL
);

CREATE TABLE job_openings (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL,
	is_remote BOOLEAN NOT NULL,
	is_open BOOLEAN NOT NULL,
	company_id INT REFERENCES companies(id) NOT NULL,
	address_id INT REFERENCES addresses(id)
);

CREATE TABLE job_opening_skills (
	id SERIAL PRIMARY KEY,
	skill_id INT REFERENCES skills(id) NOT NULL,
	job_openings_id INT REFERENCES job_openings(id) NOT NULL
);

CREATE TABLE candidate_likes_job_opening (
    id SERIAL PRIMARY KEY,
    candidate_id INT REFERENCES candidates(id) NOT NULL,
    job_opening_id INT REFERENCES job_openings(id) NOT NULL,
    liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE company_likes_candidate (
    id SERIAL PRIMARY KEY,
    company_id INT REFERENCES companies(id) NOT NULL,
    candidate_id INT REFERENCES candidates(id) NOT NULL,
    job_opening_id INT REFERENCES job_openings(id) NOT NULL,
    liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);