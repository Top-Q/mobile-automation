package il.co.topq.mobile.client.interfaces;

import il.co.topq.mobile.common.client.enums.HardwareButtons;
import il.co.topq.mobile.common.datamodel.CommandResponse;

import java.io.File;

/**
 * 
 * @author Bortman Limor,Tal ben shabtay this interface is the API that will be
 *         exposed to the clients. the features in this API has the ability to
 *         operate functions upon an android application
 * 
 */
public interface MobileClientInterface {

	public enum EDGE {
		TOP, BOTTOM
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
	public CommandResponse launch(String launcherActivityClass) throws Exception;

	/**
	 * will get the text of the input text view index
	 * 
	 * @param index
	 *            the index of the text view on the screen
	 * @return the text of the text view
	 * @throws Exception
	 */
	public CommandResponse getTextView(int index) throws Exception;

	/**
	 * will get the id of the text view with the input text
	 * 
	 * @param text
	 *            the text that is in the text view
	 * @return id of the text view
	 * @throws Exception
	 */
	public CommandResponse getTextViewIndex(String text) throws Exception;

	/**
	 * will get a list of all the texts of the current display text views
	 * 
	 * @return list of all the text views current displayed
	 * @throws Exception
	 */
	public CommandResponse getCurrentTextViews() throws Exception;

	/**
	 * will get the text of the view by the input index
	 * 
	 * @param index
	 *            the index of the view in the screen
	 * @return the text of the input index text view
	 * @throws Exception
	 */
	public CommandResponse getText(int index) throws Exception;

	/**
	 * will click on the menu item with the input text
	 * 
	 * @param item
	 *            the text of the item in the menu
	 * @return return status of the operation
	 * @throws Exception
	 */
	public CommandResponse clickOnMenuItem(String item) throws Exception;

	/**
	 * will click on any view with the input index
	 * 
	 * @param index
	 *            the index of the view on the screen
	 * @return return status of the operation
	 * @throws Exception
	 */
	public CommandResponse clickOnView(int index) throws Exception;

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
	public CommandResponse enterText(int index, String text) throws Exception;

	/**
	 * will click on button with the input index
	 * 
	 * @param line
	 *            the index of the button on the screen
	 * @return return status of the operation
	 * @throws Exception
	 */
	public CommandResponse clickOnButton(int line) throws Exception;

	/**
	 * will clear a text box with the input index
	 * 
	 * @param index
	 *            the index of the text box to clear
	 * @return return status of the operation
	 * @throws Exception
	 */
	public CommandResponse clearEditText(int index) throws Exception;

	/**
	 * will click on button that includes the input text
	 * 
	 * @param text
	 *            the text on the button
	 * @return return status of the operation
	 * @throws Exception
	 */
	public CommandResponse clickOnButtonWithText(String text) throws Exception;

	/**
	 * will click on the text with the input text
	 * 
	 * @param text
	 *            the text to click on
	 * @return return status of the operation
	 * @throws Exception
	 */
	public CommandResponse clickOnText(String text) throws Exception;

	/**
	 * simulate a click on an hardware button
	 * 
	 * @param button
	 *            an enum of the possible hardware buttons
	 * @return return status of the operation
	 * @throws Exception
	 */
	public CommandResponse clickOnHardwareButton(HardwareButtons button) throws Exception;

	/**
	 * will click on the single input char
	 * 
	 * @param key
	 *            ascii number of the char wanted to click
	 * @return return status of the operation
	 * @throws Exception
	 */
	public CommandResponse sendKey(int key) throws Exception;

	/**
	 * will close the connection to the server
	 * 
	 * @throws Exception
	 */
	public void closeConnection() throws Exception;

	public CommandResponse getViews() throws Exception;

	public CommandResponse swipeLeft() throws Exception;

	public CommandResponse swipeRight() throws Exception;

	public CommandResponse clickOnImageButton(int index) throws Exception;

	public CommandResponse clickOnImage(int index) throws Exception;

	public CommandResponse scrollDown() throws Exception;

	public CommandResponse isTextVisible(String text) throws Exception;

	public CommandResponse scrollDownUntilTextIsVisible(String text) throws Exception;

	public CommandResponse getCurrentActivity() throws Exception;

	public CommandResponse clickOnActionBarItem(int index) throws Exception;

	public CommandResponse clickOnScreen(float x, float y, boolean relative) throws Exception;

	public CommandResponse drag(float fromX, float toX, float fromY, float toY, int steps, boolean relative)
			throws Exception;

	public CommandResponse setOrientation(int orientation) throws Exception;

	public CommandResponse scrollToEdge(EDGE edge) throws Exception;

	public CommandResponse verifyViewExistsByDescription(String description, boolean click, boolean startsWith,
			boolean clickInSpecificPosition, float x, float y) throws Exception;

	public CommandResponse clickInList(int index1, int index2) throws Exception;

	public CommandResponse clickInList(int index) throws Exception;

	public CommandResponse click(String expression) throws Exception;

	public CommandResponse getAllVisibleIds() throws Exception;

	public CommandResponse waitForActivity(String activity, int timeout) throws Exception;

	public CommandResponse clickOnWebElement(String by, String expression) throws Exception;

	public CommandResponse enterTextInWebElement(String by, String expression, String text) throws Exception;

	public File takeScreenshot() throws Exception;
}
