package com.meetu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Common {

	public static String encryptByMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}

	private static String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}

	public synchronized static String generateToken() {
		long current = System.currentTimeMillis();
		String now = new Long(current).toString();
		return encryptByMD5(now);
	}

	public static String generateId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static int indexString(String[] strs, String s) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}

	public static boolean checkMobileNumber(String mobileNumber) {

		// Pattern p =
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		// Matcher m = p.matcher(mobileNumber);
		// return m.matches();

		return true;

	}

	public static String formatNull(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	// 计算年龄
	public static Integer getAgeByBirthday(Date birthday) {
		if (birthday == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	public static String getConstellation(Date date) {
		final String[] constellationArr = { "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座",
				"巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };
		final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23,
				23, 23, 22, 22 };
		if (date == null) {
			return "";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return
		return constellationArr[9];
	}

	/**
	 * 过滤查询条件中的特殊字符
	 * 
	 */
	public static String dealWithParam(String param) {
		String realParam = param.toLowerCase();
		String regEx = "%+|_+";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(realParam);
		return mat.replaceAll("\\\\%");

	}

	/**
	 * 反向筛选用户
	 * 
	 * @param u_city
	 * @param age
	 * @param users
	 */
	public static int revMatch(String u_city, int age,
			List<Map<String, Object>> users) {
		List<Map<String, Object>> removeList = new ArrayList<Map<String, Object>>();
		int count = 0;
		for (Map<String, Object> map : users) {
			String s_city = map.get("s_city").toString();
			String cityf = map.get("cityf").toString();
			int s_age_down = (int) map.get("s_age_down");
			int s_age_up = (int) map.get("s_age_up");
			String s_sex = map.get("s_sex").toString();
			// 只有用户同城限制打开的时候,并且两个人城市不相同,才向移除列表中添加
			if (s_city.equals("1") && !cityf.equals(u_city)) {
				removeList.add(map);
				count++;
				continue;
			}
			// 当不满足用户年龄范围限制的时候,向移除列表中添加
			if (s_age_down != s_age_up && s_age_up != 0) {
				if (age > s_age_up || age < s_age_down) {
					removeList.add(map);
					count++;
					continue;
				}
			}
			
		}
		users.removeAll(removeList);
		return count;
	}

	/**
	 * 通过出生日期计算年龄
	 * 
	 * @param birthDate
	 * @return
	 */
	public static int getAge(Date birthDate) {

		if (birthDate == null)
			throw new RuntimeException("出生日期不能为null");

		int age = 0;

		Date now = new Date();

		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");

		String birth_year = format_y.format(birthDate);
		String this_year = format_y.format(now);

		String birth_month = format_M.format(birthDate);
		String this_month = format_M.format(now);

		// 初步，估算
		age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

		// 如果未到出生月份，则age - 1
		if (this_month.compareTo(birth_month) < 0)
			age -= 1;
		if (age < 0)
			age = 0;
		return age;
	}

	public static void matchNum(List<Map<String, Object>> users,
			Integer targetBiuNumbers) {
		if (users.size() > targetBiuNumbers) {
			List<Map<String, Object>> removeList = new ArrayList<Map<String, Object>>();
			for (int i = targetBiuNumbers; i < users.size(); i++) {
				removeList.add(users.get(i));
			}
			users.removeAll(removeList);
		}
	}

}
