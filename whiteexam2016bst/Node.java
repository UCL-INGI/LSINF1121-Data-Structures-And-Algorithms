interface Node {
    /**
     * @return la valeur contenue dans ce noeud
     */
    int getValue();

    /**
     * @return Le noeud situe a gauche (dont la valeur est < que la valeur actuelle) s'il existe, null sinon
     */
    Node getLeft();

    /**
     * @return Le noeud situe a droite (dont la valeur est > que la valeur actuelle) s'il existe, null sinon
     */
    Node getRight();
}
