package implementations.actions;

import abstractions.Action;
import implementations.EnvironnementGrille;
import implementations.AgentTrieur;
import implementations.PerceptionTrieur;

public abstract class ActionTrieur implements Action<EnvironnementGrille, AgentTrieur, PerceptionTrieur, ActionTrieur> {

    /**
     * Permet d'effectuer une action dans l'environnement selon l'agent qui a demandé l'action
     *
     * @param environnement l'environnement exécutant l'action
     * @param agent l'agent qui a demandé l'action
     */
    @Override
    public abstract void effectuer(EnvironnementGrille environnement, AgentTrieur agent);
}
