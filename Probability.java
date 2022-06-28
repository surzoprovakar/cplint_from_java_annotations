import Annotations.*;


public class Probability {
    static {
		System.loadLibrary("PrologFromCpp");	
		probabilityValue = probIncident();
	}


	@Event(name="burglary", probValue=10)
	@Event(name="earthquake", probValue=20)

	@Outcome(conditionalEvents={@Event(name="burglary"), @Event(name="earthquake")}, incidentName="alarm", probValue=100)
	@Outcome(conditionalEvents={@Event(name="burglary"), @Event(name="!earthquake")}, incidentName="alarm", probValue=80)
	@Outcome(conditionalEvents={@Event(name="!burglary"), @Event(name="earthquake")}, incidentName="alarm", probValue=80)
	@Outcome(conditionalEvents={@Event(name="!burglary"), @Event(name="!earthquake")}, incidentName="alarm", probValue=10)
	
    @GetProbability(incidentName="alarm")



	public static native float probIncident();

	private final static float probabilityValue;

	public static void main(String ... args) {
		System.out.println("Prolog set probabilityValue to " + probabilityValue);

		//System.out.println("Prolog set reverse probabilityValue to " + (1 - probabilityValue));
	}
}
