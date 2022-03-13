package implementations;

import java.util.ArrayList;
import java.util.List;

public class CaseGrille {

    private final List<AgentTrieur> agents;
    private Objet objet;
    private double pheromones;

    public CaseGrille() {
        this.agents = new ArrayList<>();
    }

    public void addAgent(AgentTrieur agent) {
        this.getAgents().add(agent);
    }

    public void removeAgent(AgentTrieur agent) {
        this.getAgents().remove(agent);
    }

    public boolean containsAgent(AgentTrieur agent) {
        return this.getAgents().contains(agent);
    }

    public AgentTrieur getAgent(int i) {
        return this.getAgents().get(i);
    }

    public List<AgentTrieur> getAgents() {
        return agents;
    }

    public Objet getObjet() {
        return objet;
    }

    public void setObjet(Objet objet) {
        this.objet = objet;
    }

    public double getPheromones() {
        return pheromones;
    }

    public void setPheromones(double pheromones) {
        this.pheromones = pheromones;
    }
}
