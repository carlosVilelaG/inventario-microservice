package com.banco.pichincha.inventario.microservice.controlador;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.pichincha.inventario.microservice.modelo.Cuenta;
import com.banco.pichincha.inventario.microservice.modelo.Movimiento;
import com.banco.pichincha.inventario.microservice.payload.ApiResponse;
import com.banco.pichincha.inventario.microservice.service.ICuentaService;
import com.banco.pichincha.inventario.microservice.service.IMovimientoService;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
	
	@Autowired
	ICuentaService cuentaServicio;
	@Autowired 
	IMovimientoService movimientoService;
	
	@PostMapping("/registro")
	public ResponseEntity<?> registrarMovimiento(@RequestBody Movimiento movimiento){
		ApiResponse response = new ApiResponse();
		Cuenta cuenta = new Cuenta();		
		BigDecimal cero = new BigDecimal(0);
		if(null == movimiento.getCuenta() || null == movimiento.getCuenta().getNumeroCuenta()){
			response.setCodMensaje(4);
			response.getMensajes().add("No existe Numero de cuenta asignado");	
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);	
		}
		
		cuenta = cuentaServicio.findCuentaById(movimiento.getCuenta().getNumeroCuenta());
		if(null == cuenta){
			response.setCodMensaje(4);
			response.getMensajes().add("No existe Cuenta # "+movimiento.getCuenta().getNumeroCuenta());	
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);	
		}
		
		if("CREDITO".equalsIgnoreCase(movimiento.getTipoMovimient())) {
			movimiento.getSaldo().add(movimiento.getValor());		
		}
		
		if("DEBITO".equalsIgnoreCase(movimiento.getTipoMovimient())) {
			if(movimiento.getSaldo().compareTo(cero)==0) {
				response.setCodMensaje(1);
				response.getMensajes().add("Saldo no disponible");	
				return new ResponseEntity<>(response, HttpStatus.OK);	
			}
			
			if (movimientoService.superaLimiteDiarioDebito(movimiento.getCuenta().getNumeroCuenta(),
					movimiento.getValor())) {
				response.setCodMensaje(1);
				response.getMensajes().add("Cupo diario Excedido");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			movimiento.getSaldo().subtract(movimiento.getValor());
		}
		movimiento.setCuenta(cuenta);
		
		try {
			movimientoService.save(movimiento);
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
	public ResponseEntity<?> actualizarMovimiento(@RequestBody Movimiento mov, @PathVariable Long id) {
		ApiResponse response = new ApiResponse();
		try {
			Movimiento movimientoModificado = movimientoService.findMovimientoById(id);
			if(movimientoModificado == null) {
				response.setCodMensaje(2);
				response.getMensajes().add("No existe movimiento en la base de datos");	
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			movimientoModificado.setFecha(mov.getFecha());
			movimientoModificado.setSaldo(mov.getSaldo());
			movimientoModificado.setTipoMovimient(mov.getTipoMovimient());
			movimientoModificado.setValor(mov.getValor());
			movimientoService.save(movimientoModificado);
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
	public ResponseEntity<?> eliminarMovimiento(@PathVariable Long id){
		ApiResponse response = new ApiResponse();
		Movimiento mov = movimientoService.findMovimientoById(id);
		if(mov == null) {
			response.setCodMensaje(2);
			response.getMensajes().add("No existe movimiento en la base de datos");	
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		movimientoService.deleteById(id);
		response.setCodMensaje(1);
		response.getMensajes().add("Transaccion exitosa");
		return new ResponseEntity<>(response, HttpStatus.OK);		
	}

}
