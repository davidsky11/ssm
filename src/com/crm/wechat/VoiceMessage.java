package com.crm.wechat; 

/** 
 * @ClassName	VoiceMessage.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月25日 上午1:47:12
 * @Version 	V1.0    
 */
public class VoiceMessage extends BaseMessage {

	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
	
}
 