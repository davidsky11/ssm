package com.crm.common.util.net;


/**
 * 短信猫工具类
 */
public final class SmsUtil {
	// private static final String COM_15 = "COM15";
	// private static final int BAND_RATE_9600 = 9600;
	// private static final String WAVECOM = "wavecom";
	// private static Service smsService = null;
	// static {
	// try {
	// SerialModemGateway gateway = new SerialModemGateway("modem.com1", COM_15, BAND_RATE_9600, WAVECOM, "17254");
	// gateway.setInbound(true);
	// gateway.setOutbound(true);
	// gateway.setSimPin("0000");
	// smsService = Service.getInstance();
	// smsService.addGateway(gateway);
	// smsService.startService();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	/**
	 * 发送消息
	 */
	public static void sendSms(final String toNumber, final String message) {
		// try {
		// if (smsService == null) {
		// return;
		// }
		// OutboundMessage msg = new OutboundMessage(toNumber, message);
		// msg.setEncoding(MessageEncodings.ENCUCS2); // 发送中文
		// smsService.sendMessage(msg);
		// } catch (Exception e) {
		// logger.error("信息发送失败..." + e.getMessage());
		// e.printStackTrace();
		// }
	}

	/**
	 * 发送消息 群发
	 */
	public static synchronized void sendSms(final String[] toNumbers, final String message) {
		// try {
		// if (smsService == null) {
		// return;
		// }
		// List<OutboundMessage> oms = new ArrayList<>();
		// if (toNumbers == null || toNumbers.length == 0) {
		// return;
		// }
		// for (String toNumber : toNumbers) {
		// OutboundMessage msg = new OutboundMessage(toNumber, message);
		// msg.setEncoding(MessageEncodings.ENCUCS2); // 发送中文
		// oms.add(msg);
		// }
		// if (oms.size() > 0) {
		// smsService.sendMessages(oms);
		// }
		// } catch (Exception e) {
		// logger.error("信息发送失败..." + e.getMessage());
		// e.printStackTrace();
		// }
	}

	//private Logger logger = LoggerFactory.getLogger(SmsUtil.class);
}
