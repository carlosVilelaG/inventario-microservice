package com.banco.pichincha.inventario.microservice.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.pichincha.inventario.microservice.dao.MovimientoDao;
import com.banco.pichincha.inventario.microservice.modelo.Movimiento;

@Service
public class MovimientoService implements IMovimientoService {

	private static final BigDecimal LIMITE_RETIRO = new BigDecimal(1000); 
	
	@Autowired
	MovimientoDao movimientoDao;
	
	@Override
	@Transactional
	public void save(Movimiento mov) {
		movimientoDao.save(mov);		
	}

	@Override
	@Transactional(readOnly=true)
	public Movimiento findMovimientoById(Long id) {
		return movimientoDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		movimientoDao.deleteById(id);
	}
	

	@Override
	@Transactional(readOnly = true)
	public Boolean superaLimiteDiarioDebito(Long numeroCuenta, BigDecimal nuevoDebito) {
		Boolean resultado = false;
		BigDecimal retiros = new BigDecimal(0);
		retiros = movimientoDao.totalRetirosDiario(numeroCuenta, "DEBITO", new Date());
		if (LIMITE_RETIRO.compareTo(retiros) > 1 || LIMITE_RETIRO.compareTo(retiros.add(nuevoDebito)) > 1) {
			resultado = true;
		}
		return resultado;
	}

}
