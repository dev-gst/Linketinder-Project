package mocks

import main.models.dtos.request.login.LoginDetailsDTO
import main.models.entities.login.LoginDetails
import spock.lang.Specification

class LoginDetailsMock extends Specification {

    LoginDetailsDTO createLoginDetailsDTOMock(int n) {
        LoginDetailsDTO loginDetailsDTO = Mock(LoginDetailsDTO)

        loginDetailsDTO.email >> "joe$n@email.com"
        loginDetailsDTO.password >> "password$n"

        return loginDetailsDTO
    }

    LoginDetails createLoginDetailsMock(int n) {
        LoginDetails loginDetails = Mock(LoginDetails)

        loginDetails.email >> "joe$n@email.com"
        loginDetails.password >> "password$n"

        return loginDetails
    }
}
