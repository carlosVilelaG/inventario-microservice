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
import com.banco.pichincha.inventario.microservice.modelo.Persona;
import com.banco.pichincha.inventario.microservice.payload.ApiResponse;
import com.banco.pichincha.inventario.microservice.service.IClienteService;
import com.banco.pichincha.inventario.microservice.service.IPersonaService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	IPersonaService personaService;
	@Autowired 
	IClienteService clienteService;

	@GetMapping("/todos")
	public ResponseEntity<?> consultarTodo(){
		Map<String,Object> response = new HashMap<>();
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		listaClientes = clienteService.findAll();
		if(listaClientes.isEmpty()) {
			response.put("mensaje", "No existen registros de clientes");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}
		response.put("clientes", listaClientes);
		return new ResponseEntity<>(response, HttpStatus.OK);		
	}	
		
	
	@PostMapping("/registro")
	public ResponseEntity<?> registrarcuenta(@RequestBody Cliente cliente){
		ApiResponse response = new ApiResponse();
		Persona persona = new Persona();		
		
		if(null == cliente.getPersona() || null == cliente.getPersona().getCodigoPersona()){
			response.setCodMensaje(4);
			response.getMensajes().add("No fue enviado el id de la Persona");	
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);	
		}
		
		persona = personaService.findPersonaById(cliente.getPersona().getCodigoPersona());
		if(null == persona){
			response.setCodMensaje(4);
			response.getMensajes().add("No existe Persona con id "+cliente.getPersona().getCodigoPersona());	
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);		
		}
		
		cliente.setPersona(persona);
				
		try {
			clienteService.save(cliente);		
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
	public ResponseEntity<?> actualizarCuenta(@RequestBody Cliente cliente, @PathVariable Long id) {
		ApiResponse response = new ApiResponse();
		try {
			Cliente clienteModificada = clienteService.findClienteById(id);
			if(clienteModificada == null) {
				response.setCodMensaje(2);
				response.getMensajes().add("No existe el cliente en la base de datos");	
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			clienteModificada.setEstado(cliente.getEstado());
			clienteModificada.setContrasenia(cliente.getContrasenia());
			clienteService.save(clienteModificada);
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
	public ResponseEntity<?> eliminarCliente(@PathVariable Long id){
		ApiResponse response = new ApiResponse();
		Cliente clienteEliminada = clienteService.findClienteById(id);
		if(clienteEliminada == null) {
			response.setCodMensaje(2);
			response.getMensajes().add("No existe cliente en la base de datos");	
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		clienteService.deleteById(id);
		response.setCodMensaje(1);
		response.getMensajes().add("Transaccion exitosa");
		return new ResponseEntity<>(response, HttpStatus.OK);		
	}

}
