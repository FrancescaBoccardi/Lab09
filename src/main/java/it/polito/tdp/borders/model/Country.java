package it.polito.tdp.borders.model;

public class Country {
	
	private int id;
	private String abbr;
	private String nome;
	
	
	public Country(int id, String abbr, String nome) {
		this.id = id;
		this.abbr = abbr;
		this.nome = nome;
	}


	public int getId() {
		return id;
	}


	public String getAbbr() {
		return abbr;
	}


	public String getNome() {
		return nome;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return nome;
	}
	
	
	
	
	
	
	

}
