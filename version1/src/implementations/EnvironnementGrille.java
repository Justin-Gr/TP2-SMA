package implementations;

import abstractions.Environnement;
import implementations.actions.ActionTrieur;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnvironnementGrille extends Environnement<EnvironnementGrille, AgentTrieur, PerceptionTrieur, ActionTrieur> {

    private final int N;
    private final int M;

    private final Random random;

    private final CaseGrille[][] cases;

    public EnvironnementGrille(int N, int M, Map<Objet, Integer> nombreObjetsParType) {
        super(new ArrayList<>());
        this.random = new Random();

        this.N = N;
        this.M = M;

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
        } while(getCase(x, y).getAgent() != null);

        getCase(x, y).setAgent(agent);
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
        int x = 0, y = 0;
        CaseGrille caseCourante = null;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                CaseGrille caseGrille = getCase(j, i);
                if (caseGrille.getAgent() == agent) {
                    x = j;
                    y = i;
                    caseCourante = caseGrille;
                }
            }
        }

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
     * Retourne la case où se situe l'agent passé en paramètre
     *
     * @param agent l'agent en question
     * @return la case où se situe l'agent
     */
    public CaseGrille caseCouranteAgent(AgentTrieur agent) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                CaseGrille caseGrille = getCase(j, i);
                if (caseGrille.getAgent() == agent) {
                    return caseGrille;
                }
            }
        }
        return null;
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

                if (caseGrille.getAgent() != null) {
                    stringBuilder.append("[");
                    if (caseGrille.getAgent().getObjetTenu() != null) {
                        stringBuilder.append(caseGrille.getAgent().getObjetTenu());
                    } else {
                        stringBuilder.append("_");
                    }
                    stringBuilder.append("]");
                } else if (caseGrille.getObjet() != null) {
                    stringBuilder.append(" ").append(caseGrille.getObjet()).append(" ");
                } else {
                    stringBuilder.append(" . ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
