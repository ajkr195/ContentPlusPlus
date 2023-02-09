package com.contentplusplus.springboot.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class GetNextPreviousLongValue {

	public static void main(String[] args) {
		
		Long input = 5l;

		System.out.println(getnextprev(input, "getnext"));
		//getnextprev(input, "getprev");
		//getNextPrevious(input, "getNext", "getPrev");

	}

	public static Long getnextprev(Long input, String getNextOrPrev) {
		TreeSet<Long> set = LongStream.rangeClosed(1, 5).boxed().collect(Collectors.toCollection(TreeSet::new));

		if (!set.contains(input)) {
			//System.out.println("....Invalid input....");
			return null;
		}
		if (input.equals(set.first())) {
			//System.out.println(input + " - is MIN");
			return null;
		} else if (input.equals(set.last())) {
			//System.out.println(input + " - is MAX");
			return null;
		} else {
			
			if(getNextOrPrev.equalsIgnoreCase("getnext")) {
				System.out.println("Next  - " + set.tailSet(input, false).first());
				return input;
			} else if(getNextOrPrev.equalsIgnoreCase("getprev")) {
				System.out.println("Previous - " + set.headSet(input).last());
				return input;
			}
			
		}
		return null;
	}

	public static void getNextPrevious(Long myInt, String getNext, String getPrevious) {

		List<Long> intList = new ArrayList<>();

		intList.add(1l);
		intList.add(2l);
		intList.add(3l);
		intList.add(4l);
		intList.add(5l);
		intList.add(6l);
		intList.add(7l);
		intList.add(8l);
		intList.add(9l);
		intList.add(10l);

		Collections.sort(intList);

		Integer index = intList.indexOf(myInt); // get the index where the elment is

		if (index.equals(-1)) { // -1 means there is no element in the list with that value
			System.out.println("....Invalid input....");
		} else if (index.equals(0)) { // if equals 0 means it's the first element in the list
			System.out.println("Supplied " + myInt + " - is the MIN value in the list.");
		} else if (index.equals(intList.size() - 1)) { // if equals it's size -1 means it's the last element in the lsit
			System.out.println("Supplied " + myInt + " - is the MAX value in the list.");
		} else { // everything good
			if (getNext.length() != 0) {
				System.out.println("Next higher value is - " + intList.get(index + 1));
			}

			if (getPrevious.length() != 0) {
				System.out.println("Next lower value is - " + intList.get(index - 1));
			}
		}

	}

}
