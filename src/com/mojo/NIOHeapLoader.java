package com.mojo;

import com.sun.tools.hat.internal.model.Snapshot;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NIOHeapLoader {
  public Snapshot load(String fileName) throws IOException {
    long start = System.currentTimeMillis();
    FileInputStream memoryMappedFile = new FileInputStream(fileName);
    FileChannel channel = memoryMappedFile.getChannel();
    MappedByteBuffer out = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
    out.load();
    Snapshot snapshot = new MMapHProfReader(out).read();
    snapshot.resolve(true);
    long end = System.currentTimeMillis();
    System.out.println("Loaded snapshot in " + (end - start) + " ms.");
    return snapshot;
  }
}
