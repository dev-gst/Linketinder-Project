package Linketinder.repositories

import Linketinder.models.DTOs.CompanyDTO
import Linketinder.models.entities.Company

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CompanyDAO {

    Connection conn

    CompanyDAO(Connection conn) {
        this.conn = conn
    }

    Company getById(int id) {
        String query = "SELECT * FROM companies WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            Company company = new Company()

            company.id = rs.getInt("id")
            company.name = rs.getString("name")
            company.email = rs.getString("email")
            company.password = rs.getString("password")
            company.description = rs.getString("description")
            company.cnpj = rs.getString("cnpj")

            return company
        }

        return null
    }

    List<Company> getAll() {
        String query = "SELECT * FROM companies"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        List<Company> companies = new ArrayList<>()

        ResultSet rs = stmt.executeQuery()
        while (rs.next()) {
            Company company = new Company()

            company.id = rs.getInt("id")
            company.name = rs.getString("name")
            company.email = rs.getString("email")
            company.password = rs.getString("password")
            company.description = rs.getString("description")
            company.cnpj = rs.getString("cnpj")

            companies.add(company)
        }

        return companies
    }

    int save(CompanyDTO company, int addressId) {
        String query = "INSERT INTO companies (" +
                "name," +
                "email," +
                "password," +
                "description," +
                "cnpj," +
                "address_id" +
                ") " +
                "VALUES (?, ?, ?, ?, ?, ?)" +
                "RETURNING id"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, company.name)
        stmt.setString(2, company.email)
        stmt.setString(3, company.password)
        stmt.setString(4, company.description)
        stmt.setString(5, company.cnpj)

        stmt.executeQuery()

        ResultSet rs = stmt.getResultSet()
        if (rs.next()) {
            return rs.getInt("id")
        } else {
            throw new SQLException("Error saving candidate")
        }
    }

    void update(int id, CompanyDTO companyDTO, int addressId) {
        String query = "UPDATE companies SET " +
                "name = ?," +
                "email = ?," +
                "password = ?," +
                "description = ?," +
                "cnpj = ?," +
                "address_id = ?" +
                "WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, companyDTO.name)
        stmt.setString(2, companyDTO.email)
        stmt.setString(3, companyDTO.password)
        stmt.setString(4, companyDTO.description)
        stmt.setString(5, companyDTO.cnpj)
        stmt.setInt(6, addressId)
        stmt.setInt(6, id)

        stmt.executeUpdate()
    }

    void delete(int id) {
        String query = "DELETE FROM companies WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, id)

        stmt.executeUpdate()
    }
}
