package interactr.cs.kuleuven.be.purecollections;

abstract class ICons<T> {

	abstract T getHead();
	
	abstract ICons<T> getTail();
	
	abstract Computation<ICons<T>> getTailThunk();
	
}
