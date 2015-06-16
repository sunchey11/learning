package tij.enumerated;

//: enumerated/EnumClass.java
// Capabilities of the Enum class
import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

enum Shrubbery {
	GROUND, CRAWLING, HANGING
}

public class EnumClass {
	public static void main(String[] args) {
		System.out.println(Shrubbery.class.getSuperclass());//class java.lang.Enum
		System.out.println(Shrubbery.GROUND.getClass());//class tij.enumerated.Shrubbery
		System.out.println(Shrubbery.GROUND.getClass()==Shrubbery.class);//true
		for (Shrubbery s : Shrubbery.values()) {
			print(s + " ordinal: " + s.ordinal());
			printnb(s.compareTo(Shrubbery.CRAWLING) + " ");
			printnb(s.equals(Shrubbery.CRAWLING) + " ");
			print(s == Shrubbery.CRAWLING);
			print(s.getDeclaringClass());
			print(s.name());
			print("----------------------");
		}
		// Produce an enum value from a string name:
		for (String s : "HANGING CRAWLING GROUND".split(" ")) {
			Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
			print(shrub);
		}
	}
} /*
 * Output: GROUND ordinal: 0 -1 false false class Shrubbery GROUND
 * ---------------------- CRAWLING ordinal: 1 0 true true class Shrubbery
 * CRAWLING ---------------------- HANGING ordinal: 2 1 false false class
 * Shrubbery HANGING ---------------------- HANGING CRAWLING GROUND
 */// :~
