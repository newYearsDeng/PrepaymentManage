package com.northmeter.prepaymentmanage.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageStats;
import android.os.Build;
import android.os.RemoteException;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.widget.ImageView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.presenter.ChangeRoomPresenter;
import com.northmeter.prepaymentmanage.ui.i.IChangeRoom;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author zz
 * @time 2016/11/17 10:12
 * @des 实现设置的选择菜单接口
 */
public class NavagationitemSelecteImpl implements NavigationView.OnNavigationItemSelectedListener, IChangeRoom {
    private final DrawerLayout mDrawerlayout;
    private final Activity mActivity;

    public NavagationitemSelecteImpl(Activity activity, DrawerLayout drawerlayout) {
        mActivity = activity;
        mDrawerlayout = drawerlayout;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_menu_change_pwd:
                //更换密码
                Intent intent = new Intent(mActivity, ChangePwdActivity.class);
                String userType = mActivity instanceof HomeUserActivity ? Contants.USER_TYPE_USER : Contants.USER_TYPE_MANAGE;
                intent.putExtra(Contants.USER_TYPE_INTENT_EXTRA, userType);
                mActivity.startActivity(intent);
                mDrawerlayout.closeDrawers();
                break;
            case R.id.setting_menu_change_room:
                //切换房间
                if (mActivity instanceof HomeManageActivity) {
                    //管理员不使用此功能
                    ToastUtil.showShort(MyApplication.getContext(), "管理员账户无法使用此功能");
                } else {
                    String spId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
                    String spBuildingId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spId, "");
                    String decryptBuildingId = AES.decrypt(spBuildingId, Contants.SP_AES_BUILDING_KEY);

                    if (TextUtils.isEmpty(decryptBuildingId)) {
                        ToastUtil.showShort(MyApplication.getContext(),"该账户没有绑定房间");
                    } else {
                       /* new AlertDialog.Builder(mActivity)
                                .setMessage("是否解除已经绑定的房间？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ChangeRoomPresenter presenter = new ChangeRoomPresenter(NavagationitemSelecteImpl.this);
                                        presenter.unbindRoom();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();*/
                        new SweetAlertDialog(mActivity,SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("是否解除已经绑定的房间")
                                .setConfirmText("确定")
                                .setCancelText("取消")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog dialog) {
                                        ChangeRoomPresenter presenter = new ChangeRoomPresenter(NavagationitemSelecteImpl.this);
                                        presenter.unbindRoom();
                                        dialog.dismiss();


                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                           sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    }
                }
                break;
            case R.id.setting_menu_qr_code:
                //二维码推广
//                mActivity.startActivity(new Intent(mActivity, QrCodeActivity.class));
//                mDrawerlayout.closeDrawers();
//                ImageView imageView = new ImageView(mActivity);
//                imageView.setImageResource(R.drawable.icon_qr_code);
                new AlertDialog.Builder(mActivity,R.style.MyDialog1)
                        .setView(R.layout.layout_qr_code)
                        .show();
                break;
            case R.id.setting_menu_feedback:
                //意见反馈
                mActivity.startActivity(new Intent(mActivity, FeedbackActivity.class));
                mDrawerlayout.closeDrawers();
                break;
            case R.id.setting_menu_clear_cache:
                //清除缓存

                try {
                    Method getPackageSizeInfo = mActivity.getPackageManager().getClass().getMethod(
                            "getPackageSizeInfo", String.class, IPackageStatsObserver.class);
                    getPackageSizeInfo.invoke(mActivity.getPackageManager(), "com.northmeter.prepaymentmanage", new IPackageStatsObserver.Stub() {
                        @Override
                        public void onGetStatsCompleted(final PackageStats pStats, boolean succeeded)
                                throws RemoteException {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LoggerUtil.d(pStats.toString());
                                    String cacheSize = Formatter.formatFileSize(mActivity.getApplication(), pStats.cacheSize + pStats.externalCacheSize);
                                    LoggerUtil.d(cacheSize);
                                    //弹出dialog提示用户
                                    /*new AlertDialog.Builder(mActivity)
                                            .setMessage("缓存大小：" + cacheSize + "\n\n清空所有本地缓存，将会退出软件，需要重新登录\n\n是否清理？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @TargetApi(Build.VERSION_CODES.KITKAT)
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    ActivityManager am = (ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE);
                                                    am.clearApplicationUserData();
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .show();*/
                                   new SweetAlertDialog(mActivity,SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("缓存大小：" + cacheSize  )
                                            .setContentText("清空所有本地缓存，将会退出软件，需要重新登录" +
                                                    "是否清理？")
                                            .setConfirmText("确认")
                                            .setCancelText("取消")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                @TargetApi(Build.VERSION_CODES.KITKAT)
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    ActivityManager am = (ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE);
                                                    am.clearApplicationUserData();
                                                    sDialog.setTitleText("清除成功")
                                                            .setConfirmText("OK")
                                                            .setConfirmClickListener(null)
                                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                }
                                            })
                                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();

                                                }
                                            }).show();
                                }
                            });
                        }
                    });
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.setting_menu_about:
                //关于
                mActivity.startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            case R.id.setting_menu_again_login:
                //切换账户 重新登录

                Intent loginIntent = new Intent(MyApplication.getContext(), LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(loginIntent);
                break;
        }
        return true;
    }


    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void unbindSucceed() {
        //解绑成功就把sp里面的buildingid清空
        String spId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String encryptBuildingId = AES.encrypt("", Contants.SP_AES_BUILDING_KEY);
        SharedPreferencesUtil.put(MyApplication.getContext(),spId,encryptBuildingId);

        Intent intent = new Intent(MyApplication.getContext(), BindRoomActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }
}
