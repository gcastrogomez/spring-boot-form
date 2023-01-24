package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.NacionalidadService;

@Component
public class NacionalidadPropertyEditors extends PropertyEditorSupport {

	@Autowired
	private NacionalidadService nacionalidadService;

	@Override
	public void setAsText(String id) throws IllegalArgumentException {

		try {
			super.setValue(nacionalidadService.obtenerPorId(Integer.parseInt(id)));
		} catch (NumberFormatException e) {
			setValue(null);
		}
	}

}
