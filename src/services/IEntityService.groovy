package services

import model.entity.Person

interface IEntityService {
    void populate()
    void print()
    <T> void add(T t)
}