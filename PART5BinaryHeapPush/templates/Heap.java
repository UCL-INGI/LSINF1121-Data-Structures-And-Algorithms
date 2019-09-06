package templates;

/**
 * Un heap binaire, dont le contenu est place dans le tableau `content`.
 *
 * Le tableau `contenu` represente un arbre, suivant la methode vue au cours:
 * Le noeud n°i de l'arbre a pour enfant les indices 2*i et 2*I+1.
 *
 * Notez bien:
 * - Ce heap utilise le 1-indexing, c'est a dire que le premier index du tableau est 1. Ceci est cense faciliter vos
 *   calculs. La position 0 de `content` doit donc toujours rester vide.
 * - Vous pouvez utiliser la fonction increaseSize() pour doubler la taille de `content`, si besoin.
 * - /!\ important! Les tests fournits dans ce projet assument que vous utilisez l'algorithme vu au cours. Si vous
 *   n'utilisez pas l'algorithme vu au cours, les tests retourneront toujours faux.
 *
 *   INGInious va lui verifier vos reponses en se basant sur les proprietes de base d'un heap, et donc n'est pas
 *   sensible a l'algorithme que vous choississez.
 *
 *   Vous etes dont libre de choisir l'algorithme de votre choix.
 *
 * - La complexite attendue est O(log n) par insertion dans la heap. Vous recevrez la moitie des points si votre
 *   algorithme est correct (s'il produit un heap valide) et l'autre moitie des points si il respecte la complexite.
 */
public class Heap {
    protected int[] content;
    protected int size;

    public Heap(int initSize) {
        size = 0;
        content = new int[initSize];
    }

    /**
     * Doubles the size of the inner storage array
     */
    protected void increaseSize() {
        int[] newContent = new int[content.length*2];
        System.arraycopy(content, 0, newContent, 0, content.length);
        content = newContent;
    }

    /**
     * Returns the content of the inner array
     */
    public int[] getContent() {
        return content;
    }

    public int getSize() {
        return size;
    }

@        @second@@
}
