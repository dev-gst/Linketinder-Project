package application.services.interfaces

interface AnonService<AnonDTO> {

    AnonDTO getAnonById(int id);

    Set<AnonDTO> getAllAnon();
}
