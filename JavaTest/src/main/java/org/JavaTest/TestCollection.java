package org.JavaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.JavaTest.model.Apple;
import org.JavaTest.model.Banana;
import org.JavaTest.model.Oriange;
import org.apache.commons.lang3.tuple.Pair;
public class TestCollection {
	
	/**
	 * 测试一下Java1.8的stream用法
	 * 通过这个例子可以看出来stream能把集合中的每一个元素传递进来,map是将stream传递进来的每个元素进行function操作.collect将操作后的元素组装厂一个集合.
	 */
	public static void testStream() {
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
	
	/**
	 * 测试collection方法
	 * 通过这个列子,可以看出来Arrays.asList是定长的,不能再添加元素.但是ArrayList是可以通过Collections.addAll添加元素的
	 */
	public static void testCollection() {
		List<Object> arrayList1 = Arrays.asList(new Apple(),new Oriange());
		List<Banana>  arrayList2 = Arrays.asList(new Banana("banana",3,2.5f));
		for (Banana banana : arrayList2) {
			System.out.println(banana.getName());
		}
		List<?> colletctors = arrayList1.stream()
		.map(x-> x.getClass())
		.collect(Collectors.toList());
		for (Object object : colletctors) {
			System.out.println(object);
		}
		Banana banana = new Banana();
//		Collection collection = new ArrayList();
		ArrayList arrayList3 = new ArrayList();
		boolean collection1 = Collections.addAll(arrayList3, 3,new Banana());
		System.out.println(collection1);
		for (Object object : arrayList3) {
			System.out.println(object);
		}
	}
	
	/**
	 * 可以把一个集合利用迭代器的方法查看里面的元素
	 * 测试一下iterator删除的操作
	 * 测试iterator用法
	 */
	public static void testList() {
		
		ArrayList<Integer> list1 = new ArrayList();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		list1.add(5);
		Iterator<Integer> it1 = list1.iterator();
		while(it1.hasNext()) {
			System.out.print(it1.next()+"  ");
		}
		System.out.println();
		
		it1 = list1.iterator();
		for (int i = 0; i < 3; i++) {
			it1.next();
			it1.remove();
		}
		for (Object object : list1) {
			System.out.println(object);
		}
		
	}
	
	/**
	 * 测试不同容器利用iterator访问其元素
	 */
	public static void TestDifContainer() {
		HashSet<Integer> set = new HashSet();
		set.add(6);
		set.add(7);
		set.add(8);
		set.add(9);
		set.add(10);
		set.add(11);
		ArrayList<Integer> list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		Iterator<Integer> setIt = set.iterator();
		Iterator<Integer> listIt = list.iterator();
		testIterator(setIt);
		testIterator(listIt);
	}
	
	/**
	 * iterator统一访问不同类型的容器,注意泛型
	 * @param it
	 */
	public static void testIterator(Iterator<Integer> it) {
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	/**
	 * 测试linkList的用法
	 */
	public static void testLinkList() {
		LinkedList<Integer> list = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i+1);
		}
		for (Integer integer : list) {
			System.out.print(integer+"  ");
		}
		System.out.println();
		System.out.println("getFirst:"+list.getFirst());
		System.out.println("getLast:"+list.getLast());
		System.out.println("element:"+list.element());
		System.out.println("peek:"+list.peek());
		System.out.println("remove:"+list.remove());
		System.out.println("removeFirst:"+list.removeFirst());
		System.out.println("removeLast:"+list.removeLast());
		System.out.println("poll:"+list.poll());
		System.out.println("pollFirst:"+list.pollFirst());
		System.out.println("pollLast:"+list.pollLast());
		for (Integer integer : list) {
			System.out.print(integer+" ");
		}
		System.out.println();
		//获取指定位置的元素
		System.out.println("get(2):"+list.get(2));
		System.out.println("remove(2):"+list.remove(2));
		for (Integer integer : list) {
			System.out.print(integer+" ");
		}
		System.out.println();
		//在首位增加元素
		list.push(0);
		//在指定位置增加元素
		list.add(1, 1);
		for (Integer integer : list) {
			System.out.print(integer+" ");
		}
		System.out.println();
		
	}
	
	
	
	public static void main(String[] args) {
//		testStream();
//		testCollection();
//		testList();
//		TestDifContainer();
		testLinkList();
	}
	
}
