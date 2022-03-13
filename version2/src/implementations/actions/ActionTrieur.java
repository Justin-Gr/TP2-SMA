package implementations.actions;

import abstractions.Action;
import implementations.EnvironnementGrille;
import implementations.AgentTrieur;
import implementations.PerceptionTrieur;

public abstract class ActionTrieur implements Action<EnvironnementGrille, AgentTrieur, PerceptionTrieur, ActionTrieur> {

    @Override
    public abstract void effectuer(EnvironnementGrille environnement, AgentTrieur agent);
}
