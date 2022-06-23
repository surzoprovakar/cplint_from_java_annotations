package anns;
import java.lang.annotation.Repeatable; 

@Repeatable(Probs.class)
public @interface Prob {
	String event() default "";
	int outcome();
	int val();
}

