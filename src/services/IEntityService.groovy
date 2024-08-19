package services

interface IEntityService {
    void populate()
    void print()
    <T> void add(T t)
}