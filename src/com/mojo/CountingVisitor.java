package com.mojo;

import com.sun.tools.hat.internal.oql.ObjectVisitor;

public class CountingVisitor implements ObjectVisitor {
  int count = 0;

  @Override public boolean visit(Object o) {
    count++;
    return false;
  }

  public int getCount() {
    return count;
  }
}
