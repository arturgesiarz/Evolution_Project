package oop.model;

public abstract class Food implements WorldElement {
    protected int energyRegeneratedByEat;  // zmienna mowiona o tym ile regeneruje dane jedzenie
    abstract String getName();
    abstract void setTheAmountOfRegeneratedEnergy(int energyRegenerated);
    public int getEnergyRegeneratedByEat(){
        return energyRegeneratedByEat;
    }
}
