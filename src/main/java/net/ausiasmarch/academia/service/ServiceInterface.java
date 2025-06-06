package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ServiceInterface<T>{

    public Long randomCreate(Long cantidad);

    //public T randomSelection();

    public Page<T> getPage(Pageable oPageable, Optional<String> filter);

    public T get(Long id);

    public Long count();

    public Long delete(Long id);

    public T create(T oUsuarioEntity);

    public T update(T oUsuarioEntity);

    //public Long deleteAll();

}