/**
 * Un "FormalExpressionTree" est un arbre permettant de m�moriser
 * et de manipuler une expression analytique.
 *
 * Une classe impl�mentant cette interface doit disposer d'un CONSTRUCTEUR
 * prenant comme argument une cha�ne de caract�res (String) et construisant
 * l'arbre associ�.
 * Cette cha�ne est suppos�e correspondre � une expression analytique
 * syntaxiquement correcte et compl�tement parenth�s�e.
 * Lorsque cette pr�condition n'est pas v�rifi�e, une  ParseException (classe � impl�menter) doit �tre lanc�e.
 * Un CONSTRUCTEUR sans argument ou avec l'expression vide "" comme argument
 * permet de construire un arbre �l�mentaire correspondant � l'expression "0"
 */
public interface FormalExpressionTree {
  /**
   * Cette m�thode renvoie une cha�ne de caract�res correspondant �
   * l'expression analytique repr�sent�e dans l'arbre.
   *
   * @pre  this repr�sente une expression analytique syntaxiquement correcte
   * @post une cha�ne de caract�res, correspondant � l'expression analytique
   *       compl�tement parenth�s�e repr�sent�e par this, est renvoy�e.
   */
  public String toString();

  /**
   * Cette m�thode calcule le nouvel arbre correspondant � la d�riv�e formelle
   * de l'arbre courant. L'arbre courant (this) n'est pas modifi�.
   *
   * @pre   this repr�sente une expression analytique syntaxiquement correcte.
   * @post  Une r�f�rence � un nouvel arbre repr�sentant la d�riv�e formelle
   *        de this est renvoy�e.
   */
  public FormalExpressionTree derive();
}
