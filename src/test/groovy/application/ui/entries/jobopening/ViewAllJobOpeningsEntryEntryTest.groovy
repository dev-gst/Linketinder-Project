package application.ui.entries.jobopening

import application.mocks.AddressMockFactory
import application.mocks.JobOpeningMockFactory
import application.models.entities.Address
import application.models.entities.JobOpening
import application.services.interfaces.AddressService
import application.services.interfaces.JobOpeningService
import application.ui.entries.jobopenings.ViewAllJobOpeningsEntry
import spock.lang.Specification

class ViewAllJobOpeningsEntryEntryTest extends Specification {

    JobOpening mockedJobOpening
    Address mockedAddress
    JobOpeningService jobOpeningService
    AddressService addressService
    ViewAllJobOpeningsEntry viewJobOpenings

    void setup() {
        mockedJobOpening = new JobOpeningMockFactory().createJobOpeningMock(1)
        mockedAddress = new AddressMockFactory().createAddressMock(1)
        jobOpeningService = Mock(JobOpeningService.class)
        addressService = Mock(AddressService.class)

        viewJobOpenings = new ViewAllJobOpeningsEntry(jobOpeningService, addressService)
    }

    def "execute prints no job openings message when no job openings are available"() {
        given:
        jobOpeningService.getAll() >> new LinkedHashSet<>()

        when:
        viewJobOpenings.execute()

        then:
        1 * jobOpeningService.getAll() >> _
        0 * addressService.getByEntityId(_, _)
    }

    def "execute prints job openings when job openings are available"() {
        given:
        Set<JobOpening> jobOpenings = Set.of(mockedJobOpening)
        Set<Address> addresses = Set.of(mockedAddress)

        jobOpeningService.getAll() >> jobOpenings
        addressService.getByEntityId(1, JobOpening.class) >> addresses

        when:
        viewJobOpenings.execute()

        then:
        1 * jobOpeningService.getAll() >> jobOpenings
        1 * addressService.getByEntityId(1, JobOpening.class) >> addresses
    }

    def "get entry name returns correct entry name"() {
        expect:
        viewJobOpenings.getEntryName() == "Visualizar vagas"
    }

    def "execute prints job openings if at least one are found"() {
        given:
        Set<Address> addresses = Set.of(mockedAddress)
        addressService.getByEntityId(1, JobOpening.class) >> addresses

        Set<JobOpening> jobOpenings = Set.of(mockedJobOpening)
        jobOpeningService.getAll() >> jobOpenings

        ByteArrayOutputStream testOut = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOut))

        when:
        viewJobOpenings.execute()

        then:
        testOut.toString().contains(
                "***** Vagas Disponíveis *****\n" +
                        "Nome: name1\n" +
                        "Descrição: description1\n" +
                        "Presencial\n" +
                        "Aberta\n" +
                        "Endereço: São Paulo 1, SP 1 - Brazil 1\n" +
                        "\n")
    }

    def "execute prints default message if no job openings are found"() {
        given:
        jobOpeningService.getAll() >> new LinkedHashSet<>()

        ByteArrayOutputStream testOut = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOut))

        when:
        viewJobOpenings.execute()

        then:
        testOut.toString().contains("Nenhuma vaga disponível no momento.")
    }
}
