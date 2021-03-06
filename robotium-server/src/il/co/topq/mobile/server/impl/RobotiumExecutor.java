package il.co.topq.mobile.server.impl;

import il.co.topq.mobile.server.interfaces.ISoloProvider;

import il.co.topq.mobile.server.interfaces.IExecutorService;
import il.co.topq.mobile.server.interfaces.IDataCallback;



import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.jayway.android.robotium.solo.Solo;
import com.jayway.android.robotium.solo.SoloEnhanced;


/**
 * 
 * @author Bortman Limor , Tal ben shabtay
 * the class is the robotium executor
 * it will get commands from the service and executes them
 */
public class RobotiumExecutor extends Instrumentation implements ISoloProvider {
	
	private static final String TAG = "RobotiumExecutor";
	private Activity myActive = null;
	private SoloExecutor executor = null;
	private String launcherActivityClass;
	private Solo solo = null;
	private IExecutorService serviceApi;
	
	private ServiceConnection serviceConnection = new ServiceConnection() {
		
		/**
		 *	creates a connection to the service and instantiate the service api  
		 */
		  @Override
		  public void onServiceConnected(ComponentName name, IBinder service) {
		    Log.i(TAG, "Service connection established");
		    serviceApi = IExecutorService.Stub.asInterface(service);   
		    try {
				serviceApi.registerExecutor(executorListener);
			} 
		    catch (RemoteException e) {
				Log.e(TAG,"Error in registration" ,e);
			}
		  }
		 
		  /**
		   * kills the connection to the service
		   */
		  @Override
		  public void onServiceDisconnected(ComponentName name) {
		    Log.i(TAG, "Service connection closed");  
		    serviceApi = null;
		  }
	};
	
	private IDataCallback.Stub executorListener = new IDataCallback.Stub() {
		/**
		 * implements the data received from the service and executes this data 
		 * with the registered service executor
		 */
		public String dataReceived(String data) throws RemoteException {
			String result = null;
			try {
				result = getExecutor().execute(data).toString();
			} 
			catch (Exception e) {
				Log.e(TAG, "Error in command execution", e);
			}
			Log.d(TAG, "Recieved cmd result : "+result);
			return result;
		}
	};
	
	/**
	 * sets the main launcher activity class and starts the instrumentation
	 */
	@Override	
	public void onCreate(Bundle arguments) {	
		Log.d(TAG, "Creating Instrumentation");	
		super.onCreate(arguments);
		if (arguments != null) {
			if (arguments.containsKey("launcherActivityClass")) {
				this.launcherActivityClass = arguments.getString("launcherActivityClass");
				Log.d(TAG,"Activity class is : " + arguments.getString("launcherActivityClass")); 
			} 
			else {
				Log.e (TAG, "no launcherActivityClass here!");
				System.exit(100);
			}
		}
		Log.d(TAG, "Target Context : "+getTargetContext());
		Log.d(TAG, "This Context : "+getContext());
		Log.d(TAG, "Target Package : "+getTargetContext().getPackageName());
    	Intent service = new Intent(ExecutorService.class.getName());	
    	getContext().bindService(service,this.serviceConnection , 0);
    
		start();
	}
	
	@Override	
	public void onStart() {
		super.onStart();
	}

	void prepareLooper() {  
		Looper.prepare(); 
	}
	
	/**
	 * provides a solo from the instrumentation
	 */
	@Override	
	public Solo getSolo() {	
		if(this.myActive == null) {
			Log.i(TAG, "Starting AUT main activity");
			Intent intent = new Intent(Intent.ACTION_MAIN);		
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClassName(getTargetContext().getPackageName(),this.launcherActivityClass);	
			this.myActive = startActivitySync(intent);
			Log.i(TAG, "App is started");
		}
		if(this.solo == null){
			prepareLooper();
			this.solo = new SoloEnhanced(this,this.myActive);	
		}
		return this.solo;
	}
	
	/**
	 * provides a solo executor
	 * @return solo executor
	 */
	public SoloExecutor getExecutor(){	
		if(this.executor == null) {			
			this.executor = new SoloExecutor(this, this);
		}		
		return this.executor;	
	}
}
