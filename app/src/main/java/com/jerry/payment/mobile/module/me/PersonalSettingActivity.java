package com.jerry.payment.mobile.module.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.common.Urls;
import com.jerry.payment.mobile.model.UserInfo;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.okhttp.OkHttpUtils;
import com.jerry.payment.mobile.okhttp.callback.GsonCallBack;
import com.jerry.payment.mobile.utils.CropPhotoUtils;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.utils.Logs;
import com.jerry.payment.mobile.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Personal Setting Activity
 * Created by jerry on 2016/7/8.
 */
public class PersonalSettingActivity extends BaseActivity {

    private final int REQ_CODE = 16;
    private ListView listView;
    private BaseAdapter adapter;
    private List<String> infoList;
    private CircleImageView avatarIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_personal_setting_activity);
        initData();
        initView();
    }

    private void initData() {
        final int[] icons = {
                R.drawable.me_personal_nickname,
                R.drawable.me_personal_email,
                R.drawable.me_personal_address,
                R.drawable.me_personal_phone,
                R.drawable.me_personal_verification,
                R.drawable.discover_recent_contacts
        };
        infoList = new ArrayList<>();

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return icons.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(PersonalSettingActivity.this).inflate(R.layout.me_personal_setting_item, null);
                    viewHolder = new ViewHolder();
                    viewHolder.imgIV = (ImageView) convertView.findViewById(R.id.personal_list_item_img);
                    viewHolder.textTV = (TextView) convertView.findViewById(R.id.personal_item_text);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.imgIV.setImageResource(icons[position]);
                if(infoList.size() > position){
                    if(infoList.get(position).equals("true")){
                        viewHolder.textTV.setText(getString(R.string.me_verify_verified));
                    }else {
                        viewHolder.textTV.setText(infoList.get(position));
                    }
                }
                return convertView;
            }
        };
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        listView = (ListView) findViewById(R.id.personal_setting_list);
        View avatarItem = LayoutInflater.from(PersonalSettingActivity.this).inflate(R.layout.me_personal_setting_avatar, null);
        avatarIV = (CircleImageView) avatarItem.findViewById(R.id.personal_item_avatar);
        avatarIV.setBorderWidth(2);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleTV.setText(getString(R.string.me_setting));
        listView.addHeaderView(avatarItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(PersonalSettingActivity.this, PersonalModifyAvatarActivity.class);
                        startActivityForResult(intent, REQ_CODE);
                        break;
                    case 1:
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("title", getString(R.string.me_modify_nick_name));
                        bundle1.putInt("icon", R.drawable.me_modify_nickname);
                        bundle1.putString("type", "nickname");
                        bundle1.putString("hint", getString(R.string.me_personal_nick_name));
                        IntentUtils.startActivity(PersonalSettingActivity.this, ModifyCommonActivity.class, bundle1);
                        break;
                    case 2:
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("title", getString(R.string.me_modify_email));
                        bundle2.putInt("icon", R.drawable.login_email);
                        bundle2.putString("type", "email");
                        bundle2.putString("hint", getString(R.string.me_personal_email));
                        IntentUtils.startActivity(PersonalSettingActivity.this, ModifyCommonActivity.class, bundle2);
                        break;
                    case 3:
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("title", getString(R.string.me_modify_address));
                        bundle3.putInt("icon", R.drawable.me_modify_address);
                        bundle3.putString("type", "address");
                        bundle3.putString("hint", getString(R.string.me_personal_address));
                        IntentUtils.startActivity(PersonalSettingActivity.this, ModifyCommonActivity.class, bundle3);
                        break;
                    case 4:
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("title", getString(R.string.me_modify_phone));
                        bundle4.putInt("icon", R.drawable.me_sign_up_phone);
                        bundle4.putString("type", "phone");
                        bundle4.putString("hint", getString(R.string.me_personal_phone));
                        IntentUtils.startActivity(PersonalSettingActivity.this, ModifyCommonActivity.class, bundle4);
                        break;
                    case 5:
                        IntentUtils.startActivity(PersonalSettingActivity.this, AccountVerifiedActivity.class);
                        break;
                    case 6:
                        IntentUtils.startActivity(PersonalSettingActivity.this, ChangeLanguageActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadData();
        loadDummyData();
    }

    private void loadData(){
        OkHttpUtils.get()
                .url(Urls.PERSONAL_INFO)
                .build()
                .execute(new GsonCallBack<UserInfo>(UserInfo.class){

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logs.i("onError---" + e.getMessage());
                    }

                    @Override
                    public void onResponse(UserInfo response, int id) {
                        infoList.clear();
                        infoList.add(response.getName());
                        infoList.add(response.getEmail());
                        infoList.add(response.getAddress());
                        infoList.add(response.getPhoneNumber());
                        infoList.add(response.getVerification());

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadDummyData(){
        infoList.clear();
        infoList.add("Jerry");
        infoList.add("cooldyj@126.com");
        infoList.add("Newton Road,Shanghai Pudong New District,No. 350");
        infoList.add("18923456636");
        infoList.add("true");
        infoList.add(getString(R.string.me_modify_language));

        adapter.notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView imgIV;
        TextView textTV;
    }

    /**
     * 读取裁剪后的图片
     */
    private void readCropImage(Intent intent){
        if(intent == null)
            return;
        Uri uri = intent.getParcelableExtra(CropPhotoUtils.CROP_IMAGE_URI);
        Bitmap photo = CropPhotoUtils.getBitmap(PersonalSettingActivity.this, uri);
        Logs.i(photo.getByteCount() + "");
        Drawable drawable = new BitmapDrawable(getResources(), photo);
        avatarIV.setImageDrawable(drawable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == REQ_CODE && resultCode == RESULT_OK){
            if (intent != null) {
                readCropImage(intent);
            }
        }
    }
}
