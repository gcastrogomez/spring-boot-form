package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NacionalidadPropertyEditors;
import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.entities.Nacionalidad;
import com.bolsadeideas.springboot.form.app.entities.Role;
import com.bolsadeideas.springboot.form.app.entities.Usuario;
import com.bolsadeideas.springboot.form.app.services.NacionalidadService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class FormController {

	@Autowired
	private UsuarioValidador validador;

	@Autowired
	private NacionalidadService nacionalidadService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private NacionalidadPropertyEditors nacionalidadEditor;

	@Autowired
	private RolesEditor rolesEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, true));

		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());

		binder.registerCustomEditor(Nacionalidad.class, "nacionalidad", nacionalidadEditor);
		binder.registerCustomEditor(Role.class, "roles", rolesEditor);
	}

	@ModelAttribute("genero")
	public List<String> genero() {
		return Arrays.asList("Hombre", "Mujer");
	}

	@ModelAttribute("listaRoles")
	public List<Role> listaRoles() {
		return this.roleService.listar();
	}

	@ModelAttribute("listaNacionalidades")
	public List<Nacionalidad> listaNacionalidades() {
		return nacionalidadService.listar();
	}

	@ModelAttribute("nacionalidades")
	public List<String> nacionalidades() {
		return Arrays.asList("España", "Argentina", "México", "Chile", "Colombia");
	}

	@ModelAttribute("nacionalidadesMap")
	public Map<String, String> nacionalidadesMap() {
		Map<String, String> nacionalidades = new HashMap<String, String>();
		nacionalidades.put("ES", "España");
		nacionalidades.put("AR", "Argentina");
		nacionalidades.put("MX", "México");
		nacionalidades.put("CH", "Chile");
		nacionalidades.put("CO", "Colombia");
		return nacionalidades;
	}

	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesMap() {
		Map<String, String> roles = new HashMap<String, String>();
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");
		return roles;
	}

	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString() {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		return roles;
	}

	@GetMapping("/form")
	public String recibir(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Gonzalo");
		usuario.setApellido("Castro");
		usuario.setId("34.565.321-W");
		usuario.setHabilitar(true);
		usuario.setValorSecreto("Soy secreto");
		usuario.setNacionalidad(new Nacionalidad(1, "ES", "España"));
		usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER")));
		model.addAttribute("titulo", "Formulario de pro");
		model.addAttribute("usuario", usuario);
		return "form";
	}

	@PostMapping("/form")
	public String enviar(@Valid Usuario usuario, BindingResult result, Model model
	// @RequestParam(name="username") String username,
	// @RequestParam String password,
	// @RequestParam String email
	) {

		// validador.validate(usuario, result);
		if (result.hasErrors()) {
			/*
			 * Map<String, String> errores = new HashMap<>();
			 * result.getFieldErrors().forEach(err -> { errores.put(err.getField(),
			 * "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()
			 * )); }); model.addAttribute("error", errores);
			 */
			model.addAttribute("titulo", "Enviado el formulario de registro.");
			return "form";
		}
		// Usuario usuario = new Usuario();
		// usuario.setUsername(username);
		// usuario.setPassword(password);
		// usuario.setEmail(email);
		return "redirect:/ver";
	}

	@GetMapping("/ver")
	public String ver(@SessionAttribute(name = "usuario", required = false) Usuario usuario, Model model,
			SessionStatus status) {
		if (usuario == null) {
			return "redirect:/form";
		}
		model.addAttribute("titulo", "Enviado el formulario de registro.");
		// model.addAttribute("usuario", usuario);
		status.setComplete();
		return "submit";
	}

}
