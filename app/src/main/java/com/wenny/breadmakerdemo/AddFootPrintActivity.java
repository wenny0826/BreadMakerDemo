package com.wenny.breadmakerdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by ${wenny} on 2017/6/10.
 */

public class AddFootPrintActivity extends AppCompatActivity implements View.OnClickListener, DateTimePicker.OnConfirmListener {
    private Context context;
    private int position = -1;//第几个足迹
    private FootPrintEntity footPrintEntity;
    @Bind(R.id.footPrint_img)
    ImageView footPrint_img;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_confirm)
    TextView tv_confirm;
    @Bind(R.id.tv_delect)
    TextView tv_delect;
    @Bind(R.id.ll_address)
    LinearLayout ll_address;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.ed_describe)
    EditText ed_describe;

    private DateTimePicker dateTimePicker;
    private String describe;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_add_footprint);
        init();
    }
    protected void init() {
        context = this;
        Intent intent = getIntent();
        dateTimePicker = new DateTimePicker(context);
        position = intent.getIntExtra("position", -1);
        footPrintEntity = (FootPrintEntity) intent.getSerializableExtra("footPrintEntity");
        tv_time.setText(footPrintEntity.getImgTime());
        describe = footPrintEntity.getDescribe();
        if (!TextUtils.isEmpty(describe)) {
            ed_describe.setText(describe);
        }
        if(TextUtils.isEmpty(footPrintEntity.getAddress())){
            tv_address.setText(footPrintEntity.getAddress());
        }
        if(footPrintEntity.getType() == 2){
            footPrint_img.setVisibility(View.VISIBLE);
            Glide.with(context).load(footPrintEntity.getImgUrl()).asBitmap().centerCrop().placeholder(R.mipmap.cover).into(footPrint_img);
        } else {
            footPrint_img.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(footPrintEntity.getAddress())){
            tv_address.setText("点击添加地点信息");
        } else {
            tv_address.setText(footPrintEntity.getAddress());
        }


        footPrint_img.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        tv_delect.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        dateTimePicker.setOnConfirmListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.footPrint_img:
                //点击更换图片
                PhotoPicker.builder().setShowCamera(true).setPhotoCount(1).start(AddFootPrintActivity.this, 0x001);
                break;
            case R.id.tv_time:
                //点击修改时间
                dateTimePicker.show();
                dateTimePicker.setDate(footPrintEntity.getTimes());
                break;
            case R.id.tv_confirm:
                //点击完成
                describe = ed_describe.getText().toString();
                if (footPrintEntity.getType() == 1 && TextUtils.isEmpty(describe)) {
                    Toast.makeText(context, "描述不能为空", Toast.LENGTH_SHORT);
                    return;
                }
                footPrintEntity.setDescribe(describe);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("footPrintEntity", footPrintEntity);
                intent.putExtra("position", position);
                setResult(0x003, intent);
                finish();
                break;
            case R.id.tv_delect:
                //删除足迹
                Intent intent2 = new Intent(context, MainActivity.class);
                intent2.putExtra("position", position);
                setResult(0x002, intent2);
                finish();
                break;
            case R.id.ll_address:
//                startActivity(new Intent(context, AddressActivity.class));
                break;
        }
    }

    @Override
    public void onConfirm(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
        long timeInMillis = calendar.getTimeInMillis();
        footPrintEntity.setTimes(timeInMillis);
        String times = TimeUtil.date2String(timeInMillis, TimeUtil.DATE_FORMAT_2);
//        footPrintEntity.setImgTime(times);
        tv_time.setText(times);
        dateTimePicker.dismiss();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x001) {
            ArrayList<String> imagesPath = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            if (imagesPath.size() > 0) {
                String imgurl = imagesPath.get(0);
                footPrintEntity = buildImg2FootPrint(footPrintEntity, imgurl);
                Glide.with(context).load(footPrintEntity.getImgUrl()).asBitmap().centerCrop().placeholder(R.mipmap.cover).into(footPrint_img);
                tv_time.setText(footPrintEntity.getImgTime());
            }
        }
    }
    /**
     * @param imgurl
     * @return
     */
    private FootPrintEntity buildImg2FootPrint(FootPrintEntity footPrintEntity, String imgurl) {
        File file = new File(imgurl);
        footPrintEntity.setTimes(file.lastModified());
//        footPrintEntity.setImgTime(TimeUtil.date2String(file.lastModified(), TimeUtil.DATE_FORMAT_2));
        footPrintEntity.setImgUrl(PictureUtil.compressImage(imgurl, FileUtil.getThumbDir(context, imgurl), 30));
        return footPrintEntity;
    }

}
