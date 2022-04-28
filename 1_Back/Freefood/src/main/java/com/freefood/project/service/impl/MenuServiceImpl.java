package com.freefood.project.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freefood.project.model.Menu;
import com.freefood.project.repository.MenuRepository;
import com.freefood.project.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public Menu findById(Long idMenu) {
		Optional<Menu> result = this.menuRepository.findById(idMenu);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public List<Menu> findAll() {
		return this.menuRepository.findAll();
	}

	@Override
	public Menu saveMenu(Menu menu) {
		return this.menuRepository.save(menu);
	}

	@Override
	public Menu updateMenu(Menu menu) {
		return this.menuRepository.save(menu);
	}

	@Override
	public void deleteMenu(Long idMenu) {
		this.menuRepository.deleteById(idMenu);
	}

}
