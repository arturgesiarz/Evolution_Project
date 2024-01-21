package oop.model.util;

public record MapParameters(
        int width,
        int height,
        int mapMode,
        int amountOfPlantsBeginning,
        int grassEnergy,
        int amountOfPlantsDaily,
        int initialNumberOfAnimals,
        int startEnergy,
        int minimumEnergyRequiredForCopulation,
        int energyLostInCopulation,
        int minimumNumberOfMutation,
        int maximumNumberOfMutation,
        int genesMode,
        int genomeLength) {
}


