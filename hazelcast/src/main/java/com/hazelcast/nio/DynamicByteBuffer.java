/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @mdogan 12/31/12
 */
public final class DynamicByteBuffer {

    private ByteBuffer buffer;

    public DynamicByteBuffer(int cap, boolean useDirectBuffer) {
        buffer = useDirectBuffer ? ByteBuffer.allocateDirect(cap) : ByteBuffer.allocate(cap);
    }

    public DynamicByteBuffer(byte[] array) {
        buffer = ByteBuffer.wrap(array);
    }

    public DynamicByteBuffer(byte[] array, int offset, int length) {
        buffer = ByteBuffer.wrap(array, offset, length);
    }

    public DynamicByteBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public DynamicByteBuffer compact() {
        buffer.compact();
        return this;
    }

    public byte get() {
        return buffer.get();
    }

    public byte get(int index) {
        return buffer.get(index);
    }

    public DynamicByteBuffer get(byte[] dst) {
        buffer.get(dst);
        return this;
    }

    public DynamicByteBuffer get(byte[] dst, int offset, int length) {
        buffer.get(dst, offset, length);
        return this;
    }

    public char getChar() {
        check();
        return buffer.getChar();
    }

    public char getChar(int index) {
        check();
        return buffer.getChar(index);
    }

    public double getDouble() {
        check();
        return buffer.getDouble();
    }

    public double getDouble(int index) {
        check();
        return buffer.getDouble(index);
    }

    public float getFloat() {
        check();
        return buffer.getFloat();
    }

    public float getFloat(int index) {
        check();
        return buffer.getFloat(index);
    }

    public int getInt() {
        check();
        return buffer.getInt();
    }

    public int getInt(int index) {
        check();
        return buffer.getInt(index);
    }

    public long getLong() {
        check();
        return buffer.getLong();
    }

    public long getLong(int index) {
        check();
        return buffer.getLong(index);
    }

    public short getShort() {
        check();
        return buffer.getShort();
    }

    public short getShort(int index) {
        check();
        return buffer.getShort(index);
    }

    public DynamicByteBuffer put(byte b) {
        ensureSize(1);
        buffer.put(b);
        return this;
    }

    public DynamicByteBuffer put(int index, byte b) {
        ensureSize(1);
        buffer.put(index, b);
        return this;
    }

    public DynamicByteBuffer put(byte[] src) {
        ensureSize(src.length);
        buffer.put(src);
        return this;
    }

    public DynamicByteBuffer put(byte[] src, int offset, int length) {
        ensureSize(length);
        buffer.put(src, offset, length);
        return this;
    }

    public DynamicByteBuffer put(ByteBuffer src) {
        ensureSize(src.remaining());
        buffer.put(src);
        return this;
    }

    public DynamicByteBuffer putChar(int index, char value) {
        ensureSize(2);
        buffer.putChar(index, value);
        return this;
    }

    public DynamicByteBuffer putChar(char value) {
        ensureSize(2);
        buffer.putChar(value);
        return this;
    }

    public DynamicByteBuffer putDouble(int index, double value) {
        ensureSize(8);
        buffer.putDouble(index, value);
        return this;
    }

    public DynamicByteBuffer putDouble(double value) {
        ensureSize(8);
        buffer.putDouble(value);
        return this;
    }

    public DynamicByteBuffer putFloat(int index, float value) {
        ensureSize(4);
        buffer.putFloat(index, value);
        return this;
    }

    public DynamicByteBuffer putFloat(float value) {
        ensureSize(4);
        buffer.putFloat(value);
        return this;
    }

    public DynamicByteBuffer putInt(int index, int value) {
        ensureSize(4);
        buffer.putInt(index, value);
        return this;
    }

    public DynamicByteBuffer putInt(int value) {
        ensureSize(4);
        buffer.putInt(value);
        return this;
    }

    public DynamicByteBuffer putLong(int index, long value) {
        ensureSize(8);
        buffer.putLong(index, value);
        return this;
    }

    public DynamicByteBuffer putLong(long value) {
        ensureSize(8);
        buffer.putLong(value);
        return this;
    }

    public DynamicByteBuffer putShort(int index, short value) {
        ensureSize(2);
        buffer.putShort(index, value);
        return this;
    }

    public DynamicByteBuffer putShort(short value) {
        ensureSize(2);
        buffer.putShort(value);
        return this;
    }

    private void ensureSize(int i) {
        check();
        if (buffer.remaining() < i) {
            int newCap = Math.max(buffer.limit() << 1, buffer.limit() + i);
            ByteBuffer newBuffer = buffer.isDirect() ? ByteBuffer.allocateDirect(newCap) : ByteBuffer.allocate(newCap);
            newBuffer.order(buffer.order());
            buffer.flip();
            newBuffer.put(buffer);
            buffer = newBuffer;
        }
    }

    public DynamicByteBuffer duplicate() {
        check();
        return new DynamicByteBuffer(buffer.duplicate());
    }

    public DynamicByteBuffer slice() {
        check();
        return new DynamicByteBuffer(buffer.slice());
    }

    public void clear() {
        check();
        buffer.clear();
    }

    public void flip() {
        check();
        buffer.flip();
    }

    public int limit() {
        check();
        return buffer.limit();
    }

    public Buffer limit(int newLimit) {
        check();
        return buffer.limit(newLimit);
    }

    public Buffer mark() {
        check();
        return buffer.mark();
    }

    public int position() {
        check();
        return buffer.position();
    }

    public Buffer position(int newPosition) {
        check();
        return buffer.position(newPosition);
    }

    public int remaining() {
        check();
        return buffer.remaining();
    }

    public Buffer reset() {
        check();
        return buffer.reset();
    }

    public Buffer rewind() {
        check();
        return buffer.rewind();
    }

    public int capacity() {
        check();
        return buffer.capacity();
    }

    public boolean hasRemaining() {
        check();
        return buffer.hasRemaining();
    }

    public byte[] array() {
        check();
        return buffer.array();
    }

    public ByteOrder order() {
        check();
        return buffer.order();
    }

    public ByteBuffer order(ByteOrder order) {
        check();
        return buffer.order(order);
    }

    public void close() {
        buffer = null;
    }

    private void check() {
        if (buffer == null) {
            throw new IllegalStateException("Buffer is closed!");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DynamicByteBuffer{");
        if (buffer != null) {
            sb.append("position=").append(buffer.position());
            sb.append(", limit=").append(buffer.limit());
            sb.append(", capacity=").append(buffer.capacity());
            sb.append(", order=").append(buffer.order());
            sb.append(", direct=").append(buffer.isDirect());
        } else {
            sb.append("<CLOSED>");
        }
        sb.append('}');
        return sb.toString();
    }
}