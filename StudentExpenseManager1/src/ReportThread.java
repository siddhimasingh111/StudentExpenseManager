/**
 * ReportThread.java
 *
 * A dedicated thread that generates the expense report.
 * Demonstrates: Multithreading, extending Thread, start(), sleep().
 *
 * The report calculation genuinely happens inside run(), which
 * executes on a separate thread from the main program. A short
 * sleep() is used to simulate the (small but real) processing
 * time of gathering and calculating report data, and to clearly
 * show that the work is happening on a background thread.
 */
public class ReportThread extends Thread {

    private ReportGenerator reportGenerator;

    public ReportThread(ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @Override
    public void run() {
        try {
            System.out.println("[ReportThread] Generating report on thread: "
                    + Thread.currentThread().getName());

            // Simulate real processing time for report calculation
            Thread.sleep(1000);

            // The actual report generation happens here, inside the thread
            reportGenerator.printFullReport();

            Thread.sleep(200);
            System.out.println("[ReportThread] Report generation complete.");

        } catch (InterruptedException e) {
            System.out.println("Report generation was interrupted: " + e.getMessage());
            // Restore the interrupted status as good practice
            Thread.currentThread().interrupt();
        }
    }
}