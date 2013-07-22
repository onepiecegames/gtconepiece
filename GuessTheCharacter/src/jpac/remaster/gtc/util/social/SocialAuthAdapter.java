package jpac.remaster.gtc.util.social;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.util.AccessGrant;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.MethodType;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class SocialAuthAdapter {

	public enum Provider {
		FACEBOOK(Constants.FACEBOOK, "fbconnect://success",
				"fbconnect://success?error_reason");

		private String name;
		private String cancelUri;
		private String callbackUri;

		Provider(String name, String callbackUri, String cancelUri) {
			this.name = name;
			this.cancelUri = cancelUri;
			this.callbackUri = callbackUri;
		}

		String getCancelUri() {
			return this.cancelUri;
		}

		String getCallBackUri() {
			return this.callbackUri;
		}

		public void setCallBackUri(String callbackUri) {
			this.callbackUri = callbackUri;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	// Share Mail & MMS providers
	public static final String SHARE_MAIL = "share_mail";
	public static final String SHARE_MMS = "share_mms";

	// Constants
	public static final String PROVIDER = "provider";
	public static final String ACCESS_GRANT = "access_grant";

	// Facebook feed url for updating story
	private final String UPDATE_STATUS_URL = "https://graph.facebook.com/me/feed";

	// socialAuthManager object
	private SocialAuthManager socialAuthManager;

	// dialogListener object
	private DialogListener dialogListener;

	// provides currentprovider information
	private Provider currentProvider;

	// Variables, Arrays and Maps
	private String url;
	private Map<String, Object> tokenMap;
	private final Map<String, OAuthConfig> authMap;

	// Android Components
	private Context context;
	private final Handler handler = new Handler();

	public SocialAuthAdapter(DialogListener listener) {
		this.dialogListener = listener;
		authMap = new HashMap<String, OAuthConfig>();
	}

	public void setListener(DialogListener listener) {
		this.dialogListener = listener;
	}

	public void addCallBack(Provider provider, String callBack) {
		if (provider.name() == Constants.FACEBOOK
				|| provider.name() == Constants.LINKEDIN
				|| provider.name() == Constants.MYSPACE
				|| provider.name() == Constants.YAHOO
				|| provider.name() == Constants.RUNKEEPER) {
			Log.d("SocialAuthAdapter", "Callback Url not require");
		} else
			provider.setCallBackUri(callBack);
	}

	public AuthProvider getCurrentProvider() {
		if (currentProvider != null) {
			return socialAuthManager.getProvider(currentProvider.toString());

		} else {
			return null;
		}
	}

	public void share(Context context) {
		authorize(context, Provider.FACEBOOK);
	}

	public void authorize(Context ctx, Provider provider) {
		if (!SocialUtil.isNetworkAvailable(ctx)) {
			dialogListener
					.onError(new SocialAuthError(
							"Please check your Internet connection",
							new Exception("")));
			return;
		}
		context = ctx;
		currentProvider = provider;
		Log.d("SocialAuthAdapter", "Selected provider is " + currentProvider);

		// Initialize socialauth manager if not already done
		if (socialAuthManager != null) {
			// If SocialAuthManager is not null and contains Provider Id, send
			// response to listener
			if (socialAuthManager.getConnectedProvidersIds().contains(
					currentProvider.toString())) {
				Log.d("SocialAuthAdapter", "Provider already connected");
				Bundle bundle = new Bundle();
				bundle.putString(SocialAuthAdapter.PROVIDER,
						currentProvider.toString());
				dialogListener.onComplete(bundle);
			}

			// If SocialAuthManager is not null and not contains Provider Id
			else {
				connectProvider(ctx, provider);
			}

		}
		// If SocialAuthManager is null
		else {
			Log.d("SocialAuthAdapter",
					"Loading keys and secrets from configuration");

			socialAuthManager = new SocialAuthManager();
			try {
				loadConfig(ctx);

			} catch (Exception e) {
				Log.d("SocialAuthAdapter", "Could not load configuration");
			}
			connectProvider(ctx, provider);
		}
	}

	public void addConfig(Provider provider, String key, String secret,
			String permissions) throws Exception {
		OAuthConfig authConfig = new OAuthConfig(key, secret);
		authConfig.setId(provider.toString());
		authConfig.setCustomPermissions(permissions);
		authMap.put(provider.toString(), authConfig);
	}

	private void loadConfig(Context ctx) throws Exception {

		SocialAuthConfig config = new SocialAuthConfig();
		Resources resources = ctx.getResources();
		AssetManager assetManager = resources.getAssets();
		InputStream inputStream;
		boolean fileExist = false;
		try {
			inputStream = assetManager.open("oauth_consumer.properties");
			fileExist = true;
		} catch (Exception e) {
			fileExist = false;
			throw new SocialAuthException("oauth_consumer.properties not found");
		}

		if (fileExist) {
			// Add keys from oauth_consumers file. loadConfig() method
			// is removed
			config.load(inputStream);
			socialAuthManager.setSocialAuthConfig(config);
		} else {
			// Add user keys if outh_consumers file not exists
			for (String key : authMap.keySet()) {
				config.addProviderConfig(key, authMap.get(key));
			}
			socialAuthManager.setSocialAuthConfig(config);
		}
	}

	private void startDialogAuth(final Context context, final Provider provider) {
		CookieSyncManager.createInstance(context);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					// Get Callback url
					url = socialAuthManager.getAuthenticationUrl(
							provider.toString(), provider.getCallBackUri())
							+ "&type=user_agent&display=touch";

					handler.post(new Runnable() {
						@Override
						public void run() {
							Log.d("SocialAuthAdapter", "Loading URL : " + url);
							String callbackUri = provider.getCallBackUri();
							Log.d("SocialAuthAdapter", "Callback URI : "
									+ callbackUri);
							// start webview dialog
							new SocialAuthDialog(context, url, provider,
									dialogListener, socialAuthManager).show();
						}
					});
				} catch (Exception e) {

					dialogListener.onError(new SocialAuthError(
							"URL Authentication error", e));
				}
			}
		};

		new Thread(runnable).start();
	}

	private void connectProvider(final Context ctx, final Provider provider) {

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(ctx);

		if (pref.contains(provider.toString() + " key")) {
			tokenMap = new HashMap<String, Object>();

			for (Entry<String, ?> entry : pref.getAll().entrySet())
				tokenMap.put(entry.getKey().toString(), entry.getValue());

			// If Access Token is available , connect using Access Token
			try {

				HashMap<String, Object> attrMap = null;
				attrMap = new HashMap<String, Object>();

				String key = (String) tokenMap
						.get(provider.toString() + " key");

				String secret = (String) tokenMap.get(provider.toString()
						+ " secret");

				String providerid = (String) tokenMap.get(provider.toString()
						+ " providerid");

				String temp = provider.toString() + "attribute";
				for (String attr : tokenMap.keySet()) {
					if (attr.startsWith(temp)) {
						int startLocation = attr.indexOf(temp) + temp.length()
								+ 1;
						attrMap.put(attr.substring(startLocation),
								tokenMap.get(attr));
					}

				}

				for (Entry<String, Object> entry : attrMap.entrySet()) {
					System.out
							.println(entry.getKey() + ", " + entry.getValue());
				}

				final AccessGrant accessGrant = new AccessGrant(key, secret);
				accessGrant.setProviderId(providerid);
				accessGrant.setAttributes(attrMap);

				Log.d("SocialAuthAdapter", "Loading from AccessToken");

				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						try {

							socialAuthManager.connect(accessGrant);

							// To check validity of Access Token
							getCurrentProvider().getUserProfile()
									.getValidatedId();

							handler.post(new Runnable() {
								@Override
								public void run() {

									Bundle bundle = new Bundle();
									bundle.putString(
											SocialAuthAdapter.PROVIDER,
											currentProvider.toString());
									dialogListener.onComplete(bundle);
								}
							});
						} catch (Exception e) {
							dialogListener.onError(new SocialAuthError(
									"Token Error", e));
							Log.d("SocialAuthAdapter",
									"Starting webview for authentication for new Token");

							socialAuthManager = new SocialAuthManager();
							try {
								loadConfig(ctx);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							startDialogAuth(ctx, currentProvider);

						}
					}
				};

				new Thread(runnable).start();

			} catch (Exception e) {
				dialogListener.onError(new SocialAuthError("Unknown error", e));
				e.printStackTrace();
			}
		}
		// If Access Token is not available , Open Authentication Dialog
		else {
			Log.d("SocialAuthAdapter", "Starting webview for authentication");
			startDialogAuth(ctx, currentProvider);
		}

	}

	public void setDialogSize(float width, float height) {
		if (width < 0 || width > 40)
			SocialAuthDialog.width = 40;
		else
			SocialAuthDialog.width = width;

		if (height < 0 || height > 60)
			SocialAuthDialog.height = 60;
		else
			SocialAuthDialog.height = height;
	}

	public boolean signOut(String providerName) {

		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();

		if (providerName != null) {

			if (socialAuthManager.getConnectedProvidersIds().contains(
					providerName))
				socialAuthManager.disconnectProvider(providerName);

			Editor edit = PreferenceManager
					.getDefaultSharedPreferences(context).edit();
			edit.remove(providerName + " key");
			edit.commit();

			Log.d("SocialAuthAdapter", "Disconnecting Provider");

			return true;
		} else {
			Log.d("SocialAuthAdapter", "The provider name should be same");
			return false;
		}
	}

	public void setTitleVisible(boolean titleStatus) {
		SocialAuthDialog.titleStatus = titleStatus;
	}

	public void updateStatus(final String message,
			final SocialAuthListener<Integer> listener,
			final boolean shareOption) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					if (shareOption == true) {
						final List<String> activeProviders = socialAuthManager
								.getConnectedProvidersIds();
						for (int i = 0; i < activeProviders.size(); i++) {
							final String provider = activeProviders.get(i);
							final Response response = socialAuthManager
									.getProvider(provider)
									.updateStatus(message);

							handler.post(new Runnable() {
								@Override
								public void run() {
									int status = response.getStatus();
									listener.onExecute(provider,
											Integer.valueOf(status));
								}
							});
						}
					} else {
						final Response response = getCurrentProvider()
								.updateStatus(message);

						handler.post(new Runnable() {
							@Override
							public void run() {
								int status = response.getStatus();
								listener.onExecute(getCurrentProvider()
										.getProviderId(), Integer
										.valueOf(status));
							}
						});
					}

				} catch (Exception e) {
					dialogListener.onError(new SocialAuthError(
							"Message Not Posted", e));
				}
			}
		};

		new Thread(runnable).start();
	}

	public void updateStory(final String message, final String name,
			final String caption, final String description, final String link,
			final String picture, final SocialAuthListener<Integer> listener) {

		if (getCurrentProvider().getProviderId().equalsIgnoreCase("facebook")) {
			final Map<String, String> params = new HashMap<String, String>();
			params.put("name", name);
			params.put("caption", caption);
			params.put("description", description);
			params.put("link", link);
			params.put("picture", picture);

			final StringBuilder strb = new StringBuilder();

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						// Call using API method of socialauth
						strb.append("message=").append(
								URLEncoder.encode(message, Constants.ENCODING));
						strb.append("&access_token")
								.append("=")
								.append(getCurrentProvider().getAccessGrant()
										.getKey());
						final Response response = getCurrentProvider().api(
								UPDATE_STATUS_URL, MethodType.POST.toString(),
								params, null, strb.toString());

						handler.post(new Runnable() {
							@Override
							public void run() {
								int status = response.getStatus();
								listener.onExecute(getCurrentProvider()
										.getProviderId(), Integer
										.valueOf(status));
							}
						});
					} catch (Exception e) {
						dialogListener.onError(new SocialAuthError(
								"Message Not Posted", e));
					}
				}
			};

			new Thread(runnable).start();
		} else {
			Log.d("SocialAuthAdapter", "Provider Not Supported");
		}
	}

	public Response api(final String url, final String methodType,
			final Map<String, String> params,
			final Map<String, String> headerParams, final String body)
			throws Exception {
		Response response = null;
		try {
			response = getCurrentProvider().api(url, methodType, params,
					headerParams, body);
		} catch (Exception e) {
			throw new SocialAuthException(
					"Error while making request to URL : " + url, e);
		}
		return response;
	}

	public void uploadImageAsync(String message, String fileName,
			Bitmap bitmap, int quality, SocialAuthListener<Integer> listener)
			throws Exception {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (fileName.endsWith("PNG") || fileName.endsWith("png")) {
			bitmap.compress(CompressFormat.PNG, 0, bos);
		} else if (fileName.endsWith("JPEG") || fileName.endsWith("JPG")
				|| fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
			bitmap.compress(CompressFormat.JPEG, quality, bos);
		} else {
			throw new SocialAuthException("Image Format not supported");
		}

		InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
		if (getCurrentProvider().getProviderId().equalsIgnoreCase("facebook")
				|| getCurrentProvider().getProviderId().equalsIgnoreCase(
						"twitter")) {
			new UploadImageTask(listener).execute(message, fileName,
					inputStream);
		} else {
			throw new SocialAuthException("Provider not Supported");
		}
	}

	private class UploadImageTask extends AsyncTask<Object, Void, Integer> {

		SocialAuthListener<Integer> listener;

		private UploadImageTask(SocialAuthListener<Integer> listener) {
			this.listener = listener;
		}

		@Override
		protected Integer doInBackground(Object... params) {
			Response res = null;
			try {
				res = getCurrentProvider().uploadImage((String) params[0],
						(String) params[1], (InputStream) params[2]);
				Log.d("SocialAuthAdapter", "Image Uploaded");
				return Integer.valueOf(res.getStatus());
			} catch (Exception e) {
				listener.onError(new SocialAuthError("Image Upload Error", e));
				return null;
			}
		}

		@Override
		protected void onPostExecute(Integer status) {

			listener.onExecute(getCurrentProvider().getProviderId(), status);
		}
	}
}
