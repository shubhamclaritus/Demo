package space.jain.shubham.testapplication.connection;

/**
 * Created by ganesh on 2/19/16.
 */


public interface GetResultListener {
    /**
     * @param result  contain the json of the called url
     * @param urlFrom is indicator that result is fetched for given url
     */
    void onTaskComplete(String result, String urlFrom, boolean isSuccess);

}
