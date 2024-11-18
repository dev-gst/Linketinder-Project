package application.repositories.interfaces

interface DAO<ENTITY, DTO> {
    ENTITY getById(int id)

    Set<ENTITY> getByField(String fieldName, String fieldValue)

    int save(DTO dto)

    ENTITY update(int id, DTO dto)

    void delete(int id)

    Set<ENTITY> getAll()
}