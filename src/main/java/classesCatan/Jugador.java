package classesCatan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	//private boolean isGuanyador;

	//private boolean isActiu;

	@NotNull
	@Column(name="nom_jugador",nullable=false, length = 50)
	private String nom;

	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Construccio> construccionsJugador = new ArrayList<>();

	@OneToMany(mappedBy = "jugador",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	@MapKey(name = "tipus")
	@MapKeyEnumerated(EnumType.STRING)
	private Map<TipusRecurs, RecursJugador> inventari = new HashMap<>();

	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL)
	private List<CartaDesenvolupament> ma = new ArrayList<>();

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="color_jugador",nullable=false, length = 15)
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

	public Partida getPartida_id() {
		return partida_id;
	}

	public void setPartida_id(Partida partida_id) {
		this.partida_id = partida_id;
	}

	public List<Construccio> getConstruccionsJugador() {
		return construccionsJugador;
	}

	public void setConstruccionsJugador(List<Construccio> construccionsJugador) {
		this.construccionsJugador = construccionsJugador;
	}

	public Map<TipusRecurs, RecursJugador> getInventari() {
		return inventari;
	}

	public void setInventari(Map<TipusRecurs, RecursJugador> inventari) {
		this.inventari = inventari;
	}

	public List<CartaDesenvolupament> getMa() {
		return ma;
	}

	public void setMa(List<CartaDesenvolupament> ma) {
		this.ma = ma;
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