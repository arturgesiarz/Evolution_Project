package oop.model;

import java.util.Optional;

public class AnimalStats {
    Animal animal;
    private int childAmount;
    private int descendantsAmount;
    private int lifeTime;
    private int deathTime;
    private int energyAmount;

    public AnimalStats(Animal animal) {
        this.animal = animal;
    }

    public void setDeathTime(int time) {
        this.deathTime = time;
    }

    public void updateAncestorsAmount() {
        descendantsAmount++; // jakiś zwierzak wywołał tę metodę, dla swoich rodziców, więc zwiększamy liczbę potomków rodzica

        // Chcemy też zwiększyć liczbę potomków rodziców rodzica naszego zwierzaka:
        Optional <Animal> leftParent  = animal.getLeftParent();
        Optional <Animal> rightParent = animal.getRightParent();

        leftParent.ifPresent(  animal -> animal.getAnimalStats().updateAncestorsAmount() );
        rightParent.ifPresent( animal -> animal.getAnimalStats().updateAncestorsAmount() );

    }

    public void updateLifeTime() {
        this.lifeTime++;
    }

    public void updateDescendantsAmount() {
        this.descendantsAmount++;
    }

    public void updateChildAmount() {
        this.childAmount++;
    }

    public int getLifeTime() { return this.lifeTime; }

    public int getChildAmount() { return this.childAmount; }

    public void increaseEnergyAmount(int amount) { this.energyAmount = energyAmount + amount; }

    public void decreaseEnergyAmount(int amount) {this.energyAmount =  energyAmount - amount; }

    public int getEnergyAmount() { return this.energyAmount; }
}
