package space.jain.shubham.testapplication.utils;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import static android.content.Context.LOCATION_SERVICE;


/**
 * Created by Shubham.claritus on 26/9/16.
 */

public class HelperMethods {

    /**
     * Method for showing toast for short time
     */
    public static void showToastS(Context context, String message) {
        Toast toast = Toast.makeText(context, "" + message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
    public static boolean isNetworkAvailable(Context applicationContext) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(applicationContext.getApplicationContext().CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * Method for showing toast for long time
     */
    public static void showToastL(Context context, String message) {
        Toast toast = Toast.makeText(context, "" + message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }


    /**
     * @param context Method for turn on GPS
     */
    public static void turnGPSOn(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param mActivity
     * @param mManifestPermission
     * @return the checked value that is permission is granted on not
     */
    public static boolean checkPermission(Activity mActivity, String mManifestPermission) {
        Log.e("ManifestPermission", "" + mManifestPermission);
        boolean permissionStatus = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            if (ContextCompat.checkSelfPermission(mActivity, mManifestPermission.trim())
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission", "Denied");
                permissionStatus = false;
            } else {
                Log.e("Permission", "Granted");
                permissionStatus = true;
            }
        } else {
            permissionStatus = true;

        }
        return permissionStatus;
    }


    /**
     * @param mActivity
     */
    public static void goToSettings(Activity mActivity) {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mActivity.getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(myAppSettings);
    }

    public static void LogError(String url, String uri) {
        Log.e(url, uri);
    }

    /**
     * Method for Checking network connection
     */
    public static boolean isNetworkAvailable(Activity activity) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(activity.getApplicationContext().CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * @param message  that need to be display
     * @param activity
     */
    public static void openAlert(String message,
                                 Activity activity) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);


        alertDialogBuilder.setMessage(message);
        // set positive button: Yes MESSAGE
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }




    /**
     * @param text  input data
     * @param color input color #color code
     * @return colored data
     */
    public static String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }




    public static boolean checkIsGPSEnabled(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void showGPSDisabledAlertToUser(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setMessage("GPS is disabled in your device. Would you like to enable it?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(myIntent);
                        paramDialogInterface.dismiss();
                        //get gps
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }



    public static String checkDataNull(String text) {
        if (text.equals("null") || text.equals("NULL")) {
            text = " ";
        }
        return text;
    }
    public static String checkDataBlack(String text) {
        if (text.equals("null") || text.equals("NULL")) {
            text = "";
        }
        return text;
    }

    public static Date getThirtyDayBeforDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        Calendar calReturn = Calendar.getInstance();

        calReturn.add(Calendar.DATE, -30);
        Date newDate = calReturn.getTime();
        return newDate;
    }

    public static Drawable getImage(Context context, String name) {
        name=name.toLowerCase();
        if (name.equals("do"))
            name = "do_";
        else if (name.equalsIgnoreCase(" "))
            name = "in";
        Drawable drawable =null;
        try {
            drawable= context.getResources().getDrawable(context.getResources().getIdentifier(name, "drawable", context.getPackageName()));
        }catch (Exception e)
        {
            e.printStackTrace();
        }if (drawable != null) {
            return drawable;
        } else {
            return context.getResources().getDrawable(context.getResources().getIdentifier("in", "drawable", context.getPackageName()));
        }
    }


    public static Date StringToDate(String stringDate)
    {
        Date date=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             date = format.parse(stringDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public static String getUrlFrom(String urlFrom)
    {
        if(urlFrom.contains("?"))
        {
            String[] data=urlFrom.split("\\?");
            urlFrom=data[0];
        }
        return urlFrom;
    }

}
