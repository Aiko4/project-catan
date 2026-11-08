package patroDAO;

/*
 * Missatges de resultat de les operacions, centralitzats (com a Catan):
 *  - cada situació té el seu missatge
 *  - és fàcil comprovar el resultat: if (resultat.equals(Missatges.CATEGORIA_AFEGIDA)) ...
 */
public final class Missatges {

	private Missatges() {
	}

	// Error inesperat (excepció): s'ha fet rollback
	public static final String ERROR_BD = "Error de base de dades: no s'ha fet cap canvi";
}
