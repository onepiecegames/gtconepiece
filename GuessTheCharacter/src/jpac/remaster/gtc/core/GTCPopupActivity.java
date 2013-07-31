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
package jpac.remaster.gtc.core;

import jpac.remaster.gtc.R;
import jpac.remaster.gtc.util.ResourceManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/******************************************************************************
 * Superclass for all popup activity in this application. As for now, this class
 * do not serve any purpose other than as an identifier for popups.
 * 
 * @author JP Carabuena
 * @since 2.0
 *****************************************************************************/
public class GTCPopupActivity extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_template);

		String title = getStringExtra("title");
		String message = getStringExtra("message");

		setText(R.id.title, title);
		setText(R.id.message, message);

		String font = getStringExtra("title_font");
		setTypeface(R.id.title, ResourceManager.getFont(font));

		// modify ok button
		setText(R.id.okButton, getStringExtra("button1"));
		setOnClickListener(R.id.okButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK);
				finish();
			}
		});

		// modify cancel button
		setText(R.id.cancelButton, getStringExtra("button2"));
		setOnClickListener(R.id.cancelButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
		// change the default banner background if specified
		int res = getIntExtra("banner_resource");
		if (res > 1) {
			setBackground(R.id.title, res);
		}
		
		// remove the second button for acknowledgment popups
		if (getBooleanExtra("single_button")) {
			setVisibility(R.id.cancelButton, View.GONE);
		}
	}
}
