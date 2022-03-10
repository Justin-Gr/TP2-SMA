package implementations.actions;

import implementations.AgentTrieur;
import implementations.CaseGrille;
import implementations.EnvironnementGrille;

public class ActionDeplacer extends ActionTrieur {

    private final CaseGrille caseDestination;

    public ActionDeplacer(CaseGrille caseDestination) {
        this.caseDestination = caseDestination;
    }

    @Override
    public void effectuer(EnvironnementGrille environnement, AgentTrieur agent) {
        CaseGrille caseCourante = environnement.caseCouranteAgent(agent);

        if (caseDestination.getAgent() == null) {
            caseDestination.setAgent(agent);
            caseCourante.setAgent(null);
        }
    }
}
