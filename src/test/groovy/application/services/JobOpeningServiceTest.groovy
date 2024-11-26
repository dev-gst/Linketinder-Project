package application.services

import application.mocks.JobOpeningMockFactory
import application.models.dtos.request.JobOpeningDTO
import application.models.entities.Company
import application.models.entities.JobOpening
import application.repositories.interfaces.JobOpeningDAO
import spock.lang.Specification

class JobOpeningServiceTest extends Specification {

    JobOpeningDTO jobOpeningDTOMock
    JobOpening jobOpeningMock

    JobOpeningDAO jobOpeningDAO
    JobOpeningService jobOpeningService

    void setup() {
        JobOpeningMockFactory mockJobOpening = new JobOpeningMockFactory()

        jobOpeningMock = mockJobOpening.createJobOpeningMock(1)
        jobOpeningDTOMock = mockJobOpening.createJobOpeningDTOMock(1)

        jobOpeningDAO = Mock(JobOpeningDAO)
        jobOpeningService = new JobOpeningService(jobOpeningDAO)
    }

    def "get by id returns job opening when id is valid"() {
        given:
        jobOpeningDAO.getById(1) >> jobOpeningMock

        when:
        JobOpening result = jobOpeningService.getById(1)

        then:
        result == jobOpeningMock
    }

    def "get by id throws exception when id is negative"() {
        when:
        jobOpeningService.getById(-1)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field returns job openings when field name and value are valid"() {
        given:
        Set<JobOpening> jobOpenings = new LinkedHashSet<>()
        jobOpenings.add(jobOpeningMock)
        jobOpeningDAO.getByField("name", "Developer") >> jobOpenings

        when:
        Set<JobOpening> result = jobOpeningService.getByField("name", "Developer")

        then:
        result == jobOpenings
    }

    def "get by field throws exception when field name is blank"() {
        when:
        jobOpeningService.getByField("", "Developer")

        then:
        thrown(IllegalArgumentException)
    }

    def "get by entity id returns job openings when entity id and class are valid"() {
        given:
        Set<JobOpening> jobOpenings = new LinkedHashSet<>()
        jobOpenings.add(jobOpeningMock)
        jobOpeningDAO.getByCompanyId(1) >> jobOpenings

        when:
        Set<JobOpening> result = jobOpeningService.getByEntityId(1, Company.class)

        then:
        result == jobOpenings
    }

    def "get by entity throws exception when entity id is negative"() {
        when:
        jobOpeningService.getByEntityId(-1, Company.class)

        then:
        thrown(IllegalArgumentException)
    }

    def "get all returns job openings"() {
        given:
        Set<JobOpening> jobOpenings = new LinkedHashSet<>()
        jobOpenings.add(jobOpeningMock)
        jobOpeningDAO.getAll() >> jobOpenings

        when:
        Set<JobOpening> result = jobOpeningService.getAll()

        then:
        result == jobOpenings
    }

    def "Save returns id when job opening DTO is valid"() {
        given:
        jobOpeningDAO.save(jobOpeningDTOMock) >> 1

        when:
        int result = jobOpeningService.save(jobOpeningDTOMock)

        then:
        result == 1
    }

    def "Save throws exception when job opening DTO is null"() {
        when:
        jobOpeningService.save(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "Save all returns ids when job opening DTOs are valid"() {
        given:
        Set<JobOpeningDTO> jobOpeningDTOs = new LinkedHashSet<>()
        jobOpeningDTOs.add(jobOpeningDTOMock)
        jobOpeningDAO.save(jobOpeningDTOMock) >> 1

        when:
        Set<Integer> result = jobOpeningService.saveAll(jobOpeningDTOs)

        then:
        [1] as LinkedHashSet == result
    }

    def "Save all throws exception when job opening DTOs are null"() {
        when:
        jobOpeningService.saveAll(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "UpdateById returns updated job opening when id and DTO are valid"() {
        given:
        jobOpeningDAO.update(1, jobOpeningDTOMock) >> jobOpeningMock

        when:
        JobOpening result = jobOpeningService.updateById(1, jobOpeningDTOMock)

        then:
        result == jobOpeningMock
    }

    def "UpdateById throws exception when id is negative"() {
        given:

        when:
        jobOpeningService.updateById(-1, jobOpeningDTOMock)

        then:
        thrown(IllegalArgumentException)
    }

    def "DeleteById calls DAO delete when id is valid"() {
        when:
        jobOpeningService.deleteById(1)

        then:
        1 * jobOpeningDAO.delete(1)
    }

    def "DeleteById throws exception when id is negative"() {
        when:
        jobOpeningService.deleteById(-1)

        then:
        thrown(IllegalArgumentException)
    }
}