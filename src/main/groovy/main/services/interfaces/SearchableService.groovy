package main.services.interfaces

interface SearchableService<ENTITY, DTO> extends Service<ENTITY, DTO> {

    Set<ENTITY> getByField(String fieldName, String fieldValue)

    Set<ENTITY> getByEntityId(int entityId, Class<?> entityClazz)
}