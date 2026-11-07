package classesCatan;
import java.util.Objects;

import enums.TipusRecurs;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="recursJugador", uniqueConstraints = @UniqueConstraint(columnNames = {"id_jugador","tipus_jugador"}))

public class RecursJugador {

	//Atributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="tipus_jugador",nullable=false, length=20)
	@Enumerated(EnumType.STRING)
	private TipusRecurs tipus;
	
	@Column(name="quantitat_jugador",nullable=false)
	private Integer quantitat;

	//Relacions
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_jugador")
	private Jugador jugador;

	//Constructors
	public RecursJugador() {
		
	}
	
	public RecursJugador(TipusRecurs tipus, Integer quantitat) {
		this.tipus = tipus;
		this.quantitat = quantitat;
	}

	//Get Set
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipusRecurs getTipus() {
		return tipus;
	}

	public void setTipus(TipusRecurs tipus) {
		this.tipus = tipus;
	}

	public Integer getQuantitat() {
		return quantitat;
	}

	public void setQuantitat(Integer quantitat) {
		this.quantitat = quantitat;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		RecursJugador that = (RecursJugador) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "RecursJugador [id=" + id + ", tipus=" + tipus + ", quantitat=" + quantitat + "]";
	}
	
	
	
	
	
	
}
