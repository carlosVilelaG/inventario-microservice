package com.banco.pichincha.inventario.microservice.service;

import java.math.BigDecimal;

import com.banco.pichincha.inventario.microservice.modelo.Movimiento;

public interface IMovimientoService {
	
	public void save(Movimiento mov);
	
	public Movimiento findMovimientoById(Long id);
	
	public void deleteById(Long id);

	Boolean superaLimiteDiarioDebito(Long numeroCuenta, BigDecimal nuevoDebito);

}
