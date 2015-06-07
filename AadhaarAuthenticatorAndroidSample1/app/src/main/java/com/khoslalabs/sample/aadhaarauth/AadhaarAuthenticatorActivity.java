package com.khoslalabs.sample.aadhaarauth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aadhaarconnect.bridge.capture.model.auth.AuthCaptureData;
import com.aadhaarconnect.bridge.capture.model.auth.AuthCaptureRequest;
import com.aadhaarconnect.bridge.capture.model.common.Location;
import com.aadhaarconnect.bridge.capture.model.common.LocationType;
import com.aadhaarconnect.bridge.capture.model.common.request.CertificateType;
import com.aadhaarconnect.bridge.capture.model.common.request.Modality;
import com.aadhaarconnect.bridge.capture.model.common.request.ModalityType;
import com.example.aadhaarauth.R;
import com.google.gson.Gson;

public class AadhaarAuthenticatorActivity extends Activity {
	public static final int QRCODE_REQUEST = 1000;
	public static final int AADHAAR_CONNECT_AUTH_REQUEST = 1001;
	private static final String BASE_URL="https://ac.khoslalabs.com/hackgate/hackathon";

	private EditText aadhaarEditTextView;
	private ImageView qrCodeScanner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aadhaar_authenticator);
		getActionBar().setTitle("TrueScholar Exam Invigilator");
		aadhaarEditTextView = (EditText) findViewById(R.id.aadhaar_number);
		qrCodeScanner = (ImageView) findViewById(R.id.barcode);
	}

	public void scanUsingQRCode(View v) {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		try {
			startActivityForResult(intent, QRCODE_REQUEST);
		} catch (Exception e) {
			showToast("No QR Code scanning modules found.", Toast.LENGTH_LONG);
		}
	}

	public void authenticate(View v) {
		if (TextUtils.isEmpty(aadhaarEditTextView.getText())) {
			showToast(
					"Invalid Aadhaar Number. Please enter a valid Aadhaar Number",
					Toast.LENGTH_LONG);
			return;
		}

		AuthCaptureRequest authCaptureRequest = new AuthCaptureRequest();
		authCaptureRequest.setAadhaar(aadhaarEditTextView.getText().toString());
		authCaptureRequest.setModality(Modality.biometric);
		authCaptureRequest.setModalityType(ModalityType.fp);

		authCaptureRequest.setNumOffingersToCapture(2);
		authCaptureRequest.setCertificateType(CertificateType.preprod);

		Location loc = new Location();
		loc.setType(LocationType.pincode);
		loc.setPincode("560076");
		authCaptureRequest.setLocation(loc);

		Intent i = new Intent();
		//i = new Intent("com.khoslalabs.ac.action.AUTHCAPTURE");
		i = new Intent("com.aadhaarconnect.bridge.action.AUTHCAPTURE");

		i.putExtra("REQUEST", new Gson().toJson(authCaptureRequest));
		try {
			startActivityForResult(i, AADHAAR_CONNECT_AUTH_REQUEST);
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == QRCODE_REQUEST && resultCode == RESULT_OK
				&& data != null) {
			String contents = data.getStringExtra("SCAN_RESULT");
			if (!TextUtils.isEmpty(contents)) {
				String aadhaar = readValue(contents, "uid");
				aadhaarEditTextView.setText(aadhaar);
				qrCodeScanner.setImageResource(R.drawable.qrcode_green);
			} else {
				qrCodeScanner.setImageResource(R.drawable.qrcode_gray);
			}
			return;
		}

		if (resultCode == RESULT_OK
				&& requestCode == AADHAAR_CONNECT_AUTH_REQUEST && data != null) {
			String responseStr = data.getStringExtra("RESPONSE");
			final AuthCaptureData authCaptureData = new Gson().fromJson(
					responseStr, AuthCaptureData.class);
			AadhaarAuthAsyncTask authAsyncTask = new AadhaarAuthAsyncTask(this,
					authCaptureData);
			authAsyncTask.execute(BASE_URL + "/auth");
			return;
		}
	}

	// HELPER METHODS
	private String readValue(String contents, String dataName) {
		String[] keys;
		if (dataName.contains(",")) {
			keys = dataName.split(",");
		} else {
			keys = new String[] { dataName };
		}
		String value = "";
		for (String key : keys) {
			int startIndex = contents.indexOf(key + "=");
			if (startIndex >= 0) {
				int endIndex = contents.indexOf("\"", startIndex + key.length()
						+ 1 + 1);
				if (endIndex >= 0) {
					value += " ";
					value += contents.substring(startIndex + key.length() + 1,
							endIndex).replaceAll("\"", "");
				}
			}
		}
		return value.trim();
	}

	private void showToast(String text, int duration) {
		Toast toast = Toast.makeText(this, text, duration);
		toast.show();
	}
}
