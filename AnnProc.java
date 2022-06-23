import javax.annotation.processing.*;
import java.util.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import anns.*;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnProc extends AbstractProcessor {
	private AnnsData data = new AnnsData();
	@Override
	public synchronized void init(ProcessingEnvironment env) {
		super.init(env);
	}

	@Override
	public boolean process(Set<? extends TypeElement> set,
			RoundEnvironment roundEnv) {
		if (set.size() == 0) return true;
		processProbAnn(roundEnv);
		processOutcomeRange(roundEnv);
		processDependProbAnn(roundEnv);
		processGetProbAnn(roundEnv);
		generatePrologFile();
		generateCppFile();
		return true;
	}

	// private void processProbAnn(RoundEnvironment roundEnv) {
	// 	Set<? extends Element> annotatedElements =
	// 		roundEnv.getElementsAnnotatedWith(Prob.class);

	// 	for (Element element : annotatedElements) {
	// 		if (element.getKind() == ElementKind.METHOD) {
	// 			data.s_className = 
	// 			element.
	// 			getEnclosingElement().
	// 			getSimpleName().toString();
	// 			ExecutableElement el = (ExecutableElement) element;
	// 			Prob prob = el.getAnnotation(Prob.class);
	// 			data.s_pd = new AnnsData.ProbData(prob);
	// 		}
	// 	}
	// }

	private void processProbAnn(RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(Probs.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				data.s_className = 
				element.
				getEnclosingElement().
				getSimpleName().toString();
				ExecutableElement el = (ExecutableElement) element;
				Prob[] p = el.getAnnotationsByType(Prob.class);
				AnnsData.ProbData.factory(p);
			}
		}
	}

	private void processOutcomeRange(RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(OutcomeRange.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				OutcomeRange or = el.getAnnotation(OutcomeRange.class);
				data.s_ord = new AnnsData.OutcomeRangeData(or);
			}
		}
	}

	private void processDependProbAnn(RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(DependProbs.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				DependProb[] dp = el.getAnnotationsByType(DependProb.class);
				AnnsData.DependProbData.factory(dp);
			}
		}
	}
	
	private void processGetProbAnn(RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(GetProb.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				AnnsData.s_getProbOutcome = 
					el.getAnnotation(GetProb.class).outcome();
			}
		}
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> anns = new HashSet<>();
		anns.add("anns.Prob");
		anns.add("anns.OutcomeRange");
		anns.add("anns.DependProb");
		anns.add("anns.DependProbs");
		return anns;
	}

	private void generatePrologFile() {
		System.out.println("data model: " + data);
		PrologGenerator gen = new PrologGenerator(data);
		String file = gen.generate();
		System.out.println("generated: " + file);
	}
	
	private void generateCppFile() {
		CppGenerator gen = new CppGenerator(data);
		String file = gen.generate();
		System.out.println("generated: " + file);
	}
}
