package engine.util;

public class PerformanceMeasurer {
    private static long startTime;


    public static void start() {
        startTime = System.nanoTime();
    }

    public static long measure() {
        long endTime = System.nanoTime();

		// get difference of two nanoTime values
		long timeElapsed = endTime - startTime;

		//System.out.println("Execution time in nanoseconds  : " + timeElapsed);

		System.out.println("Execution time in milliseconds : " +
								timeElapsed / 1000000);

		return timeElapsed / 1000000;
    }

    public static void compare(long initialTime, long newTime) {
        float gain = (((float)newTime) / initialTime) * 100;

        System.out.println("New time is "+gain+"% of the initial time");
    }
}
