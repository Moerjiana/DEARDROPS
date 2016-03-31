package com.test;


public class Test {
	public static void main(String[] args) {
//		0x18D1C = 0x1C (MAGIC 长度) + 0x34 (头部长度) + 0x18CCC (0x00000003 之后的四字节)
		String a = "1C";
		String b = "E4";
		String c = "162C2";
		int d = Integer.parseInt(a, 16)+Integer.parseInt(b, 16)+Integer.parseInt(c, 16);
		System.out.println(d);//91074
		System.out.println(Integer.toHexString(d));//163c2
	}
}
