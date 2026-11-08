package classesCatan;
import enums.TipusCarta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name="carta_desenvolupament")
public class CartaDesenvolupament {

	//Atributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipusCarta", nullable = false, length = 20)
	private TipusCarta tipus;
	
	@Column(name = "usada", nullable = false)
	private boolean usada;
	
	@Column(name = "rondaCompra")
	private Integer rondaCompra;

	//Relacions
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partida_id", nullable = false)
	private Partida partida;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jugador_id")
	private Jugador jugador;
	
	
	// constructor buit hibernate
	public CartaDesenvolupament() {		
	}
	
	public CartaDesenvolupament(TipusCarta tipus, boolean usada, Integer rondaCompra) {
		this.tipus = tipus;
		this.usada = usada;
		this.rondaCompra = rondaCompra;
	}

	//Get Set
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipusCarta getTipus() {
		return tipus;
	}

	public void setTipus(TipusCarta tipus) {
		this.tipus = tipus;
	}

	public boolean isUsada() {
		return usada;
	}

	public void setUsada(boolean usada) {
		this.usada = usada;
	}

	public Integer getRondaCompra() {
		return rondaCompra;
	}

	public void setRondaCompra(Integer rondaCompra) {
		this.rondaCompra = rondaCompra;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
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
		CartaDesenvolupament that = (CartaDesenvolupament) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "CartaDesenvolupament [id=" + id + ", tipus=" + tipus + ", usada=" + usada + ", rondaCompra="
				+ rondaCompra + "]";
	}
}
