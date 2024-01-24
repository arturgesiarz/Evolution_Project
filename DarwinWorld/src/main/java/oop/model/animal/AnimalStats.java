package oop.model.animal;

import java.util.Optional;

public class AnimalStats {
    //
    private final Animal animal;
    private int childAmount = 0;
    private int descendantsAmount = 0;
    private int lifeTime;
    private int deathTime;
    private int energyAmount;

    public AnimalStats(Animal animal, int energyAmount) {
        this.animal = animal;
        this.lifeTime = 0;  // przezylismy 0 dni na samym stracie
        this.energyAmount = energyAmount;
    }

    public void setDeathTime(int time) {
        this.deathTime = time;
    }

    public void updateDescendantsAmount() {
        descendantsAmount++; // jakiś zwierzak wywołał tę metodę, dla swoich rodziców, więc zwiększamy liczbę potomków rodzica

        // Chcemy też zwiększyć liczbę potomków rodziców rodzica naszego zwierzaka:
        Optional <Animal> leftParent  = animal.getLeftParent();
        Optional <Animal> rightParent = animal.getRightParent();

        leftParent.ifPresent(  animal -> animal.getAnimalStats().updateDescendantsAmount() );
        rightParent.ifPresent( animal -> animal.getAnimalStats().updateDescendantsAmount() );

    }

    public void updateLifeTime() {
        this.lifeTime++;
    }

    public void updateChildAmount() {
        this.childAmount++;
    }

    public int getLifeTime() { return this.lifeTime; }

    public int getChildAmount() { return this.childAmount; }

    public void increaseEnergyAmount(int amount) { this.energyAmount = energyAmount + amount; }

    public void decreaseEnergyAmount(int amount) {this.energyAmount =  energyAmount - amount; }

    public int getEnergyAmount() { return this.energyAmount; }

    public int getDescendantsAmount() { return this.descendantsAmount; }

    public int getDeathTime() { return this.deathTime; }
}
