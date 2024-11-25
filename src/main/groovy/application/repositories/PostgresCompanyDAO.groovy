package application.repositories

import application.models.dtos.request.CompanyDTO
import application.models.entities.Company
import application.models.entities.login.LoginDetails
import application.repositories.interfaces.CompanyDAO

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class PostgresCompanyDAO implements CompanyDAO {

    private Connection conn

    PostgresCompanyDAO(Connection conn) {
        this.conn = conn
    }

    @Override
    Company getById(int id) {
        String query = "SELECT * FROM companies WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()

        return constructCompanies(rs).stream()
                .findFirst()
                .orElse(null)
    }

    @Override
    Set<Company> getByField(String fieldName, String fieldValue) {
        String query = "SELECT * FROM companies WHERE $fieldName = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setString(1, fieldValue)

        ResultSet rs = stmt.executeQuery()

        return constructCompanies(rs)
    }

    @Override
    Set<Company> getByJobOpeningId(int jobOpeningId) {
        String query = "SELECT * FROM companies WHERE id = (SELECT company_id FROM job_openings WHERE id = ?)"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, jobOpeningId)

        ResultSet rs = stmt.executeQuery()

        return constructCompanies(rs)
    }

    @Override
    Set<Company> getAll() {
        String query = "SELECT * FROM companies"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        ResultSet rs = stmt.executeQuery()

        return constructCompanies(rs)
    }

    private static Set<Company> constructCompanies(ResultSet rs) {
        Set<Company> companies = new HashSet<>()
        while (rs.next()) {
            Company company = new Company.Builder()
                    .setId(rs.getInt("id"))
                    .setName(rs.getString("name"))
                    .setLoginDetails(new LoginDetails(rs.getString("email"), rs.getString("password")))
                    .setDescription(rs.getString("description"))
                    .setCnpj(rs.getString("cnpj"))
                    .setAddressId(rs.getInt("address_id"))
                    .build()

            companies.add(company)
        }

        return companies
    }

    @Override
    int save(CompanyDTO companyDTO) {
        String query = "INSERT INTO companies (name, email, password, description, cnpj, address_id) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt = setCompanyStatement(stmt, companyDTO)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            return rs.getInt("id")
        }

        throw new SQLException("Company not saved")
    }

    @Override
    Company update(int id, CompanyDTO companyDTO) {
        String query = "UPDATE companies SET name = ?, email = ?, password = ?," +
                " description = ?, cnpj = ?, address_id = ? WHERE id = ? RETURNING *"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt = setCompanyStatement(stmt, companyDTO)
        stmt.setInt(7, id)

        stmt.executeUpdate()

        ResultSet rs = stmt.executeQuery()
        return constructCompanies(rs).stream()
                .findFirst()
                .orElse(null)
    }

    private static PreparedStatement setCompanyStatement(PreparedStatement stmt, CompanyDTO companyDTO) {
        stmt.setString(1, companyDTO.getName())
        stmt.setString(2, companyDTO.getLoginDetailsDTO().getEmail())
        stmt.setString(3, companyDTO.getLoginDetailsDTO().getPassword())
        stmt.setString(4, companyDTO.getDescription())
        stmt.setString(5, companyDTO.getCnpj())
        stmt.setInt(6, companyDTO.getAddressId())

        return stmt
    }

    @Override
    void delete(int id) {
        String query = "DELETE FROM companies WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        stmt.executeUpdate()
    }

    @Override
    Company authenticate(String email, String password) {
        String query = "SELECT * FROM companies WHERE email = ? AND password = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, email)
        stmt.setString(2, password)

        ResultSet rs = stmt.executeQuery()

        return constructCompanies(rs).stream()
                .findFirst()
                .orElse(null)
    }
}
