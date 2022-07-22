import Annotations.*;

public class Probability {
    static {
		System.loadLibrary("PrologFromCpp");	
		probabilityValue = probIncident();
	}

	// region coin.pl

	// @Event(name = "fair", probValue = 90)
	// @Event()
	// @Outcome(conditionalEvents = {@Event(name = "fair")}, incidentName = "head", probValue = 50)
	// @Outcome(conditionalEvents = {@Event(name = "!fair")}, incidentName = "head", probValue = 60)
	// @GetProbability(incidentName = "head")

	//endregion

	// region alarm.pl

	// @Event(name = "burglary", probValue = 10)
	// @Event(name = "earthquake", probValue = 20)
	// @Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 100)
	// @Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 80)
	// @Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 80)
	// @Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 10)
    // @GetProbability(incidentName="alarm")

	// endregion

	// region sneezing.pl || needs 2nd ProbValue

	// @Event(name = "flu", probValue = 100)
	// @Event(name = "hay_fever", probValue = 100)
	// @Outcome(conditionalEvents = {@Event(name = "flu")}, incidentName = "strong_sneezing", probValue = 30)
	// @Outcome(conditionalEvents = {@Event(name = "hay_fever")}, incidentName = "strong_sneezing", probValue = 20)
	// @GetProbability(incidentName = "strong_sneezing")

	// endregion

	// region epidemic.pl || needs 2nd probValue

	// @Event(name = "flu", probValue = 100)
	// @Event(name = "cold", probValue = 70)
	// @Outcome(conditionalEvents = {@Event(name = "flu"), @Event(name = "cold")}, incidentName = "epidemic", probValue = 60)
	// @GetProbability(incidentName = "epidemic")

	// endregion

	// region eruption.pl || needs 2nd probValue

	// @Event(name = "fault_rupture", probValue = 100)
	// @Event(name = "sudden_energy_release", probValue = 70)
	// @Outcome(conditionalEvents = {@Event(name = "fault_rupture"), @Event(name = "sudden_energy_release")}, incidentName = "eruption", probValue = 60)
	// @GetProbability(incidentName = "eruption")

	// endregion

	// region earthquake.pl || needs 2nd probValue

	// @Event(name = "fault_rupture", probValue = 100)
	// @Event(name = "volcanic_eruption", probValue = 100)
	// @Outcome(conditionalEvents = {@Event(name = "fault_rupture")}, incidentName = "earthquake_strong", probValue = 30)
	// @Outcome(conditionalEvents = {@Event(name = "volcanic_eruption")}, incidentName = "earthquake_strong", probValue = 20)
	// @GetProbability(incidentName = "earthquake_strong")

	// endregion

	// region bag game

	// @Event(name = "red", probValue = 40)
	// @Event(name = "green", probValue = 90)
	// @Event(name = "blue", probValue = 50)
	// @Event(name = "yellow", probValue = 60)
	// @Outcome(conditionalEvents = {@Event(name = "red"), @Event(name = "green")}, incidentName = "win", probValue = 100)
	// @Outcome(conditionalEvents = {@Event(name = "blue"), @Event(name = "yellow")}, incidentName = "win", probValue = 100)
	// @GetProbability(incidentName = "win")
	// endregion

	// region light.pl 

	// @Event(name = "push", probValue = 100)
	// @Event()
	// @Outcome(conditionalEvents = {@Event(name = "push")}, incidentName = "light", probValue = 40)
	// @Outcome(conditionalEvents = {@Event(name = "!light")}, incidentName = "replace", probValue = 100)
	// @GetProbability(incidentName = "light")
	// endregion

	// region simpson.pl || getprobability is not full. it can also take condition

	@Event(name = "female", probValue = 50)
	@Event()
	@Outcome(conditionalEvents = {@Event(name = "drug"), @Event(name = "!female")}, incidentName = "recovery", probValue = 60)
	@Outcome(conditionalEvents = {@Event(name = "!drug"), @Event(name = "!female")}, incidentName = "recovery", probValue = 70)
	@Outcome(conditionalEvents = {@Event(name = "drug"), @Event(name = "female")}, incidentName = "recovery", probValue = 20)
	@Outcome(conditionalEvents = {@Event(name = "!drug"), @Event(name = "female")}, incidentName = "recovery", probValue = 30)
	@Outcome(conditionalEvents = {@Event(name = "!female")}, incidentName = "drug", probValue = 75)
	@Outcome(conditionalEvents = {@Event(name = "female")}, incidentName = "drug", probValue = 25)
	@GetProbability(incidentName = "recovery")

	// endregion

	public static native float probIncident();

	private final static float probabilityValue;

	public static void main(String ... args) {
		System.out.println("Prolog set probabilityValue to " + probabilityValue);

		//System.out.println("Prolog set reverse probabilityValue to " + (1 - probabilityValue));
	}
}
