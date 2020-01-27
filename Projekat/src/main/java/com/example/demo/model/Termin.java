package com.example.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
public class Termin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Temporal(TemporalType.DATE)
//	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "datumPocetka", nullable = false)
	private Date datumPocetka;

	@Column(name = "termin", nullable = false)
	private Integer termin;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Sala sala;

//	@Column(name = "datumKraja", nullable = false)
//	private Date datumKraja;

//	@Column(name = "status", nullable = true)
//	private boolean status; 

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private RadniDan radniDan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

//	public Date getDatumPocetka() {
//		return datumPocetka;
//	}
//
//	public void setDatumPocetka(Date datumPocetka) {
//		this.datumPocetka = datumPocetka;
//	}
//
//	public Date getDatumKraja() {
//		return datumKraja;
//	}
//
//	public void setDatumKraja(Date datumKraja) {
//		this.datumKraja = datumKraja;
//	}

	public Date getDatumPocetka() {
		return datumPocetka;
	}

	public void setDatumPocetka(Date datumPocetka) {
		this.datumPocetka = datumPocetka;
	}

	public Integer getTermin() {
		return termin;
	}

	public void setTermin(Integer termin) {
		this.termin = termin;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

//	public boolean isStatus() {
//		return status;
//	}
//
//	public void setStatus(boolean status) {
//		this.status = status;
//	}

//	public RadniDan getRadniDan() {
//		return radniDan;
//	}
//
//	public void setRadniDan(RadniDan radniDan) {
//		this.radniDan = radniDan;
//	}

	public Termin() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Termin [id=" + id + ", datumPocetka=" + datumPocetka + ", termin=" + termin + ", sala=" + sala
				+ "]";
	}



}