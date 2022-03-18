package implementations.actions;

import implementations.AgentTrieur;
import implementations.CaseGrille;
import implementations.EnvironnementGrille;

public class ActionPrendre extends ActionTrieur {

    /**
     * Permet à un agent de récupérer l'objet présent sur sa case
     * Ne fait rien si aucun objet n'est présent
     *
     * @param environnement l'environnement exécutant l'action
     * @param agent l'agent qui a demandé l'action
     */
    @Override
    public void effectuer(EnvironnementGrille environnement, AgentTrieur agent) {
        CaseGrille caseCourante = environnement.caseCouranteAgent(agent);

        if (caseCourante.getObjet() != null) {
            agent.setObjetTenu(caseCourante.getObjet());
            caseCourante.setObjet(null);
        }
    }
}
