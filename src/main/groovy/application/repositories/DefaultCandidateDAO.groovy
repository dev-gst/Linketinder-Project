package application.repositories

import application.models.dtos.request.CandidateDTO
import application.models.entities.Candidate
import application.models.entities.login.LoginDetails
import application.repositories.interfaces.CandidateDAO
import application.utils.validation.ParamValidation

import java.sql.*
import java.time.LocalDate
import java.time.ZoneId

class DefaultCandidateDAO implements CandidateDAO {

    private Connection conn

    DefaultCandidateDAO(Connection conn) {
        ParamValidation.requireNonNull(conn, "Connection is required")

        this.conn = conn
    }

    @Override
    Candidate getById(int id) {
        String query = "SELECT * FROM candidates WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()

        return constructCandidates(rs).stream()
                .findFirst()
                .orElse(null)
    }

    @Override
    Set<Candidate> getByField(String fieldName, String fieldValue) {
        String query = "SELECT * FROM candidates WHERE $fieldName = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setString(1, fieldValue)

        ResultSet rs = stmt.executeQuery()

        return constructCandidates(rs)
    }

    @Override
    Set<Candidate> getByAddressId(int addressId) {
        String query = "SELECT * FROM candidates WHERE address_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, addressId)

        ResultSet rs = stmt.executeQuery()

        return constructCandidates(rs)
    }

    @Override
    Set<Candidate> getAll() {
        String query = "SELECT * FROM candidates"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        ResultSet rs = stmt.executeQuery()

        return constructCandidates(rs)
    }

    private static Set<Candidate> constructCandidates(ResultSet rs) {
        Set<Candidate> candidates = new LinkedHashSet<>()
        while (rs.next()) {
            Candidate candidate = new Candidate.Builder()
                    .id(rs.getInt("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .loginDetails(new LoginDetails(rs.getString("email"), rs.getString("password")))
                    .description(rs.getString("description"))
                    .birthDate(rs.getObject("birth_date", LocalDate.class))
                    .cpf(rs.getString("cpf"))
                    .education(rs.getString("education"))
                    .addressId(rs.getInt("address_id"))
                    .build()

            candidates.add(candidate)
        }

        return candidates
    }

    @Override
    int save(CandidateDTO candidateDTO) {
        String query = "INSERT INTO candidates (" +
                "first_name," +
                "last_name," +
                "email," +
                "password," +
                "description," +
                "birth_date," +
                "cpf," +
                "education," +
                "address_id" +
                ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                "RETURNING id"

        PreparedStatement stmt = conn.prepareStatement(query)
        setCandidateStatement(candidateDTO, stmt)

        stmt.executeQuery()

        ResultSet rs = stmt.getResultSet()
        if (rs.next()) {
            return rs.getInt("id")
        }

        throw new SQLException("Error saving candidate")
    }

    @Override
    Candidate update(int id, CandidateDTO candidateDTO) {
        String query = "UPDATE candidates SET " +
                "first_name = ?," +
                "last_name = ?," +
                "email = ?," +
                "password = ?," +
                "description = ?," +
                "birth_date = ?," +
                "cpf = ?," +
                "education = ?," +
                "address_id = ? " +
                "WHERE id = ?" +
                "RETURNING *"

        PreparedStatement stmt = conn.prepareStatement(query)
        setCandidateStatement(candidateDTO, stmt)
        stmt.setInt(10, id)

        ResultSet rs = stmt.executeQuery()

        return constructCandidates(rs).stream()
                .findFirst()
                .orElse(null)
    }

    private static PreparedStatement setCandidateStatement(CandidateDTO candidateDTO, PreparedStatement stmt) {
        stmt.setString(1, candidateDTO.firstName)
        stmt.setString(2, candidateDTO.lastName)
        stmt.setString(3, candidateDTO.loginDetailsDTO.email)
        stmt.setString(4, candidateDTO.loginDetailsDTO.password)
        stmt.setString(5, candidateDTO.description)
        stmt.setObject(6, candidateDTO.birthDate.atZone(ZoneId.of("UTC")).toLocalDate(), Types.DATE)
        stmt.setString(7, candidateDTO.cpf)
        stmt.setString(8, candidateDTO.education)
        stmt.setInt(9, candidateDTO.addressId)

        return stmt
    }

    @Override
    void delete(int id) {
        String query = "DELETE FROM candidates WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, id)

        stmt.executeUpdate()
    }

    @Override
    Candidate authenticate(String email, String password) {
        String query = "SELECT * FROM candidates WHERE email = ? AND password = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, email)
        stmt.setString(2, password)

        ResultSet rs = stmt.executeQuery()

        return constructCandidates(rs).stream()
                .findFirst()
                .orElse(null)
    }
}
