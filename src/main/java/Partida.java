import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import enums.ColorJugador;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;




@Entity
@Table(name="partida")
public class Partida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_partida",nullable=false,unique=true)
	private Integer id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="estat_partida",nullable=false)
	private EstatPartida estatPartida;
	
	@NotNull(message = "La data d'inici ha ser obligatoriament")
	@CreationTimestamp
	@PastOrPresent(message = "La data d'inici no pot ser futura.")
    @Column(name = "data_inici", updatable = false)
	private LocalDate dataInici;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jugadorGuanyador_id", unique = true)
	private Jugador guanyador;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jugadorActiu_id", unique = true)
	private Jugador actiu;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hexagonLladre_id", unique = true)
	private Hexagon lladre;
	
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private HashSet<Jugador> jugadors;
	
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private HashSet<Hexagon> hexagons;
	
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private HashSet<Vertex> vertexs;
	
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private HashSet<Aresta> arestes;

	//Verificar si es poden repetir cartes per saber si és ArrayList o HashSet
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private ArrayList<CartaDesenvolupament> pilaCartes;
	
	@NotNull
	@Column(name="ronda",nullable=false)
	private Integer ronda;
	
	@NotNull
	@Column(name="ultima_tirada",nullable=false)
	private Integer ultimaTirada;
	
	@NotNull
	@Column(name="punts_per_guanyar",nullable=false)
	private Integer puntsPerGuanyar;
	
	@NotNull
	@Column(name="daus_tirats",nullable=false)
	private boolean dausTirats;
	
	@NotNull
	@Column(name="lladre_pendent",nullable=false)
	private boolean lladrePendent;
	
	
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
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(dataInici, Boolean.valueOf(dausTirats), estatPartida, id, Boolean.valueOf(lladrePendent),
				puntsPerGuanyar, ronda, ultimaTirada);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partida other = (Partida) obj;
		return Objects.equals(dataInici, other.dataInici) && dausTirats == other.dausTirats
				&& estatPartida == other.estatPartida && Objects.equals(id, other.id)
				&& lladrePendent == other.lladrePendent && Objects.equals(puntsPerGuanyar, other.puntsPerGuanyar)
				&& Objects.equals(ronda, other.ronda) && Objects.equals(ultimaTirada, other.ultimaTirada);
	}

	@Override
	public String toString() {
		return "Partida [id=" + id + ", estatPartida=" + estatPartida + ", dataInici=" + dataInici + ", ronda=" + ronda
				+ ", ultimaTirada=" + ultimaTirada + ", puntsPerGuanyar=" + puntsPerGuanyar + ", dausTirats="
				+ dausTirats + ", lladrePendent=" + lladrePendent + "]";
	}
	
	

}
