package com.jeep.lolesports.utils.extractor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DailyDataExtractor {
    public static volatile boolean isProcessRunning = false;
    public static volatile ExtractorConfig extractorConfig = new ExtractorConfig();

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    DailyTask dailyTask;
    volatile boolean isStopIssued;

    public DailyDataExtractor(DailyTask dailyTask$) {
        dailyTask = dailyTask$;
    }

    public void startExecutionAt(int targetHour, int targetMin, int targetSec) {
        updateExtractor(targetHour, targetMin);
        Runnable taskWrapper = new Runnable(){

            @Override
            public void run()
            {
                dailyTask.execute();
                startExecutionAt(targetHour, targetMin, targetSec);
            }

        };
        long delay = computeNextDelay(targetHour, targetMin, targetSec);
        executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
    }

    private long computeNextDelay(int targetHour, int targetMin, int targetSec) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
        if(zonedNow.compareTo(zonedNextTarget) > 0)
            zonedNextTarget = zonedNextTarget.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    public void stop() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
            stopExtractor();
        } catch (InterruptedException ex) {
            Logger.getLogger(DailyDataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void updateExtractor(int targetHour, int targetMin) {
        extractorConfig = new ExtractorConfig(targetHour, targetMin);
        isProcessRunning = true;
    }

    public synchronized void stopExtractor() {
        isProcessRunning = false;
    }
}
