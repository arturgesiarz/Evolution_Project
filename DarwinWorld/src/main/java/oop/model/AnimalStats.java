package oop.model;

public class AnimalStats {
    //
    Animal animal;
    private int childAmount;
    private int descendantsAmount;
    private int lifeTime;
    private int deathTime;



    public AnimalStats(Animal animal) {
        this.animal = animal;
    }

    public void setDeathTime(int time) {
        this.deathTime = time;
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


}
