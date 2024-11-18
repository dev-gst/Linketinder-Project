package application.controllers

import application.models.dtos.request.AddressDTO
import application.services.ServiceFactory
import application.services.interfaces.AddressService
import application.utils.parsers.json.JsonParser
import application.utils.parsers.json.JsonParserFactory
import jakarta.servlet.ServletConfig
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/addresses/*"])
class AddressController extends HttpServlet {

    private AddressService addressService
    private JsonParser jsonParser

    @Override
    void init(ServletConfig config) {
        super.init(config)

        this.addressService = ServiceFactory.createAddressService()
        this.jsonParser = JsonParserFactory.createJacksonJsonParser()
    }

    @Override
    void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json")
        String pathInfo = req.getPathInfo()

        if (pathInfo == null || "/" == pathInfo) {
            createAddress(req, resp)
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
        }
    }

    private void createAddress(HttpServletRequest req, HttpServletResponse resp) {
        BufferedReader reader = req.getReader()
        StringBuilder requestBody = new StringBuilder()

        String line
        while ((line = reader.readLine()) != null) {
            requestBody.append(line)
        }

        AddressDTO address
        try {
            address = jsonParser.fromJson(requestBody.toString(), AddressDTO)
        } catch (Exception ignored) {
            resp.writer.write("Invalid request body")
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }

        int id = addressService.save(address)
        resp.writer.write(jsonParser.toJson(id))
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }
}
