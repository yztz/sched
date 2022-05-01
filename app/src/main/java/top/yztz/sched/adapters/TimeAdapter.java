package top.yztz.sched.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import top.yztz.sched.R;
import top.yztz.sched.config.Config;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder> {
    private Context context;

    private int currentTime = 1;

    public TimeAdapter(Context context) {
        this.context = context;
    }

    public void setCurrentTime(int time) {
        this.currentTime = time;
        notifyDataSetChanged();
    }

    public int getCurrentTime() {
        return currentTime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_timepicker, parent, false));
    }

    private void blur(MyViewHolder holder) {
        holder.textView.setTextColor(context.getColor(R.color.grey));
        holder.textView.setTextSize(15);
        holder.textView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
    }

    private void focus(MyViewHolder holder) {
        holder.textView.setTextColor(context.getColor(R.color.black));
        holder.textView.setTextSize(17);
        holder.textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        position++;
        holder.textView.setText(String.valueOf(position));
        if (currentTime == position) {
            focus(holder);
        } else {
            blur(holder);
        }
    }

    @Override
    public int getItemCount() {
        return Config.MAX_TIME_LEN;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
