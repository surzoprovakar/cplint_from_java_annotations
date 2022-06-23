import anns.*;

public class Coin {


	/* 
	static {
		System.loadLibrary("PrologFromCpp");	
		thresholdHeads = probHeads();
	}

	@OutcomeRange(ann=Prob.class, min=0, max=1)

	@Prob(event="fair", outcome=1, val=90)
	@DependProb(event="fair", prob=@Prob(outcome=1, val=50))
	@DependProb(event="!fair", prob=@Prob(outcome=1, val=40))
	@GetProb(outcome=1)	


	public static native float probHeads();

	private final static float thresholdHeads;

	public static void main(String ... args) {
		System.out.println("Prolog set thresholdHeads to " + thresholdHeads);

		System.out.println("Prolog set thresholdTails to " + (1 - thresholdHeads));
	} */


	static {
		System.loadLibrary("PrologFromCpp");	
		thresholAlarm = probAlarm();
	}

	@OutcomeRange(ann=Prob.class, min=0, max=1)

	@Prob(event="burglary", outcome=1, val=10)
	@Prob(event="earthquake", outcome=1, val=20)

	@DependProb(event={"burg", "earthquake"}, prob=@Prob(outcome=1, val=100))
	@DependProb(event={"burg", "!earthquake"}, prob=@Prob(outcome=1, val=80))
	@DependProb(event={"!burg", "earthquake"}, prob=@Prob(outcome=1, val=20))
	@DependProb(event={"!burg", "!earthquake"}, prob=@Prob(outcome=1, val=90))
	@GetProb(outcome=1)



	public static native float probAlarm();

	private final static float thresholAlarm;

	public static void main(String ... args) {
		System.out.println("Prolog set thresholdAlarm to " + thresholAlarm);

		//System.out.println("Prolog set thresholdTails to " + (1 - thresholdHeads));
	}

}

