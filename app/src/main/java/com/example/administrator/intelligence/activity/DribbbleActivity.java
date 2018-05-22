package com.example.administrator.intelligence.activity;

import android.app.DatePickerDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.intelligence.R;
import com.example.administrator.intelligence.bean.CardDataItem;
import com.example.administrator.intelligence.utils.Config;
import com.example.common.common.base.BaseActivity;
import com.example.common.common.cardView.CardAdapter;
import com.example.common.common.cardView.CardSlidePanel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class DribbbleActivity extends BaseActivity {
    private List<CardDataItem> dataList = new ArrayList<>();
    private CardSlidePanel.CardSwitchListener cardSwitchListener;
    private CardSlidePanel slidePanel;
    private Button date_selector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dribbble);
        initView();
    }


    private void initView() {
        slidePanel = findViewById(R.id.image_slide_panel);
        date_selector = findViewById(R.id.date_selector);
        date_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(DribbbleActivity.this,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                getDateInfo("" + monthOfYear, "" + dayOfMonth);
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // 1. 左右滑动监听
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
                Log.d("Card", "正在显示-" + dataList.get(index).getTitle());
            }

            @Override
            public void onCardVanish(int index, int type) {
                Log.d("Card", "正在消失-" + dataList.get(index).getTitle() + " 消失type=" + type);
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);


        // 2. 绑定Adapter
        slidePanel.setAdapter(new CardAdapter() {
            @Override
            public int getLayoutId() {
                return R.layout.card_item;
            }

            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public void bindView(View view, int index) {
                Object tag = view.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }

                viewHolder.bindData(dataList.get(index));
            }

            @Override
            public Object getItem(int index) {
                return dataList.get(index);
            }

            @Override
            public Rect obtainDraggableArea(View view) {
                // 可滑动区域定制，该函数只会调用一次
                View contentView = view.findViewById(R.id.card_item_content);
                View topLayout = view.findViewById(R.id.card_top_layout);
                View bottomLayout = view.findViewById(R.id.card_bottom_layout);
                int left = view.getLeft() + contentView.getPaddingLeft() + topLayout.getPaddingLeft();
                int right = view.getRight() - contentView.getPaddingRight() - topLayout.getPaddingRight();
                int top = view.getTop() + contentView.getPaddingTop() + topLayout.getPaddingTop();
                int bottom = view.getBottom() - contentView.getPaddingBottom() - bottomLayout.getPaddingBottom();
                return new Rect(left, top, right, bottom);
            }
        });
        getDateInfo("12", "26");
    }

    private void getDateInfo(String Month, String Day) {
        Map<String, String> map = new HashMap<>();
        map.put("key", Config.APPKEY);
        map.put("v", "1.0");
        map.put("month", Month);
        map.put("day", Day);
        OkHttpUtils
                .post()
                .url(Config.URL_JUHE_HTTPS)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray("result");
                            if (result.length() > 0) {
                                dataList.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject result_object = result.getJSONObject(i);
                                    CardDataItem dataItem = new CardDataItem();
                                    dataItem.set_id(result_object.optString("_id"));
                                    dataItem.setDes(result_object.optString("des"));
                                    dataItem.setLunar(result_object.optString("lunar"));
                                    dataItem.setPic(result_object.optString("pic"));
                                    dataItem.setTitle(result_object.optString("title"));
                                    dataItem.setYear(result_object.optString("year"));
                                    dataList.add(dataItem);
                                }
                                slidePanel.getAdapter().notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    class ViewHolder {

        ImageView imageView;
        View maskView;
        TextView userNameTv;
        TextView imageNumTv;
        TextView card_other_description;
        TextView card_des;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.card_image_view);
            maskView = view.findViewById(R.id.maskView);
            userNameTv = view.findViewById(R.id.card_user_name);
            imageNumTv = view.findViewById(R.id.card_pic_num);
            card_other_description = view.findViewById(R.id.card_other_description);
            card_des = view.findViewById(R.id.card_des);
        }

        public void bindData(CardDataItem itemData) {
            Glide.with(DribbbleActivity.this).load(itemData.getPic()).placeholder(R.drawable.error).into(imageView);
            userNameTv.setText(itemData.getTitle());
            imageNumTv.setText("" + dataList.size());
            card_other_description.setText("阴历:" + itemData.getLunar());
            card_des.setText(itemData.getDes());
        }
    }
}
