package org.JavaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMap {

	public static class Apple {
		String name;
		String weight;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		
	}

	public static class Orange{
		String name;
		String weight;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		
	}

	public static class AppleAndOrangesWhithoutGenerics{
		public static void main(String[] args) {
			ArrayList apples = new ArrayList<Apple>();
			for(int i = 0 ;i < 3;i++) {
				apples.add(new Apple());
			}
//			apples.add(new Orange());
			for(int i = 0;i<apples.size();i++) {
				String name = ((Apple)apples.get(i)).name;
			}
		}
	}
	
	
}
