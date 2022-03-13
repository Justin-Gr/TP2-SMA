package implementations;

import abstractions.Perception;

import java.util.List;

public class PerceptionTrieur implements Perception {

    private final List<CaseGrille> casesVoisines;
    private final CaseGrille caseCourante;

    public PerceptionTrieur(CaseGrille caseCourante, List<CaseGrille> casesVoisines) {
        this.caseCourante = caseCourante;
        this.casesVoisines = casesVoisines;
    }

    public List<CaseGrille> getCasesVoisines() {
        return casesVoisines;
    }

    public CaseGrille getCaseCourante() {
        return caseCourante;
    }
}
