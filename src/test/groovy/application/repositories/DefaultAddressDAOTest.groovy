package application.repositories

import application.mocks.AddressMockFactory
import application.models.dtos.request.AddressDTO
import application.models.entities.Address
import spock.lang.Specification

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class DefaultAddressDAOTest extends Specification {

    Address address1
    Address address2
    AddressDTO addressDTO1
    AddressDTO addressDTO2
    Connection conn
    PreparedStatement stmt
    ResultSet rs
    DefaultAddressDAO defaultAddressDAO

    void setup() {
        AddressMockFactory addressMock = new AddressMockFactory()

        address1 = addressMock.createAddressMock(1)
        address2 = addressMock.createAddressMock(2)
        addressDTO1 = addressMock.createAddressDTOMock(1)
        addressDTO2 = addressMock.createAddressDTOMock(2)

        conn = Mock(Connection)
        stmt = Mock(PreparedStatement)
        rs = Mock(ResultSet)
        defaultAddressDAO = new DefaultAddressDAO(conn)
    }

    def "get by id returns correct address"() {
        given:
        String query = "SELECT * FROM addresses WHERE id = ?"
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> address1.id
        rs.getString("country") >> address1.country
        rs.getString("region") >> address1.region
        rs.getString("city") >> address1.city
        rs.getString("neighborhood") >> address1.neighborhood
        rs.getString("street") >> address1.street
        rs.getString("number") >> address1.number
        rs.getString("zip_code") >> address1.zipCode

        when:
        Address address = defaultAddressDAO.getById(1)

        then:
        address.id == address1.id
        address.country == address1.country
        address.region == address1.region
        address.city == address1.city
        address.neighborhood == address1.neighborhood
        address.street == address1.street
        address.number == address1.number
        address.zipCode == address1.zipCode
    }

    def "get by id returns null for non-existent id"() {
        given:
        String query = "SELECT * FROM addresses WHERE id = ?"
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.executeQuery() >> rs
        rs.next() >> false

        when:
        Address address = defaultAddressDAO.getById(0)

        then:
        address == null
    }

    def "get by candidate id returns correct address"() {
        given:
        String query = "SELECT * FROM addresses WHERE id = (SELECT address_id FROM candidates WHERE id = ?)"
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> address1.id
        rs.getString("country") >> address1.country
        rs.getString("region") >> address1.region
        rs.getString("city") >> address1.city
        rs.getString("neighborhood") >> address1.neighborhood
        rs.getString("street") >> address1.street
        rs.getString("number") >> address1.number
        rs.getString("zip_code") >> address1.zipCode

        when:
        Set<Address> addresses = defaultAddressDAO.getByCandidateId(1)

        then:
        addresses[0].id == address1.id
        addresses[0].country == address1.country
        addresses[0].region == address1.region
        addresses[0].city == address1.city
        addresses[0].neighborhood == address1.neighborhood
        addresses[0].street == address1.street
        addresses[0].number == address1.number
        addresses[0].zipCode == address1.zipCode
    }

    def "get by job opening id returns correct address"() {
        given:
        String query = "SELECT * FROM addresses WHERE id = (SELECT address_id FROM job_openings WHERE id = ?)"
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> address1.id
        rs.getString("country") >> address1.country
        rs.getString("region") >> address1.region
        rs.getString("city") >> address1.city
        rs.getString("neighborhood") >> address1.neighborhood
        rs.getString("street") >> address1.street
        rs.getString("number") >> address1.number
        rs.getString("zip_code") >> address1.zipCode

        when:
        Set<Address> addresses = defaultAddressDAO.getByJobOpeningId(1)

        then:
        addresses[0].id == address1.id
        addresses[0].country == address1.country
        addresses[0].region == address1.region
        addresses[0].city == address1.city
        addresses[0].neighborhood == address1.neighborhood
        addresses[0].street == address1.street
        addresses[0].number == address1.number
        addresses[0].zipCode == address1.zipCode
    }

    def "get by company id returns correct address"() {
        given:
        String query = "SELECT * FROM addresses WHERE id = (SELECT address_id FROM companies WHERE id = ?)"
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> address1.id
        rs.getString("country") >> address1.country
        rs.getString("region") >> address1.region
        rs.getString("city") >> address1.city
        rs.getString("neighborhood") >> address1.neighborhood
        rs.getString("street") >> address1.street
        rs.getString("number") >> address1.number
        rs.getString("zip_code") >> address1.zipCode

        when:
        Set<Address> addresses = defaultAddressDAO.getByCompanyId(1)

        then:
        addresses[0].id == address1.id
        addresses[0].country == address1.country
        addresses[0].region == address1.region
        addresses[0].city == address1.city
        addresses[0].neighborhood == address1.neighborhood
        addresses[0].street == address1.street
        addresses[0].number == address1.number
        addresses[0].zipCode == address1.zipCode
    }

    def "get by field returns correct address"() {
        given:
        String query = "SELECT * FROM addresses WHERE ? = ?"
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.setString(1, "country") >> _
        stmt.setString(2, "Brazil") >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> address1.id
        rs.getString("country") >> address1.country
        rs.getString("region") >> address1.region
        rs.getString("city") >> address1.city
        rs.getString("neighborhood") >> address1.neighborhood
        rs.getString("street") >> address1.street
        rs.getString("number") >> address1.number
        rs.getString("zip_code") >> address1.zipCode

        when:
        Set<Address> addresses = defaultAddressDAO.getByField("country", "Brazil")

        then:
        addresses[0].id == address1.id
        addresses[0].country == address1.country
        addresses[0].region == address1.region
        addresses[0].city == address1.city
        addresses[0].neighborhood == address1.neighborhood
        addresses[0].street == address1.street
        addresses[0].number == address1.number
        addresses[0].zipCode == address1.zipCode
    }

    def "get all returns correct addresses"() {
        given:
        String query = "SELECT * FROM addresses"
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.executeQuery() >> rs
        rs.next() >> true >> true >> false
        rs.getInt("id") >> address1.id >> address2.id
        rs.getString("country") >> address1.country >> address2.country
        rs.getString("region") >> address1.region >> address2.region
        rs.getString("city") >> address1.city >> address2.city
        rs.getString("neighborhood") >> address1.neighborhood >> address2.neighborhood
        rs.getString("street") >> address1.street >> address2.street
        rs.getString("number") >> address1.number >> address2.number
        rs.getString("zip_code") >> address1.zipCode >> address2.zipCode

        when:
        Set<Address> addresses = defaultAddressDAO.getAll()

        then:
        addresses[1].id == address1.id
        addresses[1].country == address1.country
        addresses[1].region == address1.region
        addresses[1].city == address1.city
        addresses[1].neighborhood == address1.neighborhood
        addresses[1].street == address1.street
        addresses[1].number == address1.number
        addresses[1].zipCode == address1.zipCode

        addresses[0].id == address2.id
        addresses[0].country == address2.country
        addresses[0].region == address2.region
        addresses[0].city == address2.city
        addresses[0].neighborhood == address2.neighborhood
        addresses[0].street == address2.street
        addresses[0].number == address2.number
        addresses[0].zipCode == address2.zipCode
    }

    def "save returns correct id"() {
        given:
        String query = "INSERT INTO addresses (country, region, city, neighborhood, street, number, zip_code)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)" +
                "RETURNING id"
        conn.prepareStatement(query) >> stmt
        stmt.setString(1, addressDTO1.country) >> _
        stmt.setString(2, addressDTO1.region) >> _
        stmt.setString(3, addressDTO1.city) >> _
        stmt.setString(4, addressDTO1.neighborhood) >> _
        stmt.setString(5, addressDTO1.street) >> _
        stmt.setString(6, addressDTO1.number) >> _
        stmt.setString(7, addressDTO1.zipCode) >> _
        stmt.executeUpdate() >> 1
        stmt.getResultSet() >> rs
        rs.next() >> true
        rs.getInt("id") >> 1

        when:
        int id = defaultAddressDAO.save(addressDTO1)

        then:
        1 == id
    }

    def "update returns correct address"() {
        given:
        String query = "UPDATE addresses SET country = ?, region = ?, city = ?," +
                "neighborhood = ?, street = ?, number = ?, zip_code = ?" +
                " WHERE id = ?" +
                " RETURNING *"
        conn.prepareStatement(query) >> stmt
        stmt.setString(1, addressDTO1.country) >> _
        stmt.setString(2, addressDTO1.region) >> _
        stmt.setString(3, addressDTO1.city) >> _
        stmt.setString(4, addressDTO1.neighborhood) >> _
        stmt.setString(5, addressDTO1.street) >> _
        stmt.setString(6, addressDTO1.number) >> _
        stmt.setString(7, addressDTO1.zipCode) >> _
        stmt.setInt(8, 1) >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> address1.id
        rs.getString("country") >> address1.country
        rs.getString("region") >> address1.region
        rs.getString("city") >> address1.city
        rs.getString("neighborhood") >> address1.neighborhood
        rs.getString("street") >> address1.street
        rs.getString("number") >> address1.number
        rs.getString("zip_code") >> address1.zipCode

        when:
        Address address = defaultAddressDAO.update(1, addressDTO1)

        then:
        address.id == address1.id
        address.country == address1.country
        address.region == address1.region
        address.city == address1.city
        address.neighborhood == address1.neighborhood
        address.street == address1.street
        address.number == address1.number
        address.zipCode == address1.zipCode
    }

    def "delete executes query"() {
        given:
        String query = "DELETE FROM addresses WHERE id = ?"
        conn.prepareStatement(query) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.executeUpdate() >> 1

        when:
        defaultAddressDAO.delete(1)

        then:
        1 * stmt.executeUpdate()
    }
}
