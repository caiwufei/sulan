package com.yilanjiaju.sulan.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class Md5Util {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e','f' };

	protected static MessageDigest messagedigest = null;
	
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(Md5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
			nsaex.printStackTrace();
		}
	}

	/**
	 * 生成字符串的md5校验值
	 *
	 * @param s
	 * @return
	 */
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	/**
	 * 判断字符串的md5校验码是否与一个已知的md5码相匹配
	 *
	 * @param password  要校验的字符串
	 * @param md5PwdStr 已知的md5校验码
	 * @return
	 */
	public static boolean isEqualsToMd5(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

	/**
	 * 生成文件的md5校验值
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getMD5String(File file) {
		try (InputStream fis = new FileInputStream(file)){
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				messagedigest.update(buffer, 0, numRead);
			}
			return bufferToHex(messagedigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getMD5String(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int numRead = 0;
		while ((numRead = inputStream.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}
		return bufferToHex(messagedigest.digest());
	}

	/**
	 * 生成字节数组的md5校验值
	 *
	 * @param s
	 * @return
	 */
	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
		// 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
		char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	/**
	 * 将源字符串使用MD5加密为字节数组
	 * 
	 * @param source
	 * @return
	 */
	public static byte[] encode2bytes(String source) {
		byte[] result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(source.getBytes("UTF-8"));
			result = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 将源字符串使用MD5加密为32位16进制数
	 * 
	 * @param source
	 * @return
	 */
	public static String encode2hex(String source) {
		byte[] data = encode2bytes(source);
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xff & data[i]);

			if (hex.length() == 1) {
				hexString.append('0');
			}

			hexString.append(hex);
		}

		return hexString.toString();
	}

	/**
	 * 验证字符串是否匹配
	 * 
	 * @param unknown 待验证的字符串
	 * @param okHex   使用MD5加密过的16进制字符串
	 * @return 匹配返回true，不匹配返回false
	 */
	public static boolean validate(String unknown, String okHex) {
		return okHex.equals(encode2hex(unknown));
	}


	public static void main(String[] args) {

		try {
/*			String downloadUrl = "D:\\appletree\\chat_file\\ERP搭建需求6期.docx";
			String fileMd5 = Md5Util.getMD5String(new File(downloadUrl));
			String fileEncode = Md5Util.getMD5String(fileMd5 + Constant.APPLETREE_CSRC_SZ);
			String zipFilePath = "D:/appletree/chat_file/csrc_temp";
			String zipFileName =  Md5Util.getMD5String("A1422C5F6052DDDD7386D281C1C6EC5D" + "ERP搭建需求6期.docx" + fileMd5) + Constant.ZIP_FILE_SUFFIX;
			ZipUtil.compress(downloadUrl, zipFilePath, zipFileName, fileEncode);

			System.out.println("fileMd5=" + fileMd5);
			System.out.println("fileEncode=" + fileEncode);
			System.out.println("zipFileName=" + zipFileName);*/
/*			List<String> openIds = IOUtils.readLines(new FileInputStream(new File("D:/error_user.log")), "UTF-8");
			String sql = "SELECT i.Freal_name, i.Femail, i.Fqqnumber, i.Ftelephone, o.Fname, i.Fcompany, i.Fopen_id from t_user_info i " +
					     "LEFT JOIN t_org_info o on o.Fid=i.Fcompany" +
						 "where i.Fopen_id in (" ;

			String sql1 =		     ") ORDER BY o.Fname desc";
			StringBuffer stringBuffer = new StringBuffer();
			for (String openId : openIds){
				stringBuffer.append("'").append(openId.trim()).append("', ");
			}
			System.out.println(sql + stringBuffer.toString() + sql1);*/

			String jsonstring = "{\"list\":[{\"companyId\":\"fdd24230-08cb-11e9-a10a-525400be748a\",\"companyName\":\"国盛证券资产管理有限公司\"},{\"companyId\":\"ede616f5-4b7b-11e9-a10a-525400be748a\",\"companyName\":\"湘财基金管理有限公司\"},{\"companyId\":\"e939baa9-ba4e-11e8-8806-525400be748a\",\"companyName\":\"大通证券股份有限公司\"},{\"companyId\":\"d2b50808-7619-11e9-bbc3-525400be748a\",\"companyName\":\"恒生前海基金管理有限公司\"},{\"companyId\":\"cccb981f-7d01-11e9-bbc3-525400be748a\",\"companyName\":\"博远基金管理有限公司\"},{\"companyId\":\"c35b2332-8033-11e8-b556-525400be748a\",\"companyName\":\"合煦智远基金管理有限公司\"},{\"companyId\":\"c0707b3a-08d6-11e9-a10a-525400be748a\",\"companyName\":\"弘毅远方基金管理有限公司\"},{\"companyId\":\"b84aa4cd-be36-11e9-bbc3-525400be748a\",\"companyName\":\"中泰证券（上海）资产管理有限公司\"},{\"companyId\":\"b84aa4cd-be36-11e9-bbc3-525400be748a\",\"companyName\":\"中泰证券（上海）资产管理有限公司\"},{\"companyId\":\"b80581c0-9c3d-11e8-b556-525400be748a\",\"companyName\":\"西部利得基金管理有限公司\"},{\"companyId\":\"af7517db-f840-11e8-a10a-525400be748a\",\"companyName\":\"爱建证券有限责任公司\"},{\"companyId\":\"9f3f2e8e-55ba-11e9-bbc3-525400be748a\",\"companyName\":\"华菁证券有限公司\"},{\"companyId\":\"9f2d0572-477e-11e7-8a00-230cd509a937\",\"companyName\":\"东证融汇证券资产管理有限公司\"},{\"companyId\":\"9eda1d32-477e-11e7-8a00-230cd509a937\",\"companyName\":\"招商证券资产管理有限公司\"},{\"companyId\":\"9ec75590-477e-11e7-8a00-230cd509a937\",\"companyName\":\"浙江浙商证券资产管理有限公司\"},{\"companyId\":\"9ec75590-477e-11e7-8a00-230cd509a937\",\"companyName\":\"浙江浙商证券资产管理有限公司\"},{\"companyId\":\"9eb7ea4f-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华泰证券(上海）资产管理有限公司\"},{\"companyId\":\"9eb7ea4f-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华泰证券（上海）资产管理有限公司\"},{\"companyId\":\"9ea4cd75-477e-11e7-8a00-230cd509a937\",\"companyName\":\"上海光大证券资产管理有限公司\"},{\"companyId\":\"9e997c6a-477e-11e7-8a00-230cd509a937\",\"companyName\":\"上海国泰君安证券资产管理有限公司\"},{\"companyId\":\"9e48045d-477e-11e7-8a00-230cd509a937\",\"companyName\":\"上投摩根基金管理有限公司\"},{\"companyId\":\"9e404cac-477e-11e7-8a00-230cd509a937\",\"companyName\":\"红土创新基金管理有限公司\"},{\"companyId\":\"9e3b460b-477e-11e7-8a00-230cd509a937\",\"companyName\":\"先锋基金管理有限公司\"},{\"companyId\":\"9e2fdcba-477e-11e7-8a00-230cd509a937\",\"companyName\":\"中航基金管理有限公司\"},{\"companyId\":\"9e153b8f-477e-11e7-8a00-230cd509a937\",\"companyName\":\"新沃基金管理有限公司\"},{\"companyId\":\"9e119a03-477e-11e7-8a00-230cd509a937\",\"companyName\":\"永赢基金管理有限公司\"},{\"companyId\":\"9e0af052-477e-11e7-8a00-230cd509a937\",\"companyName\":\"金元顺安基金管理有限公司\"},{\"companyId\":\"9e07074b-477e-11e7-8a00-230cd509a937\",\"companyName\":\"中欧基金管理有限公司\"},{\"companyId\":\"9e03495e-477e-11e7-8a00-230cd509a937\",\"companyName\":\"诺德基金管理有限公司\"},{\"companyId\":\"9df02d29-477e-11e7-8a00-230cd509a937\",\"companyName\":\"浙商基金管理有限公司\"},{\"companyId\":\"9dec8b49-477e-11e7-8a00-230cd509a937\",\"companyName\":\"九泰基金管理有限公司\"},{\"companyId\":\"9de4e4a6-477e-11e7-8a00-230cd509a937\",\"companyName\":\"中融基金管理有限公司\"},{\"companyId\":\"9ddd0364-477e-11e7-8a00-230cd509a937\",\"companyName\":\"中金基金管理有限公司\"},{\"companyId\":\"9dd1da24-477e-11e7-8a00-230cd509a937\",\"companyName\":\"泰达宏利基金管理有限公司\"},{\"companyId\":\"9dcb59c5-477e-11e7-8a00-230cd509a937\",\"companyName\":\"益民基金管理有限公司\"},{\"companyId\":\"9dbc0d04-477e-11e7-8a00-230cd509a937\",\"companyName\":\"兴业基金管理有限公司\"},{\"companyId\":\"9db46387-477e-11e7-8a00-230cd509a937\",\"companyName\":\"江信基金管理有限公司\"},{\"companyId\":\"9d99e1ad-477e-11e7-8a00-230cd509a937\",\"companyName\":\"诺安基金管理有限公司\"},{\"companyId\":\"9d804d70-477e-11e7-8a00-230cd509a937\",\"companyName\":\"融通基金管理有限公司\"},{\"companyId\":\"9d789fa7-477e-11e7-8a00-230cd509a937\",\"companyName\":\"兴全基金管理有限公司\"},{\"companyId\":\"9d74f1ed-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华宝基金管理有限公司\"},{\"companyId\":\"9d714e86-477e-11e7-8a00-230cd509a937\",\"companyName\":\"中邮创业基金管理股份有限公司\"},{\"companyId\":\"9d5a4e82-477e-11e7-8a00-230cd509a937\",\"companyName\":\"银河基金管理有限公司\"},{\"companyId\":\"9d5648b2-477e-11e7-8a00-230cd509a937\",\"companyName\":\"德邦基金管理有限公司\"},{\"companyId\":\"9d526167-477e-11e7-8a00-230cd509a937\",\"companyName\":\"宝盈基金管理有限公司\"},{\"companyId\":\"9d4e8aa0-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华富基金管理有限公司\"},{\"companyId\":\"9d4ad2ef-477e-11e7-8a00-230cd509a937\",\"companyName\":\"国海富兰克林基金管理有限公司\"},{\"companyId\":\"9d37e4a4-477e-11e7-8a00-230cd509a937\",\"companyName\":\"东吴基金管理有限公司\"},{\"companyId\":\"9d3016a0-477e-11e7-8a00-230cd509a937\",\"companyName\":\"安信基金管理有限责任公司\"},{\"companyId\":\"9d2c410b-477e-11e7-8a00-230cd509a937\",\"companyName\":\"国泰基金管理有限公司\"},{\"companyId\":\"9d287243-477e-11e7-8a00-230cd509a937\",\"companyName\":\"摩根士丹利华鑫基金管理有限公司\"},{\"companyId\":\"9d212b39-477e-11e7-8a00-230cd509a937\",\"companyName\":\"博时基金管理有限公司\"},{\"companyId\":\"9d1d8e0d-477e-11e7-8a00-230cd509a937\",\"companyName\":\"富国基金管理有限公司\"},{\"companyId\":\"9d14cc22-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华商基金管理有限公司\"},{\"companyId\":\"9d0b59c8-477e-11e7-8a00-230cd509a937\",\"companyName\":\"景顺长城基金管理有限公司\"},{\"companyId\":\"9cfe8602-477e-11e7-8a00-230cd509a937\",\"companyName\":\"浦银安盛基金管理有限公司\"},{\"companyId\":\"9cd77762-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华夏基金管理有限公司\"},{\"companyId\":\"9c6da2fa-477e-11e7-8a00-230cd509a937\",\"companyName\":\"山西证券股份有限公司\"},{\"companyId\":\"9c6da2fa-477e-11e7-8a00-230cd509a937\",\"companyName\":\"山西证券股份有限公司\"},{\"companyId\":\"9c5e5c57-477e-11e7-8a00-230cd509a937\",\"companyName\":\"申万宏源证券有限公司\"},{\"companyId\":\"9c475cd9-477e-11e7-8a00-230cd509a937\",\"companyName\":\"宏信证券有限责任公司\"},{\"companyId\":\"9c0b8f99-477e-11e7-8a00-230cd509a937\",\"companyName\":\"方正证券股份有限公司\"},{\"companyId\":\"9bff25f2-477e-11e7-8a00-230cd509a937\",\"companyName\":\"西部证券股份有限公司\"},{\"companyId\":\"9bfb0234-477e-11e7-8a00-230cd509a937\",\"companyName\":\"联讯证券股份有限公司\"},{\"companyId\":\"9bf2430d-477e-11e7-8a00-230cd509a937\",\"companyName\":\"东北证券股份有限公司\"},{\"companyId\":\"9bee5217-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华金证券股份有限公司\"},{\"companyId\":\"9be96309-477e-11e7-8a00-230cd509a937\",\"companyName\":\"浙商证券股份有限公司\"},{\"companyId\":\"9be07ad0-477e-11e7-8a00-230cd509a937\",\"companyName\":\"财达证券股份有限公司\"},{\"companyId\":\"9bcf8953-477e-11e7-8a00-230cd509a937\",\"companyName\":\"湘财证券有限责任公司\"},{\"companyId\":\"9bc94468-477e-11e7-8a00-230cd509a937\",\"companyName\":\"东吴证券股份有限公司\"},{\"companyId\":\"9bc520fc-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华创证券有限责任公司\"},{\"companyId\":\"9bb22f34-477e-11e7-8a00-230cd509a937\",\"companyName\":\"德邦证券股份有限公司\"},{\"companyId\":\"9ba1a99d-477e-11e7-8a00-230cd509a937\",\"companyName\":\"英大证券有限责任公司\"},{\"companyId\":\"9b832f1e-477e-11e7-8a00-230cd509a937\",\"companyName\":\"世纪证券有限责任公司\"},{\"companyId\":\"9b7cc0cd-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华龙证券股份有限公司\"},{\"companyId\":\"9b6d7905-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华安证券股份有限公司\"},{\"companyId\":\"9b41096b-477e-11e7-8a00-230cd509a937\",\"companyName\":\"国都证券股份有限公司\"},{\"companyId\":\"9b41096b-477e-11e7-8a00-230cd509a937\",\"companyName\":\"国都证券有限责任公司\"},{\"companyId\":\"9b3bf398-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华鑫证券有限责任公司\"},{\"companyId\":\"9b2a2600-477e-11e7-8a00-230cd509a937\",\"companyName\":\"南京证券股份有限公司\"},{\"companyId\":\"9b0e4a18-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华融证券股份有限公司\"},{\"companyId\":\"9b0e4a18-477e-11e7-8a00-230cd509a937\",\"companyName\":\"华融证券股份有限公司\"},{\"companyId\":\"9b040d96-477e-11e7-8a00-230cd509a937\",\"companyName\":\"国联证券股份有限公司\"},{\"companyId\":\"9ad7ca04-477e-11e7-8a00-230cd509a937\",\"companyName\":\"国元证券股份有限公司\"},{\"companyId\":\"9a9f7d3d-477e-11e7-8a00-230cd509a937\",\"companyName\":\"国泰君安证券股份有限公司\"},{\"companyId\":\"9a9ba3ce-477e-11e7-8a00-230cd509a937\",\"companyName\":\"中国国际金融股份有限公司\"},{\"companyId\":\"9a8c6990-477e-11e7-8a00-230cd509a937\",\"companyName\":\"国信证券股份有限公司\"},{\"companyId\":\"9a8757b2-477e-11e7-8a00-230cd509a937\",\"companyName\":\"招商证券股份有限公司\"},{\"companyId\":\"9a82302c-477e-11e7-8a00-230cd509a937\",\"companyName\":\"东海证券股份有限公司\"},{\"companyId\":\"9a63d954-477e-11e7-8a00-230cd509a937\",\"companyName\":\"光大证券股份有限公司\"},{\"companyId\":\"95d05c52-f570-11e9-bbc3-525400be748a\",\"companyName\":\"淳厚基金管理有限公司\"},{\"companyId\":\"8c2817dc-55b7-11e9-bbc3-525400be748a\",\"companyName\":\"国联安基金管理有限公司\"},{\"companyId\":\"83edc556-8319-11e8-b556-525400be748a\",\"companyName\":\"财通基金管理有限公司\"},{\"companyId\":\"7f87cc00-c07d-11e8-8806-525400be748a\",\"companyName\":\"华润元大基金管理有限公司\"},{\"companyId\":\"7a29b322-8a60-11e8-b556-525400be748a\",\"companyName\":\"国融基金管理有限公司\"},{\"companyId\":\"71b84d06-b8d9-11e9-bbc3-525400be748a\",\"companyName\":\"朱雀基金管理有限公司\"},{\"companyId\":\"650e21f2-e65e-11e8-9014-525400be748a\",\"companyName\":\"东海基金管理有限责任公司\"},{\"companyId\":\"63136825-55b5-11e9-bbc3-525400be748a\",\"companyName\":\"博道基金管理有限公司\"},{\"companyId\":\"3ef67e4e-0a88-11e9-a10a-525400be748a\",\"companyName\":\"国开证券股份有限公司\"},{\"companyId\":\"3d770354-01e0-11e9-a10a-525400be748a\",\"companyName\":\"睿远基金管理有限公司\"}" +
					",{\"companyId\":\"3b4438a3-9ba6-11e8-b556-525400be748a\",\"companyName\":\"中信保诚基金管理有限公司\"},{\"companyId\":\"3ad3ca42-7ffe-11e8-b556-525400be748a\",\"companyName\":\"上银基金管理有限公司\"},{\"companyId\":\"2d75cb12-0b40-11e9-a10a-525400be748a\",\"companyName\":\"富安达基金管理有限公司\"},{\"companyId\":\"2a0ef9c9-e292-11e8-9014-525400be748a\",\"companyName\":\"中海基金管理有限公司\"},{\"companyId\":\"1b9dbb24-a5e9-11e8-9130-525400be748a\",\"companyName\":\"新疆前海联合基金管理有限公司\"},{\"companyId\":\"143fc9d9-db1c-11e8-80e0-525400be748a\",\"companyName\":\"蜂巢基金管理有限公司\"},{\"companyId\":\"00ac19d3-8e71-11e9-bbc3-525400be748a\",\"companyName\":\"东亚前海证券有限责任公司\"}" +
					"" +
					"" +
					"]}" +
					"";
			JSONArray array = JSON.parseObject(jsonstring).getJSONArray("list");
			HashSet<String> companyset = new HashSet<>();
			for(int i=0; i< array.size(); i++){
				String companyId = array.getJSONObject(i).getString("companyId");
				if(!companyset.contains(companyId)){
					companyset.add(companyId);
				} else {
					System.out.println(companyId);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
