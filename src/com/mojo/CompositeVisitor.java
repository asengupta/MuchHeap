package com.mojo;

import com.sun.tools.hat.internal.oql.ObjectVisitor;
import java.util.ArrayList;
import java.util.List;

public class CompositeVisitor implements ObjectVisitor {
  private List<ObjectVisitor> visitors = new ArrayList<ObjectVisitor>();

  public CompositeVisitor(ObjectVisitor... visitors) {
    for (ObjectVisitor v : visitors) {
      this.visitors.add(v);
    }
  }

  public CompositeVisitor(List<ObjectVisitor> visitors) {
    this.visitors.addAll(visitors);
  }

  public void add(ObjectVisitor visitor) {
    visitors.add(visitor);
  }

  @Override public boolean visit(Object o) {
    System.out.println("Called");
    for (ObjectVisitor visitor : visitors) {
      visitor.visit(o);
    }
    return false;
  }
}
