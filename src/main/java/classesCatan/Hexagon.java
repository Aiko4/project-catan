package classesCatan;
import java.util.List;
import java.util.Objects;

import enums.TipusTerreny;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="hexagon")
public class Hexagon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_hexagon",nullable=false,unique=true)
	private Integer id;
	
	@NotNull
	@Column(name="codi_hexagon", nullable=false)
	private Integer codi;
	
	//private boolean hasLladre;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="terreny", nullable=false, length=15)
	private TipusTerreny terreny;


	@Column(name="num_hexagon", nullable=true)
	private Integer numero;
	
	@NotNull
	@Column(name="q_hexagon", nullable=false)
	private Integer q;
	
	@NotNull
	@Column(name="r_hexagon", nullable=false)
	private Integer r;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "partida_id")
	private Partida partida_id;
	
	@OneToMany(mappedBy = "hexagon")
	private List<HexagonVertex> vertexsHexagon;
	
	// constructor hibernate
	public Hexagon() {	
	}
	
	// constructor import
	public Hexagon(Integer codi, Integer q, Integer r) {
		this.codi = codi;
		this.q = q;
		this.r = r;
	}
	
	// constructor DAO
	public Hexagon(Integer codi, TipusTerreny terreny, Integer numero, Integer q, Integer r) {
		this.codi = codi;
		this.terreny = terreny;
		this.numero = numero;
		this.q = q;
		this.r = r;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public TipusTerreny getTerreny() {
		return terreny;
	}

	public void setTerreny(TipusTerreny terreny) {
		this.terreny = terreny;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getQ() {
		return q;
	}

	public void setQ(Integer q) {
		this.q = q;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Partida getPartida_id() {
		return partida_id;
	}

	public void setPartida_id(Partida partida_id) {
		this.partida_id = partida_id;
	}

	public List<HexagonVertex> getVertexsHexagon() {
		return vertexsHexagon;
	}

	public void setVertexsHexagon(List<HexagonVertex> vertexsHexagon) {
		this.vertexsHexagon = vertexsHexagon;
	}

	@Override
	public String toString() {
		return "Hexagon [id=" + id + ", codi=" + codi + ", terreny=" + terreny + ", numero=" + numero + ", q=" + q + ", r=" + r + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codi, id, numero, q, r, terreny);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hexagon other = (Hexagon) obj;
		return Objects.equals(codi, other.codi) && Objects.equals(id, other.id) && Objects.equals(numero, other.numero)
				&& Objects.equals(q, other.q) && Objects.equals(r, other.r) && terreny == other.terreny;
	}
	

	
	
}
