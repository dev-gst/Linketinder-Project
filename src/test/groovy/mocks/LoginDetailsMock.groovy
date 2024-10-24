package mocks

import main.models.dtos.request.login.LoginDetailsDTO
import spock.lang.Specification

class LoginDetailsMock extends Specification {

    LoginDetailsDTO createLoginDetailsDTOMock(int n) {
        LoginDetailsDTO loginDetailsDTO = Mock(LoginDetailsDTO)

        loginDetailsDTO.email >> "joe$n@email.com"
        loginDetailsDTO.password >> "password$n"

        return loginDetailsDTO
    }
}
