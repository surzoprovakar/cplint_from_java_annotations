import java.io.*;
import java.util.ArrayList;

import Annotations.Event;

public class PrologGenerator {
	private AnnsData anns;
	private static final String e = "event";
	private static final String o = "outcome";
	private static final String p = AnnsData.s_getProbOutcome;
	private static final String fileName = "cplint.pl";
	

	private static class EventDB {
		String name;
		double probFirst;
		double probSecond;
		public String toString() {
			return String.format("%s(t):%f; %s(f):%f.\n", name, probFirst, name, probSecond);
		}
	}

	private static class OutcomeDB {
		String name;
		Event[] events;
		double probFirst;
		double probSecond;
		public String toString() {
			String res = "";
			res += String.format("%s(t):%f; %s(f):%f:-", name, probFirst, name, probSecond);
			for (Event e : events) {
				if (e.name().charAt(0) == '!') {
					String n = e.name().substring(1);
					res += n + "(f)" + ",";
				}
				else {
					res += e.name() + "(t)" + ",";
				}
			}
			res = res.substring(0, res.length() - 1);
			res += ".\n";
			return res;
		}
	}

	ArrayList<EventDB> events = new ArrayList<>();
	ArrayList<OutcomeDB> outcomes = new ArrayList<>();
	
	public PrologGenerator(AnnsData anns) {
		this.anns = anns;
	}
	
	private void InferProbValues() {
		for (int i = 0; i < anns.s_evs.length; i++) {
			AnnsData.EventData ed = anns.s_evs[i];
			EventDB eb = new EventDB();
			if (ed.name.length() != 0) {
				eb.name = ed.name;
				eb.probFirst = ed.probValue / 100.00;
				eb.probSecond = (100.00 - ed.probValue) / 100.00;

				events.add(eb);
			}
		}

		for (int i = 0; i < anns.s_ots.length; i++) {
			AnnsData.OutcomeData od = anns.s_ots[i];
			OutcomeDB ob = new OutcomeDB();
			ob.events = od.conditionalEvents;
			ob.name = od.incidentName;
			ob.probFirst = od.probValue / 100.00;
			ob.probSecond = (100.00 - od.probValue) / 100.00;

			outcomes.add(ob);
		}				
	}

	public String Generate() {
		InferProbValues();
		System.out.println("EventDB " + events);
		System.out.println("OutcomeDB " + outcomes);

		try {
		PrintWriter pw = new PrintWriter(new File(fileName));
		pw.println(":- use_module(library(pita)).");
		pw.println(":- pita.");
		pw.println(":- begin_lpad.");
		pw.println();

		for (int i = 0; i < events.size(); i++) {
			pw.printf("%s", events.get(i).toString());
		}

		for (int j = 0; j < outcomes.size(); j++) {
			pw.printf("%s", outcomes.get(j).toString());
		}
		
		pw.println();
		pw.println(":- end_lpad.");

		pw.println();
		pw.printf("%s1(Prob) :- prob(%s(t), Prob).\n", o, p);
		pw.printf("%s2(Prob) :- prob(%s(f), Prob).\n", o, p);
		pw.close();

		return fileName;
		} catch (IOException e) {
			return "NoFile.pl";
		}
	}

}
