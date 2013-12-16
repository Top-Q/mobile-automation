package il.co.topq.mobile.server.impl;

import il.co.topq.mobile.server.impl.SoloUtils.AXIS;
import il.co.topq.mobile.server.interfaces.ISoloProvider;
import il.co.topq.mobile.viewexpr.ViewExpressionException;
import il.co.topq.mobile.viewexpr.ViewExpressionInterperter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import il.co.topq.mobile.common.datamodel.CommandRequest;
import il.co.topq.mobile.common.datamodel.CommandResponse;
import il.co.topq.mobile.common.server.utils.JsonParser;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jayway.android.robotium.solo.By;
import com.jayway.android.robotium.solo.Solo;
import com.jayway.android.robotium.solo.SoloEnhanced;

/**
 * 
 * @author tal ben shabtay,limor bortman executes the client command with the
 *         solo interface
 */
public class SoloExecutor {

	private static final String TAG = "SoloExecutor";
	private Instrumentation instrumentation;
	private Solo solo;
	private final ISoloProvider soloProvider;

	/**
	 * creates a solo executor
	 * 
	 * @param soloProvider
	 *            an interface that provides a solo object
	 * @param instrumentation
	 *            the instrumentation of the AUT
	 */
	public SoloExecutor(final ISoloProvider soloProvider, Instrumentation instrumentation) {
		super();
		this.soloProvider = soloProvider;
		this.instrumentation = instrumentation;
	}

	/**
	 * executes the command with the solo
	 * 
	 * @param data
	 *            json of command request object
	 * @return json of a command response object
	 * @throws Exception
	 */
	public String execute(final String data) throws Exception {
		// ScriptParser parser;
		// JSONObject result = new JSONObject();
		// parser = new ScriptParser(data);
		CommandRequest request = JsonParser.fromJson(data, CommandRequest.class);
		CommandResponse response = new CommandResponse();
		// for (CommandParser command : parser.getCommands()) {
		String commandStr = request.getCommand();
		if (commandStr.equals("scrollToEdge")) {
			response = scrollToEdge(request.getParams());
		} else if (commandStr.equals("setPortraitOrientation")) {
			response = setOrientation(Solo.PORTRAIT, request.getParams());
		} else if (commandStr.equals("setLandscapeOrientation")) {
			response = setOrientation(Solo.LANDSCAPE, request.getParams());
		} else if (commandStr.equals("drag")) {
			response = drag(request.getParams());
		} else if (commandStr.equals("clickOnScreen")) {
			response = clickOnScreen(request.getParams());
		} else if (commandStr.equals("clickOnActionBarItem")) {
			response = clickOnActionBarItem(request.getParams());
		} else if (commandStr.equals("getCurrentActivity")) {
			response = getCurrentActivity(request.getParams());
		} else if (commandStr.equals("scrollDownUntilTextIsVisible")) {
			response = scrollDownUntilTextIsVisible(request.getParams());
		} else if (commandStr.equals("scrollDown")) {
			response = scrollDown(request.getParams());
		} else if (commandStr.equals("isTextVisible")) {
			response = isTextVisible(request.getParams());
		} else if (commandStr.equals("assertTextVisible")) {
			response = assertTextVisible(request.getParams());
		} else if (commandStr.equals("clickOnImageButton")) {
			response = clickOnImagButton(request.getParams());
		} else if (commandStr.equals("clickOnImage")) {
			response = clickOnImag(request.getParams());
		} else if (commandStr.equals("swipeRight")) {
			response = swipeRight(request.getParams());
		} else if (commandStr.equals("swipeLeft")) {
			response = swipeLeft(request.getParams());
		} else if (commandStr.equals("enterText")) {
			response = enterText(request.getParams());
		} else if (commandStr.equals("isButtonVisible")) {
			response = isButtonVisible(request.getParams());
		} else if (commandStr.equals("clickInControlByIndex")) {
			response = clickInControlByIndex(request.getParams());
		} else if (commandStr.equals("isViewVisibleByViewName")) {
			response = isViewVisibleByViewName(request.getParams());
		} else if (commandStr.equals("isViewVisibleByViewId")) {
			response = isViewVisibleByViewId(request.getParams());
		} else if (commandStr.equals("clickOnButton")) {
			response = clickOnButton(request.getParams());
		} else if (commandStr.equals("launch")) {
			response = launch();
		} else if (commandStr.equals("clearEditText")) {
			response = clearEditText(request.getParams());
		} else if (commandStr.equals("clickOnButtonWithText")) {
			response = clickOnButtonWithText(request.getParams());
		} else if (commandStr.equals("clickOnView")) {
			response = clickOnView(request.getParams());
		} else if (commandStr.equals("verifyViewExistsByDescription")) {
			response = verifyViewExistsByDescription(request.getParams());
		} else if (commandStr.equals("clickOnText")) {
			response = clickOnText(request.getParams());
		} else if (commandStr.equals("sendKey")) {
			response = sendKey(request.getParams());
		} else if (commandStr.equals("clickOnMenuItem")) {
			response = clickOnMenuItem(request.getParams());
		} else if (commandStr.equals("getViews")) {
			response = getViews(request.getParams());
		} else if (commandStr.equals("getText")) {
			response = getText(request.getParams());
		} else if (commandStr.equals("getTextViewIndex")) {
			response = getTextViewIndex(request.getParams());
		} else if (commandStr.equals("getTextView")) {
			response = getTextView(request.getParams());
		} else if (commandStr.equals("getCurrentTextViews")) {
			response = getCurrentTextViews(request.getParams());
		} else if (commandStr.equals("clickOnHardware")) {
			response = clickOnHardware(request.getParams());
		} else if (commandStr.equals("createFileInServer")) {
			response = createFileInServer(request.getParams());
		} else if (commandStr.equals("activateIntent")) {
			response = activateIntent(request.getParams());
		} else if (commandStr.equals("waitForActivity")) {
			response = waitForActivity(request.getParams());
		} else if (commandStr.equals("takeScreenshot")) {
			response = takeScreenshot();
		} else if (commandStr.equals("clickInList")) {
			response = clickInList(request.getParams());
		} else if (commandStr.equals("getAllVisibleIds")) {
			response = getAllVisibleIds();
		} else if (commandStr.equals("click")) {
			response = click(request.getParams());
		} else if (commandStr.equals("clickOnWebElement")) {
			response = clickOnWebElement(request.getParams());
		} else if (commandStr.equals("enterTextInWebElement")) {
			response = enterTextInWebElement(request.getParams());
		}
		response.setOriginalCommand(request.getCommand());
		response.setParams(request.getParams());
		String result = JsonParser.toJson(response);
		Log.i(TAG, "The Result is:" + result);
		return result;

	}

