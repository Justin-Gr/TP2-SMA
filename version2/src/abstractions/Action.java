package abstractions;

public interface Action<E extends Environnement<E, A, P, X>, A extends Agent<E, A, P, X>, P extends Perception, X extends Action<E, A, P, X>> {
    void effectuer(E environnement, A agent);
}
