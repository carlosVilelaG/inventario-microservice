package com.banco.pichincha.inventario.microservice.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.pichincha.inventario.microservice.modelo.Cliente;
import com.banco.pichincha.inventario.microservice.modelo.Cuenta;
import com.banco.pichincha.inventario.microservice.payload.ApiResponse;
import com.banco.pichincha.inventario.microservice.service.IClienteService;
import com.banco.pichincha.inventario.microservice.service.ICuentaService;


@RestController
@RequestMapping("/cuentas")
public class CuentaController {

	@Autowired
	ICuentaService cuentaServicio;
	@Autowired 
	IClienteService clienteService;

	@GetMapping("/todos")
	public ResponseEntity<?> consultarTodo(){
		Map<String,Object> response = new HashMap<>();
		List<Cuenta> listaCuentas = new ArrayList<Cuenta>();
		listaCuentas = cuentaServicio.findAll();
		if(listaCuentas.isEmpty()) {
			response.put("mensaje", "No existen registros de cuentas");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}
		response.put("cuentas", listaCuentas);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/obtenerAllCuentas")
	public ResponseEntity<?> consultarAllCuentas() {
		List<HashMap<String, String>> datos = new ArrayList<HashMap<String,String>>();
		ApiResponse response = new ApiResponse();
		try {
		    HashMap<String, String> map = new HashMap<>();
			for (Cuenta c : cuentaServicio.findAll()){
				map = new HashMap<>();
				map.put("cod", c.getNumeroCuenta().toString());
				map.put("name", c.getEstado());
				datos.add(map);
			}
			response.setResult(datos);
			response.setCodMensaje(1);
			response.getMensajes().add("Consulta con exito");
		} catch (Exception e) {
			response.setCodMensaje(2);
			response.getMensajes().add("Error al consultar las cuentas");
			e.printStackTrace();
		}
		return new ResponseEntity(response, HttpStatus.OK);

	}	
	
	@PostMapping("/registro")
	public ResponseEntity<?> registrarcuenta(@RequestBody Cuenta cuenta){
		ApiResponse response = new ApiResponse();
		Cliente cliente = new Cliente();		
		
		if(null == cuenta.getCliente() || null == cuenta.getCliente().getClienteId()){
			response.setCodMensaje(4);
			response.getMensajes().add("No existe Cliente asignado a la cuenta");	
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);	
		}
		
		cliente = clienteService.findClienteById(cuenta.getCliente().getClienteId());
		if(null == cliente){
			response.setCodMensaje(4);
			response.getMensajes().add("No existe Cliente con id "+cuenta.getCliente().getClienteId());	
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);		
		}
		
		cuenta.setCliente(cliente);
		
		try {
			cuentaServicio.save(cuenta);		
			response.setCodMensaje(1);
			response.getMensajes().add("Transaccion exitosa");
		}catch(Exception e) {
			response.setCodMensaje(3);
			response.getMensajes().add("Error General:".concat(e.getMessage()));
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
	
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizarCuenta(@RequestBody Cuenta cuenta, @PathVariable Long id) {
		ApiResponse response = new ApiResponse();
		try {
			Cuenta cuentaModificada = cuentaServicio.findCuentaById(id);
			if(cuentaModificada == null) {
				response.setCodMensaje(2);
				response.getMensajes().add("No existe la cuenta en la base de datos");	
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			cuentaModificada.setEstado(cuenta.getEstado());
			cuentaModificada.setSaldoInicial(cuenta.getSaldoInicial());
			cuentaModificada.setTipoCuenta(cuenta.getTipoCuenta());
			cuentaServicio.save(cuentaModificada);
			response.setCodMensaje(1);
			response.getMensajes().add("Transaccion exitosa");
		}catch(DataAccessException e) {
			response.setCodMensaje(2);
			response.getMensajes().add("Error al leer en la base de datos");
			e.printStackTrace();	
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(Exception e) {
			response.setCodMensaje(3);
			response.getMensajes().add("Error General:".concat(e.getMessage()));
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);			
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarCuenta(@PathVariable Long id){
		ApiResponse response = new ApiResponse();
		Cuenta cuentaEliminada = cuentaServicio.findCuentaById(id);
		if(cuentaEliminada == null) {
			response.setCodMensaje(2);
			response.getMensajes().add("No existe la cuenta en la base de datos");	
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		cuentaServicio.delete(id);
		response.setCodMensaje(1);
		response.getMensajes().add("Transaccion exitosa");
		return new ResponseEntity<>(response, HttpStatus.OK);		
	}
		
}
