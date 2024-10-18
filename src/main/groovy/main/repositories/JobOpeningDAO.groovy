package main.repositories

import main.models.DTOs.JobOpeningDTO
import main.models.entities.JobOpening

import java.sql.*

class JobOpeningDAO {

    Connection conn

    JobOpeningDAO(Connection conn) {
        this.conn = conn
    }

    JobOpening getById(int id) {
        String query = "SELECT * FROM job_openings WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            JobOpening jobOpening = new JobOpening()

            jobOpening.id = rs.getInt("id")
            jobOpening.name = rs.getString("name")
            jobOpening.description = rs.getString("description")
            jobOpening.isRemote = rs.getBoolean("is_remote")
            jobOpening.isOpen = rs.getBoolean("is_open")

            return jobOpening
        }

        return null
    }

    List<JobOpening> getByCompanyId(int companyId) {
        String query = "SELECT * FROM job_openings WHERE company_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, companyId)

        List<JobOpening> jobOpenings = new ArrayList<>()

        ResultSet rs = stmt.executeQuery()
        while (rs.next()) {
            JobOpening jobOpening = new JobOpening()

            jobOpening.id = rs.getInt("id")
            jobOpening.name = rs.getString("name")
            jobOpening.description = rs.getString("description")
            jobOpening.isRemote = rs.getBoolean("is_remote")
            jobOpening.isOpen = rs.getBoolean("is_open")

            jobOpenings.add(jobOpening)
        }

        return jobOpenings
    }

    List<JobOpening> getAll() {
        String query = "SELECT * FROM job_openings"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        List<JobOpening> jobOpenings = new ArrayList<>()

        ResultSet rs = stmt.executeQuery()
        while (rs.next()) {
            JobOpening jobOpening = new JobOpening()

            jobOpening.id = rs.getInt("id")
            jobOpening.name = rs.getString("name")
            jobOpening.description = rs.getString("description")
            jobOpening.isRemote = rs.getBoolean("is_remote")
            jobOpening.isOpen = rs.getBoolean("is_open")

            jobOpenings.add(jobOpening)
        }

        return jobOpenings
    }

    int save(JobOpeningDTO jobOpeningDTO, int companyId, int addressId) {
        String query = "INSERT INTO job_openings (" +
                "name," +
                "description," +
                "is_remote," +
                "is_open," +
                "company_id," +
                "address_id" +
                ") VALUES (?, ?, ?, ?, ?, ?) " +
                "RETURNING id"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, jobOpeningDTO.name)
        stmt.setString(2, jobOpeningDTO.description)
        stmt.setBoolean(3, jobOpeningDTO.isRemote)
        stmt.setBoolean(4, jobOpeningDTO.isOpen)
        stmt.setInt(5, companyId)

        addressId > 0 ?
                stmt.setInt(6, addressId) :
                stmt.setNull(6, Types.INTEGER)

        stmt.executeQuery()

        ResultSet rs = stmt.getResultSet()
        if (rs.next()) {
            return rs.getInt(1)
        } else {
            throw new SQLException("Error saving job opening")
        }
    }

    void update(int id, JobOpeningDTO jobOpeningDTO, int companyId, int addressId) {
        String query = "UPDATE job_openings SET " +
                "name = ?," +
                "description = ?," +
                "is_remote = ?," +
                "is_open = ?," +
                "company_id = ?," +
                "address_id = ? " +
                "WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, jobOpeningDTO.name)
        stmt.setString(2, jobOpeningDTO.description)
        stmt.setBoolean(3, jobOpeningDTO.isRemote)
        stmt.setBoolean(4, jobOpeningDTO.isOpen)
        stmt.setInt(5, companyId)

        addressId > 0 ?
                stmt.setInt(6, addressId) :
                stmt.setNull(6, Types.INTEGER)

        stmt.setInt(7,id)

        stmt.executeUpdate()
    }

    void delete(int id) {
        String query = "DELETE FROM job_openings WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, id)

        stmt.executeUpdate()
    }
}
