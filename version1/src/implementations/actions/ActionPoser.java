package implementations.actions;

import implementations.AgentTrieur;
import implementations.CaseGrille;
import implementations.EnvironnementGrille;

public class ActionPoser extends ActionTrieur {

    @Override
    public void effectuer(EnvironnementGrille environnement, AgentTrieur agent) {
        CaseGrille caseCourante = environnement.caseCouranteAgent(agent);

        if (caseCourante.getObjet() == null) {
            caseCourante.setObjet(agent.getObjetTenu());
            agent.setObjetTenu(null);
        }
    }
}
