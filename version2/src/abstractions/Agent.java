package abstractions;

public abstract class Agent<E extends Environnement<E, A, P, X>, A extends Agent<E, A, P, X>, P extends Perception, X extends Action<E, A, P, X>> {

    private final E environnement;

    public Agent(E environnement) {
        this.environnement = environnement;
    }

    /**
     * Permet à l'agent de jouer un tour. L'agent demande à l'environnement sa perception du monde,
     * puis s'en sert pour prendre une décision et effectuer une action
     */
    final public void jouer() {
        P perception = this.percevoir();
        this.realiserAction(perception);
    }

    /**
     * Demande à l'environnement la perception de l'agent
     *
     * @return la perception reçue
     */
    public abstract P percevoir();

    /**
     * L'agent prend une décision pour réaliser une action en fonction de la perception donnée
     *
     * @param perception la perception à considérer
     */
    public abstract void realiserAction(P perception);

    /**
     * Indique si l'agent est dans un état qui le satisfait
     *
     * @return true si l'agent est satisfait, false sinon
     */
    public abstract boolean isSatisfait();

    public E getEnvironnement() {
        return environnement;
    }
}

