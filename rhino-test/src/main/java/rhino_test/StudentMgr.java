package rhino_test;

import java.util.Map;

public class StudentMgr {
	public Student createStudent(int age, Map<String, String> map, int[] list, Object obj) {
		String string = map.get("a");
		Object string2 = map.get("x");
		Student student = new Student();
		student.setAge(10);
		return student;
	}
}
