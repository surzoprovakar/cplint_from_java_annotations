import javax.annotation.processing.*;
import java.util.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import Annotations.*;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnsProcessor extends AbstractProcessor {
	private AnnsData data = new AnnsData();
	@Override
	public synchronized void init(ProcessingEnvironment env) {
		super.init(env);
	}

	@Override
	public boolean process(Set<? extends TypeElement> set,
			RoundEnvironment roundEnv) {
		if (set.size() == 0) return true;
		ProcessEvent(roundEnv);
		ProcessOutcome(roundEnv);
		ProcessGetProbability(roundEnv);
		GeneratePrologFile();
		GenerateCppFile();
		return true;
	}

	private void ProcessEvent(RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(Events.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				Event[] evs = el.getAnnotationsByType(Event.class);
				AnnsData.EventData.factory(evs);
			}
		}
	}

	private void ProcessOutcome(RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(Events.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				Outcome[] ots = el.getAnnotationsByType(Outcome.class);
				AnnsData.OutcomeData.factory(ots);
			}
		}
	}

	private void ProcessGetProbability(RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(GetProbability.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				AnnsData.s_getProbOutcome = 
					el.getAnnotation(GetProbability.class).incidentName();
			}
		}
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> anns = new HashSet<>();
		anns.add("Annotations.Event");
		anns.add("Annotations.Events");
		anns.add("Annotations.Outcome");
		anns.add("Annotations.Outcomes");
		return anns;
	}

	private void GeneratePrologFile() {
		System.out.println("data model: " + data);
		PrologGenerator gen = new PrologGenerator(data);
		String file = gen.Generate();
		System.out.println("generated: " + file);
	}
	
	private void GenerateCppFile() {
		CppGenerator gen = new CppGenerator(data);
		String file = gen.Generate();
		System.out.println("generated: " + file);
	}
}
