-- Insert 20 Addresses
INSERT INTO addresses (country, region, city, neighborhood, street, number, zip_code) VALUES
('Brazil', 'São Paulo', 'São Paulo', 'Liberdade', 'Rua da Glória', '123', '01510-000'),
('Brazil', 'São Paulo', 'São Paulo', 'Moema', 'Avenida Ibirapuera', '456', '04520-000'),
('Brazil', 'São Paulo', 'São Paulo', 'Jardins', 'Rua Haddock Lobo', '789', '01414-000'),
('Brazil', 'Rio de Janeiro', 'Rio de Janeiro', 'Copacabana', 'Avenida Atlântica', '321', '22070-000'),
('Brazil', 'Rio de Janeiro', 'Rio de Janeiro', 'Ipanema', 'Rua Visconde de Pirajá', '654', '22410-000'),
('Brazil', 'Minas Gerais', 'Belo Horizonte', 'Savassi', 'Rua da Bahia', '987', '30160-001'),
('Brazil', 'Bahia', 'Salvador', 'Barra', 'Avenida Oceanica', '159', '40140-000'),
('Brazil', 'Paraná', 'Curitiba', 'Batel', 'Avenida do Batel', '753', '80420-000'),
('Brazil', 'Rio Grande do Sul', 'Porto Alegre', 'Cidade Baixa', 'Rua João Alfredo', '951', '90050-001'),
('Brazil', 'Ceará', 'Fortaleza', 'Aldeota', 'Avenida Dom Luís', '753', '60150-001'),
('Brazil', 'Pernambuco', 'Recife', 'Boa Viagem', 'Avenida Boa Viagem', '258', '51011-000'),
('Brazil', 'Distrito Federal', 'Brasília', 'Asa Norte', 'SQS 108 Bloco D', '888', '70832-000'),
('Brazil', 'Santa Catarina', 'Florianópolis', 'Lagoa da Conceição', 'Rua das Pitangueiras', '412', '88062-001'),
('Brazil', 'Goiás', 'Goiânia', 'Setor Bueno', 'Avenida 85', '135', '74215-001'),
('Brazil', 'Alagoas', 'Maceió', 'Pajuçara', 'Rua Jangadeiros', '777', '57030-000'),
('Brazil', 'Espírito Santo', 'Vitória', 'Praia do Canto', 'Rua Joaquim Caetano', '222', '29055-001'),
('Brazil', 'Sergipe', 'Aracaju', 'Atalaia', 'Avenida Santos Dumont', '111', '49035-000'),
('Brazil', 'Paraíba', 'João Pessoa', 'Tambiá', 'Avenida Epitácio Pessoa', '456', '58040-001'),
('Brazil', 'Amazonas', 'Manaus', 'Centro', 'Rua José de Alencar', '999', '69005-001'),
('Brazil', 'Pará', 'Belém', 'Cidade Velha', 'Rua 15 de Novembro', '303', '66015-000'),
('Brazil', 'Rio Grande do Norte', 'Natal', 'Ponta Negra', 'Avenida Engenheiro Roberto Freire', '666', '59084-100');

-- Insert 5 Candidates
INSERT INTO candidates (first_name, last_name, email, password, description, birth_date, cpf, education, address_id) VALUES
('Alice', 'Silva', 'alice.silva@example.com', 'password123', 'Experienced software engineer', '1990-01-15', '123.456.789-00', 'Bachelors in Computer Science', 1),
('Bruno', 'Souza', 'bruno.souza@example.com', 'password123', 'Junior front-end developer', '1995-03-22', '234.567.890-11', 'Bachelors in Design', 2),
('Carlos', 'Lima', 'carlos.lima@example.com', 'password123', 'Project manager with 5 years experience', '1985-07-30', '345.678.901-22', 'Masters in Business Administration', 3),
('Daniela', 'Costa', 'daniela.costa@example.com', 'password123', 'Data analyst with strong skills in SQL', '1992-09-10', '456.789.012-33', 'Bachelor in Statistics', 4),
('Eduardo', 'Pereira', 'eduardo.pereira@example.com', 'password123', 'Digital marketing specialist', '1988-12-25', '567.890.123-44', 'Bachelors in Marketing', 5);

