package com.mojo;

import com.sun.tools.hat.internal.model.JavaHeapObject;
import com.sun.tools.hat.internal.model.JavaObject;
import com.sun.tools.hat.internal.model.JavaThing;
import com.sun.tools.hat.internal.oql.OQLEngine;

public class Accessor {
  private OQLEngine engine;

  public Accessor(OQLEngine engine) {
    this.engine = engine;
  }

  public JavaHeapObject get(String fieldName, JavaHeapObject javaObj) throws Exception {
    JavaObject o = (JavaObject) javaObj;
    JavaThing field = o.getField(fieldName);
    JavaHeapObject actualField = (JavaHeapObject) engine
        .call("unwrapJavaObject", new Object[]{field});

    return actualField;
  }
}
