package com.syuq.springjwt.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import com.syuq.springjwt.models.Menu;
import com.syuq.springjwt.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/menus")
public class MenuController {
    @Autowired
    MenuRepository menuRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Menu>> getAllMenus(@RequestParam(required = false) String name) {
		try {
			List<Menu> menus = new ArrayList<Menu>();

			if (name == null)
				menuRepository.findAll().forEach(menus::add);
			else
				menuRepository.findByNameContaining(name).forEach(menus::add);

			if (menus.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(menus, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Menu> getMenusById(@PathVariable("id") long id) {
		Optional<Menu> menuData = menuRepository.findById(id);

		if (menuData.isPresent()) {
			return new ResponseEntity<>(menuData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
		try {
			Menu _menu = menuRepository
					.save(new Menu(menu.getName(), menu.getPrice(), false));
			return new ResponseEntity<>(_menu, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
  	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Menu> updateTutorial(@PathVariable("id") long id, @RequestBody Menu menu) {
		Optional<Menu> menuData = menuRepository.findById(id);

		if (menuData.isPresent()) {
			Menu _menu = menuData.get();
			_menu.setName(menu.getName());
			_menu.setPrice(menu.getPrice());
			_menu.setVoucher(menu.isVoucher());
			return new ResponseEntity<>(menuRepository.save(_menu), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
  	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			menuRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			menuRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/voucher")
	public ResponseEntity<List<Menu>> findByVoucher() {
		try {
			List<Menu> menus = menuRepository.findByVoucher(true);

			if (menus.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(menus, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
