package agh.ics.oop;

import agh.ics.oop.model.AnimalBehavior;
import agh.ics.oop.model.Mutation;
import agh.ics.oop.model.PlantGrowing;

public record SimulationSettings(
        int width,
        int height,
        int startingPlants,
        int plantsEnergy,
        int plantsPerDay,
        PlantGrowing plantsGrowing,
        int startingAnimals,
        int startingEnergy,
        int energyNeededToReproduce,
        int energyLostToReproduce,
        int minMutations,
        int maxMutations,
        Mutation mutation,
        int genomeLength,
        AnimalBehavior animalBehavior
) {}