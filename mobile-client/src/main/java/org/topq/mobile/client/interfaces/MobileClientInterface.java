package org.topq.mobile.client.interfaces;

import org.topq.mobile.common.client.enums.HardwareButtons;

/**
 * 
 * @author Bortman Limor
 *
 */
public interface MobileClientInterface {
	
	public static final String RESULT_STRING = "RESULT";
	public static final String ERROR_STRING = "ERROR";
	public static final String SUCCESS_STRING = "SUCCESS";
	public static final String NO_DATA_STRING = "NO_DATA_FROM_SERVER";
	
	public String launch(String launcherActivityClass) throws Exception;
	
	public String getTextView(int index) throws Exception ;
	
	public String getTextViewIndex(String text) throws Exception;
	
	public String getCurrentTextViews() throws Exception;
	
	public String getText(int index) throws Exception;
	
	public String clickOnMenuItem(String item) throws Exception;
	
	public String clickOnView(int index) throws Exception;
	
	public String enterText(int index, String text) throws Exception;
	
	public String clickOnButton(int index) throws Exception;
	
	public String clickInList(int index) throws Exception;
	
	public String clearEditText(int index) throws Exception;
	
	public String clickOnButtonWithText(String text) throws Exception;
	
	public String clickOnText(String text) throws Exception;

//	public String goBack() throws Exception;
	
	public String clickOnHardwereButton(HardwareButtons button) throws Exception;
	
	public String sendKey(int key) throws Exception;
	
	public void closeConnection() throws Exception;

	public byte[] pull(String fileName) throws Exception;
	
	public String push(byte[] data,String newlocalFileName) throws Exception;
	
	
	
}
