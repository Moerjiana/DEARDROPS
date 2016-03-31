package com.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	public static String url = "G:\\myFile\\galgame\\DEARDROPS\\data01000\\test\\000_00";
	public static String outUrl = "G:\\myFile\\galgame\\DEARDROPS\\data01000\\test\\out\\000_00.txt";
	
	public static void main(String[] args) {
		try {
			List<String> indexList = new ArrayList<String>();//指令内容坐标
			List<Integer> itcsList = new ArrayList<Integer>();//指令所在位置坐标
			
			List<String> textList = new ArrayList<String>();
//			String line = System.getProperty("line.separator");//获取系统换行符
			
			File file = new File(url);
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buff = new byte[(int)file.length()];
			inputStream.read(buff);//取出所有字节
			inputStream.close();
			//根据指令0x00000003,找出文字下标 
			//4字节1读取
			for (int i = 0; i < buff.length; i=i+4) {
				if (buff[i] == 3 && buff[i+1] == 0 && buff[i+2] == 0 && buff[i+3] == 0) {
					String tmpIndexStr = "";
					if (buff[i+7] != 0) {
						tmpIndexStr += getHexString(buff[i+7]);
						tmpIndexStr += getHexString(buff[i+6]);
						tmpIndexStr += getHexString(buff[i+5]);
						tmpIndexStr += getHexString(buff[i+4]);
					}else if(buff[i+6] !=0){
						tmpIndexStr += getHexString(buff[i+6]);
						tmpIndexStr += getHexString(buff[i+5]);
						tmpIndexStr += getHexString(buff[i+4]);
					}else if(buff[i+5] !=0){
						tmpIndexStr += getHexString(buff[i+5]);;
						tmpIndexStr += getHexString(buff[i+4]);;
					}else if(buff[i+4] !=0){
						tmpIndexStr += getHexString(buff[i+4]);;
					}else{
//						throw new Exception("indexStr is null");
					}
					if (!tmpIndexStr.equals("")) {
						indexList.add(tmpIndexStr);
						itcsList.add(i+4);
					}
				}
			}
			
			for (int i = 0; i < indexList.size(); i++) {//取出文本
				int tmpIndex = 0;
				int byteIndex = getByteIndex(indexList.get(i));
				boolean tmpResult = true;
				while (tmpResult) {
					if (buff[byteIndex+tmpIndex] == 0) {
						tmpResult = false;
					}else{
						tmpIndex++;
					}
				}
				byte[] tmpText = new byte[tmpIndex];
				
				tmpResult = true;
				tmpIndex = 0;
				while (tmpResult) {
					if (buff[byteIndex+tmpIndex] == 0) {
						tmpResult = false;
					}else{
						tmpText[tmpIndex] = buff[byteIndex+tmpIndex];
						tmpIndex++;
					}
				}
				//自增ID|指令坐标|坐标|长度|内容
				//坐标并不是顺序排下来,如10000之后可能有640,封包更新长度时须注意
				//如长度为0的不存
				if (tmpText.length != 0) {
					String resultStr = i+"|"+itcsList.get(i)+"|"+byteIndex+"|"+tmpText.length+"|"+new String(tmpText,"Shift_JIS");
//					System.out.println(resultStr);
					textList.add(resultStr);
				}
			}
			writeTexts(textList,outUrl);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 把剧本输出到文本中
	 * @return void    返回类型  
	 * @author zhangzhe
	 * @time 2016-3-29 下午2:20:49  
	 * @throws
	 */
	private static void writeTexts(List<String> textList,String outUrl) throws IOException {
		File outFile = new File(outUrl);
		File parentFile = outFile.getParentFile();
		parentFile.mkdirs();
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(outFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (int i = 0; i < textList.size(); i++) {
			bufferedWriter.write(textList.get(i));
			bufferedWriter.newLine();
		}
		bufferedWriter.flush();
		bufferedWriter.close();
	}
	/**
	 * 根据16进制字符串坐标,计算出文本位置
	 * @return int    返回类型  
	 * @author zhangzhe
	 * @time 2016-3-28 下午4:50:56  
	 * @throws
	 */
	public static int getByteIndex(String c){
		String a = "1C";
		String b = "E4";
//		String c = "162C2";
		return Integer.parseInt(a, 16)+Integer.parseInt(b, 16)+Integer.parseInt(c, 16);
	}
	
	/**
	 * 传入byte类型,转换为16进制字符串,如只一位,高位补0
	 * @return void    返回类型  
	 * @author zhangzhe
	 * @time 2016-3-28 下午4:21:18  
	 * @throws
	 */
	public static String getHexString(byte value){
		String hexString = Integer.toHexString(value & 0xff);
		if (hexString.length()<2) {
			hexString = "0"+hexString;
		}
		return hexString;
	}
}
