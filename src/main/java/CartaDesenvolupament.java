import com.google.protobuf.Generated;

import enums.TipusCarta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="carta_desenvolupament")
public class CartaDesenvolupament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	
	private TipusCarta tipus;
	private boolean usada;
	private Integer rondaCompra;
	
	
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
