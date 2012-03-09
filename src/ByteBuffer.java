// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.math.BigInteger;
import sign.Signlink;

public class ByteBuffer extends SubNode {

    public static ByteBuffer createBuffer(int sizeType) {
        ByteBuffer buffer = new ByteBuffer();
        buffer.offset = 0;
        if(sizeType == 0)
            buffer.payload = new byte[100];
        else if(sizeType == 1)
            buffer.payload = new byte[5000];
        else
            buffer.payload = new byte[30000];
        return buffer;
    }

    public ByteBuffer() {
    }

    public ByteBuffer(byte src[]) {
        payload = src;
        offset = 0;
    }

    public void putPacket(int id) {
        payload[offset++] = (byte)(id + isaacCipher.poll());
    }

    public void put(int value) {
        payload[offset++] = (byte) value;
    }

    public void putWord(int value) {
        payload[offset++] = (byte)(value >> 8);
        payload[offset++] = (byte)value;
    }

    public void putWordLE(int value) {
        payload[offset++] = (byte) value;
        payload[offset++] = (byte)(value >> 8);
    }

    public void putTri(int value) {
        payload[offset++] = (byte)(value >> 16);
        payload[offset++] = (byte)(value >> 8);
        payload[offset++] = (byte) value;
    }

    public void putDword(int value) {
        payload[offset++] = (byte)(value >> 24);
        payload[offset++] = (byte)(value >> 16);
        payload[offset++] = (byte)(value >> 8);
        payload[offset++] = (byte)value;
    }

    public void putDwordLE(int value) {
        payload[offset++] = (byte)value;
        payload[offset++] = (byte)(value >> 8);
        payload[offset++] = (byte)(value >> 16);
        payload[offset++] = (byte)(value >> 24);
    }

