package com.banco.pichincha.inventario.microservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.pichincha.inventario.microservice.modelo.Persona;

public interface PersonaDao extends JpaRepository<Persona, Long>{

}
