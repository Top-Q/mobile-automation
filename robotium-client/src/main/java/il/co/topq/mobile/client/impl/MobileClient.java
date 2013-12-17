package il.co.topq.mobile.client.impl;

import il.co.topq.mobile.client.interfaces.MobileClientInterface;
import il.co.topq.mobile.common.client.enums.HardwareButtons;
import il.co.topq.mobile.common.datamodel.CommandRequest;
import il.co.topq.mobile.common.datamodel.CommandResponse;
import il.co.topq.mobile.common.server.utils.JsonParser;
import il.co.topq.mobile.tcp.impl.TcpClient;

import java.io.File;
import java.io.FileOutputStream;

import net.iharder.Base64;

import org.apache.log4j.Logger;

/**
 * this is the implementation of the commands that the robotium server will
 * interpret in to methods
 * 
 * @author tal ben shabtay , limor bortman
 * 
 */
public class MobileClient implements MobileClientInterface {

	private static Logger logger = Logger.getLogger(MobileClient.class);

	private TcpClient tcpClient;
	private int serverPort;
	private String serverHost;

	public MobileClient(String serverHost, int serverPort) {
		try {
			this.serverPort = serverPort;
			this.serverHost = serverHost;
			this.tcpClient = new TcpClient(this.serverHost, this.serverPort);
		} catch (Exception e) {
			logger.error("Exception in constructor !!", e);
		}
	}

	/**
	 * send data wrapper of the input command and text into a proper command
	 * object
	 * 
	 * @param command
	 *            the string command to execute
	 * @param params
	 *            the parameters to send along side of the command
	 * @throws Exception
	 */
	private CommandResponse sendData(String command, String... params) throws Exception {
		CommandResponse result = null;
		try {
			result = sendDataAndGetJSonObj(new CommandRequest(command, params));
		} catch (Exception e) {
			logger.error("Failed to send / receive data", e);
			throw e;
		}
		return result;
	}

	/**
	 * will send the command object to the server and wait for response
	 * 
	 * @param request
	 *            a command request object
	 * @return a command response of object the input command request
	 * @throws Exception
	 */
	private CommandResponse sendDataAndGetJSonObj(CommandRequest request) throws Exception {
		String jsonRequest = JsonParser.toJson(request);
		logger.info("Sending command: " + jsonRequest);
		CommandResponse result = null;
		logger.info("Send Data to " + this.serverHost + ':' + this.serverPort);

		try {
			String resultStr = null;
			if ((resultStr = this.tcpClient.sendData(jsonRequest)) == null) {
				throw new Exception("No data recvied from server! please check server log!");
			}
			result = JsonParser.fromJson(resultStr, CommandResponse.class);
		} catch (Exception e) {
			logger.error("Failed to send / receive data", e);
			throw e;
		}
		return result;
	}

	/**
	 * NOTE ! THIS METHOD MUST BE THE FIRST COMMAND BEFORE ANY OTHER COMMAND the
	 * launch method will launch the instrumentation of the application
	 * 
	 * @param launcherActivityClass
	 *            the full class name with the package of the main launcher
	 *            activity
	 * @return return status of the operation
	 * @throws Exception
	 */

	@Override
	public CommandResponse launch(String launcherActivityClass) throws Exception {
		return sendData("launch", launcherActivityClass);
	}

	/**
	 * will get the text of the input text view index
	 * 
	 * @param index
	 *            the index of the text view on the screen
	 * @return the text of the text view
	 * @throws Exception
	 */
	@Override
	public CommandResponse getTextView(int index) throws Exception {
		return sendData("getTextView", Integer.toString(index));
	}

	/**
	 * will get the id of the text view with the input text
	 * 
	 * @param text
	 *            the text that is in the text view
	 * @return id of the text view
	 * @throws Exception
	 */
	@Override
	public CommandResponse getTextViewIndex(String text) throws Exception {
		return sendData("getTextViewIndex", text);
	}