	private CommandResponse enterTextInWebElement(String[] params) {
		CommandResponse result = new CommandResponse();
		result.setOriginalCommand("enterTextInWebElement");
		Log.i(TAG, "About to enter text in web element");
		String byStr = params[0];
		String expression = params[1];
		String text = params[2];
		if (byStr == null || byStr.equals("")) {
			result.setSucceeded(false);
			result.setResponse("By paramter can't be null");
			return result;
		}
		By by = parseWebComponenetLocator(byStr, expression);
		;
		try {
			solo.enterTextInWebElement(by, text);
			result.setSucceeded(true);
			result.setResponse("Entered text in web element using locator " + byStr + " and expression " + expression);
		} catch (Exception e) {
			result = handleException(result.getOriginalCommand(), e);
		}
		return result;

	}

	private CommandResponse clickOnWebElement(String[] params) {
		CommandResponse result = new CommandResponse();
		result.setOriginalCommand("clickOnWebElement");
		Log.i(TAG, "About to click on web element");
		String byStr = params[0];
		String expression = params[1];
		if (byStr == null || byStr.equals("")) {
			result.setSucceeded(false);
			result.setResponse("By paramter can't be null");
			return result;
		}
		By by = parseWebComponenetLocator(byStr, expression);
		try {
			solo.clickOnWebElement(by);
			result.setSucceeded(true);
			result.setResponse("Clicked on web element using locator " + byStr + " and expression " + expression);

		} catch (Exception e) {
			result = handleException(result.getOriginalCommand(), e);
		}

		return result;
	}

	private By parseWebComponenetLocator(String byStr, String expression) {
		// TODO: Handle failure
		By by = null;
		if (byStr.equals("cssSelector")) {
			by = By.cssSelector(expression);
		} else if (byStr.equals("name")) {
			by = By.name(expression);
		} else if (byStr.equals("xpath")) {
			by = By.xpath(expression);
		} else if (byStr.equals("id")) {
			by = By.id(expression);
		} else if (byStr.equals("textContent")) {
			by = By.textContent(expression);
		} else if (byStr.equals("className")) {
			by = By.className(expression);
		} else if (byStr.equals("tagName")) {
			by = By.tagName(expression);
		}
		return by;
	}

	private CommandResponse click(String[] params) {
		CommandResponse result = new CommandResponse();
		result.setOriginalCommand("click");
		final String expression = params[0];
		Log.i(TAG, "About to click on " + expression);
		try {
			List<View> views = getViews(expression);
			if (views.size() == 0) {
				result.setResponse("No views found to match expression " + expression);
				result.setSucceeded(false);
				return result;
			}
			for (View view : views) {
				solo.clickOnView(view);
			}
			result.setResponse("Clicked on " + views.size() + " views");
			result.setSucceeded(true);
		} catch (ViewExpressionException e) {
			result.setSucceeded(false);
			result.setResponse("Failed evalauating expression " + expression);
		}
		return result;
	}

