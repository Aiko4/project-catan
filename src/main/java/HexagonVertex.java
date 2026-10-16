import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="hexagon_vertex")
public class HexagonVertex {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vertex",nullable=false,unique=true)
	private Integer id;

	@NotNull
	@Column(name="posicio",nullable=false)
	@Min(0)
	@Max(5)
	private Integer posicio;
}
	