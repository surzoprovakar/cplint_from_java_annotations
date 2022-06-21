import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CppGenerator {
	private AnnsData anns;
	private static final String e = "event";
	private static final String o = "outcome";
	private static final String cppFileName = "cplint.cpp";
	private String headerFileName;


	public CppGenerator(AnnsData anns) {
		this.anns = anns;
		this.headerFileName = anns.s_className + ".h";
	}

	/*
	 * Takes as input "...foo(int,java.util.Vector);"
	 * and returns "...foo(int p1,java.util.Vector p2);"
	 *  */
	private static String addParameterNames(String fun) {
		if (fun.endsWith(";"))
			fun = fun.substring(0, fun.length()-1);

		int b = fun.indexOf('(');
		int e = fun.indexOf(')');
		StringTokenizer st = new StringTokenizer(fun.substring(b+1, e), ",");
		String res = "";
		int pn = 1;
		while(st.hasMoreTokens()) {
			res += st.nextToken() + " p" + pn;
			if (st.hasMoreTokens())
				res += ", ";
			pn++;
		}
		return fun.substring(0, b+1) + res + ")";
	}

	private String getFunctionDecl() {
		try {	
			Path fileName = Path.of(headerFileName);
			String headerSource = Files.readString(fileName);

			String[] lines = headerSource.split("\n");
			String decl = "";
			for (int i = 0; i < lines.length; i++) {
				if (lines[i].startsWith("JNIEXPORT"))  {
					decl += lines[i];
					decl += addParameterNames(lines[i+1]);
					return decl;	
				}
			}
		} catch (IOException e) {e.printStackTrace(); }
		return "";
	}

	public String generate() {
		String funcDecl = getFunctionDecl();
		try {
			PrintWriter pw = new PrintWriter(new File(cppFileName));
			pw.println("#include \"SWI-Prolog.h\"");
			pw.println("#include \"SWI-Stream.h\"");
			pw.println("#include \"SWI-cpp.h\"");
			pw.println(String.format("#include \"%s\"", headerFileName));
			pw.println();		
			pw.println("extern void init();\n\n");
			pw.println(funcDecl + "{");
			String on = o + anns.s_getProbOutcome;
			
			pw.println("init();");
			pw.println(String.format("term_t ans%s, prob%s;", on, on));
		
			pw.println(String.format("functor_t p%s;", on));
			pw.println(String.format("p%s = PL_new_functor(PL_new_atom(\"p%s\"), 1);", 
						on, on));
			pw.println(String.format("ans%s = PL_new_term_ref();", on));
			pw.println(String.format("prob%s = PL_new_term_ref();", on));
			pw.println();	
			pw.println("module_t module_pl = PL_new_module(PL_new_atom(\"cplint.pl\"));");

			pw.println(String.format("PL_cons_functor(ans%s, p%s, prob%s);", on, on, on));
			pw.println("PlCall(\"consult('cplint.pl')\");");
			pw.println("double probVal;");
			pw.println(String.format("if (PL_call(ans%s, module_pl)) {", on));
			pw.println(String.format("\tPL_get_float(prob%s, &probVal);\n}", on));
			pw.println("return (jfloat) probVal;\n}");	
			pw.close();	
			return cppFileName;
		} catch (IOException e) {
			return "NoFile.cpp";
		}
	}

}
