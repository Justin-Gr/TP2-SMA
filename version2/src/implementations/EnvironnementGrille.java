package implementations;

import abstractions.Agent;
import abstractions.Environnement;
import implementations.actions.ActionTrieur;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.max;

public class EnvironnementGrille extends Environnement<EnvironnementGrille, AgentTrieur, PerceptionTrieur, ActionTrieur> {

    private final int N;
    private final int M;
    private final double constanteEvaporation;

    private final Random random;

    private final CaseGrille[][] cases;

    public EnvironnementGrille(int N, int M, Map<Objet, Integer> nombreObjetsParType, double constanteEvaporation) {
        super(new ArrayList<>());
        this.random = new Random();

        this.N = N;
        this.M = M;
        this.constanteEvaporation = constanteEvaporation;

        this.cases = new CaseGrille[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                this.cases[i][j] = new CaseGrille();
            }
        }

        nombreObjetsParType.forEach((objet, nombreObjets) -> {
            int x, y;
            for (int i = 0; i < nombreObjets; i++) {
                do {
                    x = random.nextInt(M);
                    y = random.nextInt(N);
                } while(getCase(x, y).getObjet() != null);

                getCase(x, y).setObjet(objet);
            }
        });
    }

    /**
     * Permet d'ajouter un agent à l'environnement et de le positionner aléatoirement sur la grille
     *
     * @param agent l'agent à ajouter
     */
    @Override
    public void ajouterAgent(AgentTrieur agent) {
        super.ajouterAgent(agent);

        int x, y;
        do {
            x = random.nextInt(M);
            y = random.nextInt(N);
        } while(!getCase(x, y).getAgents().isEmpty());

        getCase(x, y).addAgent(agent);
    }

    /**
     * Permet de jouer une itération de la simulation, et évapore un certain montant des phéromones de la grille
     * à chaque début d'itération
     */
    @Override
    public void jouerIteration() {
        this.evaporerPheromones();
        super.agents.forEach(Agent::jouer);
    }

    /**
     * Calcule la perception d'un agent
     *
     * @param agent l'agent dont on veut obtenir la perception
     * @return la perception de l'agent
     */
    @Override
    public PerceptionTrieur perception(AgentTrieur agent) {
        // Détermination de la case courante
        int[] coordonneesAgent = this.coordonneesAgent(agent);
        int x = coordonneesAgent[0], y = coordonneesAgent[1];
        CaseGrille caseCourante = getCase(x, y);

        // Détermination des cases voisines
        List<CaseGrille> casesVoisines = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    CaseGrille caseVoisine = getCase(x + j*agent.getPas(), y + i*agent.getPas());
                    if (caseVoisine != null) {
                        casesVoisines.add(caseVoisine);
                    }
                }
            }
        }

        return new PerceptionTrieur(caseCourante, casesVoisines);
    }

    /**
     * Évapore les phéromones déposés dans la grille d'un certain montant
     */
    public void evaporerPheromones() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                double pheromones = this.getCase(j, i).getPheromones() - constanteEvaporation;
                this.getCase(j, i).setPheromones(max(pheromones, 0));
            }
        }
    }

    /**
     * Retourne les coordonnées X et Y d'un agent dans la grille
     *
     * @param agent l'agent dont on veut obtenir les coordonnées
     * @return les coordonnées de l'agent
     */
    public int[] coordonneesAgent(AgentTrieur agent) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                CaseGrille caseGrille = getCase(j, i);
                if (caseGrille.containsAgent(agent)) {
                    return new int[] {j, i};
                }
            }
        }
        return null;
    }

    /**
     * Retourne la case où se situe l'agent passé en paramètre
     *
     * @param agent l'agent en question
     * @return la case où se situe l'agent
     */
    public CaseGrille caseCouranteAgent(AgentTrieur agent) {
        int[] coordonneesAgent = this.coordonneesAgent(agent);
        return getCase(coordonneesAgent[0], coordonneesAgent[1]);
    }

    /**
     * Indique si les coordonnées passées en paramètres correspondent à une case de la grille
     *
     * @param x la coordonnées en X
     * @param y la coordonnées en Y
     * @return true si une case est présente, false sinon
     */
    public boolean casePresente(int x, int y) {
        return x >= 0 && x < M && y >= 0 && y < N;
    }

    /**
     * Retourne la case située aux coordonnées indiquées
     *
     * @param x la coordonnées en X
     * @param y la coordonnées en Y
     * @return la case correspondante
     */
    public CaseGrille getCase(int x, int y) {
        return casePresente(x, y) ? cases[y][x] : null;
    }

    @Override
    public EnvironnementGrille self() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                CaseGrille caseGrille = getCase(j, i);
                if (!caseGrille.getAgents().isEmpty()) {
                    stringBuilder.append("[");
                    if (caseGrille.getAgent(0).getObjetTenu() != null) {
                        stringBuilder.append(caseGrille.getAgent(0).getObjetTenu());
                    } else {
                        stringBuilder.append("_");
                    }
                    stringBuilder.append("]");
                } else if (caseGrille.getObjet() != null) {
                    stringBuilder.append(" ").append(caseGrille.getObjet()).append(" ");
                } else if (caseGrille.getPheromones() > 0) {
                    stringBuilder.append(" . ");
//                    stringBuilder.append(" ").append((int) caseGrille.getPheromones()).append(" ");
                } else {
                    stringBuilder.append("   ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
