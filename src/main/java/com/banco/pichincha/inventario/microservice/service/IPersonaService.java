package com.banco.pichincha.inventario.microservice.service;

import com.banco.pichincha.inventario.microservice.modelo.Persona;

public interface IPersonaService {

	Persona findPersonaById(Long id);

}
