package abstractions;

public abstract class Agent<E extends Environnement<E, A, P, X>, A extends Agent<E, A, P, X>, P extends Perception, X extends Action<E, A, P, X>> {

    private final E environnement;

    public Agent(E environnement) {
        this.environnement = environnement;
    }

    public void jouer() {
        P perception = this.percevoir();
        this.realiserAction(perception);
    }

    public abstract P percevoir();
    public abstract void realiserAction(P perception);
    public abstract boolean isSatisfait();

    public E getEnvironnement() {
        return environnement;
    }
}

