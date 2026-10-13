import java.time.LocalDate;
import java.util.Objects;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import enums.ColorJugador;
import enums.EstatPartida;
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
	
	@NotNull
	@CreationTimestamp
    @Column(name = "data_inici", updatable = false)
	private LocalDate dataInici;
	
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

}
