/**
 * Измеряет время.
 */
class Timer {
    private long start;
    private long finish;

    public Timer start() {
        start = System.currentTimeMillis();
        return this;
    }

    public void finish() {
        finish = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        int due = (int) ((finish - start) / 1000);
        int minutes = due / 60;
        int seconds = due % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}