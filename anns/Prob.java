package anns;
public @interface Prob {
	String event() default "";
	int outcome();
	int val();
}

