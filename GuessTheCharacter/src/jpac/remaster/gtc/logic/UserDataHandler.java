package jpac.remaster.gtc.logic;

import android.os.Bundle;
import jpac.remaster.gtc.data.Data;
import jpac.remaster.gtc.data.DataHandler;

public class UserDataHandler extends DataHandler {

	private UserData userData;
	
	@Override
	public void handle(Bundle data) {
		if (userData == null) {
			userData = new UserData();
		}
		userData.updateContent(data);
		sendDataUpdateNotification(userData, System.currentTimeMillis());
	}

	@Override
	public Data load() {
		return userData;
	}

}
