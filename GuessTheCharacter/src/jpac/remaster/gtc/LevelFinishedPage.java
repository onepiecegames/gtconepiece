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
package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.ResourceManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/******************************************************************************
 * The activity displayed when the user solved the current puzzle.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class LevelFinishedPage extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle_done_page);

		setOnClickListener(R.id.continueButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						InGamePage.class);
				startActivity(intent);
				finish();
			}
		});

		String id = getStringExtra("image");
		setImage(R.id.puzzleImage, ResourceManager.loadBitmapFromAsset(id));

		Typeface ds = ResourceManager.getFont("digitalstrip.ttf");

		int amount = getIntExtra("prize");
		setText(R.id.amountLabel, "" + amount);
		setTypeface(R.id.amountLabel, ds);

		String desc = getStringExtra("answer");
		setText(R.id.answer, desc);
		setTypeface(R.id.answer, ds);

		setTypeface(R.id.banner, ds);
		setTypeface(R.id.titleLabel, ds);
		setTypeface(R.id.receiveLabel, ds);
		setTypeface(R.id.amountLabel, ds);
		setTypeface(R.id.goldLabel, ds);

		setTypeface(R.id.continueButton,
				ResourceManager.getFont("roboto_bold.ttf"));
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), InGamePage.class);
		startActivity(intent);
		finish();
	}
}