    public void putQword(long l) {
        try {
            payload[offset++] = (byte)(int)(l >> 56);
            payload[offset++] = (byte)(int)(l >> 48);
            payload[offset++] = (byte)(int)(l >> 40);
            payload[offset++] = (byte)(int)(l >> 32);
            payload[offset++] = (byte)(int)(l >> 24);
            payload[offset++] = (byte)(int)(l >> 16);
            payload[offset++] = (byte)(int)(l >> 8);
            payload[offset++] = (byte)(int)l;
        } catch(RuntimeException runtimeexception) {
            Signlink.reportError("14395, " + l + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void putString(String s) {
        s.getBytes(0, s.length(), payload, offset);
        offset += s.length();
        payload[offset++] = 10;
    }

    public void put(byte bytes[], int i, int j) {
        for(int k = j; k < j + i; k++)
            payload[offset++] = bytes[k];
    }

    public void endVarByte(int i, byte byte0) {
        payload[offset - i - 1] = (byte) i;
    }

    public int getUbyte() {
        return payload[offset++] & 0xff;
    }

    public byte getByte() {
        return payload[offset++];
    }

    public int getUword() {
        offset += 2;
        return ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
    }

    public int getWord() {
        offset += 2;
        int i = ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
        if(i > 32767)
            i -= 0x10000;
        return i;
    }

    public int getTri() {
        offset += 3;
        return ((payload[offset - 3] & 0xff) << 16) + ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
    }

    public int getDword() {
        offset += 4;
        return ((payload[offset - 4] & 0xff) << 24) + ((payload[offset - 3] & 0xff) << 16) + ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
    }

    public long getQword() {
        long l = (long)getDword() & 0xffffffffL;
        long l1 = (long)getDword() & 0xffffffffL;
        return (l << 32) + l1;
    }

    public String getString() {
        int i = offset;
        while(payload[offset++] != 10) ;
        return new String(payload, i, offset - i - 1);
    }

    public byte[] getStringBytes() {
        int i = offset;
        while(payload[offset++] != 10) ;
        byte bytes[] = new byte[offset - i - 1];
        for(int j = i; j < offset - 1; j++)
            bytes[j - i] = payload[j];
        return bytes;
    }

    public void get(byte dest[], int off, int len) {
        for(int l = off; l < off + len; l++)
            dest[l] = payload[offset++];
    }

    public void initBitAccess() {
        bitOffset = offset * 8;
    }

    public int getBits(int amt) {
        int k = bitOffset >> 3;
        int l = 8 - (bitOffset & 7);
        int i1 = 0;
        bitOffset += amt;
        for(; amt > l; l = 8) {
            i1 += (payload[k++] & BIT_MASKS[l]) << amt - l;
            amt -= l;
        }
        if(amt == l)
            i1 += payload[k] & BIT_MASKS[l];
        else
            i1 += payload[k] >> l - amt & BIT_MASKS[amt];
        return i1;
    }

    public void endBitAccess() {
        offset = (bitOffset + 7) / 8;
    }

    public int getSmartA() {
        int i = payload[offset] & 0xff;
        if(i < 128)
            return getUbyte() - 64;
        else
            return getUword() - 49152;
    }

    public int getSmartB() {
        int i = payload[offset] & 0xff;
        if(i < 128)
            return getUbyte();
        else
            return getUword() - 32768;
    }

    public void applyRsa(BigInteger exponent, BigInteger modulus) {
        int i = offset;
        offset = 0;
        byte src[] = new byte[i];
        get(src, 0, i);
        BigInteger biginteger2 = new BigInteger(src);
        BigInteger biginteger3 = biginteger2.modPow(exponent, modulus);
        byte bytes[] = biginteger3.toByteArray();
        offset = 0;
        put(bytes.length);
        put(bytes, bytes.length, 0);
    }

    public void putByteA(int value) {
        payload[offset++] = (byte) (-value);
    }

    public void putByteB(int value) {
        payload[offset++] = (byte) (128 - value);
    }

    public int getByte128() {
        return payload[offset++] - 128 & 0xff;
    }

    public int getUbyteA() {
        return -payload[offset++] & 0xff;
    }

    public int getUbyteB() {
        return 128 - payload[offset++] & 0xff;
    }

    public byte getByteA() {
        return (byte)(-payload[offset++]);
    }

    public byte getByteB() {
        return (byte)(128 - payload[offset++]);
    }

    public void putWord128(int value) {
        payload[offset++] = (byte)(value >> 8);
        payload[offset++] = (byte)(value + 128);
    }

    public void putWordLE128(int value) {
        payload[offset++] = (byte)(value + 128);
        payload[offset++] = (byte)(value >> 8);
    }

    public int getUwordLE() {
        offset += 2;
        return ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] & 0xff);
    }

    public int getUword128() {
        offset += 2;
        return ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] - 128 & 0xff);
    }

    public int getUwordLE128() {
        offset += 2;
        return ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] - 128 & 0xff);
    }

    public int getWordLE() {
        offset += 2;
        int j = ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] & 0xff);
        if(j > 32767)
            j -= 0x10000;
        return j;
    }

    public int getWordLE128() {
        offset += 2;
        int j = ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] - 128 & 0xff);
        if(j > 32767)
            j -= 0x10000;
        return j;
    }

    public int getDwordA() {
        offset += 4;
        return ((payload[offset - 2] & 0xff) << 24) + ((payload[offset - 1] & 0xff) << 16) + ((payload[offset - 4] & 0xff) << 8) + (payload[offset - 3] & 0xff);
    }

    public int getDwordB() {
        offset += 4;
        return ((payload[offset - 3] & 0xff) << 24) + ((payload[offset - 4] & 0xff) << 16) + ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] & 0xff);
    }

    public void putReverse128(byte src[], int off, int len) {
        for(int pos = (off + len) - 1; pos >= off; pos--)
            payload[offset++] = (byte) (src[pos] + 128);
    }

    public void getReverse(byte dest[], int off, int len) {
        for(int k = (off + len) - 1; k >= off; k--)
            dest[k] = payload[offset++];
    }
    
    public byte payload[];
    public int offset;
    public int bitOffset;	
    public static final int BIT_MASKS[] = {
        0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 
        1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff, 0x3ffff, 0x7ffff, 
        0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 
        0x3fffffff, 0x7fffffff, -1
    };	
    public IsaacCipher isaacCipher;

}
