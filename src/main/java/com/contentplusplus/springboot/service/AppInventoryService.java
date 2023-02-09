package com.contentplusplus.springboot.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.helper.AppInventoryHelper;
import com.contentplusplus.springboot.model.AppInventory;
import com.contentplusplus.springboot.repository.AppInventoryRepository;

@Service
public class AppInventoryService {
	@Autowired
	AppInventoryRepository appInventoryRepository;

	public void save(MultipartFile file) {
		try {
			List<AppInventory> inventories = AppInventoryHelper.csvToInventory(file.getInputStream());
			appInventoryRepository.saveAll(inventories);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public ByteArrayInputStream load() {
		List<AppInventory> inventories = appInventoryRepository.findAll();

		ByteArrayInputStream in = AppInventoryHelper.inventoriesToCSV(inventories);
		return in;
	}

	public List<AppInventory> getAllInvetories() {
		return appInventoryRepository.findAll();
	}
}
