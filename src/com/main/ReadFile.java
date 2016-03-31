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
			List<String> indexList = new ArrayList<String>();//ָ����������
			List<Integer> itcsList = new ArrayList<Integer>();//ָ������λ������
			
			List<String> textList = new ArrayList<String>();
//			String line = System.getProperty("line.separator");//��ȡϵͳ���з�
			
			File file = new File(url);
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buff = new byte[(int)file.length()];
			inputStream.read(buff);//ȡ�������ֽ�
			inputStream.close();
			//����ָ��0x00000003,�ҳ������±� 
			//4�ֽ�1��ȡ
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
			
			for (int i = 0; i < indexList.size(); i++) {//ȡ���ı�
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
				//����ID|ָ������|����|����|����
				//���겢����˳��������,��10000֮�������640,������³���ʱ��ע��
				//�糤��Ϊ0�Ĳ���
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
	 * �Ѿ籾������ı���
	 * @return void    ��������  
	 * @author zhangzhe
	 * @time 2016-3-29 ����2:20:49  
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
	 * ����16�����ַ�������,������ı�λ��
	 * @return int    ��������  
	 * @author zhangzhe
	 * @time 2016-3-28 ����4:50:56  
	 * @throws
	 */
	public static int getByteIndex(String c){
		String a = "1C";
		String b = "E4";
//		String c = "162C2";
		return Integer.parseInt(a, 16)+Integer.parseInt(b, 16)+Integer.parseInt(c, 16);
	}
	
	/**
	 * ����byte����,ת��Ϊ16�����ַ���,��ֻһλ,��λ��0
	 * @return void    ��������  
	 * @author zhangzhe
	 * @time 2016-3-28 ����4:21:18  
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
