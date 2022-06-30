import Annotations.*;

public class AnnsData { 

	public static class EventData {
		public static void factory(Event[] evs) {
			s_evs = new EventData[evs.length];		
			for (int i = 0; i < evs.length; i++)
				s_evs[i] = new EventData(evs[i]);	
		}
		public EventData(Event e) {
			this.name = e.name(); 
			this.probValue = e.probValue();
		}	
		public final String name;
		public final int probValue;
		public String toString() {
			String res = "";
			res += "EventName: " + name + "\n";
			res += "ProbValue: " + probValue + "\n";
			return res;
		}
	}

	public static class OutcomeData {
		public static void factory(Outcome[] ots) {
			s_ots = new OutcomeData[ots.length];
			for (int i = 0; i < ots.length; i++)
				s_ots[i] = new OutcomeData(ots[i]);
		}
		public OutcomeData(Outcome o) {
			this.conditionalEvents = o.conditionalEvents();
			this.incidentName = o.incidentName();
			this.probValue = o.probValue();
		}
		public final Event[] conditionalEvents;
    	public final String incidentName;
    	public final int probValue;
		public String toString() {
			String res = "";
			for (Event e : conditionalEvents) {
				res += e.name() + " ";
			}
			res += "\n" + "OutcomeName " + incidentName + "\n";
			res += "ProbValue " + probValue + "\n";
			return res;
		}
	}

	public static EventData[] s_evs;
	public static OutcomeData[] s_ots;
	public static String s_getProbOutcome; 


	public String toString() {
		String pd =  "Annotations for " + "Probability.java\n";
		
		String ed = "EventData {\n";	
		for (EventData e : s_evs) {
			ed += e.toString();
		}
		ed += "}\n";

		String od = "OutcomeData {\n";
		for (OutcomeData o : s_ots) {	
			od += o.toString();
		}
		od += "}\n";

		return pd + ed + od;
	}
}
