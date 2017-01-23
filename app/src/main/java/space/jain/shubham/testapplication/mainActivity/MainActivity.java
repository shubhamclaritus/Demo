package space.jain.shubham.testapplication.mainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.jain.shubham.testapplication.R;
import space.jain.shubham.testapplication.connection.CustomVolleyRequest;
import space.jain.shubham.testapplication.connection.GetResultListener;
import space.jain.shubham.testapplication.utils.ApiList;
import space.jain.shubham.testapplication.utils.AppConstant;
import space.jain.shubham.testapplication.utils.HelperMethods;

public class MainActivity extends AppCompatActivity implements GetResultListener {

    @BindView(R.id.getButton)
    Button getButton;
    @BindView(R.id.postButton)
    Button postButton;
    @BindView(R.id.putButton)
    Button putButton;
    @BindView(R.id.deleteButton)
    Button deleteButton;
    @BindView(R.id.result)
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    /**
     * @param view get the context which button is clicked and what action is going to perform
     */
    @OnClick({R.id.getButton, R.id.postButton, R.id.putButton, R.id.deleteButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getButton:
                String api = "";
                if (HelperMethods.isNetworkAvailable(this)) {
                    api = ApiList.Area + ApiList.AllPlaces + AppConstant.GET;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("time", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CustomVolleyRequest.getInstance(this).setJsonRequest_POST(this, null, api, jsonObject, new HashMap<String, String>());
                } else {
                    HelperMethods.showToastS(this, "Internet Connection Is not Working");
                }
                break;
            case R.id.postButton:

                if (HelperMethods.isNetworkAvailable(this)) {
                    api = ApiList.Area + ApiList.AllPlaces + AppConstant.POST;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("time", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CustomVolleyRequest.getInstance(this).setJsonRequest_POST(this, null, api, jsonObject, new HashMap<String, String>());
                } else {
                    HelperMethods.showToastS(this, "Internet Connection Is not Working");
                }
                break;
            case R.id.putButton:

                if (HelperMethods.isNetworkAvailable(this)) {
                    api = ApiList.Area + ApiList.AllPlaces + AppConstant.PUT;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("time", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CustomVolleyRequest.getInstance(this).setJsonRequest_POST(this, null, api, jsonObject, new HashMap<String, String>());
                } else {
                    HelperMethods.showToastS(this, "Internet Connection Is not Working");
                }
                break;
            case R.id.deleteButton:

                if (HelperMethods.isNetworkAvailable(this)) {
                    api = ApiList.Area + ApiList.AllPlaces + AppConstant.DELETE;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("time", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CustomVolleyRequest.getInstance(this).setJsonRequest_POST(this, null, api, jsonObject, new HashMap<String, String>());
                } else {
                    HelperMethods.showToastS(this, "Internet Connection Is not Working");
                }
                break;
        }
    }

    /**
     * @param result  contain the json of the called url
     * @param urlFrom is indicator that result is fetched for given url
     * @param  isSuccess define the api successfully execution status
     */

    @Override
    public void onTaskComplete(String result, String urlFrom, boolean isSuccess) {
        if (isSuccess) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String statusCode = jsonObject.getString("StatusCode");
                StringBuilder stringBuilder = new StringBuilder();
                switch (Integer.parseInt(statusCode)) {
                    case HttpURLConnection.HTTP_OK:
                        resultText.setText(result);
                        break;
                    case HttpURLConnection.HTTP_INTERNAL_ERROR:
                        String exception = jsonObject.getString("Exception");
                        String reason = jsonObject.getString("Reason");
                        stringBuilder.append("Exception : ").append(exception).append("\n").append("\n").append("Reason : ").append(reason).append("\n").append("\n");
                        resultText.setText(stringBuilder);
                        break;
                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        exception = jsonObject.getString("Exception");
                        reason = jsonObject.getString("Reason");
                        stringBuilder.append("Exception : ").append(exception).append("\n").append("\n").append("Reason : ").append(reason).append("\n").append("\n");                        resultText.setText(stringBuilder);
                        break;
                    case HttpURLConnection.HTTP_UNAUTHORIZED:
                        exception = jsonObject.getString("Exception");
                        reason = jsonObject.getString("Reason");
                        stringBuilder.append("Exception : ").append(exception).append("\n").append("\n").append("Reason : ").append(reason).append("\n").append("\n");                        resultText.setText(stringBuilder);
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        exception = jsonObject.getString("Exception");
                        reason = jsonObject.getString("Reason");
                        stringBuilder.append("Exception : ").append(exception).append("\n").append("\n").append("Reason : ").append(reason).append("\n").append("\n");                        resultText.setText(stringBuilder);
                        break;
                    case HttpURLConnection.HTTP_FORBIDDEN:
                        exception = jsonObject.getString("Exception");
                        reason = jsonObject.getString("Reason");
                        stringBuilder.append("Exception : ").append(exception).append("\n").append("\n").append("Reason : ").append(reason).append("\n").append("\n");                        resultText.setText(stringBuilder);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
