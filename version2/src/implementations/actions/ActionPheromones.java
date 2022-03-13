package implementations.actions;

import implementations.AgentTrieur;
import implementations.CaseGrille;
import implementations.EnvironnementGrille;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class ActionPheromones extends ActionTrieur {

    @Override
    public void effectuer(EnvironnementGrille environnement, AgentTrieur agent) {
        double S = agent.getForceSignal();
        int dS = agent.getDistanceSignal();
        if (dS <= 0) return; // Si dS est nul, on n'émet pas de phéromones

        int[] coordonneesAgent = environnement.coordonneesAgent(agent);
        int x = coordonneesAgent[0], y = coordonneesAgent[1];

        // Les phéromones sont émis dans un carré autour de l'agent
        int longueur = dS*2 + 1;
        for (int i = -longueur/2; i <= longueur/2; i++) { // les divisions d'entiers sont volontaires
            for (int j = -longueur/2; j <= longueur/2; j++) {
                CaseGrille caseGrille = environnement.getCase(x + j, y + i);
                if (caseGrille != null) {
                    int distance = max(abs(i), abs(j));
                    double pheromones = S - distance * S/dS;
                    // Nous avons choisi de ne pas additioner les phéromones,
                    // mais plutôt de ne garder que la plus grande quantité sur la case ciblée
                    if (pheromones > caseGrille.getPheromones()) {
                        caseGrille.setPheromones(pheromones);
                    }
                }
            }
        }
    }
}
