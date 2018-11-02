package org.JavaTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

public class TestArray {
	public static void main(String[] args) {
		String teString = "hello,word:hello,java";
		List<Pair<String, String>> collect = Arrays.stream(teString.split(":"))
		.map(x -> {
			String[] splits = x.split(",");
			return Pair.of(splits[0],splits[1]);
		})
		.collect(Collectors.toList());
		for (Pair<String, String> pair : collect) {
			System.out.println(pair.getKey()+"  "+pair.getValue());
		}
	}
	
}
