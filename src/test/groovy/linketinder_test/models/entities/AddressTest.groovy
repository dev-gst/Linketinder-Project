package linketinder_test.models.entities

import Linketinder.models.entities.Address
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotEquals

class AddressTest {

    static Address address1
    static Address address2
    static Address address3

    @BeforeEach
    void initializeVariables() {
        address1 = new Address()
        address2 = new Address()
        address3 = new Address()

        address1.country = "Brazil"
        address1.state = "GO"
        address1.zipCode = "1234567"

        address2.country = "United States of America"
        address2.state = "CA"
        address2.zipCode = "7654321"

        address3.country = "Brazil"
        address3.state = "GO"
        address3.zipCode = "1234567"
    }

    @Test
    void isEquals() {
        assertEquals(address1, address3)
    }

    @Test
    void isNotEquals() {
        assertNotEquals(address1, address2)
        assertNotEquals(address2, address3)
    }

    @Test
    void toStringModel() {
        String expectedResult = "Country: Brazil, " +
                                "\nState: GO, " +
                                "\nZIP CODE: 1234567"

        String result = address1.toString()

        assertEquals(result, expectedResult)
    }
}
