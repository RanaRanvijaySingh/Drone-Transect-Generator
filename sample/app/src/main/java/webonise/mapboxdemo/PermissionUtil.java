package webonise.mapboxdemo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

public class PermissionUtil {

    private static final int MY_PERMISSIONS_REQUEST = 99;
    Activity mActivity;

    public PermissionUtil(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * Function to check permission and pop permission request dialog
     *
     * @param permissionToBeChecked   String : wanted permission eg. Manifest.permission.READ_CONTACTS
     * @param onPermissionGranted     PermissionUtil.OnPermissionGranted interface to return call
     *                                back
     * @param permissionDeniedMessage String : message to show if user deny access.
     */
    public boolean checkPermission(String permissionToBeChecked,
                                   OnPermissionGranted onPermissionGranted,
                                   String permissionDeniedMessage) {
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(mActivity, permissionToBeChecked);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted.permissionGranted();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                        permissionToBeChecked)) {
                    if (!TextUtils.isEmpty(permissionDeniedMessage)) {
                        Toast.makeText(mActivity, permissionDeniedMessage,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{permissionToBeChecked}, MY_PERMISSIONS_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface OnPermissionGranted {
        public void permissionGranted();
    }
}
