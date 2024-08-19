package Linketinder.services

import Linketinder.model.entity.Address
import Linketinder.model.entity.JobApplicant
import Linketinder.model.entity.Skill

class JobApplicantsService implements IEntityService {
    static final int MIN_APPLICANTS = 5

    int currentID
    List<JobApplicant> jobApplicants

    JobApplicantsService() {
        this.jobApplicants = new LinkedList<>()
        currentID = 1
    }

    @Override
    void populate() {
        for (int i = 0; i < MIN_APPLICANTS; i++) {
            JobApplicant jobApplicant = new JobApplicant()
            jobApplicant.ID = currentID.toBigInteger()
            jobApplicant.name = "Nome" + i
            jobApplicant.description = i <= 3 ? "Good on programing" : "Good on math"
            jobApplicant.email = "${i}@example.com"
            jobApplicant.address = i <= 3 ?
                    new Address(country: "Brazil", state: "DF", zipCode: "123456789" + i) :
                    new Address(country: "Brazil", state: "GO", zipCode: "987654321" + i)
            jobApplicant.age = 18 + i
            jobApplicant.CPF = "00000000000" + i

            i <= 3 ? jobApplicant.addSkill(Skill.JAVA, Skill.SPRING_BOOT, Skill.GROOVY) :
                    jobApplicant.addSkill(Skill.ANGULAR, Skill.JAVASCRIPT)

            currentID++

            this.jobApplicants.add(jobApplicant)
        }
    }

    @Override
    void print() {
        for (JobApplicant j in this.jobApplicants) {
            println j
        }
    }

    @Override
    <T> void add(T jobApplicant) {
        if (jobApplicant instanceof JobApplicant) {
            jobApplicant.ID = currentID
            this.jobApplicants.add(jobApplicant)
            currentID++
        }
    }
}
