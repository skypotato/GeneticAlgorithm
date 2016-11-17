package GA;

import java.util.*;

import javax.swing.event.ChangeListener;

public class GeneticAlgorithmExample {

	public static int generationNum = 1; // 세대
	public static int populationSize = 100; // 한 세대 인구 수
	public static int coupleNum = 10; // 부모 수;
	public static String answer = "tobeornottobe"; // 답

	public static GeneticAlgorithmExample ga = new GeneticAlgorithmExample();
	public static ArrayList<String> generationArr = new ArrayList<String>();
	public static ArrayList<Child> childArr = new ArrayList<Child>();
	public static ArrayList<String> parentsArr = new ArrayList<String>();

	public void fitnessFunction(List list) { // 적합도
		childArr.clear();
		for (int i = 0; i < list.size(); i++) {
			Child child = new Child();
			int fitnessNum = 0;
			for (int j = 0; j < answer.length(); j++) {
				if (answer.charAt(j) == list.get(i).toString().charAt(j))
					fitnessNum++;
			}
			child.setStr(list.get(i).toString());
			child.setNum(fitnessNum);
			childArr.add(child);
		}
		Comparator comparator = new Comparator();
		Collections.sort(childArr, comparator);
	}

	public void selection(ArrayList<Child> list) { // 선택 매커니즘
		parentsArr.clear();
		int i = 0;
		while (parentsArr.size() < coupleNum * 0.6) {
			if (!parentsArr.contains(list.get(i % populationSize).getStr()))
				parentsArr.add(list.get(i % populationSize).getStr());
			i++;
		}
		i = populationSize / 2;
		while (parentsArr.size() < coupleNum * 0.9) {
			if (!parentsArr.contains(list.get(i % populationSize).getStr()))
				parentsArr.add(list.get(i % populationSize).getStr());
			i++;
		}
		i = (int) (populationSize * 0.8);
		while (parentsArr.size() < coupleNum) {
			if (!parentsArr.contains(list.get(i % populationSize).getStr()))
				parentsArr.add(list.get(i % populationSize).getStr());
			i++;
		}
	}

	public void crossOver() { // 교배
		generationArr.clear();
		for (int i = 0; i < parentsArr.size() * 0.7; i++) {
			generationArr.add(parentsArr.get(i));
		}
		for (int i = 0; generationArr.size() < populationSize; i++) {
			int ran = (int) (Math.random() * parentsArr.size());
			String str1 = "";
			String str2 = "";
			String temp = "";

			while (i % coupleNum == ran) {
				ran = (int) (Math.random() * parentsArr.size());
			}
			str1 = parentsArr.get(i % parentsArr.size());
			str2 = parentsArr.get(ran);
			for (int j = 0; j < str1.length(); j++) {
				ran = (int) (Math.random() * 2);
				if (ran == 0)
					temp += str1.charAt(j);
				else
					temp += str2.charAt(j);
			}
			ran = (int) (Math.random() * 100);
			if (ran == 0)
				temp = ga.mutation(temp);
			if (!generationArr.contains(temp))
				generationArr.add(temp);
		}
	}

	public String mutation(String str) { // 돌연변이
		String temp = "";
		for (int i = 0; i < str.length(); i++) {
			int ran = (int) (Math.random() * 2);
			if (ran == 0)
				temp += str.charAt(i);
			else
				temp += (char) ((Math.random() * 26) + 97);
		}
		return temp;
	}

	public void printArr(ArrayList<Child> list) { // 배열출력
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getStr() + " : " + list.get(i).getNum());
		}
	}

	public static void main(String[] args) {
		while (generationArr.size() < populationSize) {
			String temp = "";
			for (int i = 0; i < answer.length(); i++) {
				temp += (char) ((Math.random() * 26) + 97);
			}
			if (!generationArr.contains(temp))
				generationArr.add(temp);
		}

		while (!generationArr.contains(answer)) {
			System.out.println("===== " + generationNum + " generation =====");
			ga.fitnessFunction(generationArr);
			ga.printArr(childArr);
			ga.selection(childArr);
			ga.crossOver();
			generationNum++;
			Scanner scanner = new Scanner(System.in);
			// scanner.nextLine();
		}

		System.out.println("===== " + generationNum + " generation =====");
		ga.fitnessFunction(generationArr);
		ga.printArr(childArr);
	}
}
