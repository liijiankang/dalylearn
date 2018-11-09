package org.JavaTest.TestAnnotation;

public class Filter {
	public static String getSQL(String filter) throws Exception {
		Class<?> filterClass = Class.forName(filter);
		boolean annotationPresent = filterClass.isAnnotationPresent(Table.class);
		if (!annotationPresent) {
			return null;
		}else {
			
		}
		return null;
	}
}
