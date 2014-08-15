package rail.client;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.Seconds;


/**
 * Created by priya on 13/08/2014.
 */
public class TrainInformation {
    private final String trainID;
    private final String eventType;
    private final String currentStationStanox;
    private final DateTime expectedEventTime;
    private final DateTime actualEventTime;
    private final String nextStationStanox;
    private final boolean terminated;
    private static final StationCodes stationCodes = new StationCodes();



    public TrainInformation(String trainID, String eventType,
                            String currentStationStanox, DateTime expectedEventTime, DateTime actualEventTime, String nextStationStanox,
                            boolean terminated) {
        this.trainID = trainID;
        this.eventType = eventType;
        this.currentStationStanox = currentStationStanox;
        this.expectedEventTime = expectedEventTime;
        this.actualEventTime = actualEventTime;
        this.nextStationStanox = nextStationStanox;
        this.terminated = terminated;
    }

    @Override
    public String toString() {
        return "|" + DateTimeUtils.printLocalDateTime(actualEventTime)+ " TrainID : " + trainID + " Stanox code: " + currentStationStanox + " Next station: " + nextStationStanox
                + " Status: " + printStatus();
    }

    public String printStatus(){
        Seconds timeDiff = timeDelta();
        String status = "";
        boolean printMoreInfo = true;

        if (timeDiff.equals(Seconds.ZERO)) {
            status = "On TIME ";
            printMoreInfo = false;
        }
        else if (timeDiff.isGreaterThan(Seconds.ZERO))
            status = "Delayed by ";
        else
        {
            status = "Ahead by ";
            timeDiff = timeDiff.multipliedBy(-1);
        }

        Period timePeriod = timeDiff.toPeriod();

        return status + (printMoreInfo ? DateTimeUtils.periodFormatter.print(timePeriod.normalizedStandard()) : "");
    }

    public boolean stannoxEquals(String stanox) {
        return this.currentStationStanox.startsWith(stanox);
    }

    public boolean hasTrainTerminated() {
        return this.terminated;
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof TrainInformation) {
            TrainInformation that = (TrainInformation) other;
            result = (this.trainID.equals(that.trainID) &&
                    this.eventType.equals(that.eventType) &&
                    this.currentStationStanox.equals(that.currentStationStanox) &&
                    this.actualEventTime.equals(that.actualEventTime) &&
                    this.expectedEventTime.equals(that.expectedEventTime) &&
                    this.nextStationStanox.equals(that.nextStationStanox) &&
                    this.terminated == that.terminated
            );
        }
        return result;
    }

    public String getTrainEventType(){
        return eventType;
    }

    public static String lookupCurrentStationName(TrainInformation trainInformation) {
        return getCompleteStationName(trainInformation.currentStationStanox);
    }

    public static String lookupNextStationName(TrainInformation trainInformation) {
        if (!trainInformation.hasTrainTerminated())
            return getCompleteStationName(trainInformation.nextStationStanox);
        else
            return "TERMINUS";
    }

    private static String getCompleteStationName(String stanox) {
        if (!stanox.isEmpty())
            return stationCodes.lookupStanoxCode(stanox);
        else
            return "NOT FOUND";
    }

    public Seconds timeDelta() {
        return Seconds.secondsBetween(expectedEventTime, actualEventTime);
    }

}
