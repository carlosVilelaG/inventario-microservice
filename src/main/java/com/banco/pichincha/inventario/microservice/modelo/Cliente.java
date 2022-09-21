package com.banco.pichincha.inventario.microservice.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Cliente implements Serializable{

	private static final long serialVersionUID = -2916656458767090768L;
	
	@Id
	@GeneratedValue(strategy = 	GenerationType.IDENTITY)
	private Long clienteId;	
	private String contrasenia;
	private String estado;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name ="persona_codigo_persona")
	private Persona persona;
	
	@OneToMany(mappedBy = "cliente")
	private List<Cuenta> misCuentas;

	public Cliente() {
		misCuentas = new ArrayList<>();
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@JsonBackReference
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	@JsonManagedReference
	public List<Cuenta> getMisCuentas() {
		return misCuentas;
	}
	public void setMisCuentas(List<Cuenta> misCuentas) {
		this.misCuentas = misCuentas;
	}
	
	
	
}
