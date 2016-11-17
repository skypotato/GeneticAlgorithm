package GA;

public class Comparator implements java.util.Comparator<Child> {

	@Override
	public int compare(Child first, Child second) {
		int firstNum = first.getNum();
		int secondNum = second.getNum();

		if (firstNum > secondNum) {
			return -1;
		} else if (firstNum < secondNum) {
			return 1;
		} else {
			return 0;
		}
	}

}
