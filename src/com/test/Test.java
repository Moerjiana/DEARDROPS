package com.test;


public class Test {
	public static void main(String[] args) {
		int[] a = new int[10];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		int[] b = new int[]{11,12,13,14,15};
		
		System.out.println("------原数据------");
		String aStr = "";
		for (int i = 0; i < a.length; i++) {
			aStr+=a[i]+",";
		}
		System.out.println(aStr);
		
		int oldsize = 2;
		int newsize = b.length;
		int zuobiao = 2;
		if (oldsize>newsize) {
			int diffsize = oldsize-newsize;
			//---------------------------------当现数据比原数据短时逻辑
			//在234换数,5开始往钱移
			for (int i = 0; i <= diffsize; i++) {
				a[i+zuobiao] = b[i]; 
			}
			//执行2遍,每遍往前挪一位,从5开始
			for (int i = 0; i < diffsize; i++) {
				for (int j = zuobiao+diffsize; j < a.length-1; j++) {
					a[j]=a[j+1];
					if (j == a.length-2) {
						a[j+1] = 0;
					}
				}
			}
		}else if(oldsize<newsize){
			int diffsize = newsize-oldsize;
			for (int i = 0; i <= oldsize; i++) {
				a[i+zuobiao] = b[i]; 
			}
			
			for (int i = 0; i < diffsize-1; i++) {
				for (int j = zuobiao+diffsize; j < a.length-1; j++) {
					int y = j-(zuobiao+diffsize);
					a[a.length-1-y] = a[a.length-2-y];
				}
			}
			for (int i = 0; i < diffsize-1; i++) {
				a[i+zuobiao+diffsize] = b[i+diffsize];
			}
		}
		//---------------------------------现数据比原数据长时逻辑
		//执行2遍,每遍往后挪一位,从5开始
//		for (int i = 0; i < 2; i++) {
//			for (int j = a.length-1; j > 5; j--) {
//				a[j]=a[j-1];
//				if (j == 6) {
//					
//				}
//			}
//		}

		System.out.println("----------------");
		aStr = "";
		for (int i = 0; i < a.length; i++) {
			aStr+=a[i]+",";
		}
		System.out.println(aStr);
	}
	
	public void test1(){
//		0x18D1C = 0x1C (MAGIC 长度) + 0x34 (头部长度) + 0x18CCC (0x00000003 之后的四字节)
		String a = "1C";
		String b = "E4";
		String c = "162C2";
		int d = Integer.parseInt(a, 16)+Integer.parseInt(b, 16)+Integer.parseInt(c, 16);
		System.out.println(d);//91074
		System.out.println(Integer.toHexString(d));//163c2
	}
}
