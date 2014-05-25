package com.mojo;

import com.sun.tools.hat.internal.model.JavaField;
import com.sun.tools.hat.internal.model.JavaHeapObject;
import com.sun.tools.hat.internal.model.JavaObject;
import com.sun.tools.hat.internal.model.JavaThing;
import com.sun.tools.hat.internal.model.Snapshot;
import com.sun.tools.hat.internal.oql.OQLEngine;
import com.sun.tools.hat.internal.oql.OQLException;
import com.sun.tools.hat.internal.oql.ObjectVisitor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
  public static void main(String[] args) throws IOException, OQLException {
    final Snapshot snapshot = new NIOHeapLoader().load("C:/Projects/eclipse-mat/heap.bin");
//    Snapshot snapshot = new HeapLoader().load("C:/Projects/eclipse-mat/heap.bin");

    OQLEngine oqlEngine = new OQLEngine(snapshot);
    CountingVisitor counter = new CountingVisitor();
    ObjectVisitor inspector = new InspectingVisitor(oqlEngine, snapshot,
        new JavaHeapObjectCallback() {
          @Override public void run(JavaHeapObject obj, Accessor accessor) throws Exception {
            if (obj.toString().contains("java.lang.Thread")) {
              System.out.println("It is a thread!!");
              System.out.println("NAME=" + accessor.asString("name", obj));
              Map<String, JavaThing> fields = accessor.fields(obj);
              for (String k : fields.keySet()) {
                System.out.println(k + " = " + fields.get(k));
              }
            }
          }
        }
    );

    ObjectVisitor compositeVisitor = new CompositeVisitor(counter, inspector);
    oqlEngine.executeQuery("select t from java.lang.Thread t", compositeVisitor);
    if (counter.getCount() > 6) System.out.println("Too many threads!!!");
    System.out.println(counter.getCount());
  }
}
