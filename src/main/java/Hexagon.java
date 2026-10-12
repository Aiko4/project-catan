import enums.TipusTerreny;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="terreny", nullable=false)
	private TipusTerreny terreny;
	
	
	@NotNull
	@Column(name="num_hexagon", nullable=false)
	private Integer numero;
	
	@NotNull
	@Column(name="q_hexagon", nullable=false)
	private Integer q;
	
	@NotNull
	@Column(name="r_hexagon", nullable=false)
	private Integer r;
	
	
	
	public Hexagon() {
		
	}
	
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
	
	
	
	
	
}
