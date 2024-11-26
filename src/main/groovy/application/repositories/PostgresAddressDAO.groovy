package application.repositories

import application.models.dtos.request.AddressDTO
import application.models.entities.Address
import application.repositories.interfaces.AddressDAO
import application.utils.validation.ParamValidation

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class PostgresAddressDAO implements AddressDAO {

    private Connection conn

    PostgresAddressDAO(Connection conn) {
        ParamValidation.requireNonNull(conn, "Connection cannot be null")

        this.conn = conn
    }

    @Override
    Address getById(int id) {
        String query = "SELECT * FROM addresses WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()

        return constructAddresses(rs).stream()
                .findFirst()
                .orElse(null)
    }

    @Override
    Set<Address> getByCandidateId(int candidateId) {
        String query = "SELECT * FROM addresses WHERE id = (SELECT address_id FROM candidates WHERE id = ?)"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, candidateId)

        ResultSet rs = stmt.executeQuery()

        return constructAddresses(rs)
    }

    @Override
    Set<Address> getByCompanyId(int companyId) {
        String query = "SELECT * FROM addresses WHERE id = (SELECT address_id FROM companies WHERE id = ?)"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, companyId)

        ResultSet rs = stmt.executeQuery()

        return constructAddresses(rs)
    }

    @Override
    Set<Address> getByJobOpeningId(int jobOpeningId) {
        String query = "SELECT * FROM addresses WHERE id = (SELECT address_id FROM job_openings WHERE id = ?)"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, jobOpeningId)

        ResultSet rs = stmt.executeQuery()

        return constructAddresses(rs)
    }

    @Override
    Set<Address> getByField(String fieldName, String fieldValue) {
        String query = "SELECT * FROM addresses WHERE $fieldName = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setString(1, fieldName)

        ResultSet rs = stmt.executeQuery()

        return constructAddresses(rs)
    }

    @Override
    Set<Address> getAll() {
        String query = "SELECT * FROM addresses"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        ResultSet rs = stmt.executeQuery()

        return constructAddresses(rs)
    }

    private static Set<Address> constructAddresses(ResultSet rs) {
        Set<Address> addresses = new HashSet<>()
        while (rs.next()) {
            Address address = new Address.Builder()
                    .id(rs.getInt("id"))
                    .country(rs.getString("country"))
                    .region(rs.getString("region"))
                    .city(rs.getString("city"))
                    .neighborhood(rs.getString("neighborhood"))
                    .street(rs.getString("street"))
                    .number(rs.getString("number"))
                    .zipCode(rs.getString("zip_code"))
                    .build()

            addresses.add(address)
        }

        return addresses
    }

    @Override
    int save(AddressDTO addressDTO) {
        String query = "INSERT INTO addresses (country, region, city, neighborhood, street, number, zip_code)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)" +
                        "RETURNING id"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, addressDTO.country)
        stmt.setString(2, addressDTO.region)
        stmt.setString(3, addressDTO.city)
        stmt.setString(4, addressDTO.neighborhood)
        stmt.setString(5, addressDTO.street)
        stmt.setString(6, addressDTO.number)
        stmt.setString(7, addressDTO.zipCode)

        stmt.executeQuery()

        ResultSet rs = stmt.getResultSet()
        if (rs.next()) {
            return rs.getInt("id")
        } else {
            throw new SQLException("Error saving address")
        }
    }

    @Override
    Address update(int id, AddressDTO addressDTO) {
        String query = "UPDATE addresses SET country = ?, region = ?, city = ?," +
                "neighborhood = ?, street = ?, number = ?, zip_code = ?" +
                " WHERE id = ?" +
                " RETURNING *"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, addressDTO.country)
        stmt.setString(2, addressDTO.region)
        stmt.setString(3, addressDTO.city)
        stmt.setString(4, addressDTO.neighborhood)
        stmt.setString(5, addressDTO.street)
        stmt.setString(6, addressDTO.number)
        stmt.setString(7, addressDTO.zipCode)
        stmt.setInt(8, id)

        ResultSet rs = stmt.executeQuery()

        return constructAddresses(rs).stream()
                .findFirst()
                .orElse(null)
    }

    @Override
    void delete(int id) {
        String query = "DELETE FROM addresses WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, id)

        stmt.executeUpdate()
    }
}
