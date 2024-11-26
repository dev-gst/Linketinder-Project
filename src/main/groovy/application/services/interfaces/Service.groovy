package application.services.interfaces

interface Service<ENTITY, DTO> {

    ENTITY getById(int id)

    Set<ENTITY> getAll()

    int save(DTO entity)

    Set<Integer> saveAll(Set<DTO> dtoSet)

    ENTITY updateById(int id, DTO entity)

    void deleteById(int id)

    Set<ENTITY> getByField(String fieldName, String fieldValue)

    Set<ENTITY> getByEntityId(int entityId, Class<?> entityClazz)
}