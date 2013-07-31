/******************************************************************************
 * Copyright 2013
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package jpac.remaster.gtc.data;

import java.util.ArrayList;

import android.os.Bundle;

/******************************************************************************
 * Handles a the operation specific type of data. The following are the
 * functions of a handler:
 * 
 * - Parse bundle data
 * - Pass data to registered listeners
 * - Return data from request
 * 
 * @author JP Carabuena
 * @since 2.0
 *****************************************************************************/
public abstract class DataHandler {

	// ========================================================================
	// A list of registered data state listeners.
	// ========================================================================
	protected ArrayList<DataStateListener> dataStateListeners;
	
	/**************************************************************************
	 * Create the data handler.
	 *************************************************************************/
	public DataHandler() {
		dataStateListeners = new ArrayList<DataStateListener>(5);
	}
	
	/**************************************************************************
	 * Parse bundle data and converts it to specialized content.
	 *************************************************************************/
	public abstract void handle(Bundle data);
	
	/**************************************************************************
	 * Retrieve data object. Make sure that the raw data is already handled by
	 * invoking the {@link #handle(Bundle)} method before loading the data.
	 *************************************************************************/
	public abstract Data load();
	
	/**************************************************************************
	 * Register a data state listener to this handler.
	 *************************************************************************/
	public void addDataStateListener(DataStateListener listener) {
		dataStateListeners.add(listener);
	}
	
	/**************************************************************************
	 * Unregister a data state listener.
	 *************************************************************************/
	public boolean removeDataStateListener(DataStateListener listener) {
		return dataStateListeners.remove(listener);
	}

	/**************************************************************************
	 * Send notification to all data state listeners that the data has been
	 * updated.
	 *************************************************************************/
	public void sendDataUpdateNotification(Data data, long timeStamp) {
		int n = dataStateListeners.size();
		
		for (int i=0; i<n; i++) {
			dataStateListeners.get(i).onDataUpdate(data, timeStamp);
		}
	}
}
