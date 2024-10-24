package main.models.entities.login

class LoginDetails {

    String email
    String password

    LoginDetails(String email, String password) {
        this.email = email
        this.password = password
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        LoginDetails that = (LoginDetails) o

        if (email != that.email) return false
        if (password != that.password) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = (email != null ? email.hashCode() : 0)
        result = 31 * result + (password != null ? password.hashCode() : 0)
        return result
    }
}
