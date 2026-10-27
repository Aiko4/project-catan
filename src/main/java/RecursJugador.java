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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_recurs_jugador",nullable=false, unique=true)
	private Integer id;
	
	@NotNull
	@Column(name="tipus_jugador",nullable=false)
	@Enumerated(EnumType.STRING)
	private TipusRecurs tipus;
	
	@NotNull
	@Column(name="quantitat_jugador",nullable=false)
	private Integer quantitat;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_jugador")
	private Jugador jugador;
	
	public RecursJugador() {
		
	}
	
	public RecursJugador(TipusRecurs tipus, Integer quantitat) {
		this.tipus = tipus;
		this.quantitat = quantitat;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(id, quantitat, tipus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecursJugador other = (	RecursJugador) obj;
		return Objects.equals(id, other.id) && Objects.equals(quantitat, other.quantitat) && tipus == other.tipus;
	}

	@Override
	public String toString() {
		return "RecursJugador [id=" + id + ", tipus=" + tipus + ", quantitat=" + quantitat + ", getId()=" + getId()
				+ ", getTipus()=" + getTipus() + ", getQuantitat()=" + getQuantitat() + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	
	
}
