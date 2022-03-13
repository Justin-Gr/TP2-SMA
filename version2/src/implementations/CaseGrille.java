package implementations;

public class CaseGrille {

    private AgentTrieur agent;
    private Objet objet;

    public CaseGrille() {
    }

    public AgentTrieur getAgent() {
        return agent;
    }

    public Objet getObjet() {
        return objet;
    }

    public void setAgent(AgentTrieur agent) {
        this.agent = agent;
    }

    public void setObjet(Objet objet) {
        this.objet = objet;
    }
}
