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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

/******************************************************************************
 * The activity displayed when the user already solved all the puzzles.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class GameFinishedPage extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_finished_page);

		setOnClickListener(R.id.continueButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		final ViewFlipper flipper = getViewFlipper(R.id.descFlipper);
		if (flipper.getChildCount() > 1) {
			flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.fade_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.fade_out));
			flipper.setFlipInterval(5000);
			flipper.startFlipping();
		}

		Typeface ds = ResourceManager.getFont("digitalstrip.ttf");

		setTypeface(R.id.banner, ds);
		setTypeface(R.id.titleLabel, ds);
		setTypeface(R.id.finishLabel, ds);
		setTypeface(R.id.continueButton, ResourceManager.getFont("roboto_bold.ttf"));
	}
}
