package models;

public class CountSessionStateModel {
    private int grownResult;
    private int fellResult;
    private int maintainedResult;

    public CountSessionStateModel(int grownResult, int fellResult, int maintainedResult) {
        this.grownResult = grownResult;
        this.fellResult = fellResult;
        this.maintainedResult = maintainedResult;
    }

    public CountSessionStateModel() {
        this.grownResult = 0;
        this.fellResult = 0;
        this.maintainedResult = 0;
    }

    public void addCountSessionStateModel(CountSessionStateModel other) {
        this.grownResult += other.grownResult;
        this.fellResult += other.fellResult;
        this.maintainedResult += other.maintainedResult;
    }


    public void increaseGrownResult() {
        this.grownResult++;
    }

    public void increaseFellResult() {
        this.fellResult++;
    }

    public void increaseMaintainedResult() {
        this.maintainedResult++;
    }

    public int getGrownResult() {
        return grownResult;
    }

    public int getFellResult() {
        return fellResult;
    }

    public int getMaintainedResult() {
        return maintainedResult;
    }

    @Override
    public String toString() {
        return "CountSessionStateModel{" +
                "grownResult = " + grownResult +
                ", fellResult = " + fellResult +
                ", maintainedResult = " + maintainedResult +
                '}';
    }
}
