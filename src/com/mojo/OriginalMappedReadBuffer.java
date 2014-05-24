/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


/*
 * The Original Code is HAT. The Initial Developer of the
 * Original Code is Bill Foote, with contributions from others
 * at JavaSoft/Sun.
 */

package com.mojo;

import com.sun.tools.hat.internal.parser.ReadBuffer;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Implementation of ReadBuffer using mapped file buffer
 *
 * @author A. Sundararajan
 */
class OriginalMappedReadBuffer implements ReadBuffer {
  private MappedByteBuffer buf;

  OriginalMappedReadBuffer(MappedByteBuffer buf) {
    this.buf = buf;
  }

  // factory method to create correct ReadBuffer for a given file
  static ReadBuffer create(RandomAccessFile file) throws IOException {
    FileChannel ch = file.getChannel();
    long size = ch.size();
    // if file size is more than 2 GB and when file mapping is
    // configured (default), use mapped file reader
    if (canUseFileMap() && (size <= Integer.MAX_VALUE)) {
      System.out.println("Using mapped file");
      MappedByteBuffer buf;
      try {
        buf = ch.map(FileChannel.MapMode.READ_ONLY, 0, size);
        ch.close();
        return new OriginalMappedReadBuffer(buf);
      } catch (IOException exp) {
        exp.printStackTrace();
        System.err.println("File mapping failed, will use direct read");
        // fall through
      }
    } // else fall through
    return new OriginalFileReadBuffer(file);
  }

  private static boolean canUseFileMap() {
    // set jhat.disableFileMap to any value other than "false"
    // to disable file mapping
    String prop = System.getProperty("jhat.disableFileMap");
    return prop == null || prop.equals("false");
  }

  private void seek(long pos) throws IOException {
    assert pos <= Integer.MAX_VALUE : "position overflow";
    buf.position((int) pos);
  }

  public synchronized void get(long pos, byte[] res) throws IOException {
    seek(pos);
    buf.get(res);
  }

  public synchronized char getChar(long pos) throws IOException {
    seek(pos);
    return buf.getChar();
  }

  public synchronized byte getByte(long pos) throws IOException {
    seek(pos);
    return buf.get();
  }

  public synchronized short getShort(long pos) throws IOException {
    seek(pos);
    return buf.getShort();
  }

  public synchronized int getInt(long pos) throws IOException {
    seek(pos);
    return buf.getInt();
  }

  public synchronized long getLong(long pos) throws IOException {
    seek(pos);
    return buf.getLong();
  }
}
