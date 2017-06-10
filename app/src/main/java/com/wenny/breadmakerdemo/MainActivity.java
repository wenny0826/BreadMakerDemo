package com.wenny.breadmakerdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

public class MainActivity extends AppCompatActivity implements FootPrintAdapter.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.add)
    TextView add;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.img_cover)
    ImageView img_cover;
    @Bind(R.id.tv_camera)
    TextView tv_camera;
    @Bind(R.id.tv_text)
    TextView tv_text;
    @Bind(R.id.img_setting)
    ImageView img_setting;

    private FootPrintAdapter footPrintAdapter;
    private List<FootPrintEntity> footPrintEntities;
    private String cover;
    private AlertDialog alertDialog;
    private Context context;
    //获取SD卡的路径
    String sdPath = Environment.getExternalStorageDirectory().getPath();
    private String picPath;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initAlertDialog();
        bindListener();
    }
    private void init(){
        context = this;
        ButterKnife.bind(this);
        footPrintEntities = new ArrayList<>();
        footPrintAdapter = new FootPrintAdapter(context);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(footPrintAdapter);
    }
    public void bindListener() {
        footPrintAdapter.setOnItemClickListener(this);
        img_cover.setOnClickListener(this);
        add.setOnClickListener(this);
        tv_text.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
        img_setting.setOnClickListener(this);
    }
    private void initAlertDialog(){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(0,100,0,0);
        TextView textView1 = new TextView(context);
        textView1.setText("预览游记");
        textView1.setPadding(0,20,0,20);
        textView1.setGravity(Gravity.CENTER);
        TextView textView2 = new TextView(context);
        textView2.setText("结束游记");
        textView2.setGravity(Gravity.CENTER);
        textView2.setPadding(0,20,0,20);
        TextView textView3 = new TextView(context);
        textView3.setText("删除游记");
        textView3.setGravity(Gravity.CENTER);
        textView3.setPadding(0,20,0,20);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preview();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishTravel();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delectTravel();
            }
        });
        linearLayout.addView(textView1);
        linearLayout.addView(textView2);
        linearLayout.addView(textView3);
        alertDialog = new AlertDialog.Builder(context).setView(linearLayout).setTitle("").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        }).create();

    }

    @Override
    public void onItemClick(View view, int position, FootPrintEntity footPrintEntity) {
        Intent intent = new Intent(context, AddFootPrintActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("footPrintEntity", footPrintEntity);
        startActivityForResult(intent, 0x003);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                //添加足迹
                PhotoPicker.builder()
                        .setShowCamera(false)
                        .setPhotoCount(9)
                        .setGridColumnCount(3)
                        .start(this, 0x001);
                break;
            case R.id.img_cover:
                //点击更换封面
                PhotoPicker.builder().setShowCamera(true).setPhotoCount(1).start(this, 0x002);
                break;
            case R.id.tv_camera:
                //调用系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                picPath = sdPath + "/Travel" + System.currentTimeMillis() + ".png";
                Uri uri = Uri.fromFile(new File(picPath));
                //为拍摄的图片指定一个存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 0x004);
                break;
            case R.id.tv_text:
                addText();
                break;
            case R.id.img_setting:
                alertDialog.show();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x001 && resultCode == RESULT_OK) {
            ArrayList<String> imagesPath = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            if (imagesPath.size() == 1) {
                addOneImgResult(imagesPath.get(0));
            } else if (imagesPath.size() > 1) {
                addMoreImgResult(imagesPath);
            }
        } else if (requestCode == 0x003 && resultCode == 0x003) {
            //修改足迹后返回
            FootPrintEntity footPrintEntity = (FootPrintEntity) data.getSerializableExtra("footPrintEntity");
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                footPrintEntities.remove(position);
                footPrintEntities.add(footPrintEntity);
                footPrintByStor(footPrintEntities);
            } else {
                footPrintEntities.add(footPrintEntity);
                footPrintByStor(footPrintEntities);
            }
        } else if (requestCode == 0x003 && resultCode == 0x002) {
            //删除足迹后返回
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                footPrintEntities.remove(position);
                footPrintAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 0x002 && resultCode == RESULT_OK) {
            //更换封面
            ArrayList<String> imagesPath = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            String imgurl = imagesPath.get(0);
            cover = PictureUtil.compressImage(imgurl, FileUtil.getThumbDir(context, imgurl), 30);
            Glide.with(context).load(cover).asBitmap().centerCrop().into(img_cover);
        } else if (requestCode == 0x004 && resultCode == RESULT_OK) {
            //调用系统相机返回结果
            Log.d("print", "onActivityResult: " + data);
            getCamearPic(picPath);
        }
    }
    /**
     * 预览
     */
    private void preview(){
        showToast(context,"预览游记");
    }
    /**
     * 结束
     */
    private void finishTravel(){
        showToast(context,"结束游记");
    }
    /**
     * 删除
     */
    private void delectTravel(){
        showToast(context,"删除游记");
    }
    /**
     * 添加了多张图片
     *
     * @param imagesPath
     */
    private void addMoreImgResult(ArrayList<String> imagesPath) {
        for (int i = 0; i < imagesPath.size(); i++) {
            String s = imagesPath.get(i);
            footPrintEntities.add(buildImg2FootPrint(s));
        }
        footPrintByStor(footPrintEntities);
    }

    /**
     * 添加了一张图片，跳转到添加足迹界面
     *
     * @param imagesPath
     */
    private void addOneImgResult(String imagesPath) {
        FootPrintEntity footPrintEntity = buildImg2FootPrint(imagesPath);
        Intent intent = new Intent(context, AddFootPrintActivity.class);
        intent.putExtra("footPrintEntity", footPrintEntity);
        startActivityForResult(intent, 0x003);
    }


    /**
     * 添加文字
     */
    private void addText(){
        FootPrintEntity footPrintEntity = new FootPrintEntity();
        footPrintEntity.setType(1);
        long currentTimeMillis = System.currentTimeMillis();
        footPrintEntity.setTimes(currentTimeMillis);
//        footPrintEntity.setAddress(AppContext.addr);
        Intent intent = new Intent(context, AddFootPrintActivity.class);
        intent.putExtra("footPrintEntity", footPrintEntity);
        startActivityForResult(intent, 0x003);
    }

    /**
     * 调用系统相机返回结果
     * @param picPath
     */
    private void getCamearPic(String picPath) {
        FootPrintEntity footPrintEntity = buildImg2FootPrint(picPath);
//        footPrintEntity.setAddress(AppContext.addr);
        Intent intent = new Intent(context, AddFootPrintActivity.class);
        intent.putExtra("footPrintEntity", footPrintEntity);
        startActivityForResult(intent, 0x003);
    }

    /**
     * 给足迹重新排序
     *
     * @param footPrintEntities
     */
    private void footPrintByStor(List<FootPrintEntity> footPrintEntities) {
        Collections.sort(footPrintEntities);
        //获得第一天
        FootPrintEntity footPrintEntity = footPrintEntities.get(0);
        footPrintEntity.setDays("1");
        footPrintEntity.setShowDays(true);
        //上一天
        FootPrintEntity footPrintEntity_last = footPrintEntities.get(0);
        long times_first = footPrintEntity.getTimes();
        for (int i = 0; i < footPrintEntities.size(); i++) {
            if (i > 0) {
                FootPrintEntity footPrintEntity1 = footPrintEntities.get(i);
                int days = TimeUtil.getDayDiff(TimeUtil.getYearMonthDay(times_first), TimeUtil.getYearMonthDay(footPrintEntity1.getTimes()));
                footPrintEntity1.setDays(days + "");
                if (days == Integer.parseInt(footPrintEntity_last.getDays())) {
                    footPrintEntity1.setShowDays(false);
                } else {
                    footPrintEntity1.setShowDays(true);
                }
                footPrintEntity_last = footPrintEntity1;
            }
        }
        if (TextUtils.isEmpty(cover)) {
            cover = footPrintEntity.getImgUrl();
            Glide.with(context).load(cover).asBitmap().centerCrop().into(img_cover);
        }
        //打印出了重新排序后的足迹
        Log.d("print", "footPrintByStor: " + footPrintEntities.toString());
        //重新显示按顺序显示足迹
        footPrintAdapter.setFootPrintEntities(footPrintEntities);
    }

    /**
     * @param imgurl
     * @return
     */
    private FootPrintEntity buildImg2FootPrint(String imgurl) {
        File file = new File(imgurl);
        FootPrintEntity footPrintEntity = new FootPrintEntity();
        footPrintEntity.setTimes(file.lastModified());
//        footPrintEntity.setImgTime(TimeUtil.date2String(file.lastModified(), TimeUtil.DATE_FORMAT_2));
        footPrintEntity.setImgUrl(PictureUtil.compressImage(imgurl, FileUtil.getThumbDir(context, imgurl), 30));
        footPrintEntity.setType(2);
        return footPrintEntity;
    }
    protected void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }
}
