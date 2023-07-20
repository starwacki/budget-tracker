package com.starwacki.budgettracker.utils;

public interface Mapper <E,D>{

    D mapEntityToDTO(E entity);

    E mapDTOToEntity(D dto);


}
