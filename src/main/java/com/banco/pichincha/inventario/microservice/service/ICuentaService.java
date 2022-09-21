package com.banco.pichincha.inventario.microservice.service;

import java.util.List;

import com.banco.pichincha.inventario.microservice.modelo.Cuenta;

public interface ICuentaService {

	public void save(Cuenta cuenta);

	public void delete(Long id);
	
	public Cuenta findCuentaById(Long id);
	
	public List<Cuenta> findAll();

	List<Cuenta> findCuentaByIdCliente(Long idCliente);
}
