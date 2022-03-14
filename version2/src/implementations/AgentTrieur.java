package implementations;

import abstractions.Agent;
import implementations.actions.*;

import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;

public class AgentTrieur extends Agent<EnvironnementGrille, AgentTrieur, PerceptionTrieur, ActionTrieur> {

    private final static String MEMOIRE_CASE_SANS_OBJET = "0";

    private final int pas;
    private final double kPlus;
    private final double kMoins;
    private final double tauxErreur;
    private final double forceSignal;
    private final int distanceSignal;
    private final int attenteMax;

    private final List<String> memoire;
    private final int tailleMemoire;

    private Objet objetTenu;
    private int attenteAide;

    public AgentTrieur(EnvironnementGrille environnement, int pas, int tailleMemoire, double kPlus, double kMoins,
                       double tauxErreur, double forceSignal, int distanceSignal, int attenteMax) {
        super(environnement);
        this.pas = pas;
        this.tailleMemoire = tailleMemoire;
        this.memoire = generate(() -> MEMOIRE_CASE_SANS_OBJET)
                .limit(this.tailleMemoire)
                .collect(toList());
        this.kPlus = kPlus;
        this.kMoins = kMoins;
        this.tauxErreur = tauxErreur;
        this.forceSignal = forceSignal;
        this.distanceSignal = distanceSignal;
        this.attenteMax = attenteMax;
    }

    public void memoriser(Objet objet) {
        this.getMemoire().add(objet != null ? objet.toString() : MEMOIRE_CASE_SANS_OBJET);
        // Si l'agent n'a plus de place dans sa mémoire
        if (this.getMemoire().size() > this.getTailleMemoire()) {
            // On enlève l'objet retenu le plus ancien
            this.getMemoire().remove(0);
        }
    }

    @Override
    public PerceptionTrieur percevoir() {
        PerceptionTrieur perception = this.getEnvironnement().perception(this);

        Objet objet = perception.getCaseCourante().getObjet();
        this.memoriser(objet);

        return perception;
    }

    @Override
    public void realiserAction(PerceptionTrieur perception) {
        Random random = new Random();
        Objet objetPresent = perception.getCaseCourante().getObjet();
        Objet objetTenu = this.getObjetTenu();

        // S'il y a un objet sur la case
        if (objetPresent != null) {
            // Si l'agent ne porte pas d'objet
            if (objetTenu == null) {
                double f = calculerF(objetPresent);
                double pPrise = pow(getkPlus() / (getkPlus() + f), 2);

                // On teste si l'agent peut prendre l'objet
                if (random.nextDouble() < pPrise) {
                    this.getEnvironnement().realisationAction(this, new ActionPrendre());
                    return;
                }
            }
        } else {
            // Sinon s'il n'y a pas d'objet sur la case et que l'agent porte un objet
            if (objetTenu != null) {
                double f = calculerF(objetTenu);
                double pDepot = pow(f / (getkMoins() + f), 2);

                // On teste si l'agent peut poser l'objet, ou on le pose de force si l'agent a trop attendu
                if (random.nextDouble() < pDepot || this.getAttenteAide() >= this.getAttenteMax()) {
                    this.resetAttente();
                    this.getEnvironnement().realisationAction(this, new ActionPoser());
                    return;
                }
            }
        }

        // Sinon on va tenter de se déplacer sur une case voisine
        List<CaseGrille> casesVoisinesCibles = perception.getCasesVoisines();

        // Dans le cas où l'agent ne porte pas d'objet
        if (objetTenu == null) {
            // Ce dernier est attiré par la case voisine ayant le plus de phéromones
            double pheromonesMax = casesVoisinesCibles.stream()
                    .mapToDouble(CaseGrille::getPheromones)
                    .max()
                    .orElseThrow();

            casesVoisinesCibles = casesVoisinesCibles.stream()
                    .filter(caseGrille -> caseGrille.getPheromones() == pheromonesMax)
                    .collect(toList());
        } else if (objetTenu.isLourd() && perception.getCaseCourante().getAgents().size() < 2) {
            // Sinon si l'objet tenu est lourd et que l'agent est seul, l'agent attend et émet des phéromones
            this.incrementerAttente();
            this.getEnvironnement().realisationAction(this, new ActionPheromones());
            return;
        }

        // On choisit aléatoirement la case sur laquelle se déplacer
        if (!casesVoisinesCibles.isEmpty()) {
            CaseGrille caseDestination = casesVoisinesCibles.get(random.nextInt(casesVoisinesCibles.size()));
            this.getEnvironnement().realisationAction(this, new ActionDeplacer(caseDestination));
            this.resetAttente();
        }
    }

    public double calculerF(Objet objetCible) {
        String typeObjet = objetCible.toString();

        long nombresObjetsCibles = this.getMemoire().stream()
                .filter(objet -> objet.equals(typeObjet))
                .count();
        long nombresObjetsNonCibles = this.getMemoire().stream()
                .filter(objet -> !objet.equals(typeObjet) && !objet.equals(MEMOIRE_CASE_SANS_OBJET))
                .count();
        return (nombresObjetsCibles + this.getTauxErreur()*nombresObjetsNonCibles) / (double) this.getTailleMemoire();
    }

    public void incrementerAttente() {
        this.attenteAide++;
    }

    public void resetAttente() {
        this.attenteAide = 0;
    }

    @Override
    public boolean isSatisfait() {
        return false;
    }

    public int getPas() {
        return pas;
    }

    public Objet getObjetTenu() {
        return objetTenu;
    }

    public void setObjetTenu(Objet objetTenu) {
        this.objetTenu = objetTenu;
    }

    public List<String> getMemoire() {
        return memoire;
    }

    public int getTailleMemoire() {
        return tailleMemoire;
    }

    public double getkPlus() {
        return kPlus;
    }

    public double getkMoins() {
        return kMoins;
    }

    public double getTauxErreur() {
        return tauxErreur;
    }

    public double getForceSignal() {
        return forceSignal;
    }

    public int getDistanceSignal() {
        return distanceSignal;
    }

    public int getAttenteAide() {
        return attenteAide;
    }

    public int getAttenteMax() {
        return attenteMax;
    }
}
