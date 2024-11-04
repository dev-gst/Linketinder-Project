package main.ui.helpers

import main.util.exception.ParamValidation

import java.time.Instant
import java.time.LocalDate

class UserInputCollector {

    static int getChoice(int limit, Scanner scanner) {
        ParamValidation.requireNonNull(scanner, "Scanner cannot be null")
        if (limit <= 0) throw new IllegalArgumentException("Limit must be greater than 0")

        println "Digite uma opção entre 1 e ${limit}: "

        int usrInput
        do {
            usrInput = getChoiceLogic(scanner)
        } while (usrInput <= 0 || usrInput > limit)

        return usrInput
    }

    private static Integer getChoiceLogic(Scanner scanner) {
        int usrInput = -1
        try {
            usrInput = Integer.parseInt(scanner.nextLine())
        } catch (Exception ignored) {
            println "Por favor, digite um número válido!"
        }

        return usrInput
    }

    static String getString(Scanner scanner) {
        String field
        do {
            field = getStringLogic(scanner)
        } while (field == null || field.isBlank())

        return field
    }

    private static String getStringLogic(Scanner scanner) {
        String field = null
        try {
            field = scanner.nextLine()
        } catch (Exception ignored) {
            println "Entrada inválida, tente novamente!"
        }

        return field
    }

    static Instant getInstant(Scanner scanner) {
        while (true) {
            try {
                String field = scanner.nextLine()
                LocalDate date = LocalDate.parse(field)
                Instant instant = Instant.parse(date.toString() + "T00:00:00Z")

                return instant
            } catch (Exception ignored) {
                println "Entrada inválida, tente novamente!"
            }
        }
    }

    static LocalDate getLocalDate(Scanner scanner) {
        while (true) {
            try {
                String field = scanner.nextLine()
                return LocalDate.parse(field)
            } catch (Exception ignored) {
                println "Entrada inválida, tente novamente!"
            }
        }
    }

    static boolean getBoolean(Scanner scanner) {
        while (true) {
            try {
                String field = scanner.nextLine()
                return field.trim().equalsIgnoreCase("s")

            } catch (Exception ignored) {
                println "Entrada inválida, tente novamente!"
            }
        }
    }

    static int getInteger(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine())
            } catch (Exception ignored) {
                println "Entrada inválida, tente novamente!"
            }
        }
    }
}
