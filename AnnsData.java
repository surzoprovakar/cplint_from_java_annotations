import javax.lang.model.type.MirroredTypeException;
import anns.*;

public class AnnsData { 

	public static class OutcomeRangeData {
		public OutcomeRangeData(OutcomeRange or) {
			Class annClass = null;	
			try {	
				annClass = or.ann();
			} catch( MirroredTypeException mte ) {
				try { 
					annClass = Class.forName(mte.getTypeMirror().toString()); 
				} catch(ClassNotFoundException e) { annClass = String.class; }
			} finally {
				this.ann = annClass;	
			} 
			this.min = or.min(); 
			this.max = or.max();	
		}	
		public final Class ann;
		public final int min;
		public final int max;
	}

	public static class ProbData {
		public ProbData(Prob p) {
			this.event = p.event(); 
			this.outcome = p.outcome();
			this.val = p.val();	
		}
		public final String event;
		public final int outcome;
		public final int val;
		public String toString() {
			String res = "";	
			res += "event:" + event + "\n";
			res += "outcome: " + outcome + "\n";
			res += "val: " + val+ "\n";
			return res;
		}
	}

	public static class DependProbData {
		public static void factory(DependProb[] dpa) {
			s_dpd = new DependProbData[dpa.length];		
			for (int i = 0; i < dpa.length; i++)
				s_dpd[i] = new DependProbData(dpa[i]);	
		}
		public DependProbData(DependProb dp) {
			this.event = dp.event(); 
			this.prob = new ProbData(dp.prob());
		}	
		public final String event;
		public final ProbData prob;
	}

	public static DependProbData[] s_dpd;
	public static OutcomeRangeData s_ord;
	public static ProbData s_pd;
	public static int s_getProbOutcome; 
	public static String s_className;


	public String toString() {
		String pd =  "Annotations for " +
			s_className + ".java\n";
		pd += "ProbData {\n";
		pd += s_pd + "}\n";
		
		String ord = "OutcomeRangeData {\n";	
		ord += "ann: " + s_ord.ann + "\n"; 
		ord += "min: " + s_ord.min + "\n"; 
		ord += "max: " + s_ord.max + "}\n";

		String dpd = "DependProbData {\n";
		for (DependProbData d : s_dpd) {	
			dpd += "event: " +  d.event + "\n";
			dpd += "prob: " + d.prob + "\n";
		}
		dpd += "}\n";
		return pd + ord + dpd;
	}
}
