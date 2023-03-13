package ui;

import java.util.List;

public class FullInformation {
        //private int id;
    //private int number;
    //private int idGroup;
    private Driver driver;
    private boolean wrongGroup;
    private int heatId;
    private String transponder;
    private double totalTime;
    private List<Lap> laps;
    private List<Double> lapsAbs;
    private int lapsCount;
    private int bestLap;
    private double bestLapTime;
    private double avgLapTime;
    private double avgLapTime3;
    private double consistency;
    private List<Double> bestThree;
    private int bestThreeLap;
    private double bestThreeTime;
    private boolean finished;
    private boolean checked;
    private int[] positions;
    private Pace pace;
    private int position;

  /*  public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public int getIdDriver() {
        return idDriver;
    }
*/
    public boolean isWrongGroup() {
        return wrongGroup;
    }

    public int getHeatId() {
        return heatId;
    }

    public String getTransponder() {
        return transponder;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public List<Lap> getLaps() {
        return laps;
    }

    public List<Double> getLapsAbs() {
        return lapsAbs;
    }

    public int getLapsCount() {
        return lapsCount;
    }

    public int getBestLap() {
        return bestLap;
    }

    public double getBestLapTime() {
        return bestLapTime;
    }

    public double getAvgLapTime() {
        return avgLapTime;
    }

    public double getAvgLapTime3() {
        return avgLapTime3;
    }

    public double getConsistency() {
        return consistency;
    }

    public List<Double> getBestThree() {
        return bestThree;
    }

    public int getBestThreeLap() {
        return bestThreeLap;
    }

    public double getBestThreeTime() {
        return bestThreeTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isChecked() {
        return checked;
    }

    public int[] getPositions() {
        return positions;
    }

    public Pace getPace() {
        return pace;
    }

    public int getPosition() {
        return position;
    }
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


  /*  public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }*/

    public void setWrongGroup(boolean wrongGroup) {
        this.wrongGroup = wrongGroup;
    }

    public void setHeatId(int heatId) {
        this.heatId = heatId;
    }

    public void setTransponder(String transponder) {
        this.transponder = transponder;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public void setLaps(List<Lap> laps) {
        this.laps = laps;
    }

    public void setLapsAbs(List<Double> lapsAbs) {
        this.lapsAbs = lapsAbs;
    }

    public void setLapsCount(int lapsCount) {
        this.lapsCount = lapsCount;
    }

    public void setBestLap(int bestLap) {
        this.bestLap = bestLap;
    }

    public void setBestLapTime(double bestLapTime) {
        this.bestLapTime = bestLapTime;
    }

    public void setAvgLapTime(double avgLapTime) {
        this.avgLapTime = avgLapTime;
    }

    public void setAvgLapTime3(double avgLapTime3) {
        this.avgLapTime3 = avgLapTime3;
    }

    public void setConsistency(double consistency) {
        this.consistency = consistency;
    }

    public void setBestThree(List<Double> bestThree) {
        this.bestThree = bestThree;
    }

    public void setBestThreeLap(int bestThreeLap) {
        this.bestThreeLap = bestThreeLap;
    }

    public void setBestThreeTime(double bestThreeTime) {
        this.bestThreeTime = bestThreeTime;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public void setPace(Pace pace) {
        this.pace = pace;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
