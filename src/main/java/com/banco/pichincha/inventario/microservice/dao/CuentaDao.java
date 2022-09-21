package com.banco.pichincha.inventario.microservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.banco.pichincha.inventario.microservice.modelo.Cuenta;

public interface CuentaDao extends JpaRepository<Cuenta, Long>{
	
	@Query("SELECT c FROM Cuenta c where c.cliente.clienteId =?1")
	public List<Cuenta> listaCuentasByIdCliente(Long idCliente);

}
