package implementations;

import abstractions.Agent;
import implementations.actions.ActionDeplacer;
import implementations.actions.ActionPoser;
import implementations.actions.ActionPrendre;
import implementations.actions.ActionTrieur;

import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;

public class AgentTrieur extends Agent<EnvironnementGrille, AgentTrieur, PerceptionTrieur, ActionTrieur> {

    private final static String MEMOIRE_OBJET_PAR_DEFAUT = "0";

    private final int pas;
    private final double kPlus;
    private final double kMoins;
    private final double tauxErreur;

    private final List<String> memoire;
    private final int tailleMemoire;

    private Objet objetTenu;

    public AgentTrieur(EnvironnementGrille environnement, int pas, int tailleMemoire,
                       double kPlus, double kMoins, double tauxErreur) {
        super(environnement);
        this.pas = pas;
        this.tailleMemoire = tailleMemoire;
        this.memoire = generate(() -> MEMOIRE_OBJET_PAR_DEFAUT)
                .limit(this.tailleMemoire)
                .collect(toList());
        this.kPlus = kPlus;
        this.kMoins = kMoins;
        this.tauxErreur = tauxErreur;
    }

    public void memoriser(Objet objet) {
        this.getMemoire().add(objet != null ? objet.toString() : MEMOIRE_OBJET_PAR_DEFAUT);
        if (this.getMemoire().size() > this.getTailleMemoire()) {
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

                // On essaye de prendre l'objet
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

                // On essaye de poser l'objet
                if (random.nextDouble() < pDepot) {
                    this.getEnvironnement().realisationAction(this, new ActionPoser());
                    return;
                }
            }
        }

        // Sinon si on n'a pas réussi à prendre ou poser un objet, on se déplace sur une case voisine sans agent
        List<CaseGrille> casesVoisinesLibres = perception.getCasesVoisines().stream()
                .filter(caseGrille -> caseGrille.getAgent() == null)
                .collect(toList());

        if (!casesVoisinesLibres.isEmpty()) {
            // La case est choisie aléatoirement
            CaseGrille caseDestination = casesVoisinesLibres.get(random.nextInt(casesVoisinesLibres.size()));
            this.getEnvironnement().realisationAction(this, new ActionDeplacer(caseDestination));
        }
    }

    public double calculerF(Objet objetCible) {
        String typeObjet = objetCible.toString();

        long nombresObjetsCibles = this.getMemoire().stream()
                .filter(objet -> objet.equals(typeObjet))
                .count();
        long nombresObjetsNonCibles = this.getMemoire().stream()
                .filter(objet -> !objet.equals(typeObjet) && !objet.equals(MEMOIRE_OBJET_PAR_DEFAUT))
                .count();
        return (nombresObjetsCibles + tauxErreur*nombresObjetsNonCibles) / (double) this.getTailleMemoire();
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
}
