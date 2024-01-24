package oop.presenter;

import oop.model.animal.Animal;

public class ColorDisplay {
    //
    private int x;
    private int y;
    private int z;

    public ColorDisplay() {
    }

    public void determineColor(int animalEnergy) {
        if (animalEnergy < 5) { x = 0 ; y = 0 ; z = 0; }
        else if (animalEnergy < 10) { x = 204; y = 0; z = 0; }
        else if (animalEnergy < 20) { x = 255; y = 128; z = 0; }
        else if (animalEnergy < 30) { x = 204; y = 204; z = 0; }
        else { x = 51; y = 153; z = 255;  }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }


}
