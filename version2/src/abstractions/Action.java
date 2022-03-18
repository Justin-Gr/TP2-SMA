package abstractions;

public interface Action<E extends Environnement<E, A, P, X>, A extends Agent<E, A, P, X>, P extends Perception, X extends Action<E, A, P, X>> {

    /**
     * Permet d'effectuer une action dans l'environnement selon l'agent qui a demandé l'action
     *
     * @param environnement l'environnement exécutant l'action
     * @param agent l'agent qui a demandé l'action
     */
    void effectuer(E environnement, A agent);
}
