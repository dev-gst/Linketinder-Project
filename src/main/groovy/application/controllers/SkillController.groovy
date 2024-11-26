package application.controllers

import application.models.dtos.request.SkillDTO
import application.services.ServiceFactory
import application.services.SkillService
import application.utils.parsers.json.JsonParser
import application.utils.parsers.json.JsonParserFactory
import jakarta.servlet.ServletConfig
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/skills/*"])
class SkillController extends HttpServlet {

    private SkillService skillService
    private JsonParser jsonParser

    @Override
    void init(ServletConfig config) {
        super.init(config)

        this.skillService = ServiceFactory.createSkillService()
        this.jsonParser = JsonParserFactory.createJacksonJsonParser()
    }

    @Override
    void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json")
        String pathInfo = req.getPathInfo()

        if (pathInfo == null || "/" == pathInfo) {
            createSkill(req, resp)
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
        }
    }

    private void createSkill(HttpServletRequest req, HttpServletResponse resp) {
        BufferedReader reader = req.getReader()
        StringBuilder requestBody = new StringBuilder()

        String line
        while ((line = reader.readLine()) != null) {
            requestBody.append(line)
        }

        SkillDTO skillDTO
        try {
            skillDTO = jsonParser.fromJson(requestBody.toString(), SkillDTO.class)
        } catch (Exception ignored) {
            resp.writer.write("Invalid request body")
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }

        int id = skillService.save(skillDTO)
        resp.writer.write(jsonParser.toJson(id))
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }
}
