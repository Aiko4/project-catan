package classesCatan;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import enums.EstatPartida;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


@Entity
@Table(name="partida")
@NamedQuery(
		name = "Partida.infoPartida",
		query = "SELECT p " + 
				"FROM Partida " + 
				"WHERE p.id = :id"
)
public class Partida {

	//Atributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name="estat_partida",nullable=false, length=20)
	private EstatPartida estatPartida;

	@CreationTimestamp
	@PastOrPresent(message = "La data d'inici no pot ser futura.")
	@Column(name = "data_inici", updatable = false, nullable=false)
	private LocalDate dataInici;

	@Column(name="ronda",nullable=false)
	private Integer ronda;

	@Column(name="ultima_tirada",nullable=false)
	private Integer ultimaTirada;

	@Column(name="punts_per_guanyar",nullable=false)
	private Integer puntsPerGuanyar;

	@Column(name="daus_tirats",nullable=false)
	private boolean dausTirats;

	@Column(name="lladre_pendent",nullable=false)
	private boolean lladrePendent;

	//Relacions
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jugadorGuanyador_id", unique = true, nullable=true)
	private Jugador guanyador;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jugadorActiu_id", unique = true, nullable=true)
	private Jugador actiu;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hexagonLladre_id", unique = true, nullable=true)
	private Hexagon lladre;

	@OneToMany(mappedBy = "partida_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Jugador> jugadors = new HashSet<>();

	@OneToMany(mappedBy = "partida_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Hexagon> hexagons = new HashSet<>();

	@OneToMany(mappedBy = "partida_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Vertex> vertexs = new HashSet<>();

	@OneToMany(mappedBy = "partida_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Aresta> arestes = new HashSet<>();

	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CartaDesenvolupament> pilaCartes = new ArrayList<>();

	//Constructors
	public Partida() {

	}

	public Partida(EstatPartida estatPartida,LocalDate dataInici, Integer ronda, Integer ultimaTirada, Integer puntsPerGuanyar, boolean dausTirats, boolean lladrePendent) {
		this.estatPartida=estatPartida;
		this.dataInici=dataInici;
		this.ronda=ronda;
		this.ultimaTirada=ultimaTirada;
		this.puntsPerGuanyar=puntsPerGuanyar;
		this.dausTirats=dausTirats;
		this.lladrePendent=lladrePendent;
		this.jugadors=new HashSet<>();
	}

	//Get Set
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstatPartida getEstatPartida() {
		return estatPartida;
	}

	public void setEstatPartida(EstatPartida estatPartida) {
		this.estatPartida = estatPartida;
	}

	public LocalDate getDataInici() {
		return dataInici;
	}

	public void setDataInici(LocalDate dataInici) {
		this.dataInici = dataInici;
	}

	public Integer getRonda() {
		return ronda;
	}

	public void setRonda(Integer ronda) {
		this.ronda = ronda;
	}

	public Integer getUltimaTirada() {
		return ultimaTirada;
	}

	public void setUltimaTirada(Integer ultimaTirada) {
		this.ultimaTirada = ultimaTirada;
	}

	public Integer getPuntsPerGuanyar() {
		return puntsPerGuanyar;
	}

	public void setPuntsPerGuanyar(Integer puntsPerGuanyar) {
		this.puntsPerGuanyar = puntsPerGuanyar;
	}

	public boolean isDausTirats() {
		return dausTirats;
	}

	public void setDausTirats(boolean dausTirats) {
		this.dausTirats = dausTirats;
	}

	public boolean isLladrePendent() {
		return lladrePendent;
	}

	public void setLladrePendent(boolean lladrePendent) {
		this.lladrePendent = lladrePendent;
	}

	public Jugador getGuanyador() {
		return guanyador;
	}

	public void setGuanyador(Jugador guanyador) {
		this.guanyador = guanyador;
	}

	public Jugador getActiu() {
		return actiu;
	}

	public void setActiu(Jugador actiu) {
		this.actiu = actiu;
	}

	public Hexagon getLladre() {
		return lladre;
	}

	public void setLladre(Hexagon lladre) {
		this.lladre = lladre;
	}

	public Set<Jugador> getJugadors() {
		return jugadors;
	}

	public void setJugadors(Set<Jugador> jugadors) {
		this.jugadors = jugadors;
	}

	public Set<Hexagon> getHexagons() {
		return hexagons;
	}

	public void setHexagons(Set<Hexagon> hexagons) {
		this.hexagons = hexagons;
	}

	public Set<Vertex> getVertexs() {
		return vertexs;
	}

	public void setVertexs(Set<Vertex> vertexs) {
		this.vertexs = vertexs;
	}

	public Set<Aresta> getArestes() {
		return arestes;
	}

	public void setArestes(Set<Aresta> arestes) {
		this.arestes = arestes;
	}

	public List<CartaDesenvolupament> getPilaCartes() {
		return pilaCartes;
	}

	public void setPilaCartes(List<CartaDesenvolupament> pilaCartes) {
		this.pilaCartes = pilaCartes;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Partida partida = (Partida) o;
		return Objects.equals(id, partida.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Partida [id=" + id + ", estatPartida=" + estatPartida + ", dataInici=" + dataInici + ", ronda=" + ronda
				+ ", ultimaTirada=" + ultimaTirada + ", puntsPerGuanyar=" + puntsPerGuanyar + ", dausTirats="
				+ dausTirats + ", lladrePendent=" + lladrePendent + "]";
	}



}