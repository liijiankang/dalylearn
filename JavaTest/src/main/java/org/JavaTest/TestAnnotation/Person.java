package org.JavaTest.TestAnnotation;

@Table("person")
public class Person {
	@Column("name")
	private String name;
	@Column("gender")
	private String gender;
	@Column("addr")
	private String addr;
	@Column("email")
	private String email;
	@Column("age")
	private int age;
	
	
	public Person() {
		super();
	}
	public Person(String name, String gender, String addr, String email, int age) {
		super();
		this.name = name;
		this.gender = gender;
		this.addr = addr;
		this.email = email;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
