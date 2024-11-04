package main.ui.helpers

import main.models.entities.Candidate

import java.time.Instant

import static main.ui.helpers.UserInputCollector.*

class UserInfoCollector {

    static Map<String, String> gatherAddressInfo(Scanner scanner) {
        print "Digite o país: "
        String country = getString(scanner)

        print "Digite o estado: "
        String region = getString(scanner)

        print "Digite a cidade: "
        String city = getString(scanner)

        print "Digite o bairro: "
        String neighborhood = getString(scanner)

        print "Digite a rua: "
        String street = getString(scanner)

        print "Digite o número: "
        String number = getString(scanner)

        print "Digite o CEP: "
        String zipCode = getString(scanner)

        Map<String, String> results = new HashMap<>()
        results.put("country", country)
        results.put("region", region)
        results.put("city", city)
        results.put("neighborhood", neighborhood)
        results.put("street", street)
        results.put("number", number)
        results.put("zipCode", zipCode)

        return results
    }

    static Map<String, String> updateAddressInfo(Scanner scanner) {
        print "Deseja atualizar as informações de endereço? (s/N): "
        if (getBoolean(scanner)) {
            return gatherAddressInfo(scanner)
        }

        return null
    }

    static Set<String> gatherSkills(Scanner scanner) {
        Set<String> skills = new LinkedHashSet<>()

        println "Digite uma habilidade (ou 'sair' para finalizar): "

        do {
            String skill = getString(scanner)
            skills.add(skill)
        } while (!skills.contains("sair"))

        skills.remove("sair")

        return skills
    }

    static Set<String> updateSkills(Scanner scanner) {
        print "Deseja atualizar suas habilidades? (s/N): "
        if (getBoolean(scanner)) {
            return gatherSkills(scanner)
        }

        println "Ação cancelada."

        return null
    }

    static Map<String, String> gatherCandidateInfo(Scanner scanner) {
        print "Insira seu primeiro nome: "
        String firstName = getString(scanner)

        print "Insira seu sobrenome: "
        String lastName = getString(scanner)

        print "Insira seu email: "
        String email = getString(scanner)

        print "Insira sua senha: "
        String password = getString(scanner)

        print "Insira sua BIO: "
        String description = getString(scanner)

        print "Insira sua data de nascimento(YYYY-MM-DD): "
        Instant birthDate = getInstant(scanner)

        print "Insira seu CPF: "
        String cpf = getString(scanner)

        print "Insira sua formação acadêmica: "
        String education = getString(scanner)

        Map<String, String> results = new HashMap<>()
        results.put("firstName", firstName)
        results.put("lastName", lastName)
        results.put("email", email)
        results.put("password", password)
        results.put("cpf", cpf)
        results.put("birthDate", birthDate.toString())
        results.put("description", description)
        results.put("education", education)

        return results
    }

    static Map<String, String> updateCandidateInfo(Candidate candidate, Scanner scanner) {
        println "***** Atualizar Informações do Candidato *****"

        print "Deseja atualizar seu nome (${candidate.firstName}) (s/N)? "
        String firstName = null
        if (getBoolean(scanner)) {
            firstName = getString(scanner)
        }

        print "Deseja atualizar seu sobrenome (${candidate.lastName}) (s/N)? "
        String lastName = null
        if (getBoolean(scanner)) {
            lastName = getString(scanner)
        }

        print "Deseja atualizar seu email (${candidate.loginDetails.email}) (s/N)? "
        String email = null
        if (getBoolean(scanner)) {
            email = getString(scanner)
        }

        print "Deseja atualizar sua senha? (s/N)? "
        String password = null
        if (getBoolean(scanner)) {
            password = getString(scanner)
        }

        print "Deseja atualizar sua descrição (${candidate.description}) (s/N)? "
        String description = null
        if (getBoolean(scanner)) {
            description = getString(scanner)
        }

        print "Deseja atualizar sua data de nascimento (${candidate.birthDate}) (s/N)? "
        Instant birthDate = null
        if (getBoolean(scanner)) {
            birthDate = getInstant(scanner)
        }

        print "Deseja atualizar o CPF (${candidate.cpf}) (s/N)? "
        String cpf = null
        if (getBoolean(scanner)) {
            cpf = getString(scanner)
        }

        print "Deseja atualizar a formação acadêmica (${candidate.education}) (s/N)? "
        String education = null
        if (getBoolean(scanner)) {
            education = getString(scanner)
        }

        Map<String, String> results = new HashMap<>()
        results.put("firstName", firstName != null ? firstName : candidate.firstName)
        results.put("lastName", lastName != null ? lastName : candidate.lastName)
        results.put("email", email != null ? email : candidate.loginDetails.email)
        results.put("password", password != null ? password : candidate.loginDetails.password)
        results.put("cpf", cpf != null ? cpf : candidate.cpf)
        results.put("birthDate", birthDate != null ? birthDate.toString() : candidate.birthDate.toString())
        results.put("description", description != null ? description : candidate.description)
        results.put("education", education != null ? education : candidate.education)

        return results
    }
}