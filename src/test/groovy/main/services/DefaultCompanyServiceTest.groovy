package main.services

import main.models.dtos.request.company.CompanyDTO
import main.models.entities.Address
import main.models.entities.company.Company
import main.models.entities.jobOpening.JobOpening
import main.repositories.interfaces.CompanyDAO
import main.util.exception.custom.EntityNotFoundException
import mocks.CompanyMock
import spock.lang.Specification

class DefaultCompanyServiceTest extends Specification {

    Company companyMock1
    Company companyMock2
    CompanyDTO companyDTOMock1
    CompanyDTO companyDTOMock2
    CompanyDAO companyDAOMock
    DefaultCompanyService companyService

    void setup() {
        CompanyMock companyMockBuilder = new CompanyMock()

        companyMock1 = companyMockBuilder.createCompanyMock(1)
        companyMock2 = companyMockBuilder.createCompanyMock(2)

        companyDTOMock1 = companyMockBuilder.createCompanyDTOMock(1)
        companyDTOMock2 = companyMockBuilder.createCompanyDTOMock(2)

        companyDAOMock = Mock(CompanyDAO)

        companyService = new DefaultCompanyService(companyDAOMock)
    }


    def "get by id returns correct company"() {
        given:
        companyDAOMock.getById(1) >> companyMock1
        Company expected = companyMock1

        when:
        Company result = companyService.getById(1)

        then:
        expected == result
        1 * companyDAOMock.getById(1) >> companyMock1
    }

    def "get by id throws exception when id is negative"() {
        when:
        companyService.getById(-1)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by entity id returns companies by JobOpening class"() {
        given:
        Set<Company> expected = new LinkedHashSet<>()
        expected.add(companyMock1)
        companyDAOMock.getByJobOpeningId(1) >> expected

        when:
        Set<Company> result = companyService.getByEntityId(1, JobOpening.class)

        then:
        result.size() == 1
        result.contains(companyMock1)
        1 * companyDAOMock.getByJobOpeningId(1) >> expected
    }

    def "get by entity id throws exception for invalid class"() {
        when:
        companyService.getByEntityId(1, String.class)

        then:
        thrown(ClassNotFoundException)
    }

    def "get by entity id throws exception when entity id is negative"() {
        when:
        companyService.getByEntityId(-1, JobOpening.class)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by entity id throws exception when entity class is null"() {
        when:
        companyService.getByEntityId(1, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field returns companies"() {
        given:
        Set<Company> expected = new LinkedHashSet<>()
        expected.add(companyMock1)
        expected.add(companyMock2)
        companyDAOMock.getByField("name", "company") >> expected

        when:
        Set<Company> result = companyService.getByField("name", "company")

        then:
        result.size() == 2
        result.contains(companyMock1)
        result.contains(companyMock2)
        1 * companyDAOMock.getByField("name", "company") >> expected
    }

    def "get by field throws exception when field name is null"() {
        when:
        companyService.getByField(null, "company")

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field throws exception when field name is blank"() {
        when:
        companyService.getByField("", "company")

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field throws exception when field value is null"() {
        when:
        companyService.getByField("name", null)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field throws exception when field value is blank"() {
        when:
        companyService.getByField("name", "")

        then:
        thrown(IllegalArgumentException)
    }

    def "get all returns all companies"() {
        given:
        Set<Company> expected = new LinkedHashSet<>()
        expected.add(companyMock1)
        expected.add(companyMock2)
        companyDAOMock.getAll() >> expected

        when:
        Set<Company> result = companyService.getAll()

        then:
        expected == result
        1 * companyDAOMock.getAll() >> expected
    }

    def "save returns newly saved company id"() {
        given:
        companyDAOMock.getByField("email", "joe1@email.com") >> new LinkedHashSet<>()
        companyDAOMock.save(companyDTOMock1) >> 1

        when:
        int result = companyService.save(companyDTOMock1)

        then:
        1 == result
    }

    def "save returns already saved company id"() {
        given:
        companyMock1.id >> 1
        Set<Company> foundCompanies = new LinkedHashSet<>()
        foundCompanies.add(companyMock1)
        companyDAOMock.getByField("email", "joe1@email.com") >> foundCompanies

        when:
        int result = companyService.save(companyDTOMock1)

        then:
        1 == result
    }

    def "save throws exception when companyDTO is null"() {
        when:
        companyService.save(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "save all returns newly saved companies ids"() {
        given:
        companyDAOMock.getByField("email", "joe1@email.com") >> new LinkedHashSet<>()
        companyDAOMock.save(companyDTOMock1) >> 1

        companyDAOMock.getByField("email", "joe2@email.com") >> new LinkedHashSet<>()
        companyDAOMock.save(companyDTOMock2) >> 2

        Set<CompanyDTO> companyDTOS = new LinkedHashSet<>()
        companyDTOS.add(companyDTOMock1)
        companyDTOS.add(companyDTOMock2)

        when:
        Set<Integer> result = companyService.saveAll(companyDTOS)

        then:
        result.size() == 2
        result.contains(1)
        result.contains(2)
    }

    def "update by id returns updated company"() {
        given:
        Set<Address> foundAddresses = new LinkedHashSet<>()
        foundAddresses.add(companyMock1.companyDetails.address)

        companyDAOMock.getById(1) >> companyMock1
        companyDAOMock.update(companyMock1.id, companyDTOMock1) >> companyMock1

        when:
        Company result = companyService.updateById(1, companyDTOMock1)

        then:
        result == companyMock1
    }

    def "update by id returns updated company with address not found"() {
        given:
        companyDAOMock.getById(1) >> companyMock1
        companyDAOMock.update(1, companyDTOMock1) >> companyMock1

        when:
        Company result = companyService.updateById(1, companyDTOMock1)

        then:
        result == companyMock1
    }

    def "update by id throws exception when id is negative"() {
        when:
        companyService.updateById(-1, companyDTOMock1)

        then:
        thrown(IllegalArgumentException)
    }

    def "update by id throws exception when companyDTO is null"() {
        when:
        companyService.updateById(1, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "update by id throws exception when company not found"() {
        given:
        companyDAOMock.getById(1) >> null

        when:
        companyService.updateById(1, companyDTOMock1)

        then:
        thrown(EntityNotFoundException)
    }

    def "delete by id deletes company"() {
        when:
        companyService.deleteById(1)

        then:
        1 * companyDAOMock.delete(1)
    }

    def "delete by id throws exception when id is negative"() {
        when:
        companyService.deleteById(-1)

        then:
        thrown(IllegalArgumentException)
    }
}