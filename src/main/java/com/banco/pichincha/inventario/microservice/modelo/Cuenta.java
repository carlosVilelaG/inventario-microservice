package com.banco.pichincha.inventario.microservice.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Cuenta implements Serializable {

	private static final long serialVersionUID = 6853566039551996500L;

	@Id
	private Long numeroCuenta;
	
	private String tipoCuenta;
	private BigDecimal SaldoInicial;
	private String estado;	
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "cuenta")
	private List<Movimiento> misMovimientos;
	
	public Cuenta() {
		misMovimientos = new ArrayList<>();
	}
	public Long getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(Long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	public BigDecimal getSaldoInicial() {
		return SaldoInicial;
	}
	public void setSaldoInicial(BigDecimal saldoInicial) {
		SaldoInicial = saldoInicial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@JsonBackReference
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@JsonManagedReference
	public List<Movimiento> getMisMovimientos() {
		return misMovimientos;
	}
	public void setMisMovimientos(List<Movimiento> misMovimientos) {
		this.misMovimientos = misMovimientos;
	}
	
	
}
