package it.polito.tdp.lab04.model;

public class Corso {
	private String codIns;
	private Integer numeroCrediti;
	private String nome;
	private Integer periodoDidattico;
	
	public Corso(String codIns, Integer numeroCrediti, String nome, Integer periodoDidattico) {
		super();
		this.codIns = codIns;
		this.numeroCrediti = numeroCrediti;
		this.nome = nome;
		this.periodoDidattico = periodoDidattico;
	}

	@Override
	public String toString() {
		return nome;
	}

	public String getCodIns() {
		return codIns;
	}

	public void setCodIns(String codIns) {
		this.codIns = codIns;
	}

	public Integer getNumeroCrediti() {
		return numeroCrediti;
	}

	public void setNumeroCrediti(Integer numeroCrediti) {
		this.numeroCrediti = numeroCrediti;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPeriodoDidattico() {
		return periodoDidattico;
	}

	public void setPeriodoDidattico(Integer periodoDidattico) {
		this.periodoDidattico = periodoDidattico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codIns == null) ? 0 : codIns.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Corso other = (Corso) obj;
		if (codIns == null) {
			if (other.codIns != null) {
				return false;
			}
		} else if (!codIns.equals(other.codIns)) {
			return false;
		}
		return true;
	}

	@Override
	protected Corso clone() throws CloneNotSupportedException {
		return this.clone();
	}

	public String toLongString() {
		return codIns + ": " + nome + ", " + numeroCrediti + " crediti - " + periodoDidattico + " periodo.\n";
	}
	
	
	
}
