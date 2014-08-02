package ch.quazz.caverna;

public class Cattle {
    private int dogs = 0;
    private int sheep = 0;
    private int donkeys = 0;
    private int boars = 0;
    private int cattle = 0;

    public int score()
    {
        return dogs + farmAnimalScore();
    }

    public int dogs() {
        return dogs;
    }

    public void setDogs(int dogs) {
        this.dogs = dogs;
    }

    public int sheep() {
        return sheep;
    }

    public void setSheep(int sheep) {
        this.sheep = sheep;
    }

    public int donkeys() {
        return donkeys;
    }

    public void setDonkeys(int donkeys) {
        this.donkeys = donkeys;
    }

    public void setBoars(int boars) {
        this.boars = boars;
    }

    public void setCattle(int cattle) {
        this.cattle = cattle;
    }

    private int farmAnimalScore() {
        int sum = 0;
        for(int num : new int[]{sheep, donkeys, boars, cattle}) {
            if(num < 1) {
                sum -= 2;
            }
            else {
                sum += num;
            }
        }
        return sum;
    }
}
