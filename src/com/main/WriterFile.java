package com.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriterFile {
	public static String url = "G:\\myFile\\galgame\\DEARDROPS\\data01000\\test\\000_00";
	public static String outUrl = "G:\\myFile\\galgame\\DEARDROPS\\data01000\\test\\out\\000_00.txt";
	public static String returnUrl = "G:\\myFile\\galgame\\DEARDROPS\\data01000\\test\\return\\000_00";
	
	static Logger logger = LoggerFactory.getLogger(WriterFile.class.getName());
	
	public static void main(String[] args) {
		Map<Integer, Integer> ifUpIndexMap = new HashMap<Integer,Integer>();//是否已进行过长度更新操作
		Map<Integer, Integer> upIndexMap = new HashMap<Integer,Integer>();//长度更新后的新文件坐标位置
		byte[] lastByte = null;
		//先处理文字修改后,坐标应处的位置
		File file = new File(outUrl);
		//先读取原文件,取出大小
		
		//存1个MAP,key=坐标,value=1,1说明已修改过,不再进行大于此坐标的更新,null对大于此坐标的的坐标数值进行增减操作
		try {
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buff = new byte[(int)file.length()];
			
			inputStream.read(buff);
			String text = new String(buff,"GBK");
			String line = System.getProperty("line.separator");
			String[] lineTextArr = text.split(line); //文本数组
			
			int maxReturnSize = 0;
			for (int i = 0; i < lineTextArr.length; i++) {//读取修改后文件,计算出增加或减少个数
				String[] split = lineTextArr[i].split("\\|");
				if (Integer.parseInt(split[3]) != split[4].getBytes("GBK").length ) {//如果不等
					maxReturnSize += (Integer.parseInt(split[3]) - split[4].getBytes("GBK").length);
				}
//				System.out.println(split[3]+"|"+split[4]+"|"+split[4].getBytes("GBK").length);
//				logger.info(split[3]+"|"+split[4]+"|"+split[4].getBytes("GBK").length);
			}
			
			
			if (maxReturnSize < 0) {//如果为负数,取出最后几位被截取掉的byte元素 
				lastByte = new byte[0-maxReturnSize];
				for (int i = 0; i < 0-maxReturnSize; i++) {
					lastByte[i] = buff[buff.length-1-i];
				}
			}
			
			file = new File(url);
			byte[] oldBuff = new byte[(int)file.length()];
			inputStream = new FileInputStream(file);
			inputStream.read(oldBuff);
			//复制后的长度应调大,最后统一处理00等数据,可能会出现先大再小,尾位丢失的情况
			byte[] reBuff = Arrays.copyOf(oldBuff, oldBuff.length+maxReturnSize);//复制到新数组中 //if maxReturnSize < 0 以00结尾
			
			for (int i = 0; i < lineTextArr.length; i++) {//往byte数组中,写入数据
				String[] split = lineTextArr[i].split("\\|");//自增ID|指令坐标|坐标|长度|内容
				byte[] tmpReByte = split[4].getBytes("GBK");
				if (Integer.parseInt(split[3]) != tmpReByte.length ) {//如果不等
					if (i != 0) {
						//长度不相等,找到指令坐标
					}
//					for (int j = 0; j < tmpReByte.length; j++) {
//						reBuff[Integer.parseInt(split[2])+j] = tmpReByte[j];
//					}
				}else{
					if (upIndexMap.get(split[2]) != null && !("".equals(upIndexMap.get(split[2])))) {//长度有变化
						//更新过长度
					}else{
						//跟原来没变化
						for (int j = 0; j < tmpReByte.length; j++) {
							reBuff[Integer.parseInt(split[2])+j] = tmpReByte[j];
						}
					}
				}
			}
			writeTexts(reBuff, returnUrl);
			
//			System.out.println(upIndexMap.size());
			//0A OD换行符
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addArrInsertNum(){
		//插入byte
		//更新之后的指令坐标
		//添加已更新标识
		
	}
	
	private static void writeTexts(byte[] text,String outUrl) throws IOException {
		File outFile = new File(outUrl);
		File parentFile = outFile.getParentFile();
		parentFile.mkdirs();
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		FileOutputStream outputStream = new FileOutputStream(outFile);
		outputStream.write(text);
		outputStream.flush();
		outputStream.close();
	}
}
