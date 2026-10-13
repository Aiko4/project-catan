import enums.TipusCarta;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="carta_desenvolupament")
public class CartaDesenvolupament {
	
	private int id;
	private TipusCarta tipus;
	private boolean usada;
	private Integer rondaCompra;
	
}
