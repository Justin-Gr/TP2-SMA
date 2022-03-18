package implementations.actions;

import implementations.AgentTrieur;
import implementations.CaseGrille;
import implementations.EnvironnementGrille;

public class ActionDeplacer extends ActionTrieur {

    private final CaseGrille caseDestination;

    /**
     * Initialise la stratégie avec la case de destination souhaitée
     *
     * @param caseDestination la case de destination souhaitée
     */
    public ActionDeplacer(CaseGrille caseDestination) {
        this.caseDestination = caseDestination;
    }

    /**
     * Permet à l'agent de se déplacer sur la case souhaitée
     *
     * @param environnement l'environnement exécutant l'action
     * @param agent l'agent qui a demandé l'action
     */
    @Override
    public void effectuer(EnvironnementGrille environnement, AgentTrieur agent) {
        CaseGrille caseCourante = environnement.caseCouranteAgent(agent);

        if (agent.getObjetTenu() != null) {
            // Si l'agent porte un objet lourd et qu'il est seul sur la case, il ne peut pas se déplacer
            if (agent.getObjetTenu().isLourd() && caseCourante.getAgents().size() < 2) {
                return;
            }
        }

        // Déplacement de l'agent
        caseDestination.addAgent(agent);
        caseCourante.removeAgent(agent);
    }
}
