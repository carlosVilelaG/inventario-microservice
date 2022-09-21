package com.banco.pichincha.inventario.microservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.pichincha.inventario.microservice.dao.ClienteDao;
import com.banco.pichincha.inventario.microservice.modelo.Cliente;

@Service
public class ClienteService implements IClienteService{
	
	@Autowired
	ClienteDao clienteDao; 
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findClienteById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);

	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		clienteDao.deleteById(id);
	}
}
