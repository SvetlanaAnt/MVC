package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Klinika;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.PacijentRepository;

@Service
public class KlinikaService {
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	@Autowired
	private PacijentRepository pacijentRepository;
	
	

	public Klinika findOne(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}
	
	public Klinika findByNaziv(String naziv) {
		return klinikaRepository.findByNaziv(naziv);
	}
	
	public Klinika findByAdresa(String adresa) {
		return klinikaRepository.findByAdresa(adresa);
	}
	
	public List<Klinika> findAll() {
		return klinikaRepository.findAll();
	}
	public Klinika save(Klinika klinika) {
		return klinikaRepository.save(klinika);
	}
	public void delete(Klinika klinika) {
		klinikaRepository.delete(klinika);
	}
	public Klinika findById(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}
	
	public List<Pacijent>findByIdKlinike(Long id){
		return pacijentRepository.findByIdKlinike(id);
	}

	public float nedeljniPrihod(Long id) {
		return klinikaRepository.nedeljniPrihod(id);
	}
}


