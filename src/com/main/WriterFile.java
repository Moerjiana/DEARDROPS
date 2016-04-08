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
		Map<Integer, Integer> ifUpIndexMap = new HashMap<Integer,Integer>();//�Ƿ��ѽ��й����ȸ��²���
		Map<Integer, Integer> upIndexMap = new HashMap<Integer,Integer>();//���ȸ��º�����ļ�����λ��
		byte[] lastByte = null;
		//�ȴ��������޸ĺ�,����Ӧ����λ��
		File file = new File(outUrl);
		//�ȶ�ȡԭ�ļ�,ȡ����С
		
		//��1��MAP,key=����,value=1,1˵�����޸Ĺ�,���ٽ��д��ڴ�����ĸ���,null�Դ��ڴ�����ĵ�������ֵ������������
		try {
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buff = new byte[(int)file.length()];
			
			inputStream.read(buff);
			String text = new String(buff,"GBK");
			String line = System.getProperty("line.separator");
			String[] lineTextArr = text.split(line); //�ı�����
			
			int maxReturnSize = 0;
			for (int i = 0; i < lineTextArr.length; i++) {//��ȡ�޸ĺ��ļ�,��������ӻ���ٸ���
				String[] split = lineTextArr[i].split("\\|");
				if (Integer.parseInt(split[3]) != split[4].getBytes("GBK").length ) {//�������
					maxReturnSize += (Integer.parseInt(split[3]) - split[4].getBytes("GBK").length);
				}
//				System.out.println(split[3]+"|"+split[4]+"|"+split[4].getBytes("GBK").length);
//				logger.info(split[3]+"|"+split[4]+"|"+split[4].getBytes("GBK").length);
			}
			
			
			if (maxReturnSize < 0) {//���Ϊ����,ȡ�����λ����ȡ����byteԪ�� 
				lastByte = new byte[0-maxReturnSize];
				for (int i = 0; i < 0-maxReturnSize; i++) {
					lastByte[i] = buff[buff.length-1-i];
				}
			}
			
			file = new File(url);
			byte[] oldBuff = new byte[(int)file.length()];
			inputStream = new FileInputStream(file);
			inputStream.read(oldBuff);
			//���ƺ�ĳ���Ӧ����,���ͳһ����00������,���ܻ�����ȴ���С,βλ��ʧ�����
			byte[] reBuff = Arrays.copyOf(oldBuff, oldBuff.length+maxReturnSize);//���Ƶ��������� //if maxReturnSize < 0 ��00��β
			
			for (int i = 0; i < lineTextArr.length; i++) {//��byte������,д������
				String[] split = lineTextArr[i].split("\\|");//����ID|ָ������|����|����|����
				byte[] tmpReByte = split[4].getBytes("GBK");
				if (Integer.parseInt(split[3]) != tmpReByte.length ) {//�������
					if (i != 0) {
						//���Ȳ����,�ҵ�ָ������
					}
//					for (int j = 0; j < tmpReByte.length; j++) {
//						reBuff[Integer.parseInt(split[2])+j] = tmpReByte[j];
//					}
				}else{
					if (upIndexMap.get(split[2]) != null && !("".equals(upIndexMap.get(split[2])))) {//�����б仯
						//���¹�����
					}else{
						//��ԭ��û�仯
						for (int j = 0; j < tmpReByte.length; j++) {
							reBuff[Integer.parseInt(split[2])+j] = tmpReByte[j];
						}
					}
				}
			}
			writeTexts(reBuff, returnUrl);
			
//			System.out.println(upIndexMap.size());
			//0A OD���з�
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addArrInsertNum(){
		//����byte
		//����֮���ָ������
		//����Ѹ��±�ʶ
		
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
