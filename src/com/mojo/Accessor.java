package com.mojo;

import com.sun.tools.hat.internal.model.JavaField;
import com.sun.tools.hat.internal.model.JavaHeapObject;
import com.sun.tools.hat.internal.model.JavaObject;
import com.sun.tools.hat.internal.model.JavaThing;
import com.sun.tools.hat.internal.model.JavaValueArray;
import com.sun.tools.hat.internal.oql.OQLEngine;
import java.util.HashMap;
import java.util.Map;

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

  public String asString(String fieldName, JavaHeapObject o) throws Exception {
    return asString(get(fieldName, o));
  }

  public String asString(JavaHeapObject o) throws Exception {
    return new String((char[]) ((JavaValueArray) o).getElements());
  }

  public Map<String, JavaThing> fields(JavaHeapObject o) {
    JavaObject obj = (JavaObject) o;
    HashMap<String, JavaThing> allFields = new HashMap<>();
    JavaField[] fieldsForInstance = obj.getClazz().getFieldsForInstance();
    for (JavaField f : fieldsForInstance) {
      JavaThing fieldObj = obj.getField(f.getName());
      allFields.put(f.getName(), fieldObj);
    }
    return allFields;
  }

}
