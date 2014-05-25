MuchHeap
========

MuchHeap is Java heap analysis API built atop the JHAT source code. It loads heap dumps faster than JHAT. For example, MuchHeap loads a 240 MB heap dump in 10 secs, as opposed to 130 secs for the original JHAT.
MuchHeap is still very much a work in progress, but it's intent is to expose a useful API for developers to automate rules for analysing heap dumps. Work will continue on making MuchHeap loading/processing times faster.

Here's an example, which prints out an error message if the heap dump has more than 6 threads.

  Snapshot snapshot = new NIOHeapLoader().load("C:/path/to/heap.bin");
  OQLEngine oqlEngine = new OQLEngine(snapshot);
  CountingVisitor counter = new CountingVisitor();

  oqlEngine.executeQuery("select t from java.lang.Thread t", counter);
  if (counter.getCount() > 6) System.out.println("Too many threads!!!");

MuchHeap is very indebted to the JHAT developers, whose code it reuses profusely.
