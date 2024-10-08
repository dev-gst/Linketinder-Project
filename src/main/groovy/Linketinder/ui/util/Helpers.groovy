package Linketinder.ui.util

import Linketinder.models.DTOs.AddressDTO
import Linketinder.models.DTOs.SkillDTO

import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeParseException

class Helpers {

    static int getUsrChoice(int limit) {
        while (true) {
            Scanner scanner = new Scanner(System.in)
            try {
                int usrInput = scanner.nextInt()
                if (usrInput > 0 && usrInput <= limit) {
                    return usrInput
                } else {
                    throw new NumberFormatException()
                }
            } catch (NoSuchElementException | IllegalStateException | NumberFormatException ignored) {
                println "Please insert a valid number"
            }
        }
    }

    static String getStringFieldFromUsr(Scanner scanner) {
        while (true) {
            try {
                String field = scanner.nextLine()
                if (field) {
                    return field
                } else {
                    throw new IllegalArgumentException("Field cannot be empty")
                }
            } catch (NoSuchElementException | IllegalStateException | IllegalArgumentException ignored) {
                println "Invalid input, try again!"
            }
        }
    }

    static Instant getInstantFieldFromUsr(Scanner scanner) {
        while (true) {
            try {
                String field = scanner.nextLine()
                LocalDate date = LocalDate.parse(field)
                Instant instant = Instant.parse(date.toString() + "T00:00:00Z")

                return instant
            } catch (DateTimeParseException | NoSuchElementException | IllegalStateException ignored) {
                println "Invalid input, try again!"
            }
        }
    }

    static AddressDTO createAddress() {
        Scanner scanner = new Scanner(System.in)
        AddressDTO addressDTO = new AddressDTO()

        print "Digite o país: "
        String country = getStringFieldFromUsr(scanner)

        print "Digite o estado: "
        String region = getStringFieldFromUsr(scanner)

        print "Digite a cidade: "
        String city = getStringFieldFromUsr(scanner)

        print "Digite o bairro: "
        String neighborhood = getStringFieldFromUsr(scanner)

        print "Digite a rua: "
        String street = getStringFieldFromUsr(scanner)

        print "Digite o número: "
        String number = getStringFieldFromUsr(scanner)

        print "Digite o CEP: "
        String zipCode = getStringFieldFromUsr(scanner)

        addressDTO.country = country
        addressDTO.region = region
        addressDTO.city = city
        addressDTO.neighborhood = neighborhood
        addressDTO.street = street
        addressDTO.number = number
        addressDTO.zipCode = zipCode

        return addressDTO
    }

    static Set<SkillDTO> gatherSkills() {
        Scanner scanner = new Scanner(System.in)
        Set<SkillDTO> skills = new HashSet<>()

        while (true) {
            println "Digite uma habilidade (ou 'sair' para finalizar): "
            String skill = getStringFieldFromUsr(scanner)

            if (skill.equalsIgnoreCase("sair")) {
                if (skills.isEmpty()) {
                    println "É necessário inserir pelo menos uma habilidade"
                    continue
                }

                break
            }

            SkillDTO skillDTO = new SkillDTO()
            skillDTO.name = skill

            skills.add(skillDTO)
        }

        return skills
    }
}