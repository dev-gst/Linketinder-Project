package services

import model.entity.Address
import model.entity.JobApplicant
import model.entity.Skill

class JobApplicantsService implements IEntityService {
    static final int MAX_APPLICANTS = 5

    JobApplicant[] jobApplicants = new JobApplicant[5]

    @Override
    void populate() {
        JobApplicant[] jobApplicants = new JobApplicant[5]

        for (int i = 1; i <= MAX_APPLICANTS; i++) {
            JobApplicant jobApplicant = new JobApplicant()
            jobApplicant.ID = i.toBigInteger()
            jobApplicant.name = "Nome" + i
            jobApplicant.description = i <= 3 ? "Good on programing" : "Good on math"
            jobApplicant.email = "${i}@example.com"
            jobApplicant.address = i <= 3 ?
                    new Address(country: "Brazil", state: "DF", CEP: "123456789" + i) :
                    new Address(country: "Brazil", state: "GO", CEP: "987654321" + i)
            jobApplicant.age = 18 + i
            jobApplicant.CPF = "00000000000" + i

            i <= 3 ? jobApplicant.addSkill(Skill.JAVA, Skill.SPRING_BOOT, Skill.GROOVY) :
                    jobApplicant.addSkill(Skill.ANGULAR, Skill.JAVASCRIPT)

            jobApplicants[i - 1] = jobApplicant
        }

        this.jobApplicants = jobApplicants
    }

    @Override
    void print() {
        for (JobApplicant j in this.jobApplicants) {
            println j
        }
    }
}