	private List<View> getViews(String expression) throws ViewExpressionException {
		ViewExpressionInterperter interperter = new ViewExpressionInterperter(solo.getViews().get(0).getRootView());
		return interperter.evaluate(expression);
	}

	private CommandResponse scrollToEdge(String[] params) {
		String command = "the command \"scroll to edge\": ";
		CommandResponse result = new CommandResponse();
		try {
			if (params[0].equals("top")) {
				while (scrollUp()) {

				}
				result.setSucceeded(true);
				result.setResponse(command);
				Log.e(TAG, "Scrollup was successfull");
			} else {
				while (scrollDown()) {

				}
				result.setSucceeded(true);
				result.setResponse(command);
			}
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse setOrientation(int orientation, String[] params) {
		String command = "the command set orientation: ";
		CommandResponse result = new CommandResponse();
		try {
			this.solo.setActivityOrientation(orientation);
			result.setSucceeded(true);
			result.setResponse(command);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse waitForActivity(String[] params) {
		String command = "the command wait for activity: ";
		CommandResponse result = new CommandResponse();
		try {
			if (params.length == 2) {
				result.setSucceeded(solo.waitForActivity(params[0], Integer.parseInt(params[1])));
			} else if (params.length == 1) {
				result.setSucceeded(solo.waitForActivity(params[0]));
			}
			result.setResponse(command + "Wait for activity ended with status " + result.isSucceeded());
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse clickOnActionBarItem(String[] params) {
		String command = "the command click on actionbar item: ";
		CommandResponse result = new CommandResponse();
		try {
			solo.clickOnActionBarItem(Integer.parseInt(params[0]));
			result.setSucceeded(true);
			result.setResponse("Unable to tell whether the action suceeded or not");
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse getCurrentActivity(String[] params) {
		String command = "the command get current activity";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = this.solo.getCurrentActivity().getLocalClassName();
			if (response == null || response.equals("")) {
				result.setResponse(command + ",Response: failed to get the activity name");
				result.setSucceeded(false);
			} else {
				result.setResponse(command + ", Response: Activity name is: " + response);
				result.setSucceeded(true);
			}
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse scrollDown(String[] params) {
		String command = "the command scroll down";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = "scrolling down";
			if (scrollDown()) {
				response += command + ",Response: " + response + " scrolled down successfully";
				result.setSucceeded(true);
				result.setResponse(response);
			} else {
				response += command + ",Response: " + response + " can't scroll down";
				result.setSucceeded(true);
				result.setResponse(response);

			}

		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse isTextVisible(String[] params) {
		String command = "the command is text visible";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = "checking if text is visible";
			if (solo.searchText(params[0])) {
				result.setResponse(command + ",Response: " + response + " is visible");
			} else {
				result.setResponse(command + ",Response: " + response + " is not visible");
			}
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse assertTextVisible(String[] params) {
		String command = "the command is text visible";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = "checking if text is visible";
			if (solo.searchText(params[0])) {
				result.setResponse(command + ",Response: " + response + " is visible");
				result.setSucceeded(true);
			} else {
				result.setResponse(command + ",Response: " + response + " is not visible");
				result.setSucceeded(false);
			}
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse scrollDownUntilTextIsVisible(String[] params) {
		String command = "the command scroll down until text is visible";
		CommandResponse result = new CommandResponse();
		String response;
		try {
			response = "Trying to scroll down until the requested text will be visible";
			while (!this.solo.searchText(params[0])) {
				if (!scrollDown()) {
					break;
				}
			}
			if (this.solo.searchText(params[0])) {
				result.setResponse(command + ",Response: " + response + ": text found");
			} else {
				result.setResponse(command + ",Response: " + response + ": text not found");
			}
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * checks if the view is visible with the input id
	 * 
	 * @param arguments
	 *            id of the view
	 * @return response with the status of the command
	 */
	private CommandResponse isViewVisibleByViewId(String[] arguments) {
		CommandResponse result = new CommandResponse();
		String command = "the command isViewVisible";
		try {
			int viewId = Integer.parseInt(arguments[0]);
			command += "(" + viewId + ")";
			View view = this.solo.getView(viewId);
			if (view != null) {
				if (view.isShown()) {
					result.setResponse("view with ID: " + viewId + " is visible");
					result.setSucceeded(true);
				} else {
					result.setResponse("view with ID: " + viewId + " is not visible");
					result.setSucceeded(true);
				}
			} else {
				result.setResponse("view with ID: " + viewId + " is not found ");
				result.setSucceeded(false);
			}
		} catch (Throwable e) {
			result.setResponse(command + "failed due to " + e.getMessage());
			result.setSucceeded(false);
			Log.d(TAG, result.getResponse());
		}
		return result;
	}

	/**
	 * checks if the view is visible with the input view name
	 * 
	 * @param arguments
	 *            view name
	 * @return response with the status of the command
	 */
	private CommandResponse isViewVisibleByViewName(String[] arguments) {
		CommandResponse result = new CommandResponse();
		String command = "the command isViewVisible";
		try {
			String viewName = arguments[0];
			command += "(" + viewName + ")";
			View view = findViewByName(viewName);
			if (view.isShown()) {
				result.setResponse("view: " + viewName + " is visible");
				result.setSucceeded(true);
			} else {
				result.setResponse("view: " + viewName + " is not visible");
				result.setSucceeded(true);
			}
		} catch (Throwable e) {
			result.setResponse(command + "failed due to " + e.getMessage());
			Log.d(TAG, result.getResponse());
		}
		return result;
	}

	private CommandResponse swipeLeft(String[] parameters) {
		String command = "the command swipe left";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = "attempting to swipe to the left";
			String[] dragParams = new String[] { Float.toString(0.9f), Float.toString(0.1f), Float.toString(0.5f),
					Float.toString(0.5f), Integer.toString(25), "relative" };
			CommandResponse dragResponse = drag(dragParams);
			result.setResponse(command + ",Response: " + response + ", " + dragResponse.getResponse());
			result.setSucceeded(dragResponse.isSucceeded());
			if (!result.isSucceeded()) {
				throw new Exception("Attempt to swipe to the left has failed");
			}
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse swipeRight(String[] parameters) {
		String command = "the command swipe right";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = "attempting to swipe to the right";
			String[] dragParams = new String[] { Float.toString(0.1f), Float.toString(0.9f), Float.toString(0.5f),
					Float.toString(0.5f), Integer.toString(25), "relative" };
			CommandResponse dragResponse = drag(dragParams);
			result.setResponse(command + ",Response: " + response + ", " + dragResponse.getResponse());
			result.setSucceeded(dragResponse.isSucceeded());
			if (!result.isSucceeded()) {
				throw new Exception("Attempt to swipe to the right has failed");
			}
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse clickOnImagButton(String[] parameters) {
		String command = "the command click on image button";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = "attempting to click on an image button";
			this.solo.clickOnImageButton(Integer.parseInt(parameters[0]));
			result.setResponse(command + ",Response: " + response);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse clickOnImag(String[] parameters) {
		String command = "the command click on image";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = "attempting to click on an image button";
			solo.clickOnImage(Integer.parseInt(parameters[0]));
			result.setResponse(command + ",Response: " + response);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse activateIntent(String[] arguments) {
		String command = null;
		CommandResponse result = new CommandResponse();
		try {
			command = "the command  activateIntent(" + arguments[0] + " " + arguments[1] + " " + arguments[2] + " "
					+ arguments[3] + " " + arguments[4] + ")";

			/*
			 * if (arguments[0].equals("ACTION_VIEW")) { Intent webIntent = new
			 * Intent(Intent.ACTION_VIEW, Uri.parse(arguments[1])); Log.d(TAG,
			 * "Sending intent to " + solo.getClass().getSimpleName());
			 * solo.getCurrentActivity().startActivityForResult(webIntent, 1);
			 * }else if (arguments[0].equals("com.greenroad.PlayTrip")) {
			 */
			Log.d(TAG, "Sending intent");
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction(arguments[0]);
			for (int i = 1; i < arguments.length; i = i + 2) {
				broadcastIntent.putExtra(/*
										 * solo.getCurrentActivity().
										 * getCallingPackage()+
										 */arguments[i], arguments[i + 1]);
			}
			this.solo.getCurrentActivity().sendBroadcast(broadcastIntent);
			result.setResponse("Activate Intent Succeeded");
			result.setSucceeded(true);
			// }
			/*
			 * else if (arguments[0].equals("ACTION_SEND")) { Intent sendIntent
			 * = new Intent(); sendIntent.setAction(arguments[0]); for(int i =
			 * 1;i<arguments.length;i=i+2){ Log.i(TAG, "check: " + arguments[i]
			 * + " " + arguments[i+1]);
			 * sendIntent.putExtra(solo.getCurrentActivity
			 * ().getCallingPackage()+
			 * "."+arguments[i],android.content.Intent.EXTRA_TEXT,
			 * arguments[i+1]); }
			 * solo.getCurrentActivity().startActivity(sendIntent); }
			 */
		} catch (Exception e) {
			result = handleException(command, e);
		}
		return result;
	}

	@SuppressLint("NewApi")
	private CommandResponse createFileInServer(String[] arguments) {
		String command = "the command  createFileInServer";
		CommandResponse result = new CommandResponse();
		try {
			if (Boolean.parseBoolean(arguments[2])) {
				byte[] data = Base64.decode(arguments[1], Base64.URL_SAFE);
				command += "(" + arguments[0] + ", " + data + ")";
				command += "(" + arguments[0] + ", " + data + ")";
				FileOutputStream fos = new FileOutputStream(arguments[0]);
				fos.write(data);
				fos.close();
			} else {
				command += "(" + arguments[0] + ", " + arguments[1] + ")";
				FileWriter out = new FileWriter(arguments[0]);
				out.write(arguments[1]);
				out.close();
			}
			result.setResponse(command);
			result.setSucceeded(true);
			Log.d(TAG, "run the command:" + command);
		} catch (Exception e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * gets the text of all the current text views
	 * 
	 * @return response with the status of the command
	 */
	private CommandResponse getCurrentTextViews(String[] arguments) {
		String command = "the command  getCurrentTextViews";
		CommandResponse result = new CommandResponse();
		StringBuilder response = new StringBuilder();
		try {
			command += "(" + arguments[0] + ")";
			List<TextView> textViews = this.solo.getCurrentViews(TextView.class);
			for (int i = 0; i < textViews.size(); i++) {
				response.append(i).append(",").append(textViews.get(i).getText().toString()).append(";");
			}
			result.setResponse(command + ",Response: " + response.toString());
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * will get the text view with the input index
	 * 
	 * @param arguments
	 *            index of the text view
	 * @return command response with the text of the input text view index
	 */
	private CommandResponse getTextView(String[] arguments) {
		String command = "the command  getTextView";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			command += "(" + arguments[0] + ")";
			response = this.solo.getCurrentViews(TextView.class).get(Integer.parseInt(arguments[0])).getText()
					.toString();
			result.setResponse(command + ",Response: " + response);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * gets the text of the view by the input index
	 * 
	 * @param arguments
	 *            the index of the view
	 * @return response with the status of the command
	 */
	private CommandResponse getTextViewIndex(String[] arguments) {
		String command = "the command  getTextViewIndex";
		CommandResponse result = new CommandResponse();
		StringBuilder response = new StringBuilder();
		try {
			command += "(" + arguments[0] + ")";
			List<TextView> textViews = this.solo.getCurrentViews(TextView.class);
			for (int i = 0; i < textViews.size(); i++) {
				if (arguments[0].trim().equals(textViews.get(i).getText().toString())) {
					response.append(i).append(";");
				}
			}
			result.setResponse(command + ",Response: " + response.toString());
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * gets the text by index
	 * 
	 * @param arguments
	 *            index
	 * @return response with the status of the command
	 */
	private CommandResponse getText(String[] arguments) {
		String command = "the command  getText";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			command += "(" + arguments[0] + ")";
			response = solo.getText(Integer.parseInt(arguments[0])).getText().toString();
			result.setResponse(command + ",Response: " + response);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse getViews(String[] arguments) {
		String command = "the command  getViews";
		CommandResponse result = new CommandResponse();
		String response = "";
		try {
			response = solo.getViews().toString();
			result.setResponse(command + ",Response: " + response);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * clicks on a menu item
	 * 
	 * @param arguments
	 *            the item to click on
	 * @return response with the status of the command
	 */
	private CommandResponse clickOnMenuItem(String[] arguments) {
		String command = "the command  clickOnMenuItem";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + arguments[0] + ")";
			this.solo.clickOnMenuItem(arguments[0]);
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * sends a single character key click
	 * 
	 * @param arguments
	 *            the char to click on
	 * @return response with the status of the command
	 */
	private CommandResponse sendKey(String[] arguments) {
		String command = "the command  sendKey";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + arguments[0] + ")";
			this.solo.sendKey(Integer.parseInt(arguments[0]));
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * clicks in the input control on the input index
	 * 
	 * @param commandParameters
	 *            [0] the control id , [1] the index of the item to click
	 * @return response with the status of the command
	 * @throws Exception
	 */
	private CommandResponse clickInControlByIndex(String[] commandParameters) throws Exception {
		String command = "The command clickInControlByIndex";
		CommandResponse result = new CommandResponse();
		try {
			int controlId = Integer.parseInt(commandParameters[0]);
			int indexToClickOn = Integer.parseInt(commandParameters[1]);
			command += "(controlId: " + controlId + ")";
			command += "(indexToClickOn: " + indexToClickOn + ")";
			View control = this.solo.getView(controlId);
			if (control != null) {
				if (indexToClickOn < control.getTouchables().size()) {
					clickOnView(control.getTouchables().get(indexToClickOn), false, false);
					result.setResponse(command);
					result.setSucceeded(true);
				} else {
					result.setResponse(command
							+ "failed due to: index to click in control is out of bounds. control touchables: "
							+ control.getTouchables().size());
					result.setSucceeded(false);
				}
			} else {
				result.setResponse(command + "failed due to failed to find control with id: " + controlId);
				result.setSucceeded(false);
			}
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * This method will search the requested view / control by its name in the
	 * currentViews <br>
	 * 
	 * @param viewName
	 *            the name of the view
	 * @return response with the status of the command
	 * @throws Exception
	 */
	private View findViewByName(String viewName) throws Exception {
		ArrayList<View> currentViews = this.solo.getCurrentViews();
		for (View view : currentViews) {
			if (view.getClass().getName().contains(viewName)) {
				return view;
			}
		}
		throw new Exception("View : " + viewName + " was not found in current views ");
	}

	/**
	 * click on the view id
	 * 
	 * @param arguments
	 *            id of the view to click
	 * @return response with the status of the command
	 */
	private CommandResponse clickOnView(String[] arguments) {
		String command = "the command  clickOnView";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + arguments[0] + ")";
			View view = solo.getView(Integer.parseInt(arguments[0]));
			clickOnView(view, false, false);
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Exception e) {
			result = handleException(command, e);
		}
		return result;
	}

	private CommandResponse verifyViewExistsByDescription(String[] arguments) {
		String command = "the command  verify view exists by description";
		CommandResponse result = new CommandResponse();
		if (arguments == null || arguments.length < 2) {
			result.setResponse("Not all parameters were received");
			result.setSucceeded(false);
		} else {
			try {
				boolean startsWith = false;
				boolean clickInSpecificPosition = false;
				float x, y;
				x = y = 0.0f;
				if (arguments.length > 2) {
					startsWith = Boolean.parseBoolean(arguments[2]);
					clickInSpecificPosition = Boolean.parseBoolean(arguments[3]);
					if (clickInSpecificPosition) {
						x = Float.parseFloat(arguments[4]);
						y = Float.parseFloat(arguments[5]);
					}
				}
				boolean click = arguments[1].equals("true") ? true : false;
				command += "(" + arguments[0] + ", also click: " + (click ? "yes" : "no") + ")";
				for (View view : solo.getViews()) {
					if (view == null || view.getContentDescription() == null) {
						continue;
					}
					if (view.getContentDescription() != null) {
						boolean condition;
						if (startsWith) {
							condition = view.getContentDescription().toString().startsWith(arguments[0]);
						} else {
							condition = view.getContentDescription().equals(arguments[0]);
						}
						if (condition && view.getVisibility() == View.VISIBLE) {
							try {
								if (click) {

									if (clickInSpecificPosition) {
										int[] location = new int[2];
										view.getLocationOnScreen(location);
										int addX = (int) (x * ((float) view.getWidth()));
										int addY = (int) (y * ((float) view.getHeight()));
										this.solo.clickOnScreen(x + addX, y + addY);
									} else {
										clickOnView(view, false, false);
									}

								}
							} catch (Throwable e) {

							}
							String addToCommand = (click ? (": Clicked on view with description: " + arguments[0])
									: ("View with description: " + arguments[0] + "exists"));
							result.setResponse(command + addToCommand);
							result.setSucceeded(true);
							return result;
						}
					}
				}
				result.setResponse(command + " :No view with description " + arguments[0] + " found");
				result.setSucceeded(false);
				return result;
			} catch (Exception e) {
				result = handleException(command, e);
			}
		}
		return result;
	}

	private CommandResponse clickInList(String[] arguments) {
		String command = "the command  clickInList(";
		CommandResponse result = new CommandResponse();
		try {
			if (arguments.length == 1) {
				command += "(" + arguments[0] + ")";
				this.solo.clickInList(Integer.parseInt(arguments[0]));
			} else {
				command += "(" + arguments[0] + ", " + arguments[1] + ")";
				this.solo.clickInList(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
			}

			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	public CommandResponse getAllVisibleIds() {
		CommandResponse result = new CommandResponse();
		String response = "";
		for (View view : this.solo.getViews()) {
			try {
				response += view.getContentDescription() + ":" + Integer.toHexString(view.getId()) + ":"
						+ view.getClass().getSimpleName() + "\r\n";
			} catch (Exception e) {
			}
		}
		result.setResponse(response);
		result.setSucceeded(true);
		return result;
	}

	private boolean scrollDown() {
		int index = 0;
		while (index < 5) {
			if (solo.scrollDown())
				return true;
			index++;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
		}
		return false;
	}

	private boolean scrollUp() {
		int index = 0;
		while (index < 5) {
			if (solo.scrollUp())
				return true;
			index++;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
		}
		return false;
	}

	public View findViewInList(View view, String description) {
		if (view == null)
			return null;
		View result;
		String tag;
		try {
			tag = (String) view.getTag();
		} catch (Throwable t) {
			Log.e(TAG, "error casting");
			tag = "";
		}
		Log.w(TAG, tag + " was found while searching list");
		if (view.getTag() != null && tag.equals(description)) {
			return view;
		} else {
			try {
				int max = ((ViewGroup) view).getChildCount();
				for (int i = 0; i < max; i++) {
					View nextChild = ((ViewGroup) view).getChildAt(i);
					result = findViewInList(nextChild, description);
					if (result != null)
						return result;
				}
			} catch (Exception e) {
			}

		}
		return null;
	}

	// //////////////////////////////////////////////////////////////////////////////////

	/**
	 * click on button with the input text
	 * 
	 * @param arguments
	 *            the text of the button to click
	 * @return response with the status of the command
	 */
	private CommandResponse clickOnButtonWithText(String[] arguments) {
		String command = "the command  clickOnButton";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + arguments[0] + ")";
			this.solo.clickOnButton(arguments[0]);
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * clears the text box
	 * 
	 * @param arguments
	 *            the id of the text box to clear
	 * @return response with the status of the command
	 */
	private CommandResponse clearEditText(String[] arguments) {
		String command = "the command  clearEditText";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + arguments[0] + ")";
			this.solo.clearEditText(Integer.parseInt(arguments[0]));
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * checks if button is visible
	 * 
	 * @param arguments
	 *            [0] search button by id or text, [1] the text or id to search
	 *            the button
	 * @return response with the status of the command
	 */
	private CommandResponse isButtonVisible(String[] arguments) {
		String command = "the command isButtonVisible ";
		CommandResponse result = new CommandResponse();
		boolean isVisible = false;
		try {
			String searchButtonByKey = arguments[0];
			command += "(findButtonBy: " + arguments[0] + ")";
			if (searchButtonByKey.equalsIgnoreCase("text")) {
				String searchButtonByTextValue = arguments[1];
				command += "(Value: " + searchButtonByTextValue + ")";
				isVisible = isButtonVisibleByText(searchButtonByTextValue);
			} else if (searchButtonByKey.equalsIgnoreCase("id")) {
				int searchButtonByIntValue = Integer.parseInt(arguments[1]);
				command += "(Value: " + searchButtonByIntValue + ")";
				isVisible = isButtonVisibleById(searchButtonByIntValue);
			}
		} catch (Throwable e) {
			return handleException(command, e);
		}
		if (isVisible) {
			result.setResponse(command + " is visible");
			result.setSucceeded(true);
		} else {
			result.setResponse(command + " is not visible");
			result.setSucceeded(true);
		}
		return result;
	}

	/**
	 * checks if the button with input text is visble
	 * 
	 * @param buttonText
	 *            the text of the button
	 * @return response with the status of the command
	 * @throws Exception
	 */
	private boolean isButtonVisibleByText(String buttonText) throws Exception {
		Button button = this.solo.getButton(buttonText);
		if (button != null) {
			return button.isShown();
		} else {
			throw new Exception("Button with text: " + buttonText + " was not found");
		}
	}

	/**
	 * checks if the button with input id is visible
	 * 
	 * @param buttonId
	 *            the id of the button
	 * @return response with the status of the command
	 * @throws Exception
	 */
	private boolean isButtonVisibleById(int buttonId) throws Exception {
		ArrayList<Button> currentButtons = this.solo.getCurrentViews(Button.class);
		for (Button button : currentButtons) {
			if (button.getId() == buttonId) {
				if (button.isShown()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * click on button with the input id
	 * 
	 * @param params
	 *            the id of the button
	 * @return response with the status of the command
	 */
	private CommandResponse clickOnButton(String[] params) {
		String command = "the command  clickOnButton";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + params[0] + ")";
			this.solo.clickOnButton(Integer.parseInt(params[0]));
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			return handleException(command, e);
		}
		return result;
	}

	/**
	 * enter text to the input text box id with the input text
	 * 
	 * @param params
	 *            [0] the id of the text box , [1] the text to enter
	 * @return response with the status of the command
	 */
	private CommandResponse enterText(String[] params) {
		String command = "the command  enterText";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + params[0] + "," + params[1] + ")";
			this.solo.enterText(Integer.parseInt(params[0]), params[1]);
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;

	}

	/**
	 * click on the input text
	 * 
	 * @param params
	 *            the text to click on
	 * @return response with the status of the command
	 */
	private CommandResponse clickOnText(String[] params) {
		String command = "the command clickOnText";
		CommandResponse result = new CommandResponse();
		try {
			if (params.length == 1) {
				command += "(" + params[0] + ")";
				solo.clickOnText(params[0]);
			}
			if (params.length == 2) {
				command += "(" + params[0] + "," + params[1] + ")";
				solo.clickOnText(params[0], Integer.parseInt(params[1]));

			}
			if (params.length == 3) {
				command += "(" + params[0] + "," + params[1] + "," + params[2] + ")";
				solo.clickOnText(params[0], Integer.parseInt(params[1]), Boolean.parseBoolean(params[2]));
			}

			result.setResponse(command + " : requested text was found and clicked on");
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;

	}

	/**
	 * 
	 * @param params
	 *            x and y coordinates to click and whether the the coordinates
	 *            are relative or absolute
	 * @return response with the status of the command
	 * 
	 */

	private CommandResponse clickOnScreen(String[] params) {
		String command = "the command click on screen: ";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + params[0] + "," + params[1] + "," + params[2] + ")";
			boolean relative = params[2].equals("relative");
			float x = Float.parseFloat(params[0]);
			float y = Float.parseFloat(params[1]);
			if (relative) {
				x = SoloUtils.convertRelativeToAbsolute(x, AXIS.X, this.solo);
				y = SoloUtils.convertRelativeToAbsolute(y, AXIS.Y, this.solo);
			}

			this.solo.clickOnScreen(x, y);
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * 
	 * @param params
	 *            x and y start coordinates, x and y end coordinates and whether
	 *            the the coordinates are relative or absolute
	 * @return response with the status of the command
	 * 
	 */

	private CommandResponse drag(String[] params) {
		String command = "the command click on screen: ";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + params[0] + "," + params[1] + "," + params[2] + "," + params[3] + "," + params[4] + ","
					+ params[5] + ")";
			int steps = Integer.parseInt(params[4]);
			boolean relative = params[5].equals("relative");
			float x1 = Float.parseFloat(params[0]);
			float x2 = Float.parseFloat(params[1]);
			float y1 = Float.parseFloat(params[2]);
			float y2 = Float.parseFloat(params[3]);
			if (relative) {
				x1 = SoloUtils.convertRelativeToAbsolute(x1, AXIS.X, this.solo);
				x2 = SoloUtils.convertRelativeToAbsolute(x2, AXIS.X, this.solo);
				y1 = SoloUtils.convertRelativeToAbsolute(y1, AXIS.Y, this.solo);
				y2 = SoloUtils.convertRelativeToAbsolute(y2, AXIS.Y, this.solo);
			}
			this.solo.drag(x1, x2, y1, y2, steps);
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * click on hardware button
	 * 
	 * @param keyString
	 *            an hardware button to click
	 * @return response with the status of the command
	 */
	private CommandResponse clickOnHardware(String[] keyString) {
		String command = "the command clickOnHardware";
		CommandResponse result = new CommandResponse();
		try {
			command += "(" + keyString[0] + ")";
			int key = (keyString[0] == "HOME") ? KeyEvent.KEYCODE_HOME : KeyEvent.KEYCODE_BACK;
			this.instrumentation.sendKeyDownUpSync(key);
			result.setResponse("click on hardware");
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * NOTE ! THIS METHOD MUST BE THE FIRST COMMAND BEFORE ANY OTHER COMMAND the
	 * launch method will launch the instrumentation of the application
	 * 
	 * @return return status of the operation
	 */
	private CommandResponse launch() {
		Log.i(TAG, "Robotium: About to launch application");
		CommandResponse result = new CommandResponse();
		String command = "the command  launch";
		try {
			this.solo = this.soloProvider.getSolo();
			result.setResponse(command);
			result.setSucceeded(true);
		} catch (Throwable e) {
			result = handleException(command, e);
		}
		return result;
	}

	/**
	 * handle an exception
	 * 
	 * @param command
	 *            the command that caused the exception
	 * @param e
	 *            the exception that was thrown
	 * @return response with the status of the command
	 */
	private CommandResponse handleException(final String command, Throwable e) {
		CommandResponse result = new CommandResponse();
		result.setResponse(command + " failed due to " + e.getMessage());
		Log.e(TAG, result.getResponse());
		return result;
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	private CommandResponse takeScreenshot() {
		Log.i(TAG, "About to take screenshot");
		return soloEnhancedScreenshot();
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	private CommandResponse soloEnhancedScreenshot() {
		CommandResponse response = new CommandResponse();
		response.setOriginalCommand("takeScreenshot");
		byte[] imageData = ((SoloEnhanced) solo).takeScreenshot(true);
		if (null == imageData) {
			response.setSucceeded(false);
			response.setResponse("Failed getting a screenshot");
			return response;
		}
		response.setResponse(Base64.encodeToString(imageData, Base64.DEFAULT));
		response.setSucceeded(true);
		return response;
	}

	/**
	 * clicks on a view
	 * 
	 * @param view
	 *            the view to click
	 * @throws Exception
	 */
	private void clickOnView(View view, boolean immediatly, boolean longClick) throws Exception {
		if (view.isShown()) {
			if (longClick) {
				this.solo.clickLongOnView(view);
			} else {
				this.solo.clickOnView(view, immediatly);
			}
		} else {
			throw new Exception("clickOnView FAILED view: " + view.getClass().getSimpleName() + " is not shown");
		}
	}

}