-- Insert 5 Companies
INSERT INTO companies (name, email, password, description, cnpj, address_id) VALUES
('Tech Innovations', 'contact@techinnovations.com', 'password123', 'Leading tech company specializing in AI solutions', '12.345.678/0001-90', 6),
('Creative Designs', 'info@creativedesigns.com', 'password123', 'Full-service creative agency', '23.456.789/0001-01', 7),
('Green Energy Solutions', 'hello@greenenergy.com', 'password123', 'Sustainable energy solutions provider', '34.567.890/0001-12', 8),
('HealthFirst', 'contact@healthfirst.com', 'password123', 'Health technology company focused on innovative solutions', '45.678.901/0001-23', 9),
('EduConnect', 'info@educonnect.com', 'password123', 'Online education platform', '56.789.012/0001-34', 10);

-- Insert 10 Job Openings (2 for each company)
INSERT INTO job_openings (name, description, is_remote, is_open, company_id, address_id) VALUES
('Software Engineer', 'Develop and maintain software applications.', true, true, 1, 11),
('AI Specialist', 'Work on AI solutions and machine learning models.', false, true, 1, 12),
('Graphic Designer', 'Create visual content for clients.', true, true, 2, 13),
('Web Developer', 'Develop and maintain client websites.', false, true, 2, 14),
('Renewable Energy Consultant', 'Consulting on renewable energy solutions.', true, true, 3, 15),
('Solar Project Manager', 'Manage solar energy projects from start to finish.', false, true, 3, 16),
('Software Quality Assurance', 'Ensure quality of software products.', true, true, 4, 17),
('Health IT Analyst', 'Analyze IT systems in healthcare environments.', false, true, 4, 18),
('Content Writer', 'Write engaging content for educational platforms.', true, true, 5, 19),
('Education Technology Specialist', 'Implement and manage educational technologies.', false, true, 5, 20);

-- Insert 50 Skills
INSERT INTO skills (name) VALUES
('Java Programming'),
('Python Programming'),
('JavaScript Development'),
('C# Development'),
('Ruby on Rails'),
('React.js'),
('Angular Development'),
('Node.js Development'),
('HTML/CSS'),
('SQL Database Management'),
('NoSQL Database Management'),
('Machine Learning'),
('Data Analysis'),
('Cybersecurity'),
('DevOps Practices'),
('Cloud Computing'),
('Agile Methodologies'),
('Project Management'),
('UI/UX Design'),
('Graphic Design'),
('SEO Optimization'),
('Content Writing'),
('Digital Marketing'),
('Web Development'),
('Mobile App Development'),
('API Development'),
('Blockchain Technology'),
('System Administration'),
('Network Configuration'),
('Technical Support'),
('Software Testing'),
('Quality Assurance'),
('Business Analysis'),
('Customer Relationship Management (CRM)'),
('E-commerce Development'),
('Social Media Management'),
('Public Speaking'),
('Event Planning'),
('Research Skills'),
('Financial Analysis'),
('Statistical Analysis'),
('Product Management'),
('Video Editing'),
('Photography'),
('Content Strategy'),
('Creative Writing'),
('Game Development'),
('Virtual Reality Development'),
('Augmented Reality Development'),
('Information Technology (IT) Support'),
('Data Visualization'),
('Business Intelligence (BI) Tools');

INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (1, 1);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (2, 1);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (3, 1);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (4, 1);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (5, 1);

INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (6, 2);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (7, 2);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (8, 2);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (9, 2);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (10, 2);

INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (11, 3);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (12, 3);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (13, 3);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (14, 3);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (15, 3);

INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (7, 4);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (17, 4);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (13, 4);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (25, 4);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (43, 4);

INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (6, 5);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (12, 5);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (18, 5);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (22, 5);
INSERT INTO candidate_skills (skill_id, candidate_id) VALUES (50, 5);

INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (31, 1);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (28, 1);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (35, 1);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (44, 1);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (34, 1);

INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (26, 2);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (37, 2);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (48, 2);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (19, 2);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (11, 2);

INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (12, 3);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (16, 3);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (19, 3);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (34, 3);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (17, 3);

INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (47, 4);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (37, 4);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (43, 4);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (25, 4);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (33, 4);

INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (6, 5);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (34, 5);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (18, 5);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (9, 5);
INSERT INTO job_opening_skills (skill_id, job_openings_id) VALUES (21, 5);

INSERT INTO candidate_likes_job_opening (candidate_id, job_opening_id) VALUES (1, 5);
INSERT INTO candidate_likes_job_opening (candidate_id, job_opening_id) VALUES (2, 3);

INSERT INTO company_likes_candidate (company_id, candidate_id, job_opening_id) VALUES (3, 1, 5);
