package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.Constants;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.social.AppRater;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class AboutUsPage extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_about);

		setOnClickListener(R.id.backButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				flip();
			}
		});

		setOnClickListener(R.id.rateButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppRater.showRateDialog(
						AboutUsPage.this,
						getSharedPreferences(Constants.SHARED_PREFERENCES,
								MODE_PRIVATE).edit());
			}
		});

		setText(R.id.changeLog,
				ResourceManager.loadDataFromAsset("data/changelog.txt"));
		getTextView(R.id.changeLog).setMovementMethod(
				new ScrollingMovementMethod());

		setText(R.id.disclaimer,
				ResourceManager.loadDataFromAsset("data/disclaimer.txt"));
		getTextView(R.id.disclaimer).setMovementMethod(
				new ScrollingMovementMethod());

		setText(R.id.about, ResourceManager.loadDataFromAsset("data/dev.txt"));
		getTextView(R.id.about)
				.setMovementMethod(new ScrollingMovementMethod());

		Typeface digstrip = ResourceManager.getFont("digitalstrip.ttf");
		setTypeface(R.id.appName, digstrip);
		setTypeface(R.id.appVersion, digstrip);
		setTypeface(R.id.changeLogLabel, digstrip);
		setTypeface(R.id.changeLog, digstrip);
		setTypeface(R.id.disclaimerLabel, digstrip);
		setTypeface(R.id.disclaimer, digstrip);
		setTypeface(R.id.devName, digstrip);
		setTypeface(R.id.devPosition, digstrip);
		setTypeface(R.id.about, digstrip);
		setTypeface(R.id.creditsLabel, digstrip);

		Credits credit_data[] = new Credits[] {
				new Credits(R.drawable.fishiebug, "Fishiebug",
						"http://fishiebug.deviantart.com"),
				new Credits(R.drawable.orochimarusama1, "Orochimarusama1",
						"http://orochimarusama1.deviantart.com"),
				new Credits(R.drawable.maradin, "Maradin Apple",
						"http://maradin-apple.tumblr.com/") };

		CreditsAdapter adapter = new CreditsAdapter(this, R.layout.credits,
				credit_data);

		ListView credits = (ListView) getView(R.id.credits);
		credits.setAdapter(adapter);

	}

	private void flip() {
		ViewFlipper flipper = getViewFlipper(R.id.flipper);
		if (flipper.getDisplayedChild() != flipper.getChildCount() - 1) {
			flipper.showNext();

			if (flipper.getDisplayedChild() == flipper.getChildCount() - 1) {
				setText(R.id.backButton,
						ResourceManager.loadString(R.string.label_close));
			}
		} else {
			finish();
		}
	}

	class Credits {

		public int icon;
		public String title;
		public String link;

		public Credits() {
			super();
		}

		public Credits(int icon, String title, String link) {
			super();
			this.icon = icon;
			this.title = title;
			this.link = link;
		}
	}

	class CreditsAdapter extends ArrayAdapter<Credits> {

		Context context;
		int layoutResourceId;
		Credits[] data;

		public CreditsAdapter(Context context, int layoutResourceId,
				Credits[] data) {
			super(context, layoutResourceId, data);

			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.data = data;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			CreditsHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);

				holder = new CreditsHolder();
				holder.imgIcon = (ImageView) row.findViewById(R.id.thumbnail);
				holder.txtTitle = (TextView) row.findViewById(R.id.name);
				holder.visitLink = (Button) row.findViewById(R.id.visitButton);

				row.setTag(holder);
			} else {
				holder = (CreditsHolder) row.getTag();
			}

			Typeface digstrip = ResourceManager.getFont("digitalstrip.ttf");

			final Credits credit = data[position];
			holder.txtTitle.setText(credit.title);
			holder.txtTitle.setTypeface(digstrip);
			holder.imgIcon.setImageResource(credit.icon);
			holder.visitLink.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(credit.link));
					startActivity(browserIntent);
				}
			});

			return row;
		}
	}

	static class CreditsHolder {
		ImageView imgIcon;
		TextView txtTitle;
		Button visitLink;
	}
}
