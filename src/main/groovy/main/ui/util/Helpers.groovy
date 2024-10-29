package main.ui.util

import main.models.dtos.request.AddressDTO
import main.models.dtos.request.SkillDTO

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
                println "Invalid request, try again!"
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
                println "Invalid request, try again!"
            }
        }
    }

    static AddressDTO createAddress() {
        Scanner scanner = new Scanner(System.in)

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

        AddressDTO addressDTO = new AddressDTO.Builder()
                .country(country)
                .region(region)
                .city(city)
                .neighborhood(neighborhood)
                .street(street)
                .number(number)
                .zipCode(zipCode)
                .build()

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

            SkillDTO skillDTO = new SkillDTO.Builder()
                    .name(skill)
                    .build()

            skills.add(skillDTO)
        }

        return skills
    }

    static boolean getBooleanFromUsr(Scanner scanner) {
        while (true) {
            try {
                String field = scanner.nextLine()
                return field.equalsIgnoreCase("s")

            } catch (NoSuchElementException | IllegalStateException | IllegalArgumentException ignored) {
                println "Invalid request, try again!"
            }
        }
    }

    static int getIntFromUsr(Scanner scanner) {
        while (true) {
            try {
                int field = scanner.nextInt()
                return field
            } catch (NoSuchElementException | IllegalStateException | NumberFormatException ignored) {
                println "Invalid request, try again!"
            }
        }
    }
}