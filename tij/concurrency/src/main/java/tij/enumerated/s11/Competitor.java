//: enumerated/Competitor.java
// Switching one enum on another.
package tij.enumerated.s11;

public interface Competitor<T extends Competitor<T>> {
  Outcome compete(T competitor);
} ///:~
