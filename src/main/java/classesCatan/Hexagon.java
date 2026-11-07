package classesCatan;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import enums.TipusTerreny;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="hexagon")
@NamedQueries({
	@NamedQuery(
			name = "Hexagon.buscarPerCodi",
			query = "FROM Hexagon h WHERE h.codi = :codi"
	),
	
	@NamedQuery(
			name = "Hexagon.MostrarTauler",
		    query = "SELECT DISTINCT h " +
		            "FROM Hexagon h " +
		            "LEFT JOIN FETCH h.vertexsHexagon hv " +
		            "LEFT JOIN FETCH hv.vertex v " +
		            "LEFT JOIN FETCH v.assentament a " +
		            "LEFT JOIN FETCH a.jugador " +
		            "WHERE h.partida_id.id = :idPartida"
)})
public class Hexagon {

	//Atributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="codi", nullable=false)
	private Integer codi;

	@Enumerated(EnumType.STRING)
	@Column(name="terreny", nullable=false, length=15)
	private TipusTerreny terreny;

	@Column(name="num_hexagon")
	private Integer numero;
	
	@Column(name="q_hexagon", nullable=false)
	private Integer q;
	
	@Column(name="r_hexagon", nullable=false)
	private Integer r;

	//Relacions
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partida_id")
	private Partida partida_id;

	@OneToMany(mappedBy = "hexagon", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<HexagonVertex> vertexsHexagon = new HashSet<>();
	
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

	//Get Set
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

	public Set<HexagonVertex> getVertexsHexagon() {
		return vertexsHexagon;
	}

	public void setVertexsHexagon(Set<HexagonVertex> vertexsHexagon) {
		this.vertexsHexagon = vertexsHexagon;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    Hexagon hexagon = (Hexagon) o;

	    return Objects.equals(codi, hexagon.codi);
	}

	@Override
	public int hashCode() {
	    return Objects.hashCode(codi);
	}

	@Override
	public String toString() {
		return "Hexagon [id=" + id + ", codi=" + codi + ", terreny=" + terreny + ", numero=" + numero + ", q=" + q + ", r=" + r + "]";
	}


	
	
}
