package application.controllers


import application.models.dtos.request.JobOpeningDTO
import application.services.JobOpeningService
import application.services.ServiceFactory
import application.utils.parsers.json.JsonParser
import application.utils.parsers.json.JsonParserFactory
import jakarta.servlet.ServletConfig
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/job-openings/*"])
class JobOpeningController extends HttpServlet {

    private JobOpeningService jobOpeningService
    private JsonParser jsonParser

    @Override
    void init(ServletConfig config) {
        super.init(config)

        this.jobOpeningService = ServiceFactory.createJobOpeningService()
        this.jsonParser = JsonParserFactory.createJacksonJsonParser()
    }

    @Override
    void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json")
        String pathInfo = req.getPathInfo()

        if (pathInfo == null || "/" == pathInfo) {
            createJobOpening(req, resp)
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
        }
    }

    private void createJobOpening(HttpServletRequest req, HttpServletResponse resp) {
        BufferedReader reader = req.getReader()
        StringBuilder requestBody = new StringBuilder()

        String line
        while ((line = reader.readLine()) != null) {
            requestBody.append(line)
        }

        JobOpeningDTO jobOpeningDTO
        try {
            jobOpeningDTO = jsonParser.fromJson(requestBody.toString(), JobOpeningDTO.class)
        } catch (Exception ignored) {
            resp.writer.write("Invalid request body")
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }

        int id = jobOpeningService.save(jobOpeningDTO)
        resp.writer.write(jsonParser.toJson(id))
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }
}
