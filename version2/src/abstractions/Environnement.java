package abstractions;

import java.util.List;

public abstract class Environnement<E extends Environnement<E, A, P, X>, A extends Agent<E, A, P, X>, P extends Perception, X extends Action<E, A, P, X>> {
    protected final List<A> agents;

    public Environnement(List<A> agents) {
        this.agents = agents;
    }

    /**
     * Permet d'ajouter un agent à l'environnement et de le positionner aléatoirement sur la grille
     *
     * @param agent l'agent à ajouter
     */
    public void ajouterAgent(A agent) {
        agents.add(agent);
    }

    /**
     * Lance une itération de la simulation, càd que tous les agents jouent un tour
     */
    public void jouerIteration() {
        agents.forEach(Agent::jouer);
    }

    /**
     * Indique si l'environnement est stable, càd si tous les agents sont satisfaits
     *
     * @return true si tous les agents sont satisfaits, false sinon
     */
    public boolean stable() {
        return agents.stream().allMatch(Agent::isSatisfait);
    }

    /**
     * Calcule la perception d'un agent
     *
     * @param agent l'agent dont on veut obtenir la perception
     * @return la perception de l'agent
     */
    public abstract P perception(A agent);

    /**
     * Exécute l'action demandée par l'agent
     *
     * @param agent l'agent demandant l'action
     * @param action l'action à exécuter
     */
    public void realisationAction(A agent, X action) {
        action.effectuer(self(), agent);
    }

    public abstract E self();
}

