package anns;
import java.lang.annotation.Repeatable;  

@Repeatable(DependProbs.class)
public @interface DependProb {
	String event();
	Prob prob();
}

