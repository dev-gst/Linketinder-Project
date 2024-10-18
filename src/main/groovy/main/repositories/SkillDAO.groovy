package main.repositories

import main.models.DTOs.SkillDTO
import main.models.entities.Skill

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class SkillDAO {

    private Connection conn

    SkillDAO(Connection conn) {
        this.conn = conn
    }

    Skill getById(int id) {
        String query = "SELECT * FROM skills WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, id)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            Skill skill = new Skill()

            skill.id = rs.getInt("id")
            skill.name = rs.getString("name")

            return skill
        }

        return null
    }

    Skill getByName(String name) {
        String query = "SELECT * FROM skills WHERE name = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setString(1, name)

        ResultSet rs = stmt.executeQuery()
        if (rs.next()) {
            Skill skill = new Skill()

            skill.id = rs.getInt("id")
            skill.name = rs.getString("name")

            return skill
        }

        return null
    }

    List<Skill> getByCandidateId(int candidateId) {
        String query = "SELECT * FROM skills " +
                "JOIN candidate_skills ON skills.id = candidate_skills.skill_id " +
                "WHERE candidate_skills.candidate_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, candidateId)

        List<Skill> skills = new ArrayList<>()

        ResultSet rs = stmt.executeQuery()
        while (rs.next()) {
            Skill skill = new Skill()

            skill.id = rs.getInt("id")
            skill.name = rs.getString("name")

            skills.add(skill)
        }

        return skills
    }

    List<Skill> getByJobOpeningId(int jobOpeningId) {
        String query = "SELECT * FROM skills " +
                "JOIN job_opening_skills ON skills.id = job_opening_skills.skill_id " +
                "WHERE job_opening_skills.job_openings_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)
        stmt.setInt(1, jobOpeningId)

        List<Skill> skills = new ArrayList<>()

        ResultSet rs = stmt.executeQuery()
        while (rs.next()) {
            Skill skill = new Skill()

            skill.id = rs.getInt("id")
            skill.name = rs.getString("name")

            skills.add(skill)
        }

        return skills
    }

    List<Skill> getAll() {
        String query = "SELECT * FROM skills"

        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY)

        List<Skill> skills = new ArrayList<>()

        ResultSet rs = stmt.executeQuery()
        while (rs.next()) {
            Skill skill = new Skill()

            skill.id = rs.getInt("id")
            skill.name = rs.getString("name")

            skills.add(skill)
        }

        return skills
    }

    int save(SkillDTO skillDTO) {
        String query = "INSERT INTO skills (name) VALUES (?) RETURNING id"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, skillDTO.name)

        stmt.executeQuery()

        ResultSet rs = stmt.getResultSet()
        if (rs.next()) {
            return rs.getInt('id')
        } else {
            throw new SQLException("Error saving skill")
        }
    }

    void saveCandidateSkills(int candidateId, Set<Skill> skills) {
        String query = "INSERT INTO candidate_skills (candidate_id, skill_id) VALUES (?, ?)"

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Skill skill : skills) {
                stmt.setInt(1, candidateId)
                stmt.setInt(2, skill.id)

                stmt.addBatch()
            }

            stmt.executeBatch()
        }
    }

    void saveJobOpeningSkills(int jobOpeningId, Set<Skill> skills) {
        String query = "INSERT INTO public.job_opening_skills (job_openings_id, skill_id) VALUES (?, ?)"

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Skill skill : skills) {
                stmt.setInt(1, jobOpeningId)
                stmt.setInt(2, skill.id)

                stmt.addBatch()
            }

            stmt.executeBatch()
        }
    }

    void update(int id, SkillDTO skillDTO) {
        String query = "UPDATE skills SET name = ? WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setString(1, skillDTO.name)
        stmt.setInt(2, id)

        stmt.executeUpdate()
    }

    void delete(int id) {
        String query = "DELETE FROM skills WHERE id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, id)

        stmt.executeUpdate()
    }

    void deleteCandidateSkills(int candidateId) {
        String query = "DELETE FROM candidate_skills WHERE candidate_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, candidateId)

        stmt.executeUpdate()
    }

    void deleteJobOpeningSkills(int jobOpeningId) {
        String query = "DELETE FROM job_opening_skills WHERE job_openings_id = ?"

        PreparedStatement stmt = conn.prepareStatement(query)
        stmt.setInt(1, jobOpeningId)

        stmt.executeUpdate()
    }
}

