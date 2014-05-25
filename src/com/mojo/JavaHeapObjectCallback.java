package com.mojo;

import com.sun.tools.hat.internal.model.JavaHeapObject;

public abstract class JavaHeapObjectCallback {
  public abstract void run(JavaHeapObject javaObj, Accessor accessor) throws Exception;
}
