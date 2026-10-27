import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import enums.TipusRecurs;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import enums.ColorJugador;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="jugador")
public class Jugador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_jugador",nullable=false,unique=true)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "partida_id", nullable = false)
	private Partida partida_id;
	
	@NotNull
	@Column(name="nom_jugador",nullable=false, length = 50)
	private String nom;
	
	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private ArrayList<Construccio> construccionsJugador;

	@OneToMany(mappedBy = "jugador",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	@MapKey(name = "tipus")
	@MapKeyEnumerated(EnumType.STRING)
	private HashMap<TipusRecurs, RecursJugador> inventari;
	
	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL)
    private ArrayList<CartaDesenvolupament> ma = new ArrayList<>();
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="color_jugador",nullable=false, length = 10)
	private ColorJugador colorJugador;
	
	@NotNull
	@Column(name="ordre_torn",nullable=false)
	private Integer ordreTorn;
	
	@NotNull
	@Column(name="cavallers_jugats",nullable=false)
	@ColumnDefault("0")
	private Integer cavallersJugats = 0;
	
	
	// constructor buit per a Hibernate
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

	@Override
	public int hashCode() {
		return Objects.hash(cavallersJugats, colorJugador, id, nom, ordreTorn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return Objects.equals(cavallersJugats, other.cavallersJugats) && colorJugador == other.colorJugador
				&& Objects.equals(id, other.id) && Objects.equals(nom, other.nom)
				&& Objects.equals(ordreTorn, other.ordreTorn);
	}

	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nom=" + nom + ", colorJugador=" + colorJugador + ", ordreTorn=" + ordreTorn
				+ ", cavallersJugats=" + cavallersJugats + "]";
	}
	
	
	
}
 