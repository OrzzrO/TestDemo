package com.me.geekpracticedemo.ui.main.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.BaseFragment;
import com.me.geekpracticedemo.base.contract.main.SettingContract;
import com.me.geekpracticedemo.model.bean.VersionBean;
import com.me.geekpracticedemo.model.event.NightModeEvent;
import com.me.geekpracticedemo.presenter.main.SettingPresenter;
import com.me.geekpracticedemo.ui.main.activity.MainActivity;
import com.me.geekpracticedemo.util.ACache;
import com.me.geekpracticedemo.util.RxBus;
import com.me.geekpracticedemo.util.ShareUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by user on 2017/7/28.
 */

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingContract.View, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox mCbSettingCache;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox mCbSettingImage;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox mCbSettingNight;
    @BindView(R.id.ll_setting_feedback)
    LinearLayout mLlSettingFeedback;
    @BindView(R.id.tv_setting_clear)
    TextView mTvSettingClear;
    @BindView(R.id.ll_setting_clear)
    LinearLayout mLlSettingClear;
    @BindView(R.id.tv_setting_update)
    TextView mTvSettingUpdate;
    @BindView(R.id.ll_setting_update)
    LinearLayout mLlSettingUpdate;



    private File mCacheFile;
    private String versionName;
    private boolean isNull;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initEventAndData() {
        mCacheFile = new File(Constants.PATH_CACHE);
        Log.w("hongTest", "initEventAndData: mcache = " + mCacheFile );
        mTvSettingClear.setText(ACache.getCacheSize(mCacheFile));
        mCbSettingCache.setChecked(mPresenter.getAutoCacheState());
        mCbSettingImage.setChecked(mPresenter.getNoImageState());
        mCbSettingNight.setChecked(mPresenter.getNightModeState());
        mCbSettingCache.setOnCheckedChangeListener(this);
        mCbSettingImage.setOnCheckedChangeListener(this);
        mCbSettingNight.setOnCheckedChangeListener(this);
        try {
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
            versionName = pi.versionName;
            mTvSettingUpdate.setText(String.format("当前版本号 v%s", versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        isNull = savedInstanceState == null;
        super.onCreate(savedInstanceState);
    }


    @Override
    public void showUpdateDialog(VersionBean bean) {
        StringBuilder content = new StringBuilder("版本号: v");
        content.append(bean.getCode());
        content.append("\r\n");
        content.append("版本大小: ");
        content.append(bean.getSize());
        content.append("\r\n");
        content.append("更新内容:\r\n");
        content.append(bean
                           .getDes()
                           .replace("\\r\\n", "\r\n"));
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("检测到新版本!");
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Activity mActivity = getActivity();
                if (mActivity instanceof MainActivity) {
                    ((MainActivity) mActivity).checkPermissions();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_setting_night:
                Log.w("hongTest", "onCheckedChanged: isNull =" + isNull );
                if (isNull) {
                    Log.w("hongTest", "onCheckedChanged: 点击按钮  b = " + b );
                    mPresenter.setNightModeState(b);
                    Log.w("hongTest", "onCheckedChanged: 存储到sp中 = " );

                    NightModeEvent event = new NightModeEvent();
                    event.setNightMode(b);
                    RxBus
                        .getDefault()
                        .post(event);
                    Log.w("hongTest", "onCheckedChanged:  post一个事件-" );
                }
                break;
            case R.id.cb_setting_image:
                mPresenter.setNoImageState(b);
                Log.w("hongTest", "onCheckedChanged: 无图模式 invoke" );
                break;
            case R.id.cb_setting_cache:
                mPresenter.setAutoCacheState(b);
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.ll_setting_feedback,
              R.id.ll_setting_clear,
              R.id.ll_setting_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_feedback:
                ShareUtil.sendEmail(mContext,"选择邮件客户端");
                break;
            case R.id.ll_setting_clear:
                Log.w("hongTest", "onViewClicked: mcache = " + mCacheFile );
                ACache.deleteDir(mCacheFile);
                mTvSettingClear.setText(ACache.getCacheSize(mCacheFile));
                break;
            case R.id.ll_setting_update:
                mPresenter.checkVersion(versionName);

                break;
        }
    }
}
