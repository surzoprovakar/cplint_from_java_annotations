import anns.*;

public class Coin {


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
	}

}

