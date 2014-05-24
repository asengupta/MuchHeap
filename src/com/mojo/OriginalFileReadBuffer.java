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

/**
 * Implementation of ReadBuffer using a RandomAccessFile
 *
 * @author A. Sundararajan
 */
class OriginalFileReadBuffer implements ReadBuffer {
  // underlying file to read
  private RandomAccessFile file;

  OriginalFileReadBuffer(RandomAccessFile file) {
    this.file = file;
  }

  private void seek(long pos) throws IOException {
    file.getChannel().position(pos);
  }

  public synchronized void get(long pos, byte[] buf) throws IOException {
    seek(pos);
    file.read(buf);
  }

  public synchronized char getChar(long pos) throws IOException {
    seek(pos);
    return file.readChar();
  }

  public synchronized byte getByte(long pos) throws IOException {
    seek(pos);
    return (byte) file.read();
  }

  public synchronized short getShort(long pos) throws IOException {
    seek(pos);
    return file.readShort();
  }

  public synchronized int getInt(long pos) throws IOException {
    seek(pos);
    return file.readInt();
  }

  public synchronized long getLong(long pos) throws IOException {
    seek(pos);
    return file.readLong();
  }
}
