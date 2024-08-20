package Linketinder.ui

import Linketinder.models.entities.Address
import Linketinder.models.entities.Company
import Linketinder.models.enums.SkillEnum
import Linketinder.ui.util.Helpers

class CompanyBuilderMenu {

    static Company create() {

        Company company = new Company()
        Address address = new Address()

        print "Enter company's name "
        String name = Helpers.getFieldFromUsr()

        print "Enter company's CNPJ "
        String cnpj = Helpers.getFieldFromUsr()

        print "Enter company's description "
        String description = Helpers.getFieldFromUsr()

        print "Enter company's email "
        String email = Helpers.getFieldFromUsr()

        print "Enter company's Country "
        String country = Helpers.getFieldFromUsr()

        print "Enter company's State "
        String state = Helpers.getFieldFromUsr()

        print "Enter company's Zip Code "
        String zipCode = Helpers.getFieldFromUsr()

        print "Enter required skills (separated by comma) "
        SkillEnum[] skills = Helpers.getSkillsFromUsr()

        company.name = name
        company.CNPJ = cnpj
        company.description = description
        company.email = email
        company.addSkill skills

        address.country = country
        address.state = state
        address.zipCode = zipCode

        company.address = address

        return company
    }
}
