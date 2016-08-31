package com.crm.common.license;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.apache.commons.codec.digest.DigestUtils;

import com.crm.common.license.LicenseInfo;
import com.crm.common.license.TgLicenseChcker;
import com.crm.common.util.io.PropsUtil;
import com.crm.common.util.math.RSAUtil;
import com.crm.common.util.net.IPUtil;
import com.crm.common.util.net.MacUtil;

import jodd.datetime.JDateTime;

/** 
 * @ClassName	TgLicenseChcker.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月17日 下午6:47:03
 * @Version 	V1.0    
 */
public class TgLicenseChcker {

	private static String licenseFileName = "license.lic";
	private static final String SALT = "TG";
	private static LicenseInfo licenseInfo;
	private static final int CHECK_HOUR = 23;
	private static final String PERMISSION_TYPE_DEV = "开发版";
	private static final String PERMISSION_TYPE_FREE = "免费版";
	private static final String PERMISSION_TYPE_OFFICAL = "正式版";
	private static ServletContext application;
	private static String basePackageName = "";

	private TgLicenseChcker() {
		throw new Error("工具类不能实例化！");
	}

	private static LicenseInfo getLicenseInfo() {
		if (licenseInfo == null) {
			licenseInfo = new LicenseInfo();
			String macAddress = "";
			String ipAddress = "";
			String permissionType = "正式版";
			permissionType = PropsUtil.getValue("permissionType", licenseFileName);
			if ("开发版".equals(permissionType)) {
				macAddress = "00-00-00-00";
				ipAddress = "127.0.0.1";
			} else if ("免费版".equals(permissionType)) {
				macAddress = "00-00-00-00";
				ipAddress = "127.0.0.1";
			} else {
				macAddress = PropsUtil.getValue("macAddress", licenseFileName);
				ipAddress = PropsUtil.getValue("ipAddress", licenseFileName);
			}

			String product = PropsUtil.getValue("product", licenseFileName);
			String version = PropsUtil.getValue("version", licenseFileName);
			String expireDateStr = PropsUtil.getValue("expireDate", licenseFileName);
			String customer = PropsUtil.getValue("customer", licenseFileName);
			String digitalSignature = DigestUtils.sha256Hex("TG" + basePackageName + product + version + permissionType
					+ expireDateStr + customer + macAddress + ipAddress);
			String publicKey = PropsUtil.getValue("licenseKey", licenseFileName);
			String licenseCode = PropsUtil.getValue("licenseCode", licenseFileName);
			licenseInfo.setProduct(product);
			licenseInfo.setVersion(version);
			licenseInfo.setPermissionType(permissionType);
			licenseInfo.setExpireDate((new JDateTime(expireDateStr)).convertToDate());
			licenseInfo.setCustomer(customer);
			licenseInfo.setMacAddress(macAddress);
			licenseInfo.setIpAddress(ipAddress);
			licenseInfo.setDigitalSignature(digitalSignature);
			licenseInfo.setLicenseKey(publicKey);
			licenseInfo.setLicenseCode(licenseCode);
		}

		return licenseInfo;
	}

	private static boolean checkLicense() {
		licenseInfo = getLicenseInfo();
		boolean flag = false;

		try {
			flag = RSAUtil.verify(licenseInfo.getDigitalSignature().getBytes(), licenseInfo.getLicenseKey(),
					licenseInfo.getLicenseCode());
		} catch (Exception arg1) {
			arg1.printStackTrace();
		}

		return flag;
	}

	private static boolean checkExpire() {
		JDateTime expireDate = (new JDateTime(getLicenseInfo().getExpireDate())).addDay(1);
		return expireDate.compareTo(new JDateTime()) < 0;
	}

	public static void checkAll() {
		licenseInfo = getLicenseInfo();
		boolean valid = checkLicense();
		if (!valid) {
			System.err.println("对不起，系统license验证失败，系统停止运行！");
			System.exit(0);
		}

		boolean expire = checkExpire();
		if (expire) {
			System.err.println("对不起，license已过期，系统停止运行！");
			System.exit(0);
		}

		if ("开发版".equals(licenseInfo.getPermissionType())) {
			System.out.println("现在使用的是开发版！");
			if ((new JDateTime()).getHour() == 23) {
				System.err.println("对不起，您现在使用的是开发版，每天会定时停止运行！");
				System.exit(0);
			}
		} else if ("免费版".equals(licenseInfo.getPermissionType())) {
			System.out.println("现在使用的是免费版！");
		} else {
			boolean e;
			try {
				e = IPUtil.getLocalIpList().contains(licenseInfo.getIpAddress());
				if (!e) {
					System.err.println("对不起，本机IP地址与license不匹配，系统停止运行！");
					System.exit(0);
				}
			} catch (Exception arg3) {
				System.err.println("获取不到IP地址！");
				arg3.printStackTrace();
			}

			try {
				e = MacUtil.getAllMACAddress().contains(licenseInfo.getMacAddress());
				if (!e) {
					System.err.println("对不起，本机MAC地址与license不匹配，系统停止运行！");
					System.exit(0);
				}
			} catch (Exception arg2) {
				System.err.println("获取不到mac地址！");
				arg2.printStackTrace();
			}
		}

		application.setAttribute("licenseInfo", licenseInfo);
	}

	public static void checkInit(ServletContext applicationParm, Class<?> c) {
		application = applicationParm;
		String packageName = c.getPackage().getName();
		basePackageName = packageName.substring(0, packageName.indexOf(".core"));
		checkAll();
		Runnable checkRunnable = new Runnable() {
			public void run() {
				System.out.println("***************定时任务，检查license 开始 ***************");
				TgLicenseChcker.checkAll();
				System.out.println("***************定时任务，检查license 结束 ***************");
			}
		};
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		long oneDay = 86400000L;
		long initDelay = (new JDateTime()).setTime(23, 0, 0, 0).getTimeInMillis() - System.currentTimeMillis();
		if (initDelay <= 0L) {
			initDelay += oneDay;
		}

		executor.scheduleAtFixedRate(checkRunnable, initDelay, oneDay, TimeUnit.MILLISECONDS);
	}
}
 