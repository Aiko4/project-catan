package patroDAO;

/*
 * Missatges de resultat de les operacions, centralitzats (com a Catan):
 *  - cada situació té el seu missatge
 *  - és fàcil comprovar el resultat: if (resultat.equals(Missatges.CATEGORIA_AFEGIDA)) ...
 */
public final class Missatges {

	private Missatges() {
	}

	// Correctes
	public static final String PARTIDA_CREADA = "Partida creada";
	public static final String LLANCAMENT_AFEGIT = "Llançament afegit";
	public static final String VIDEOJOC_ESBORRAT = "Videojoc esborrat";

	// Errors de dades
	public static final String VIDEOJOC_NO_EXISTEIX = "No existeix el videojoc";
	public static final String ESTUDI_NO_EXISTEIX = "No existeix l'estudi";
	public static final String CATEGORIA_NO_EXISTEIX = "No existeix la categoria";
	public static final String PLATAFORMA_NO_EXISTEIX = "No existeix la plataforma";
	public static final String VIDEOJOC_REPETIT = "Ja existeix un videojoc amb aquest nom";
	public static final String JA_TE_CATEGORIA = "El videojoc ja té aquesta categoria";
	public static final String JA_LLANCAT = "El videojoc ja té un llançament en aquesta plataforma";
	public static final String PREU_INVALID = "El preu ha de ser més gran que 0";

	// Error inesperat (excepció): s'ha fet rollback
	public static final String ERROR_BD = "Error de base de dades: no s'ha fet cap canvi";
}
