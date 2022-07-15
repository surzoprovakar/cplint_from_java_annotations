import Annotations.*;

public class Probability {
    static {
		System.loadLibrary("PrologFromCpp");	
		probabilityValue = probIncident();
	}

	// region alarm.pl
	// @Event(name = "burglary", probValue = 10)
	// @Event(name = "earthquake", probValue = 20)

	// @Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 100)
	// @Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 80)
	// @Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 80)
	// @Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 10)
	
    // @GetProbability(incidentName="alarm")
	// endregion

	// region coin.pl
	// @Event(name = "fair", probValue = 90)

	// @Outcome(conditionalEvents = {@Event(name = "fair")}, incidentName = "head", probValue = 50)
	// @Outcome(conditionalEvents = {@Event(name = "!fair")}, incidentName = "head", probValue = 60)

	// @GetProbability(incidentName = "head")
	//endregion


	// region sneezing.pl
	@Event(name = "flu", probValue = 100)
	@Event(name = "hay_fever", probValue = 100)

	@Outcome(conditionalEvents = {@Event(name = "flu")}, incidentName = "strong_sneezing", probValue = 30)
	@Outcome(conditionalEvents = {@Event(name = "hay_fever")}, incidentName = "strong_sneezing", probValue = 20)

	@GetProbability(incidentName = "strong_sneezing")
	// endregion

	public static native float probIncident();

	private final static float probabilityValue;

	public static void main(String ... args) {
		System.out.println("Prolog set probabilityValue to " + probabilityValue);

		//System.out.println("Prolog set reverse probabilityValue to " + (1 - probabilityValue));
	}
}
