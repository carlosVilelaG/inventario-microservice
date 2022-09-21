package com.banco.pichincha.inventario.microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.pichincha.inventario.microservice.dao.PersonaDao;
import com.banco.pichincha.inventario.microservice.modelo.Persona;

@Service
public class PersonaService implements IPersonaService{

	@Autowired
	PersonaDao personaDao;
	
	@Override
	@Transactional(readOnly=true)
	public Persona findPersonaById(Long id) {
		return personaDao.findById(id).orElse(null);
	}
}
