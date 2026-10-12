import org.hibernate.annotations.ColumnDefault;

import enums.ColorJugador;
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
@Table(name="jugador")
public class Jugador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_jugador",nullable=false,unique=true)
	private Integer id;
	
	@NotNull
	@Column(name="nom_jugador",nullable=false, length = 50)
	private String nom;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="color_jugador",nullable=false)
	private ColorJugador colorJugador;
	
	
	@NotNull
	@Column(name="ordre_torn",nullable=false)
	private Integer ordreTorn;
	
	
	@NotNull
	@Column(name="cavallers_jugats",nullable=false)
	@ColumnDefault("0")
	private Integer cavallersJugats = 0;
	
	public Jugador() {
		
	}
	
	public Jugador(String nom, ColorJugador colorJugador, Integer ordreTorn) {
		this.nom = nom;
		this.colorJugador = colorJugador;
		this.ordreTorn = ordreTorn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ColorJugador getColorJugador() {
		return colorJugador;
	}

	public void setColorJugador(ColorJugador colorJugador) {
		this.colorJugador = colorJugador;
	}

	public Integer getOrdreTorn() {
		return ordreTorn;
	}

	public void setOrdreTorn(Integer ordreTorn) {
		this.ordreTorn = ordreTorn;
	}

	public Integer getCavallersJugats() {
		return cavallersJugats;
	}

	public void setCavallersJugats(Integer cavallersJugats) {
		this.cavallersJugats = cavallersJugats;
	}
	
	
	
}
 