package services

import model.entity.Address
import model.entity.Company
import model.entity.Skill

class CompanyService implements IEntityService {
    static final int MIN_APPLICANTS = 5

    int currentId
    List<Company> companies

    CompanyService() {
        this.currentId = 1
        this.companies = new LinkedList<>()
    }

    @Override
    void populate() {
        for (int i = 0; i < MIN_APPLICANTS; i++) {
            Company company = new Company()
            company.ID = currentId
            company.name = "Company Name" + i
            company.description = i <= 3 ? "Good company" : "Bad company"
            company.email = "${i}@example.com"
            company.address = i <= 3 ?
                    new Address(country: "Brazil", state: "DF", CEP: "123456789" + i) :
                    new Address(country: "Brazil", state: "GO", CEP: "987654321" + i)
            company.CNPJ = "00000000000" + i

            i < 3 ? company.addSkill(Skill.JAVA, Skill.SPRING_BOOT, Skill.GROOVY) :
                    company.addSkill(Skill.ANGULAR, Skill.JAVASCRIPT)

            currentId++

            this.companies.add(company)
        }
    }

    @Override
    void print() {
        for (Company c in this.companies) {
            println c
        }
    }

    @Override
    <T> void add(T company) {
        if (company instanceof Company) {
            company.ID = currentId
            this.companies.add(company)
            currentId++
        }
    }
}
