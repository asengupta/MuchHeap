package com.mojo;

import com.sun.tools.hat.internal.model.JavaHeapObject;
import com.sun.tools.hat.internal.model.JavaValueArray;
import com.sun.tools.hat.internal.model.Snapshot;
import com.sun.tools.hat.internal.oql.OQLEngine;
import com.sun.tools.hat.internal.oql.ObjectVisitor;
import java.util.Enumeration;

public class InspectingVisitor implements ObjectVisitor {
  private OQLEngine engine;
  private Snapshot snapshot;

  public InspectingVisitor(OQLEngine engine, Snapshot snapshot) {
    this.engine = engine;
    this.snapshot = snapshot;
  }

  @Override public boolean visit(Object o) {
    try {
      Object iterator = engine.call("wrapIterator", new Object[]{o, true});
      if (iterator instanceof Enumeration) {
        System.out.println("Was enumerator");
        output((Enumeration) iterator, engine);
      } else {
        JavaHeapObject javaObj = (JavaHeapObject) engine.call("unwrapJavaObject", new Object[]{o});
        System.out.println("Was Java object");
        output(javaObj, engine, snapshot);
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return true;
    }
  }

  private void output(JavaHeapObject javaObj, OQLEngine engine, Snapshot snapshot)
      throws Exception {
    if (javaObj.toString().contains("java.lang.Thread")) {
      System.out.println("It is a thread!!");
      JavaValueArray name = (JavaValueArray) new Accessor(engine).get("name", javaObj);
      System.out.println(String.valueOf((char[]) name.getElements()));
    }
  }

  private void output(Enumeration iterator, OQLEngine engine) throws Exception {
    while (iterator.hasMoreElements()) {
      Object o = iterator.nextElement();
      JavaHeapObject unwrapped = (JavaHeapObject) engine.call("unwrapJavaObject", new Object[]{o});
      System.out.println(o);
    }
  }
}
