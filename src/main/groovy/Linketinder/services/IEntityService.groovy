package Linketinder.services

interface IEntityService {
    void populate()
    <T> void add(T t)
}