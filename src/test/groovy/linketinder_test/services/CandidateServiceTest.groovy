package linketinder_test.services

import Linketinder.models.entities.Address
import Linketinder.models.entities.Candidate
import Linketinder.models.enums.SkillEnum
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import Linketinder.services.CandidateService

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.mockito.Mockito.*

class CandidateServiceTest {

    CandidateService candidateService

    @BeforeEach
    void initializeVariables() {
        candidateService = new CandidateService()
    }

    @Test
    void populateWorks() {
        candidateService.populate()

        Address address1 = mock(Address.class)
        when(address1.country).thenReturn("Brazil")
        when(address1.state).thenReturn("DF")
        when(address1.zipCode).thenReturn("1234567890")

        Address address2 = mock(Address.class)
        when(address2.country).thenReturn("Brazil")
        when(address2.state).thenReturn("GO")
        when(address2.zipCode).thenReturn("9876543214")

        Candidate candidate1 = mock(Candidate.class)
        when(candidate1.ID).thenReturn(1.toBigInteger())
        when(candidate1.name).thenReturn("Nome0")
        when(candidate1.description).thenReturn("Good on programing")
        when(candidate1.email).thenReturn("0@example.com")
        when(candidate1.address).thenReturn(address1)
        when(candidate1.age).thenReturn(18)
        when(candidate1.CPF).thenReturn("000000000000")
        when(candidate1.skills)
                .thenReturn(new LinkedHashSet<>(Arrays
                        .asList(SkillEnum.JAVA, SkillEnum.SPRING_BOOT, SkillEnum.GROOVY)))

        Candidate candidate2 = mock(Candidate.class)
        when(candidate2.ID).thenReturn(5.toBigInteger())
        when(candidate2.name).thenReturn("Nome4")
        when(candidate2.description).thenReturn("Good on math")
        when(candidate2.email).thenReturn("4@example.com")
        when(candidate2.address).thenReturn(address2)
        when(candidate2.age).thenReturn(22)
        when(candidate2.CPF).thenReturn("000000000004")
        when(candidate2.skills)
                .thenReturn(new LinkedHashSet<>(Arrays
                        .asList(SkillEnum.ANGULAR, SkillEnum.JAVASCRIPT)))

        assertEquals(candidateService.candidates.get(0), candidate1)
        assertEquals(candidateService.candidates.get(4), candidate2)
    }

    @Test
    void addWorks() {
        Candidate candidate = mock(Candidate.class)
        candidateService.add(candidate)

        assertEquals(candidateService.candidates.get(0), candidate)
    }
}
