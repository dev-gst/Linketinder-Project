package application.services.interfaces

interface AuthService<ENTITY> {

    ENTITY authenticate(String email, String password)
}
