package agh.ics.oop.model;

import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements Comparator<Animal> {
//    Jeżeli na jednym polu kilka zwierzaków rywalizuje o roślinę (albo o możliwość rozmnażania),
//    to konflikt ten jest rozwiązywany w następujący sposób:
//    pierwszeństwo mają organizmy o największej energii,
//    jeżeli to nie pozwala rozstrzygnąć, to pierwszeństwo mają organizmy najstarsze,
//    jeżeli to nie pozwala rozstrzygnąć, to pierwszeństwo mają organizmy o największej liczbie dzieci,
//    jeżeli to nie pozwala rozstrzygnąć, to wśród remisujących organizmów wybieramy losowo.
    @Override
    public int compare(Animal a1,Animal a2) {
        int condition1 = Integer.compare(a1.getEnergy(), a2.getEnergy());
        if(condition1 != 0){
            return condition1;
        }

        AnimalStatistics a1Stats = a1.getStatistics();
        AnimalStatistics a2Stats = a2.getStatistics();

        int condition2 = Integer.compare(a1Stats.getAge(), a2Stats.getAge());
        if(condition2 != 0){
            return condition2;
        }

        int condition3 = Integer.compare(a1Stats.getChildrenCount(), a2Stats.getChildrenCount());
        if(condition3 != 0){
            return condition3;
        }

        double randomVal = Math.random();
        if(randomVal < 0.5){
            return 1;
        }
        return -1;
    }
}
