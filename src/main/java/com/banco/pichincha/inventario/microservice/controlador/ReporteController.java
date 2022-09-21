package com.banco.pichincha.inventario.microservice.controlador;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.pichincha.inventario.microservice.modelo.Cuenta;
import com.banco.pichincha.inventario.microservice.payload.ApiResponse;
import com.banco.pichincha.inventario.microservice.service.ICuentaService;

@RestController
@RequestMapping("/cuentas")
public class ReporteController {
	
	@Autowired
	ICuentaService cuentaServicio;
	
	@GetMapping("/todos")
	public ResponseEntity<?> consultarEstadoCuenta(@RequestParam(name="idCliente") Long idCliente,
    		@RequestParam(name="fechaI") String fechaInicio, @RequestParam(name="fechaF") String fechaFin){
		ApiResponse response = new ApiResponse();
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	        String currentDateTime = dateFormatter.format(new Date());
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	        Date fechaInicial = null;
	        try {
	        	fechaInicial = formatter.parse(fechaInicio);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        Date fechaFinal = null;
	        try {
	        	fechaFinal = formatter.parse(fechaFin);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		List<Cuenta> listaCuenta = new ArrayList<>();
		listaCuenta = cuentaServicio.findCuentaByIdCliente(idCliente);
	        

		if(listaCuenta.isEmpty()) {
			response.setCodMensaje(1);
			response.getMensajes().add("No existen registros de cuentas");
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}	

}
