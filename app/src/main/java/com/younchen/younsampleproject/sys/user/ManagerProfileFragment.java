package com.younchen.younsampleproject.sys.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaRouter;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.utils.ReflectHelper;

import java.util.List;

import butterknife.BindView;

import static android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME;


/**
 * Created by yinlongquan on 2017/10/23.
 */

public class ManagerProfileFragment extends BaseFragment {

    @BindView(R.id.btn_start)
    Button mStartBtn;

    private final int REQUEST_PROVISION_MANAGED_PROFILE = 200;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                hasManagedProfile();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean hasManagedProfile() {
        UserManager userManager = (UserManager) getActivity().getSystemService(Context.USER_SERVICE);

        if (userManager == null) {
            return false;
        }

        try {
            Integer userId = (Integer) ReflectHelper.invokeStaticMethod(UserHandle.class, "myUserId", null, null);

            List<Object> profiles = (List<Object>) ReflectHelper.invokeMethod(userManager, "getProfiles", new Class[]{Integer.class}, new Object[]{userId});

            for (Object userInfo : profiles) {
                if (userInfo != null && (boolean) ReflectHelper.invokeMethod(userInfo, "isManagedProfile", null, null)) {
                    return true;
                }
            }
        } catch (SecurityException e) {
            return false;
        }
        return false;
    }

    private boolean supportsManagedProfiles(ResolveInfo resolveInfo) {
        try {
            ApplicationInfo appInfo = getContext().getPackageManager().getApplicationInfo(
                    resolveInfo.activityInfo.packageName, 0 /* default flags */);
            return versionNumberAtLeastL(appInfo.targetSdkVersion);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean versionNumberAtLeastL(int versionNumber) {
        return versionNumber >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PROVISION_MANAGED_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getContext(), "Provisioning done.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Provisioningfailed.", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void registerPovisionManagerProfile() {
        if (null == this) {
            return;
        }
        Intent intent = new Intent(android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE);
        intent.putExtra(EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME,
                this.getContext().getPackageName());
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_PROVISION_MANAGED_PROFILE);
        } else {
            Toast.makeText(getContext(), "Device provisioning is not enabled.Stopping.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_user_profile, container, false);
    }

    @Override
    public void onBackKeyPressed() {

    }
}
