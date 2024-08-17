package ui
import services.CompanyService
import services.IEntityService
import services.JobApplicantsService

class MainMenu {
    static final int MenuEntries = 5

    final IEntityService jobApplicantsService
    final IEntityService companyService

    MainMenu() {
        this.jobApplicantsService = new JobApplicantsService()
        this.jobApplicantsService.populate()

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
                this.jobApplicantsService.print()
                break
            case 2:
                this.companyService.print()
                break
            case 3:
                this.jobApplicantsService.add(JobApplicantBuilderMenu.create())
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
}
