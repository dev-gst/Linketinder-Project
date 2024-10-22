package main.services.interfaces

interface Service<ENTITY, DTO> {
    ENTITY getById(int id)

    Set<ENTITY> getAll()

    ENTITY save(DTO entity)

    Set<ENTITY> saveAll(Set<DTO> dtoSet)

    ENTITY updateById(int id, DTO entity)

    void deleteById(int id)
}