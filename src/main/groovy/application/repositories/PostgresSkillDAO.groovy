package application.repositories

import application.models.dtos.request.SkillDTO
import application.models.entities.Skill
import application.repositories.interfaces.SkillDAO
import application.utils.validation.ParamValidation

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class PostgresSkillDAO implements SkillDAO {

    Connection conn

    PostgresSkillDAO(Connection conn) {
        ParamValidation.requireNonNull(conn, "Connection cannot be null")

        this.conn = conn
    }

    @Override
    Skill getById(int id) {
        String query = "SELECT * FROM skills WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()


        return constructSkills(rs).stream()
                .findFirst()
                .orElse(null)
    }

    @Override
    Set<Skill> getByField(String fieldName, String fieldValue) {
        String query = "SELECT * FROM skills WHERE $fieldName = ?"
        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setString(1, fieldValue)

        ResultSet rs = stmt.executeQuery()

        return constructSkills(rs)
    }

    @Override
    Set<Skill> getByCandidateId(int candidateId) {
        String query = "SELECT * FROM skills " +
                "JOIN candidate_skills ON skills.id = candidate_skills.skill_id " +
                "WHERE candidate_skills.candidate_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, candidateId)

        ResultSet rs = stmt.executeQuery()

        return constructSkills(rs)
    }

    @Override
    Set<Skill> getByJobOpeningId(int jobOpeningId) {
        String query = "SELECT * FROM skills " +
                "JOIN job_opening_skills ON skills.id = job_opening_skills.skill_id " +
                "WHERE job_opening_skills.job_openings_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, jobOpeningId)

        ResultSet rs = stmt.executeQuery()

        return constructSkills(rs)
    }

    @Override
    Set<Skill> getAll() {
        String query = "SELECT * FROM skills"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        ResultSet rs = stmt.executeQuery()

        return constructSkills(rs)
    }

    private static Set<Skill> constructSkills(ResultSet rs) {
        Set skills = new LinkedHashSet<Skill>()
        while (rs.next()) {
            Skill skill = new Skill.Builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .build()

            skills.add(skill)
        }

        return skills
    }

    @Override
    int save(SkillDTO skillDTO) {
        String query = "INSERT INTO skills (name) VALUES (?) RETURNING id"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, skillDTO.name)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            return rs.getInt("id")
        }

        throw new SQLException("Error saving skill")
    }

    @Override
    Skill update(int id, SkillDTO skillDTO) {
        String query = "UPDATE skills SET name = ? WHERE id = ? RETURNING *"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, skillDTO.name)
        stmt.setInt(2, id)

        ResultSet rs = stmt.executeQuery()

        return constructSkills(rs).stream()
                .findFirst()
                .orElse(null)
    }

    @Override
    void delete(int id) {
        String query = "DELETE FROM skills WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, id)

        stmt.execute()
    }

    @Override
    int saveCandidateSkill(int candidateId, int skillId) {
        String query = "INSERT INTO candidate_skills (candidate_id, skill_id) VALUES (?, ?)"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, candidateId)
        stmt.setInt(2, skillId)

        return stmt.executeUpdate()
    }

    @Override
    int saveJobOpeningSkill(int jobOpeningId, int skillId) {
        String query = "INSERT INTO job_opening_skills (job_opening_id, skill_id) VALUES (?, ?)"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, jobOpeningId)
        stmt.setInt(2, skillId)

        return stmt.executeUpdate()
    }

    @Override
    void deleteCandidateSkill(int candidateId, int skillId) {
        String query = "DELETE FROM candidate_skills WHERE candidate_id = ? AND skill_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, candidateId)
        stmt.setInt(2, skillId)

        stmt.executeUpdate()
    }

    @Override
    void deleteAllCandidateSkills(int candidateId) {
        String query = "DELETE FROM candidate_skills WHERE candidate_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, candidateId)

        stmt.executeUpdate()
    }

    @Override
    void deleteJobOpeningSkill(int jobOpeningId, int skillId) {
        String query = "DELETE FROM job_opening_skills WHERE job_opening_id = ? AND skill_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, jobOpeningId)
        stmt.setInt(2, skillId)

        stmt.executeUpdate()
    }

    @Override
    void deleteAllJobOpeningSkills(int jobOpeningId) {
        String query = "DELETE FROM job_opening_skills WHERE job_opening_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, jobOpeningId)

        stmt.executeUpdate()
    }
}
