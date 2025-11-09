package iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DepartingCitiesExtendedIterator<String> implements ExtendedIterator<Object> {
	List<String> depCities= new ArrayList<String>();
	int i=0;
	public DepartingCitiesExtendedIterator(List<String> departingCities) {
		this.depCities=departingCities;
	}

	@Override
	public boolean hasNext() {
		return i<=depCities.size()-1;
		
	}

	@Override
	public Object next() {
		String itzuli= depCities.get(i);
		i=i+1;
		return itzuli;
	}

	@Override
	public Object previous() {
		String itzuli= depCities.get(i);
		i=i-1;
		return itzuli;
	}

	@Override
	public boolean hasPrevious() {

		return i>=0;
	}

	@Override
	public void goFirst() {
		i=0;
	
	}

	@Override
	public void goLast() {
		i=depCities.size()-1;
		
	}
}
