package oop.model.util;

public record MapParameters(
        int width,
        int height,
        int mapMode, // 1 - BASIC, 0 - MAP WITH HOLES
        int amountOfPlantsBeginning,
        int grassEnergy,
        int amountOfPlantsDaily,
        int initialNumberOfAnimals,
        int startEnergy,
        int minimumEnergyRequiredForCopulation,
        int energyLostInCopulation,
        int minimumNumberOfMutation,
        int maximumNumberOfMutation,
        int genesMode, // 1 - BASIC, 0 - EXTENDED
        int genomeLength) {
}


