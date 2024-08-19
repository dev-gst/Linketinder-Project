package Linketinder.ui

import Linketinder.model.entity.Address
import Linketinder.model.entity.JobApplicant
import Linketinder.model.entity.Skill
import Linketinder.ui.util.Helpers

class JobApplicantBuilderMenu {

    static JobApplicant create() {

        JobApplicant jobApplicant = new JobApplicant()
        Address address = new Address()

        print "Enter applicant's name "
        String name = Helpers.getFieldFromUsr()

        print "Enter applicant's age "
        int age = Helpers.getAgeFromUsr()

        print "Enter applicant's CPF "
        String cpf = Helpers.getFieldFromUsr()

        print "Enter applicant's description "
        String description = Helpers.getFieldFromUsr()

        print "Enter applicant's email "
        String email = Helpers.getFieldFromUsr()

        print "Enter applicant's Country "
        String country = Helpers.getFieldFromUsr()

        print "Enter applicant's State "
        String state = Helpers.getFieldFromUsr()

        print "Enter applicant's Zip Code "
        String zipCode = Helpers.getFieldFromUsr()

        print "Enter applicant's skills (separated by comma) "
        Skill[] skills = Helpers.getSkillsFromUsr()

        jobApplicant.name = name
        jobApplicant.age = age
        jobApplicant.CPF = cpf
        jobApplicant.description = description
        jobApplicant.email = email
        jobApplicant.addSkill skills

        address.country = country
        address.state = state
        address.zipCode = zipCode

        jobApplicant.address = address

        return jobApplicant
    }
}
