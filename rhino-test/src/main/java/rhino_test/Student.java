package rhino_test;

import java.util.List;
import java.util.Map;

public class Student {
	private String name = "bbq";
	private int age = 10;
	private long timeM = 110002222333331111l;
	private List<String> addresses;
	private String[] colors;

	public String[] getColors() {
		return colors;
	}

	public Student getMate() {
		return this;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	private Map<String, String> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getTimeM() {
		return timeM;
	}

	public void setTimeM(long timeM) {
		this.timeM = timeM;
	}

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	public Map<String, String> getUsers() {
		return users;
	}

	public void setUsers(Map<String, String> users) {
		this.users = users;
	}

}
