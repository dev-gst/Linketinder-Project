package Linketinder.services

import Linketinder.models.entities.Address
import Linketinder.models.entities.Company
import Linketinder.models.enums.SkillEnum

class CompanyService implements IEntityService {
    static final int MIN_COMPANIES = 5

    int currentId
    List<Company> companies

    CompanyService() {
        this.currentId = 1
        this.companies = new LinkedList<>()
    }

    @Override
    void populate() {
        for (int i = 0; i < MIN_COMPANIES; i++) {
            Company company = new Company()
            company.ID = currentId.toBigInteger()
            company.name = "Company Name" + i
            company.description = i <= 3 ? "Good company" : "Bad company"
            company.email = "${i}@example.com"
            company.address = i <= 3 ?
                    new Address(country: "Brazil", state: "DF", zipCode: "123456789" + i) :
                    new Address(country: "Brazil", state: "GO", zipCode: "987654321" + i)
            company.CNPJ = "00000000000" + i

            i < 3 ? company.addSkill(SkillEnum.JAVA, SkillEnum.SPRING_BOOT, SkillEnum.GROOVY) :
                    company.addSkill(SkillEnum.ANGULAR, SkillEnum.JAVASCRIPT)

            currentId++

            this.companies.add(company)
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
