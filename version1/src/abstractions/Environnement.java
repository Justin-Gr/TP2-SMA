package abstractions;

import java.util.List;

public abstract class Environnement<E extends Environnement<E, A, P, X>, A extends Agent<E, A, P, X>, P extends Perception, X extends Action<E, A, P, X>> {
    private final List<A> agents;

    public Environnement(List<A> agents) {
        this.agents = agents;
    }

    public void ajouterAgent(A agent) {
        agents.add(agent);
    }

    public void jouerIteration() {
        agents.forEach(Agent::jouer);
    }

    public boolean stable() {
        return agents.stream().allMatch(Agent::isSatisfait);
    }

    public abstract P perception(A agent);

    public void realisationAction(A agent, X action) {
        action.effectuer(self(), agent);
    }

    public abstract E self();
}

