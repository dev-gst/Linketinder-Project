package Linketinder.ui
import Linketinder.services.CompanyService
import Linketinder.services.CandidateService

class MainMenu {
    static final int MenuEntries = 5

    final CandidateService candidateService
    final CompanyService companyService

    MainMenu() {
        this.candidateService = new CandidateService()
        this.candidateService.populate()

        this.companyService = new CompanyService()
        this.companyService.populate()
    }

    void start() {
        while (true) {
            printMenu()

            int usrInput = getUsrChoice()
            if (MenuEntries == usrInput) {
                return
            }

            printUsrChoice usrInput
        }
    }

    private void printUsrChoice(int choice) {
        switch (choice) {
            case 1:
                printEntities(this.candidateService.candidates)
                break
            case 2:
                printEntities(this.companyService.companies)
                break
            case 3:
                this.candidateService.add(CandidateBuilderMenu.create())
                break
            case 4:
                this.companyService.add(CompanyBuilderMenu.create())
        }
    }

    private static int getUsrChoice() {
        while (true) {
            Scanner scanner = new Scanner(System.in)
            try {
                int usrInput = scanner.nextInt()
                if (usrInput > 0 && usrInput <= MenuEntries) {
                    return usrInput
                } else {
                    println "Please insert a valid number"
                }
            } catch (NoSuchElementException | IllegalStateException ignored) {
                println "Please insert a valid number"
            }
        }
    }

    private static void printMenu() {
        println "***********************"
        println "1 - Show candidates"
        println "2 - Show companies"
        println "3 - Add Job Applicant"
        println "4 - Add Company"
        println "5 - Exit"
        println "***********************"
    }

    private static <E> void printEntities(List<E> elementList) {
        elementList.forEach {
            element -> println element
        }
    }
}
