import implementations.AgentTrieur;
import implementations.EnvironnementGrille;
import implementations.Objet;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        //==============================VARIABLES ENVIRONNEMENT==============================

        int N = 50;
        int M = 50;
        int nA = 200;
        int nB = 200;
        int nC = 200;
        double constanteEvaporation = 0.5;

        Map<Objet, Integer> nombreObjetsParType = new HashMap<>() {{
            put(Objet.A, nA);
            put(Objet.B, nB);
            put(Objet.C, nC);
        }};

        //==============================VARIABLES AGENTS==============================

        int nombreAgents = 20;
        int pas = 1;
        int tailleMemoire = 20;
        double kPlus = 0.1;
        double kMoins = 0.3;
        double tauxErreur = 0.0;
        double forceSignal = 4;
        int distanceSignal = 2;
        int attenteMax = 10;

        //==============================INIT SIMULATION==============================

        int nombreIterations = 100000;

        EnvironnementGrille environnement = new EnvironnementGrille(N, M, nombreObjetsParType, constanteEvaporation);

        for (int i = 0; i < nombreAgents; i++) {
            AgentTrieur agent = new AgentTrieur(environnement, pas, tailleMemoire, kPlus, kMoins,
                    tauxErreur, forceSignal, distanceSignal, attenteMax);
            environnement.ajouterAgent(agent);
        }

        //==============================LANCEMENT SIMULATION==============================

        System.out.println("Début de la simulation\n");
        System.out.println(environnement);

        for (int i = 1; i <= nombreIterations; i++) {
            environnement.jouerIteration();

            if (i % 10000 == 0) {
                System.out.printf("Itération %s\n", i);
                System.out.printf("\n%s\n", environnement);
            }
        }

        System.out.println("Fin de la simulation");
        System.out.printf("\n%s\n", environnement);
    }
}
