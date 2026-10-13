import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CIUTAT")
public class Ciutat extends Assentament {

}
