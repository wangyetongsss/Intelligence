package com.example.administrator.intelligence.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.intelligence.R;
import com.example.administrator.intelligence.bean.ChatMessage;
import com.example.administrator.intelligence.bean.ChatMessage.Type;

import java.util.List;

public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ChatMessage> mDatas;
    private OnContentLongClickListener contentLongClickListener;
    private OnContentClickListener contentClickListener;
    private Context mContext;

    public ChatMessageAdapter(Context context, List<ChatMessage> datas) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 接受到消息为1，发送消息为0
     */
    @Override
    public int getItemViewType(int position) {
        ChatMessage msg = mDatas.get(position);
        return msg.getType() == Type.INPUT ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ChatMessage chatMessage = mDatas.get(position);

        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (chatMessage.getType() == Type.INPUT) {
                convertView = mInflater.inflate(R.layout.main_chat_from_msg,
                        parent, false);
                viewHolder.createDate = (TextView) convertView
                        .findViewById(R.id.chat_from_createDate);
                viewHolder.content = (TextView) convertView
                        .findViewById(R.id.chat_from_content);
                viewHolder.chat_from_image_ll = (LinearLayout) convertView
                        .findViewById(R.id.chat_from_image_ll);
                viewHolder.chat_from_image = (ImageView) convertView
                        .findViewById(R.id.chat_from_image);
                convertView.setTag(viewHolder);
            } else {
                convertView = mInflater.inflate(R.layout.main_chat_send_msg,
                        null);
                viewHolder.createDate = (TextView) convertView
                        .findViewById(R.id.chat_send_createDate);
                viewHolder.content = (TextView) convertView
                        .findViewById(R.id.chat_send_content);
                viewHolder.chat_send_image_rl = (RelativeLayout) convertView
                        .findViewById(R.id.chat_send_image_rl);
                viewHolder.chat_send_image = (ImageView) convertView
                        .findViewById(R.id.chat_send_image);
                convertView.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (chatMessage.getType() == Type.INPUT) {
            if (!TextUtils.isEmpty(chatMessage.getUrl())) {
                viewHolder.chat_from_image_ll.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(chatMessage.getUrl()).placeholder(R.drawable.error).into(viewHolder.chat_from_image);
            } else {
                viewHolder.chat_from_image_ll.setVisibility(View.GONE);
            }
        } else {
            if (!TextUtils.isEmpty(chatMessage.getUrl())) {
                viewHolder.chat_send_image_rl.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(chatMessage.getUrl()).placeholder(R.drawable.error).into(viewHolder.chat_send_image);
            } else {
                viewHolder.chat_send_image_rl.setVisibility(View.GONE);
            }
        }
        viewHolder.content.setText(chatMessage.getMsg());
        viewHolder.createDate.setText(chatMessage.getDateStr());
        viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //短按
                if (contentClickListener != null) {
                    contentClickListener.getContentCLick(chatMessage.getMsg());
                }

            }
        });
        viewHolder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //长按
                if (contentLongClickListener != null) {
                    contentLongClickListener.getContentLong(chatMessage.getMsg());
                }
                return false;
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public TextView createDate;
        public TextView name;
        public TextView content;
        public RelativeLayout chat_send_image_rl;
        public LinearLayout chat_from_image_ll;
        public ImageView chat_send_image;
        public ImageView chat_from_image;
    }

    public void setOnContentLongClick(OnContentLongClickListener contentLongClickListener) {
        this.contentLongClickListener = contentLongClickListener;
    }

    public void setOnContentClick(OnContentClickListener contentClickListener) {
        this.contentClickListener = contentClickListener;
    }

    public interface OnContentLongClickListener {
        void getContentLong(String content);
    }

    public interface OnContentClickListener {
        void getContentCLick(String content);
    }
}
