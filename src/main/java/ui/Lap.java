package ui;

public class Lap {
    private String time;
    private double laptime;
    private boolean valid;
    private int lap;

    public String getTime() {
        return time;
    }

    public double getLaptime() {
        return laptime;
    }

    public boolean isValid() {
        return valid;
    }

    public int getLap() {
        return lap;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLaptime(double laptime) {
        this.laptime = laptime;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }
}
