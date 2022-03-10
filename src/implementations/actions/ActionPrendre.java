package implementations.actions;

import implementations.AgentTrieur;
import implementations.CaseGrille;
import implementations.EnvironnementGrille;

public class ActionPrendre extends ActionTrieur {

    @Override
    public void effectuer(EnvironnementGrille environnement, AgentTrieur agent) {
        CaseGrille caseCourante = environnement.caseCouranteAgent(agent);

        if (caseCourante.getObjet() != null) {
            agent.setObjetTenu(caseCourante.getObjet());
            caseCourante.setObjet(null);
        }
    }
}
