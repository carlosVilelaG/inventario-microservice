package com.banco.pichincha.inventario.microservice.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.banco.pichincha.inventario.microservice.modelo.Movimiento;

public interface MovimientoDao extends JpaRepository<Movimiento, Long>{
	
	@Query("SELECT SUM(m.valor) FROM Movimiento m where m.cuenta.numeroCuenta=?1 and m.tipoMovimient=?1 and m.Fecha=?2")
	public BigDecimal totalRetirosDiario(Long numeroCuenta,String tipoMovimiento, Date fecha);
}
