package application.repositories

import application.models.dtos.request.JobOpeningDTO
import application.models.entities.JobOpening
import application.repositories.interfaces.JobOpeningDAO

import java.sql.*

class DefaultJobOpeningDAO implements JobOpeningDAO {

    Connection conn

    DefaultJobOpeningDAO(Connection conn) {
        this.conn = conn
    }

    @Override
    JobOpening getById(int id) {
        String query = "SELECT * FROM job_openings WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()

        return constructJobOpenings(rs).stream()
                .findFirst()
                .orElse(null)
    }

    @Override
    Set<JobOpening> getByField(String fieldName, String fieldValue) {
        String query = "SELECT * FROM job_openings WHERE $fieldName = ?";

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setString(1, fieldValue)

        ResultSet rs = stmt.executeQuery()

        return constructJobOpenings(rs)
    }

    @Override
    Set<JobOpening> getByCompanyId(int companyId) {
        String query = "SELECT * FROM job_openings WHERE company_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, companyId)

        ResultSet rs = stmt.executeQuery()

        return constructJobOpenings(rs)
    }

    @Override
    Set<JobOpening> getAll() {
        String query = "SELECT * FROM job_openings"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        ResultSet rs = stmt.executeQuery()

        return constructJobOpenings(rs)
    }

    private static Set constructJobOpenings(ResultSet rs) {
        Set<JobOpening> jobOpenings = new LinkedHashSet<>()
        while (rs.next()) {
            JobOpening jobOpening = new JobOpening.Builder()
                    .setId(rs.getInt("id"))
                    .setName(rs.getString("name"))
                    .setDescription(rs.getString("description"))
                    .setIsOpen(rs.getBoolean("is_open"))
                    .setIsRemote(rs.getBoolean("is_remote"))
                    .setCompanyId(rs.getInt("company_id"))
                    .setAddressId(Optional.of(rs.getInt("address_id")))
                    .build()

            jobOpenings.add(jobOpening)
        }

        return jobOpenings
    }

    @Override
    int save(JobOpeningDTO jobOpeningDTO) {
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
        stmt = setJobOpeningStatement(stmt, jobOpeningDTO)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            return rs.getInt(1)
        }

        throw new SQLException("Error saving job opening")
    }

    @Override
    JobOpening update(int id, JobOpeningDTO jobOpeningDTO) {
        String query = "UPDATE job_openings SET " +
                "name = ?," +
                "description = ?," +
                "is_remote = ?," +
                "is_open = ?," +
                "company_id = ?," +
                "address_id = ? " +
                "WHERE id = ? " +
                "RETURNING *"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt = setJobOpeningStatement(stmt, jobOpeningDTO)

        stmt.setInt(7, id)

        ResultSet rs = stmt.executeQuery()

        return constructJobOpenings(rs).stream()
                .findFirst()
                .orElse(null)
    }

    private static PreparedStatement setJobOpeningStatement(PreparedStatement stmt, JobOpeningDTO jobOpeningDTO) {
        stmt.setString(1, jobOpeningDTO.name)
        stmt.setString(2, jobOpeningDTO.description)
        stmt.setBoolean(3, jobOpeningDTO.isRemote)
        stmt.setBoolean(4, jobOpeningDTO.isOpen)
        stmt.setInt(5, jobOpeningDTO.companyId)

        if (jobOpeningDTO.addressId.isPresent()) {
            stmt.setInt(6, jobOpeningDTO.addressId.get())
        } else {
            stmt.setNull(6, Types.INTEGER)
        }

        return stmt
    }

    void delete(int id) {
        String query = "DELETE FROM job_openings WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, id)

        stmt.executeUpdate()
    }
}
