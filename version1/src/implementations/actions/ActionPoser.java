package implementations.actions;

import implementations.AgentTrieur;
import implementations.CaseGrille;
import implementations.EnvironnementGrille;

public class ActionPoser extends ActionTrieur {

    /**
     * Permet à un agent de poser l'objet tenu sur sa case
     * Ne fait rien si aucun objet n'est tenu
     *
     * @param environnement l'environnement exécutant l'action
     * @param agent l'agent qui a demandé l'action
     */
    @Override
    public void effectuer(EnvironnementGrille environnement, AgentTrieur agent) {
        CaseGrille caseCourante = environnement.caseCouranteAgent(agent);

        if (caseCourante.getObjet() == null) {
            caseCourante.setObjet(agent.getObjetTenu());
            agent.setObjetTenu(null);
        }
    }
}
