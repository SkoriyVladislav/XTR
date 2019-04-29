package by.Skoriy.Syndroms;

import java.util.List;

public class Syndrome {
    private List<int []> syndromes;
    private List<int []> normOfSyndrome;

    public Syndrome(List<int[]> syndromes, List<int[]> normOfSyndrome) {
        this.syndromes = syndromes;
        this.normOfSyndrome = normOfSyndrome;
    }

    public Syndrome() {
    }

    public List<int[]> getSyndromes() {
        return syndromes;
    }

    public List<int[]> getNormOfSyndrome() {
        return normOfSyndrome;
    }
}
