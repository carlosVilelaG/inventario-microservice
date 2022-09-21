package com.banco.pichincha.inventario.microservice.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Movimiento implements Serializable{
	
	private static final long serialVersionUID = -7726516023729847542L;
	
	@Id
	@GeneratedValue(strategy = 	GenerationType.IDENTITY)
	private Long idMovimiento;
	
	@Temporal(TemporalType.TIME)
	private Date Fecha;
	private String tipoMovimient;
	private BigDecimal valor;
	private BigDecimal saldo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cuenta_numero_cuenta")
	private Cuenta cuenta;
	
	public Movimiento() {
	}
	
	public Long getIdMovimiento() {
		return idMovimiento;
	}
	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	public String getTipoMovimient() {
		return tipoMovimient;
	}
	public void setTipoMovimient(String tipoMovimient) {
		this.tipoMovimient = tipoMovimient;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	@JsonBackReference
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
}
