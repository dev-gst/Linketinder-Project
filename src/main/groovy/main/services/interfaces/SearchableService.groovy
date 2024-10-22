package main.services.interfaces

interface SearchableService<ENTITY, DTO> extends Service<ENTITY, DTO> {
    Set<ENTITY> findByField(String fieldName, String fieldValue)
}