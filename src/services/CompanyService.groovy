package services

import model.entity.Address
import model.entity.Company
import model.entity.Skill

class CompanyService implements IEntityService {
    static final int MAX_APPLICANTS = 5

    Company[] companies = new Company[MAX_APPLICANTS]

    @Override
    void populate() {
        for (int i = 1; i <= MAX_APPLICANTS; i++) {
            Company company = new Company()
            company.ID = i.toBigInteger()
            company.name = "Company Name" + i
            company.description = i <= 3 ? "Good company" : "Bad company"
            company.email = "${i}@example.com"
            company.address = i <= 3 ?
                    new Address(country: "Brazil", state: "DF", CEP: "123456789" + i) :
                    new Address(country: "Brazil", state: "GO", CEP: "987654321" + i)
            company.CNPJ = "00000000000" + i

            i <= 3 ? company.addSkill(Skill.JAVA, Skill.SPRING_BOOT, Skill.GROOVY) :
                    company.addSkill(Skill.ANGULAR, Skill.JAVASCRIPT)

            this.companies[i - 1] = company
        }
    }

    @Override
    void print() {
        for (Company c in this.companies) {
            println c
        }
    }
}
