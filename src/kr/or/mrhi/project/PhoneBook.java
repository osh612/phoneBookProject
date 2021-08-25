package kr.or.mrhi.project;

import java.util.Objects;

public class PhoneBook {
	private String phoneNumber; // 전화번호
	private String name; // 성명
	private String gender; // 성별
	private String job; // 직업
	private String birthday; // 생년월일
	private int age; // 나이

	public PhoneBook(String phoneNumber, String name, String gender, String job, String birthday, int age) {
		super();
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.gender = gender;
		this.job = job;
		this.birthday = birthday;
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		String year = this.birthday.substring(0, 4);
		String month = this.birthday.substring(5, 7);
		String day = this.birthday.substring(8);
		String strBirthDay = year + "년 " + month + "월 " + day + "일";

		return phoneNumber + "\t" + name + "\t" + gender + "\t" + job + "\t" + strBirthDay + "\t" + age + "세";
	}

	@Override
	public int hashCode() {
		return Objects.hash(phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhoneBook) {
			PhoneBook phonebook = (PhoneBook) obj;
			return this.phoneNumber.equals(phonebook.getPhoneNumber());
		}

		return true;
	}

}
