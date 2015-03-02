package tij.nio;

//: io/FileLocking.java
import java.nio.channels.*;
import java.util.concurrent.*;
import java.io.*;

public class FileLocking {
  public static void main(String[] args) throws Exception {
    FileOutputStream fos= new FileOutputStream("file1.txt");
    FileLock fl = fos.getChannel().tryLock();
    if(fl != null) {
      System.out.println("Locked File");
      TimeUnit.MILLISECONDS.sleep(1000000);
      fl.release();
      System.out.println("Released Lock");
    }else{
    	System.out.println("error");
    }
    fos.close();
  }
} /* Output:
Locked File
Released Lock
*///:~