	/**
	 * will get a list of all the texts of the current display text views
	 * 
	 * @return list of all the text views current displayed
	 * @throws Exception
	 */
	@Override
	public CommandResponse getCurrentTextViews() throws Exception {
		return sendData("getCurrentTextViews", "a");
	}

	/**
	 * will get the text of the view by the input index
	 * 
	 * @param index
	 *            the index of the view in the screen
	 * @return the text of the input index text view
	 * @throws Exception
	 */
	@Override
	public CommandResponse getText(int index) throws Exception {
		return sendData("getText", Integer.toString(index));
	}

	/**
	 * will click on the menu item with the input text
	 * 
	 * @param item
	 *            the text of the item in the menu
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse clickOnMenuItem(String item) throws Exception {
		return sendData("clickOnMenuItem", item);
	}

	/**
	 * will click on any view with the input index
	 * 
	 * @param index
	 *            the index of the view on the screen
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse clickOnView(int index) throws Exception {
		return sendData("clickOnView", Integer.toString(index));
	}

	@Override
	public CommandResponse verifyViewExistsByDescription(String description, boolean click, boolean startsWith,
			boolean clickInSpecificPosition, float x, float y) throws Exception {
		return sendData("verifyViewExistsByDescription", description, Boolean.toString(click),
				Boolean.toString(startsWith), Boolean.toString(clickInSpecificPosition), Float.toString(x),
				Float.toString(y));
	}

	/**
	 * will enter text in a text box by the input text and index
	 * 
	 * @param index
	 *            the index of the text box on the screen
	 * @param text
	 *            the text to enter
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse enterText(int index, String text) throws Exception {
		return sendData("enterText", Integer.toString(index), text);
	}

	/**
	 * will click on button with the input index
	 * 
	 * @param index
	 *            the index of the button on the screen
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse clickOnButton(int index) throws Exception {
		return sendData("clickOnButton", Integer.toString(index));
	}

	/**
	 * will clear a text box with the input index
	 * 
	 * @param index
	 *            the index of the text box to clear
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse clearEditText(int index) throws Exception {
		return sendData("clearEditText", Integer.toString(index));
	}

	/**
	 * will click on button that includes the input text
	 * 
	 * @param text
	 *            the text on the button
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse clickOnButtonWithText(String text) throws Exception {
		return sendData("clickOnButtonWithText", text);
	}

	/**
	 * will click on the text with the input text
	 * 
	 * @param text
	 *            the text to click on
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse clickOnText(String text) throws Exception {
		return sendData("clickOnText", text);
	}

	@Override
	public CommandResponse waitForActivity(String activityName, int timeout) throws Exception {
		return sendData("waitForActivity", activityName, Integer.toString(timeout));
	}
	
	@Override
	public CommandResponse finishOpenedActivities() throws Exception {
		return sendData("finishOpenedActivities");
	}

	/**
	 * will click on the single input char
	 * 
	 * @param key
	 *            ascii number of the char wanted to click
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse sendKey(int key) throws Exception {
		return sendData("sendKey", Integer.toString(key));
	}

	/**
	 * simulate a click on an hardware button
	 * 
	 * @param button
	 *            an enum of the possible hardware buttons
	 * @return return status of the operation
	 * @throws Exception
	 */
	@Override
	public CommandResponse clickOnHardwareButton(HardwareButtons button) throws Exception {
		return sendData("clickOnHardware", button.name());
	}

	/**
	 * will close the connection to the server
	 * 
	 * @throws Exception
	 */
	@Override
	public void closeConnection() throws Exception {
		sendData("exit");
	}

	@Override
	public CommandResponse getViews() throws Exception {
		return sendData("getViews");
	}

	@Override
	public CommandResponse swipeLeft() throws Exception {
		return sendData("swipeLeft");
	}

	@Override
	public CommandResponse swipeRight() throws Exception {
		return sendData("swipeRight");
	}

