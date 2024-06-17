package model;

import java.io.Serializable;

public class Glass implements Serializable {

    private int diameter;
    private int lensDistance;
    private int sideArmLength;
    private int sideArmThickness;
    private int LensThickness;
    private int red;
    private int green;
    private int blue;
    private double surface;
    private double volume;

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getLensDistance() {
        return lensDistance;
    }

    public void setLensDistance(int lensDistance) {
        this.lensDistance = lensDistance;
    }

    public int getSideArmLength() {
        return sideArmLength;
    }

    public void setSideArmLength(int sideArmLength) {
        this.sideArmLength = sideArmLength;
    }

    public int getSideArmThickness() {
        return sideArmThickness;
    }

    public void setSideArmThickness(int sideArmThickness) {
        this.sideArmThickness = sideArmThickness;
    }

    public int getLensThickness() {
        return LensThickness;
    }

    public void setLensThickness(int lensThickness) {
        LensThickness = lensThickness;
    }

    public void calculate (int diam, int lensD, int lensT, int sideArmT, int sideArmL) {
        setSurface((int) ((((double) diam /2)*((double) diam /2)*3.14 + diam * 3.14 * lensT)*2+2*(sideArmT*sideArmT*2 + sideArmT*sideArmL*4) + (lensT*lensD*4+lensT*lensT*2)));
        setVolume((int) (((double) diam /2)*((double) diam /2)*3.14*2*lensT + (sideArmL*sideArmT*sideArmL) + (sideArmT*lensT*lensD)));
        return;
    }

    @Override
    public String toString() {
        return "A generált szemüveg paraméterei: \n\n" +
                "Lencse átmérője: " + diameter + "mm \n" +
                "Lencsék távolsága: " + lensDistance + "mm \n" +
                "Szárak hossza: " + sideArmLength + "mm \n" +
                "Szárak vastagsága: " + sideArmThickness + "mm \n" +
                "Lencsék vastagsága: " + LensThickness + "mm \n" +
                "Színe: Piros: " + red + " Zöld: " + green + " Kék: " + blue + "\n" +
                "Felszíne (egészre kerekítve): ≈" + surface + "mm² \n" +
                "Térfogata (egészre kerekítve): ≈" + volume + "mm³ \n";
    }
}
