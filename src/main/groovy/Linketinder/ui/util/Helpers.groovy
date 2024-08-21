package Linketinder.ui.util

import Linketinder.models.enums.SkillEnum

class Helpers {
    static String getFieldFromUsr(Scanner scanner) {
        while (true) {
            try {
                String field = scanner.nextLine()
                if (field) {
                    return field
                } else {
                    println "Invalid input, try again!"
                }
            } catch (NoSuchElementException | IllegalStateException ignored) {
                println "Invalid input, try again!"
            }
        }
    }

    static int getAgeFromUsr(Scanner scanner) {
        int age
        while (true) {
            String field = getFieldFromUsr(scanner)
            try {
                age = Integer.valueOf(field)
                if (age >= 16 && age <= 125) {
                    return age
                }
            } catch (NumberFormatException ignored) {}

            println "Invalid age, try again!"
        }
    }

    static SkillEnum[] getSkillsFromUsr(Scanner scanner) {
        List<SkillEnum> skillList = new LinkedList<>()
        while (true) {
            String commonString = getFieldFromUsr(scanner)
            String[] rawSkills = new String[1]

            if (commonString.contains(",")) {
                rawSkills = Arrays.asList(commonString.split(","))
            } else {
                rawSkills[0] = commonString
            }

            rawSkills.each { String skill ->
                 switch (skill.toUpperCase().trim()) {
                    case "JAVA":
                        skillList.add(SkillEnum.JAVA)
                        break
                    case "SPRING BOOT":
                        skillList.add(SkillEnum.SPRING_BOOT)
                        break
                    case "GROOVY":
                        skillList.add(SkillEnum.GROOVY)
                        break
                    case "JAVASCRIPT":
                        skillList.add(SkillEnum.JAVASCRIPT)
                        break
                    case "ANGULAR":
                        skillList.add(SkillEnum.ANGULAR)
                        break
                }
            }

            if (!skillList.isEmpty()) {
                break
            } else {
                println "Invalid input, try again!"
            }
        }

        return skillList.toArray() as SkillEnum[]
    }
}
