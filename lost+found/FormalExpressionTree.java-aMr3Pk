/** MODIFICATION HERE : add toStringJs
 * Un "FormalExpressionTree" est un arbre permettant de memoriser
 * et de manipuler une expression analytique.
 *
 * Une classe implementant cette interface doit disposer d'un CONSTRUCTEUR
 * prenant comme argument une chaine de caracteres (String) et construisant
 * l'arbre associe.
 * Cette chaine est supposee correspondre a une expression analytique
 * syntaxiquement correcte et completement parenthesee.
 * Lorsque cette precondition n'est pas verifiee, une ParseException (classe a implementer) doit etre lancee.
 * Un CONSTRUCTEUR sans argument ou avec l'expression vide "" comme argument
 * permet de construire un arbre elementaire correspondant a l'expression "0"
 */
public interface FormalExpressionTree {
  /**
   * Cette methode renvoie une chaine de caracteres correspondant a
   * l'expression analytique representee dans l'arbre.
   *
   * @pre  this represente une expression analytique syntaxiquement correcte
   * @post une chaine de caracteres, correspondant a l'expression analytique
   *       completement parenthesee representee par this, est renvoyee.
   */
  public String toString();
  
  public String toStringJs();

  /**
   * Cette methode calcule le nouvel arbre correspondant a la derivee formelle
   * de l'arbre courant. L'arbre courant (this) n'est pas modifie.
   *
   * @pre   this represente une expression analytique syntaxiquement correcte.
   * @post  Une reference a un nouvel arbre representant la derivee formelle
   *        de this est renvoyee.
   */
  public FormalExpressionTree derive();
}
