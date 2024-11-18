package application.controllers

import application.models.dtos.request.CompanyDTO
import application.services.ServiceFactory
import application.services.interfaces.CompanyService
import application.utils.parsers.json.JsonParser
import application.utils.parsers.json.JsonParserFactory
import jakarta.servlet.ServletConfig
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/companies/*"])
class CompanyController extends HttpServlet {

    private CompanyService companyService
    private JsonParser jsonParser

    @Override
    void init(ServletConfig config) throws ServletException {
        super.init(config)

        this.companyService = ServiceFactory.createCompanyService()
        this.jsonParser = JsonParserFactory.createJacksonJsonParser()
    }

    @Override
    void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json")
        String pathInfo = req.getPathInfo()

        if (pathInfo == null || "/" == pathInfo) {
            createCompany(req, resp)
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
        }
    }

    private void createCompany(HttpServletRequest req, HttpServletResponse resp) {
        BufferedReader reader = req.getReader()
        StringBuilder requestBody = new StringBuilder()

        String line
        while ((line = reader.readLine()) != null) {
            requestBody.append(line)
        }

        CompanyDTO companyDTO
        try {
            companyDTO = jsonParser.fromJson(requestBody.toString(), CompanyDTO)
        } catch (Exception ignored) {
            resp.writer.write("Invalid request body")
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }

        int id = companyService.save(companyDTO)
        resp.writer.write(jsonParser.toJson(id))
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }
}
