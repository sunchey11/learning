package c2;

import java.util.ArrayList;
import java.util.List;

/**
 * jdk1.6会出现 java.lang.OutOfMemoryError: PermGen space
 * jdk1.7会出现 java.lang.OutOfMemoryError: Java heap space
 * 因为jdk1.6时，常量池在Permgen中，jdk1.7时，常量池在heap中
 * VM Args：-XX:PermSize=10M -XX:MaxPermSize=10M
 * @author zzm
 */
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		// 使用List保持着常量池引用，避免Full GC回收常量池行为
		List<String> list = new ArrayList<String>();
		// 10MB的PermSize在integer范围内足够产生OOM了
		int i = 0; 
		while (true) {
			list.add(String.valueOf(i++).intern());
		}
	}
}