	@Override
	public CommandResponse clickOnImageButton(int index) throws Exception {
		return sendData("clickOnImageButton", Integer.toString(index));
	}

	@Override
	public CommandResponse clickOnImage(int index) throws Exception {
		return sendData("clickOnImage", Integer.toString(index));
	}

	@Override
	public CommandResponse scrollDown() throws Exception {
		return sendData("scrollDown", new String[] {});
	}

	@Override
	public CommandResponse isTextVisible(String text) throws Exception {
		return sendData("isTextVisible", new String[] { text });
	}

	@Override
	public CommandResponse assertTextVisible(String text) throws Exception {
		return sendData("assertTextVisible", new String[] { text });
	}

	@Override
	public CommandResponse scrollDownUntilTextIsVisible(String text) throws Exception {
		return sendData("scrollDownUntilTextIsVisible", new String[] { text });
	}

	@Override
	public CommandResponse getCurrentActivity() throws Exception {
		return sendData("getCurrentActivity", new String[] {});
	}

	@Override
	public CommandResponse clickOnActionBarItem(int index) throws Exception {
		return sendData("clickOnActionBarItem", new String[] { Integer.toString(index) });
	}

	@Override
	public CommandResponse clickOnScreen(float x, float y, boolean relative) throws Exception {
		return sendData("clickOnScreen", new String[] { Float.toString(x), Float.toString(y),
				(relative ? "relative" : "absolute") });
	}

	@Override
	public CommandResponse drag(float fromX, float toX, float fromY, float toY, int steps, boolean relative)
			throws Exception {
		return sendData("drag",
				new String[] { Float.toString(fromX), Float.toString(toX), Float.toString(fromY), Float.toString(toY),
						Integer.toString(steps), (relative ? "relative" : "absolute") });
	}

	@Override
	public CommandResponse setOrientation(int orientation) throws Exception {
		if (orientation == 0) {
			return sendData("setLandscapeOrientation");
		} else if (orientation == 1) {
			return sendData("setPortraitOrientation");
		}
		return null;
	}

	@Override
	public CommandResponse scrollToEdge(EDGE edge) throws Exception {
		return sendData("scrollToEdge", new String[] { (edge == EDGE.TOP ? "top" : "bottom") });
	}

	/**
	 * Takes a screenshot
	 * 
	 * @return JPEG file that represents the current screenshot or null if fails
	 *         to take the screenshot.
	 * @throws Exception
	 */
	@Override
	public File takeScreenshot() throws Exception {
		CommandResponse response = sendData("takeScreenshot");
		File tempFile = null;
		if (response.isSucceeded()) {
			byte[] body = Base64.decode(response.getResponse());

			tempFile = File.createTempFile("screenshot", ".jpg");
			FileOutputStream imageOutFile = null;
			try {
				imageOutFile = new FileOutputStream(tempFile);
				imageOutFile.write(body);
			} finally {
				if (imageOutFile != null) {
					imageOutFile.close();
				}
			}
		}

		return tempFile;
	}

	@Override
	public CommandResponse clickInList(int index) throws Exception {
		return sendData("clickInList", Integer.toString(index));
	}

	@Override
	public CommandResponse clickInList(int index1, int index2) throws Exception {
		return sendData("clickInList", Integer.toString(index1), Integer.toString(index2));
	}

	@Override
	public CommandResponse getAllVisibleIds() throws Exception {
		return sendData("getAllVisibleIds");
	}

	@Override
	public CommandResponse click(String expression) throws Exception {
		return sendData("click", expression);
	}

	@Override
	public CommandResponse clickOnWebElement(String by, String expression) throws Exception {
		return sendData("clickOnWebElement", by, expression);
	}

	@Override
	public CommandResponse enterTextInWebElement(String by, String expression, String text) throws Exception {
		return sendData("enterTextInWebElement", by, expression, text);
	}


}
