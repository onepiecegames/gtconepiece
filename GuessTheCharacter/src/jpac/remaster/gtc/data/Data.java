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
 *****************************************************************************/
package jpac.remaster.gtc.data;

import android.os.Bundle;

/******************************************************************************
 * Represents an application data.
 *****************************************************************************/
public interface Data {

	/**************************************************************************
	 * Returns the identification name for this data.
	 *************************************************************************/
	public long identify();
	
	/**************************************************************************
	 * Checks if the contents of data is valid.
	 *************************************************************************/
	public boolean isValid();
	
	/**************************************************************************
	 * Returns the content of the data as a bundle object.
	 *************************************************************************/
	public Bundle readContent();
	
	/**************************************************************************
	 * Update the contents of the data using a bundle object.
	 *************************************************************************/
	public void updateContent(Bundle bundle);
}
