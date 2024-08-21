package Linketinder.ui

import Linketinder.models.entities.Address
import Linketinder.models.entities.Candidate
import Linketinder.models.enums.SkillEnum
import Linketinder.ui.util.Helpers

class CandidateBuilderMenu {

    static Candidate create() {
        Scanner scanner = new Scanner(System.in)
        Candidate candidate = new Candidate()
        Address address = new Address()

        print "Enter applicant's name "
        String name = Helpers.getFieldFromUsr(scanner)

        print "Enter applicant's age "
        int age = Helpers.getAgeFromUsr(scanner)

        print "Enter applicant's CPF "
        String cpf = Helpers.getFieldFromUsr(scanner)

        print "Enter applicant's description "
        String description = Helpers.getFieldFromUsr(scanner)

        print "Enter applicant's email "
        String email = Helpers.getFieldFromUsr(scanner)

        print "Enter applicant's Country "
        String country = Helpers.getFieldFromUsr(scanner)

        print "Enter applicant's State "
        String state = Helpers.getFieldFromUsr(scanner)

        print "Enter applicant's Zip Code "
        String zipCode = Helpers.getFieldFromUsr(scanner)

        print "Enter applicant's skills (separated by comma) "
        SkillEnum[] skills = Helpers.getSkillsFromUsr(scanner)

        candidate.name = name
        candidate.age = age
        candidate.CPF = cpf
        candidate.description = description
        candidate.email = email
        candidate.addSkill skills

        address.country = country
        address.state = state
        address.zipCode = zipCode

        candidate.address = address

        return candidate
    }
}
