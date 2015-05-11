package c3;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -verbose:gc -XX:+PrintGCDetails
 * @author zzm
 */
public class GCLog1 {

	static class OOMObject {
	}

	public static void main(String[] args) {
		while (true) {
			new OOMObject();
		}
	}
}

