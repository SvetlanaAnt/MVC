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

import com.fasterxml.jackson.annotation.JsonFormat;


public class OdmorOdsustvoMedicinskaSestra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@JsonFormat(pattern = "yyyy-MM-dd")
//    @Column(updatable = false)
//	private Date datumOd;

//	@JsonFormat(pattern = "yyyy-MM-dd")
//	@Column(name="do", nullable=false)
//	@Column(updatable = false)
//	private Date datumDo;
	
//	private Time trajanje;

//	@Column(name="opis", nullable=false)
//	private String opis;


//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private MedicinskaSestra medicinskaSestra;
	
//	@Column(name="status", nullable=false)
//	private boolean status; //odobreno-1, odbijeno-0
//	
//	@Column(name="tip", nullable=false)
//	private TipOdmorOdsustvo tip;

	public OdmorOdsustvoMedicinskaSestra() {
		super();
	}
//	public String getOpis() {
//		return opis;
//	}
//	public void setOpis(String opis) {
//		this.opis = opis;
//	}
//	public MedicinskaSestra getMedicinskaSestra() {
//		return medicinskaSestra;
//	}
//	public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
//		this.medicinskaSestra = medicinskaSestra;
//	}
//	public TipOdmorOdsustvo getTip() {
//		return tip;
//	}
//	public void setTip(TipOdmorOdsustvo tip) {
//		this.tip = tip;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public boolean isStatus() {
//		return status;
//	}
//	public void setStatus(boolean status) {
//		this.status = status;
//	}
	
//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		return super.equals(obj);
//	}
//	@Override
//	public int hashCode() {
//		// TODO Auto-generated method stub
//		return super.hashCode();
//	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return super.toString();
//	}
	
}
