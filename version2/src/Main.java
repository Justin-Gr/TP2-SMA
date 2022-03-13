import implementations.AgentTrieur;
import implementations.EnvironnementGrille;
import implementations.Objet;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        int N = 50;
        int M = 50;

        Map<Objet, Integer> nombreObjetsParType = new HashMap<>();
        nombreObjetsParType.put(Objet.A, 200);
        nombreObjetsParType.put(Objet.B, 200);
        nombreObjetsParType.put(Objet.C, 200);

        int nombreAgents = 20;
        int pas = 1;
        int tailleMemoire = 20;
        double kPlus = 0.1;
        double kMoins = 0.3;
        double tauxErreur = 0.0;

        int nombreIterations = 100000;

        EnvironnementGrille environnement = new EnvironnementGrille(N, M, nombreObjetsParType);

        for (int i = 0; i < nombreAgents; i++) {
            AgentTrieur agent = new AgentTrieur(environnement, pas, tailleMemoire, kPlus, kMoins, tauxErreur);
            environnement.ajouterAgent(agent);
        }

        System.out.println("Début de la simulation\n");
        System.out.println(environnement);

        for (int i = 1; i <= nombreIterations; i++) {
            environnement.jouerIteration();

            if (i % 10000 == 0) {
                System.out.printf("Itération %s\n", i);
                // System.out.printf("\n%s\n", environnement);
            }
        }

        System.out.printf("\n%s\n", environnement);
        System.out.println("Fin de la simulation");
    }
}
