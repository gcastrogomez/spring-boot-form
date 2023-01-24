package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.entities.Nacionalidad;

@Service
public class NacionalidadServiceImpl implements NacionalidadService {
	
	private List<Nacionalidad> lista;
	
	public NacionalidadServiceImpl() {
		this.lista=Arrays.asList(
				new Nacionalidad(1, "ES", "España"), 
				new Nacionalidad(2, "AR", "Argentina"), 
				new Nacionalidad(3, "MX", "México"), 
				new Nacionalidad(4, "CL", "Chile"), 
				new Nacionalidad(5, "CO", "Colombia")				
				);
	}

	@Override
	public List<Nacionalidad> listar() {
		return lista;
	}

	@Override
	public Nacionalidad obtenerPorId(Integer id) {
		Nacionalidad resultado = null;
		for(Nacionalidad nacionalidad: this.lista) {
			if (id==nacionalidad.getId()) {
				resultado = nacionalidad;
				break;
			}
		}
		return resultado;
	}

}
