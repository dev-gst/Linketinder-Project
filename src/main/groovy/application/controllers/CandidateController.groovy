package application.controllers

import application.models.dtos.request.CandidateDTO
import application.services.CandidateService
import application.services.ServiceFactory
import application.utils.parsers.json.JsonParser
import application.utils.parsers.json.JsonParserFactory
import jakarta.servlet.ServletConfig
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse


@WebServlet(urlPatterns = ["/candidates/*"])
class CandidateController extends HttpServlet {

    private CandidateService candidateService
    private JsonParser jsonParser

    @Override
    void init(ServletConfig config) throws ServletException {
        super.init(config)

        this.jsonParser = JsonParserFactory.createJacksonJsonParser()
        this.candidateService = ServiceFactory.createCandidateService()
    }

    @Override
    void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json")
        String pathInfo = req.getPathInfo()

        if (pathInfo == null || "/" == pathInfo) {
            createCandidate(req, resp)
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
        }
    }

    private void createCandidate(HttpServletRequest req, HttpServletResponse resp) {
        BufferedReader reader = req.getReader()
        StringBuilder requestBody = new StringBuilder()

        String line
        while ((line = reader.readLine()) != null) {
            requestBody.append(line)
        }

        CandidateDTO candidateDTO
        try {
            candidateDTO = jsonParser.fromJson(requestBody.toString(), CandidateDTO)
        } catch (Exception ignored) {
            resp.writer.write("Invalid request body")
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }

        int id = candidateService.save(candidateDTO)
        resp.writer.write(jsonParser.toJson(id))
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }
}
