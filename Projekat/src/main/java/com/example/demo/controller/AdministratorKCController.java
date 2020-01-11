package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKCDTO;
import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Klinika;
import com.example.demo.model.Pacijent;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.AdministratorKlinikeService;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinickiCentarService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.PacijentService;


@CrossOrigin
@RestController
@RequestMapping(value="/api/administratoriKC", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorKCController {
	
	@Autowired
	private AdministratorKCService administratorKCService;
	
	@Autowired
	private AdministratorKlinikeService administratorKlinikeService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private KlinickiCentarService KCService;
	
	@Autowired
	private EmailService emailService;
	
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//vrati mi sve admnistratore kc
	@GetMapping(value = "/svi")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<AdministratorKCDTO>> getAll(Principal p) {

		List<AdministratorKC> administratoriKC = administratorKCService.findAll();

		System.out.println("ISPISANI SVI ADMINISTRATORI KC IZ BAZE");
		List<AdministratorKCDTO> administratoriKCDTO = new ArrayList<>();
		for (AdministratorKC aKC : administratoriKC) {
			administratoriKCDTO.add(new AdministratorKCDTO(aKC));
		}

		return new ResponseEntity<>(administratoriKCDTO, HttpStatus.OK);
	}
	
	//vrati mi trenutnog admnistratora kc
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/pronadjenAdministratorKC/{email}")
	public ResponseEntity<AdministratorKCDTO> getAdministratorKCByEmail(@PathVariable String email){
		System.out.println(email);
		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		if (administratorKC == null) {
			System.out.println("NIJE PRONADJEN");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJEN: "+ administratorKC.getEmail());
		
		return new ResponseEntity<>(new AdministratorKCDTO(administratorKC), HttpStatus.OK);
	}
	
	//vrati mi admina kc pomocu id-a
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/pronadjenAdminKC/{id}")
	public ResponseEntity<AdministratorKCDTO> getAdministratorKCByEmail(@PathVariable Long id){
		System.out.println(id);
		AdministratorKC administratorKC = administratorKCService.findById(id);
		if (administratorKC == null) {
			System.out.println("NIJE PRONADJEN");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJEN: "+ administratorKC.getEmail());
		
		return new ResponseEntity<>(new AdministratorKCDTO(administratorKC), HttpStatus.OK);
	}


	//vrati mi listu zahteva od korisnika tj mejlove
	@GetMapping(value = "/listaZahtevaZaRegistraciju")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PacijentDTO>> getListaZahtevaZaRegistraciju(Principal pr) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(pr.getName());
		
		KlinickiCentar kc = administratorKC.getKlinickiCentar();
		List<PacijentDTO> lista = new ArrayList<PacijentDTO>();
		for(Pacijent p : kc.getZahteviZaRegistraciju()) {
			if(p.getOdobrenaRegistracija() == false) {
				PacijentDTO pDTO = new PacijentDTO(p);
				lista.add(pDTO);
			}
			
		}
		
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	//izmena podataka o adminu 
	@PutMapping(path="/izmena", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<AdministratorKCDTO> izmeniAdminaKC(@RequestBody AdministratorKCDTO akcDTO) {

		System.out.println("ADMIN KC IZMENA");
		AdministratorKC aKC = administratorKCService.findByEmail(akcDTO.getEmail());
		
		if(akcDTO.getIme() != null && akcDTO.getIme() != "") {
			System.out.println("izmenjeno ime admina");
			aKC.setIme(akcDTO.getIme());	
		}
		if(akcDTO.getPrezime() != null && akcDTO.getPrezime() != "") {
			System.out.println("izmenjeno prezime admina");
			aKC.setPrezime(akcDTO.getPrezime());
		}
		if(akcDTO.getLozinka() != null && akcDTO.getLozinka() != "") {
			System.out.println("izmenjena lozinka admina");
			aKC.setLozinka(akcDTO.getLozinka());
		}
		aKC = administratorKCService.save(aKC);
		return new ResponseEntity<>(new AdministratorKCDTO(aKC), HttpStatus.OK);
	}

	//TODO 1: NE RADI
	//potvrda registracije
	@PostMapping(path = "/potvrda/{email}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> potvrdaRegistracijePacijenata(@PathVariable String email){
		System.out.println("------------------------------------");
		Pacijent p = pacijentService.findByEmail(email);
		PacijentDTO pDTO = new PacijentDTO(p);

		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);	
	
		if(kc.getZahteviZaRegistraciju().isEmpty()) {
			System.out.println("prazna listaaa");
			return new ResponseEntity<>("U listi ne postoji pacijent", HttpStatus.BAD_REQUEST);
		}else {
			p.setOdobrenaRegistracija(true);
			p = pacijentService.save(p);
			System.out.println(p.getOdobrenaRegistracija());
			
			kc.getZahteviZaRegistraciju().remove(p);
			kc.setZahteviZaRegistraciju(kc.getZahteviZaRegistraciju());
			kc = KCService.save(kc);
			System.out.println(kc.getZahteviZaRegistraciju().toString());
		}

			
		String subject ="Odobrena registracija";
		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() 
					+ ",\n\nMolimo Vas da potvrdite vasu registraciju klikom na sledeci link: http://localhost:3000 .";

		System.out.println(text);
		
		//slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Odobreno", HttpStatus.OK);
	}
	
	//TODO 2: NE RADI
	//odbijanje registracije pacijenata
	@PostMapping(path = "/odbijanje/{email}/{razlog}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> odbijanjeRegistracijePacijenata(@PathVariable String email, @PathVariable String razlog){
		System.out.println("------------------------------------");
		Pacijent p = pacijentService.findByEmail(email);
		PacijentDTO pDTO = new PacijentDTO(p);

		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);

//		if(kc.getZahteviZaRegistraciju().isEmpty()) {
//			System.out.println("prazna listaaa");
//			return new ResponseEntity<>("U listi ne postoji taj pacijent", HttpStatus.BAD_REQUEST);
//		}else {
			System.out.println("Uspesno obrisan pacijent");
			kc.getZahteviZaRegistraciju().remove(p);
//			pacijentService.delete(p);
			kc.setZahteviZaRegistraciju(kc.getZahteviZaRegistraciju());
			kc = KCService.save(kc);
			
//		}

		String subject ="Odobijena registracija";
		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() 
					+ ",\n\nVasa registracija je odbijena od strane administratorskog tima Klinickog Centra. \nRazlog odbijanja: \n"
					+ razlog + "\n\nS postovanjem,\nKlinicki Centar";
		System.out.println(text);
			
		//slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Odbijeno", HttpStatus.OK);
	}
	
	//TODO 3: brisanje klinike
	//TODO 4: brisanje admina klinike
	//TODO 5: brisanje admina kc
	
	//TODO 7: izmena admina klinike
	
	//dodavanje nove klinike
	@PostMapping(path = "/dodavanjeKlinike", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<KlinikaDTO> dodavanjeKlinike(@RequestBody KlinikaDTO klinikaDTO) {
		System.out.println("------------------------------------------------------");
		Klinika klinika = new Klinika();
		if(klinikaDTO.getNaziv() != "" && klinikaDTO.getNaziv() != null) {
			
			klinika.setNaziv(klinikaDTO.getNaziv());
			klinika.setOpis(klinikaDTO.getOpis());
			klinika.setAdresa(klinikaDTO.getAdresa());
			klinika.setOcena(klinikaDTO.getOcena());
			
			List<KlinickiCentar> listaKC = KCService.find();
			KlinickiCentar kc = listaKC.get(0);
			
			klinika.setKlinickiCentar(kc);
			klinika = klinikaService.save(klinika);
			
			kc.getListaKlinika().add(klinika);
			kc = KCService.save(kc);
			System.out.println("------------------------------------------------------");
		}
		
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.CREATED);
	}
	
	//dodavanje novog administratora klinike
	@PostMapping(path = "/dodavanjeAdminaKlinike", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<AdministratorKlinikeDTO> dodavanjeAdminaKlinike(@RequestBody AdministratorKlinikeDTO akDTO) {
		System.out.println("------------------------------------------------------");
		AdministratorKlinike ak = new AdministratorKlinike();
		ak.setIme(akDTO.getIme());
		ak.setPrezime(akDTO.getPrezime());
		ak.setEmail(akDTO.getEmail());
		ak.setLozinka(akDTO.getLozinka());
		ak.setTelefon(akDTO.getTelefon());
			
		Klinika k = klinikaService.findById(akDTO.getIdKlinike());
		ak.setKlinika(k);
		ak = administratorKlinikeService.save(ak);
		k.getListaAdminKlinike().add(ak);
		k = klinikaService.save(k);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new AdministratorKlinikeDTO(ak), HttpStatus.CREATED);
	}
	
	//dodavanje novog administratora kc
	@PostMapping(path = "/dodavanjeAdminaKC", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<AdministratorKCDTO> dodavanjeAdminaKC(@RequestBody AdministratorKCDTO akDTO) {
		System.out.println("------------------------------------------------------");
		AdministratorKC ak = new AdministratorKC();
		ak.setIme(akDTO.getIme());
		ak.setPrezime(akDTO.getPrezime());
		ak.setEmail(akDTO.getEmail());
		ak.setLozinka(akDTO.getLozinka());

		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		ak.setKlinickiCentar(kc);
		
		ak = administratorKCService.save(ak);
		
		kc.getListaAdminKC().add(ak);
		kc = KCService.save(kc);
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new AdministratorKCDTO(ak), HttpStatus.CREATED);
	}
	
	
}
