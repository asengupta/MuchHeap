package com.mojo;

import com.sun.tools.hat.internal.model.JavaHeapObject;
import com.sun.tools.hat.internal.model.JavaValueArray;
import com.sun.tools.hat.internal.model.Snapshot;
import com.sun.tools.hat.internal.oql.OQLEngine;
import com.sun.tools.hat.internal.oql.OQLException;
import com.sun.tools.hat.internal.oql.ObjectVisitor;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, OQLException {
    Snapshot snapshot = new NIOHeapLoader().load("C:/Projects/eclipse-mat/heap.bin");
//    Snapshot snapshot = new HeapLoader().load("C:/Projects/eclipse-mat/heap.bin");

    OQLEngine oqlEngine = new OQLEngine(snapshot);
    CountingVisitor counter = new CountingVisitor();
//    ObjectVisitor inspector = new InspectingVisitor(oqlEngine, snapshot,
//        new JavaHeapObjectCallback() {
//          @Override public void run(JavaHeapObject javaObj, Accessor accessor) throws Exception {
//            if (javaObj.toString().contains("java.lang.Thread")) {
//              System.out.println("It is a thread!!");
//              JavaValueArray name = (JavaValueArray) accessor.get("name", javaObj);
//              System.out.println(String.valueOf((char[]) name.getElements()));
//            }
//          }
//        });
//    ObjectVisitor compositeVisitor = new CompositeVisitor(counter, inspector);
    oqlEngine.executeQuery("select t from java.lang.Thread t", counter);
    if (counter.getCount() > 6) System.out.println("Too many threads!!!");
    System.out.println(counter.getCount());
  }
}
