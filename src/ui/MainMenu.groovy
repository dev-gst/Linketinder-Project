package ui

import services.CompanyService
import services.IEntityService
import services.JobApplicantsService

class MainMenu {
    static final int MenuEntries = 3

    final IEntityService jobApplicantsService;
    final IEntityService companyService;

    MainMenu() {
        this.jobApplicantsService = new JobApplicantsService()
        this.jobApplicantsService.populate()

        this.companyService = new CompanyService()
        this.companyService.populate()
    }

    void start() {
        while (true) {
            printMenu()

            int usrInput = getUsrInput()
            if (MenuEntries == usrInput) {
                return
            }

            printUsrChoice usrInput
        }
    }

    private void printUsrChoice(int choice) {
        switch (choice) {
            case 1:
                this.jobApplicantsService.print()
                break
            case 2:
                this.companyService.print()
        }
    }

    private static int getUsrInput() {
        while (true) {
            Scanner scanner = new Scanner(System.in)
            try {
                int usrInput = scanner.nextInt()
                if (usrInput > 0 && usrInput <= MenuEntries) {
                    return usrInput
                } else {
                    println "Please insert a valid number"
                }
            } catch (NoSuchElementException | IllegalStateException e) {
                println "Please insert a valid number"
            }
        }
    }

    private static void printMenu() {
        println "***********************"
        println "1 - Show candidates"
        println "2 - Show companies"
        println "3 - Exit"
        println "***********************"
    }
}
