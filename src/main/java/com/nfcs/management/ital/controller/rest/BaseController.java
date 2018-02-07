package com.nfcs.management.ital.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nfcs.management.ital.model.BaseEntity;
import com.nfcs.management.ital.repos.BaseEntityRepository;

import java.lang.reflect.ParameterizedType;

public class BaseController<T extends BaseEntity> {
	@Autowired
	BaseEntityRepository<T, Long> ber;

	@SuppressWarnings("unchecked")
	Class<? extends BaseEntity> entityClass = (Class<? extends BaseEntity>) ((ParameterizedType) this.getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];

	public BaseController() {
		super();
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<T> get(@PathVariable(value = "id") Long entityId) {
		T entity = ber.findById(entityId).get();

		if (entity == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(entity);
	}

	@GetMapping(value = "/all.json", produces = { "application/json" })
	@ResponseBody
	public List<T> getAllEntitiesJson(Model model) {
		return ber.findAll();
	}

	@GetMapping()
	public String getAllEntities(Model model) {
		List<T> entities = ber.findAll();
		model.addAttribute("entities", entities);
		return "/" + entityClass.getName().substring(entityClass.getName().lastIndexOf('.') + 1).toLowerCase() + "s";
	}

	@PostMapping(value = "/save")
	@ResponseBody
	public T newEntity(@Validated @RequestBody T entity) {
		return ber.save(entity);
	}

	@DeleteMapping(value = "{id}")
	@ResponseBody
	public ResponseEntity<T> deleteEntity(@PathVariable(value = "id") Long entityId) {
		T entity = ber.findById(entityId).get();

		if (entity == null) {
			return ResponseEntity.notFound().build();
		}

		ber.delete(entity);
		return ResponseEntity.ok().build();
	}

	@PutMapping(value = "{id}")
	public @ResponseBody ResponseEntity<T> updateEntity(@PathVariable(value = "id") Long entityId,
			@Validated @RequestBody T entityDetails) {
		T entity = ber.findById(entityId).get();

		if (entity == null) {
			return ResponseEntity.notFound().build();
		}

		T updatedEntity = ber.save(entity);
		return ResponseEntity.ok(updatedEntity);
	}
}