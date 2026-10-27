import enums.TipusCarta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="carta_desenvolupament")
public class CartaDesenvolupament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	@NotNull
	@Column(name = "tipusCarta", nullable = false, length = 20)
	private TipusCarta tipus;
	
	@NotNull
	@Column(name = "usada", nullable = false)
	private boolean usada;
	
	@NotNull
	@Column(name = "rondaCompra", nullable = false)
	private Integer rondaCompra;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "partida_id", nullable = false)
	private Partida partida;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "jugador_id", nullable = false)
	private Jugador jugador;
	
	
	// constructor buit hibernate
	public CartaDesenvolupament() {		
	}
	
	public CartaDesenvolupament(Integer id, TipusCarta tipus, boolean usada, Integer rondaCompra) {
		this.id = id;
		this.tipus = tipus;
		this.usada = usada;
		this.rondaCompra = rondaCompra;
	}

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

	@Override
	public String toString() {
		return "CartaDesenvolupament [id=" + id + ", tipus=" + tipus + ", usada=" + usada + ", rondaCompra="
				+ rondaCompra + "]";
	}
}
