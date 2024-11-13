package application.repositories

import application.models.dtos.request.SkillDTO
import application.models.entities.Skill
import spock.lang.Specification

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class DefaultSkillDAOTest extends Specification {

    Connection conn
    DefaultSkillDAO defaultSkillDAO

    void setup() {
        conn = Mock(Connection)
        defaultSkillDAO = new DefaultSkillDAO(conn)
    }


    def "get by id returns correct skill"() {
        given:
        String query = "SELECT * FROM skills WHERE id = ?"
        PreparedStatement stmt = Mock(PreparedStatement)
        ResultSet rs = Mock(ResultSet)
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> 1
        rs.getString("name") >> "Java"

        when:
        Skill skill = defaultSkillDAO.getById(1)

        then:
        skill.id == 1
        skill.name == "Java"
    }

    def "get by id returns null for non-existent id"() {
        given:
        String query = "SELECT * FROM skills WHERE id = ?"
        PreparedStatement stmt = Mock(PreparedStatement)
        ResultSet rs = Mock(ResultSet)
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.setInt(1, 999) >> _
        stmt.executeQuery() >> rs
        rs.next() >> false

        when:
        Skill skill = defaultSkillDAO.getById(999)

        then:
        skill == null
    }

    def "get by field returns correct skills"() {
        given:
        String query = "SELECT * FROM skills WHERE ? = ?"
        PreparedStatement stmt = Mock(PreparedStatement)
        ResultSet rs = Mock(ResultSet)
        conn.prepareStatement(query, ResultSet.CONCUR_READ_ONLY) >> stmt
        stmt.setString(1, "name") >> _
        stmt.setString(2, "Java") >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> 1
        rs.getString("name") >> "Java"

        when:
        Set<Skill> skills = defaultSkillDAO.getByField("name", "Java")

        then:
        skills.size() == 1
        skills.first().name == "Java"
    }

    def "save inserts skill and returns id"() {
        given:
        String query = "INSERT INTO skills (name) VALUES (?) RETURNING id"
        PreparedStatement stmt = Mock(PreparedStatement)
        ResultSet rs = Mock(ResultSet)
        conn.prepareStatement(query) >> stmt
        stmt.setString(1, "Java") >> _
        stmt.executeQuery() >> rs
        rs.next() >> true
        rs.getInt("id") >> 1

        when:
        int id = defaultSkillDAO.save(new SkillDTO.Builder().name("Java").build())

        then:
        id == 1
    }

    def "update modifies skill and returns updated skill"() {
        given:
        String query = "UPDATE skills SET name = ? WHERE id = ? RETURNING *"
        PreparedStatement stmt = Mock(PreparedStatement)
        ResultSet rs = Mock(ResultSet)
        conn.prepareStatement(query) >> stmt
        stmt.setString(1, "Advanced Java") >> _
        stmt.setInt(2, 1) >> _
        stmt.executeQuery() >> rs
        rs.next() >> true >> false
        rs.getInt("id") >> 1
        rs.getString("name") >> "Advanced Java"

        when:
        Skill updatedSkill = defaultSkillDAO.update(1, new SkillDTO.Builder().name("Advanced Java").build())

        then:
        updatedSkill.id == 1
        updatedSkill.name == "Advanced Java"
    }

    def "delete removes skill by id"() {
        given:
        String query = "DELETE FROM skills WHERE id = ?"
        PreparedStatement stmt = Mock(PreparedStatement)
        conn.prepareStatement(query) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.execute() >> _

        when:
        defaultSkillDAO.delete(1)

        then:
        1 * stmt.execute()
    }

    def "delete candidate skill removes skill of candidate"() {
        given:
        String query = "DELETE FROM candidate_skills WHERE candidate_id = ? AND skill_id = ?"
        PreparedStatement stmt = Mock(PreparedStatement)
        conn.prepareStatement(query) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.setInt(2, 1) >> _
        stmt.executeUpdate() >> _

        when:
        defaultSkillDAO.deleteCandidateSkill(1, 1)

        then:
        1 * stmt.executeUpdate()
    }

    def "delete all candidate skills removes all skills of candidate"() {
        given:
        String query = "DELETE FROM candidate_skills WHERE candidate_id = ?"
        PreparedStatement stmt = Mock(PreparedStatement)
        conn.prepareStatement(query) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.executeUpdate() >> _

        when:
        defaultSkillDAO.deleteAllCandidateSkills(1)

        then:
        1 * stmt.executeUpdate()
    }

    def "delete job opening skill removes skill of job opening"() {
        given:
        String query = "DELETE FROM job_opening_skills WHERE job_opening_id = ? AND skill_id = ?"
        PreparedStatement stmt = Mock(PreparedStatement)
        conn.prepareStatement(query) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.setInt(2, 1) >> _
        stmt.executeUpdate() >> _

        when:
        defaultSkillDAO.deleteJobOpeningSkill(1, 1)

        then:
        1 * stmt.executeUpdate()
    }

    def "delete all job opening skills removes all skills of job opening"() {
        given:
        String query = "DELETE FROM job_opening_skills WHERE job_opening_id = ?"
        PreparedStatement stmt = Mock(PreparedStatement)
        conn.prepareStatement(query) >> stmt
        stmt.setInt(1, 1) >> _
        stmt.executeUpdate() >> _

        when:
        defaultSkillDAO.deleteAllJobOpeningSkills(1)

        then:
        1 * stmt.executeUpdate()
    }
}
