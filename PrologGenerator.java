import java.io.*;
import java.util.ArrayList;

public class PrologGenerator {
	private AnnsData anns;
	private static final String e = "event";
	private static final String o = "outcome";
	private static final String fileName = "cplint.pl";
	
	private static class DP {
		String outcome;
		double probFirst;
		double probSecond;
		public String toString() {
			return String.format("outcome: %s, probFirst: %f, probSecond: %f\n", 
				       	outcome, probFirst, probSecond);	
		} 	
	}
	ArrayList<DP> probs = new ArrayList<>();
	
	public PrologGenerator(AnnsData anns) {
		this.anns = anns;
	}
	
	private void inferProbValues() {
		int suffix = 1;	
		for (int i = 0; i < anns.s_dpd.length; i++) {
			suffix = 1;	
			AnnsData.DependProbData dpd = anns.s_dpd[i];

			for (int j = 0; j < dpd.event.length; j++) {
				if (anns.s_pd[j].event.equals(dpd.event)) {
					DP dp = new DP();	
					dp.outcome = "outcome" + suffix;	
					dp.probFirst = anns.s_pd[j].val / 100.00;		
					dp.probSecond = dpd.prob.val / 100.0;
					probs.add(dp);
					dp = new DP();	
					dp.outcome = "outcome" + (++suffix);	
					dp.probFirst = anns.s_pd[j].val / 100.00;		
					dp.probSecond = (100 - dpd.prob.val) / 100.0;
					probs.add(dp);
				}
				else if (("!" + anns.s_pd[j].event).equals(dpd.event)) {
					DP dp = new DP();	
					dp.outcome = "outcome" + suffix;	
					dp.probFirst = (100 - anns.s_pd[j].val) / 100.00;		
					dp.probSecond = (100 - dpd.prob.val) / 100.0;
					probs.add(dp);
					dp = new DP();	
					dp.outcome = "outcome" + (++suffix);	
					dp.probFirst = anns.s_pd[j].val / 100.00;		
					dp.probSecond = dpd.prob.val / 100.0;
					probs.add(dp);
				}
			}			
		}				
	}

	public String Generate() {
		inferProbValues();
		System.out.println("dp " + probs);

		try {
		PrintWriter pw = new PrintWriter(new File(fileName));
		pw.println(":- use_module(library(pita)).");
		pw.println(":- pita.");
		pw.println(":- begin_lpad.");
		//the following is hardcoded
		//need to figure out how to generate 
		//this logic based on AnnsData
		pw.printf("%s(V) : %f; %s(V) : %f :-", 
				probs.get(0).outcome, probs.get(0).probSecond, 
				probs.get(1).outcome, probs.get(1).probSecond);	
		pw.printf("%s(V), %s3(V).\n", e, o);
		pw.printf("%s(V) : %f; %s(V) : %f :-",	
				probs.get(2).outcome, probs.get(2).probSecond, 
				probs.get(3).outcome, probs.get(3).probSecond);	
		pw.printf("%s(V), \\+ %s3(V).\n", e, o);
		for (int i = 0; i < anns.s_pd.length; i++) {
			pw.printf("outcome3(V) : %f ; (\\+ outcome3(V)) : %f.\n", 
			 	anns.s_pd[i].val/100.0, (100.00 - anns.s_pd[i].val) / 100.0);
		}
		
		pw.println(e + "(v).");
		pw.println(":- end_lpad.");
		pw.printf("p%s1(V) :- prob(%s1(v), V).\n", o, o);
		pw.printf("p%s2(V) :- prob(%s2(v), V).\n", o, o);
		pw.close();

		return fileName;
		} catch (IOException e) {
			return "NoFile.pl";
		}
	}

}
