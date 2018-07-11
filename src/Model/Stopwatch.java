package Model;

public class Stopwatch extends Thread {

    private Long startingTime;
    private int sleepDuration;

    private int duration;
    private int durationTime;

    private boolean isPause;

    public Stopwatch() {
        this.isPause = false;
        this.sleepDuration = 1000;
    }

    public void run() {
        startingTime = System.nanoTime();

        while (true) {
            if (Thread.interrupted()) {
                break;
            }
            if (isPause) {
                try {
                    sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                } finally {
                    startingTime = System.nanoTime();
                }
            }
            if (!isPause) calculateDuration();
        }
    }

    private void calculateDuration() {
        long endTime = System.nanoTime();
        duration = (int)((double)(endTime - startingTime) / 1000000000.0);
    }

    public void setPause() {
        isPause = true;
    }

    public void setUnPause() {
        durationTime += duration;
        isPause = false;
    }
    public int getDuration() {
        return durationTime + duration;
    }

    public boolean isPause() { return isPause; }
}
