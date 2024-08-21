package Linketinder.services

import Linketinder.models.entities.Address
import Linketinder.models.entities.Candidate
import Linketinder.models.enums.SkillEnum

class CandidateService implements IEntityService {
    static final int MIN_CANDIDATES = 5

    int currentID
    List<Candidate> candidates

    CandidateService() {
        this.candidates = new LinkedList<>()
        currentID = 1
    }

    @Override
    void populate() {
        for (int i = 0; i < MIN_CANDIDATES; i++) {
            Candidate candidate = new Candidate()
            candidate.ID = currentID.toBigInteger()
            candidate.name = "Nome" + i
            candidate.description = i <= 3 ? "Good on programing" : "Good on math"
            candidate.email = "${i}@example.com"
            candidate.address = i <= 3 ?
                    new Address(country: "Brazil", state: "DF", zipCode: "123456789" + i) :
                    new Address(country: "Brazil", state: "GO", zipCode: "987654321" + i)
            candidate.age = 18 + i
            candidate.CPF = "00000000000" + i

            i <= 3 ? candidate.addSkill(SkillEnum.JAVA, SkillEnum.SPRING_BOOT, SkillEnum.GROOVY) :
                    candidate.addSkill(SkillEnum.ANGULAR, SkillEnum.JAVASCRIPT)

            currentID++

            this.candidates.add(candidate)
        }
    }

    @Override
    <T> void add(T candidate) {
        if (candidate instanceof Candidate) {
            candidate.ID = currentID
            this.candidates.add(candidate)
            currentID++
        }
    }
}
