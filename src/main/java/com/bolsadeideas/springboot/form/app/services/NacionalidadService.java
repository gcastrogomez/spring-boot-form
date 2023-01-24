package com.bolsadeideas.springboot.form.app.services;

import java.util.List;

import com.bolsadeideas.springboot.form.app.entities.Nacionalidad;

public interface NacionalidadService {
	
	public List<Nacionalidad> listar();
	public Nacionalidad obtenerPorId(Integer id);
	
}
