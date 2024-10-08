package Linketinder.repositories

import Linketinder.config.Env
import Linketinder.models.DTOs.CandidateDTO
import Linketinder.models.entities.Candidate

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate

class CandidateDAO {
    private Connection conn

    CandidateDAO(Connection conn) {
        this.conn = conn
    }

    Candidate getById(int id) {
        String query = "SELECT * FROM candidates WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            Candidate candidate = new Candidate()

            candidate.id = rs.getInt("id")
            candidate.firstName = rs.getString("first_name")
            candidate.lastName = rs.getString("last_name")
            candidate.email = rs.getString("email")
            candidate.password = rs.getString("password")
            candidate.description = rs.getString("description")
            candidate.birthDate = rs.getObject("birth_date", LocalDate.class).atStartOfDay(Env.TIMEZONE).toInstant()
            candidate.cpf = rs.getString("cpf")
            candidate.education = rs.getString("education")

            return candidate
        }

        return null
    }

    Candidate getByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM candidates WHERE email = ? AND password = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setString(1, email)
        stmt.setString(2, password)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            Candidate candidate = new Candidate()

            candidate.id = rs.getInt("id")
            candidate.firstName = rs.getString("first_name")
            candidate.lastName = rs.getString("last_name")
            candidate.email = rs.getString("email")
            candidate.password = rs.getString("password")
            candidate.description = rs.getString("description")
            candidate.birthDate = rs.getObject("birth_date", LocalDate.class).atStartOfDay(Env.TIMEZONE).toInstant()
            candidate.cpf = rs.getString("cpf")
            candidate.education = rs.getString("education")

            return candidate
        }

        return null
    }

    List<Candidate> getAll() {
        String query = "SELECT * FROM candidates"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        List<Candidate> candidates = new ArrayList<>()

        ResultSet rs = stmt.executeQuery()
        while (rs.next()) {
            Candidate candidate = new Candidate()

            candidate.id = rs.getInt("id")
            candidate.firstName = rs.getString("first_name")
            candidate.lastName = rs.getString("last_name")
            candidate.email = rs.getString("email")
            candidate.password = rs.getString("password")
            candidate.description = rs.getString("description")
            candidate.birthDate = rs.getObject("birth_date", LocalDate.class).atStartOfDay(Env.TIMEZONE).toInstant()
            candidate.cpf = rs.getString("cpf")
            candidate.education = rs.getString("education")

            candidates.add(candidate)
        }

        return candidates
    }

    int save(CandidateDTO candidateDTO, int addressID) {
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
        stmt.setString(1, candidateDTO.firstName)
        stmt.setString(2, candidateDTO.lastName)
        stmt.setString(3, candidateDTO.email)
        stmt.setString(4, candidateDTO.password)
        stmt.setString(5, candidateDTO.description)
        stmt.setObject(6, LocalDate.ofInstant(candidateDTO.birthDate, Env.TIMEZONE))
        stmt.setString(7, candidateDTO.cpf)
        stmt.setString(8, candidateDTO.education)
        stmt.setInt(9, addressID)

        stmt.executeQuery()

        ResultSet rs = stmt.getResultSet()
        if (rs.next()) {
            return rs.getInt("id")
        } else {
            throw new SQLException("Error saving candidate")
        }
    }

    void update(int id, CandidateDTO candidateDTO, int addressID) {
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
                "WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, candidateDTO.firstName)
        stmt.setString(2, candidateDTO.lastName)
        stmt.setString(3, candidateDTO.email)
        stmt.setString(4, candidateDTO.password)
        stmt.setString(5, candidateDTO.description)
        stmt.setObject(6, LocalDate.ofInstant(candidateDTO.birthDate, Env.TIMEZONE))
        stmt.setString(7, candidateDTO.cpf)
        stmt.setString(8, candidateDTO.education)
        stmt.setInt(9, addressID)
        stmt.setInt(10, id)

        stmt.executeUpdate()
    }

    void delete(int id) {
        String query = "DELETE FROM candidates WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, id)

        stmt.executeUpdate()
    }
}
