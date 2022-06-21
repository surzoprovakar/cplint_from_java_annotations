#include <iostream> 
#include "SWI-Prolog.h"
#include "SWI-Stream.h"
#include "SWI-cpp.h"
#include "Coin.h"

extern void init(); 

jfloat toGeneratePHeads() {
	init();
	term_t ansHeads, probHeads;
	functor_t pheads;
	pheads = PL_new_functor(PL_new_atom("pheads"), 1);
	ansHeads = PL_new_term_ref();
	probHeads = PL_new_term_ref();

	module_t coin_pl = PL_new_module(PL_new_atom("coin.pl"));

	PL_cons_functor(ansHeads, pheads, probHeads); 
	double probVal;	
	PlCall("consult('coin.pl')");
	if (PL_call(ansHeads, coin_pl)) {
		PL_get_float(probHeads, &probVal);
		//std::cout << "The probability of heads is " << probVal << std::endl;
	}	
	return (jfloat) probVal;	
}


JNIEXPORT jfloat JNICALL Java_Coin_probHeads (JNIEnv * env, jclass cl) {

	init();
	term_t ansOutcome1, probOutcome1;
	functor_t pOutcome1;
	pOutcome1 = PL_new_functor(PL_new_atom("poutcome1"), 1);
	ansOutcome1 = PL_new_term_ref();
	probOutcome1 = PL_new_term_ref();

	module_t module_pl = PL_new_module(PL_new_atom("cplint.pl"));

	PL_cons_functor(ansOutcome1, pOutcome1, probOutcome1);
	PlCall("consult('cplint.pl')");
	double probVal;
	if (PL_call(ansOutcome1, module_pl)) {
		PL_get_float(probOutcome1, &probVal);
	}
	return (jfloat) probVal;
	
}


JNIEXPORT jfloat JNICALL Java_Coin_probHeads_back (JNIEnv * env, jclass cl) {
	init();
	term_t ansHeads, ansTails, probHeads, probTails;
	functor_t pheads, ptails;
	pheads = PL_new_functor(PL_new_atom("pheads"), 1);
	ptails = PL_new_functor(PL_new_atom("ptails"), 1);
	ansHeads = PL_new_term_ref();
	ansTails = PL_new_term_ref();
	probHeads = PL_new_term_ref();
	probTails = PL_new_term_ref();

	module_t coin_pl = PL_new_module(PL_new_atom("coin.pl"));

	PL_cons_functor(ansHeads, pheads, probHeads); 
	double probVal;	
	if (PL_call(ansHeads, coin_pl)) {
		PL_get_float(probHeads, &probVal);
		//std::cout << "The probability of heads is " << probVal << std::endl;
	}	

	PL_cons_functor(ansTails, ptails, probTails); 
	if (PL_call(ansTails, coin_pl)) {
		PL_get_float(probTails, &probVal);
		//std::cout << "The probability of tails is " << probVal << std::endl;
	}
	return (jfloat) probVal;	
}
