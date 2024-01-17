package oop.model.util;

public record MapParameters(
        int amountOfPlantsBeginning,
        int amountOfPlantsDaily,
        int grassEnergy,
        int genesMode,
        int minimumEnergyRequiredForCopulation,
        int energyLostInCopulation) {
}
