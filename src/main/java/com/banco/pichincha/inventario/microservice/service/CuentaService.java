package com.banco.pichincha.inventario.microservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.pichincha.inventario.microservice.dao.CuentaDao;
import com.banco.pichincha.inventario.microservice.modelo.Cuenta;

@Service
public class CuentaService implements ICuentaService {

	@Autowired
	CuentaDao cuentaDao;

	@Override
	@Transactional
	public void save(Cuenta cuenta) {
		cuentaDao.save(cuenta);
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		cuentaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Cuenta findCuentaById(Long id) {
		return cuentaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Cuenta> findAll() {
		return cuentaDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Cuenta> findCuentaByIdCliente(Long idCliente) {
		return cuentaDao.listaCuentasByIdCliente(idCliente);
	}
}