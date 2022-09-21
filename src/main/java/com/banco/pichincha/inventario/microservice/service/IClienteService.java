package com.banco.pichincha.inventario.microservice.service;

import java.util.List;

import com.banco.pichincha.inventario.microservice.modelo.Cliente;

public interface IClienteService {

	Cliente findClienteById(Long id);

	List<Cliente> findAll();

	void save(Cliente cliente);

	void deleteById(Long id);

}
