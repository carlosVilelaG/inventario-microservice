package com.banco.pichincha.inventario.microservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.pichincha.inventario.microservice.modelo.Cliente;

public interface ClienteDao extends JpaRepository<Cliente, Long>{

}
