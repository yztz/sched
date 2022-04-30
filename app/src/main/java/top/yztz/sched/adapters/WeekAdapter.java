package top.yztz.sched.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import top.yztz.sched.R;
import top.yztz.sched.config.Config;
import top.yztz.sched.utils.DateUtils;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.MyViewHolder> {
    private Context context;
    private OnWeekClickListener listener;


    private int currentWeek = DateUtils.weekNo;

    public WeekAdapter(Context context, OnWeekClickListener listener) {
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_week, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        position++;
        holder.tvTitle.setText(String.valueOf(position));
        if (position == currentWeek) {
            holder.root.setBackground(context.getDrawable(R.drawable.week_selected));
        } else {
            holder.root.setBackground(null);
        }

        int finalPosition = position;
        holder.root.setOnClickListener(v -> {
            currentWeek = finalPosition;
            notifyDataSetChanged();
            if (null != listener) listener.onClick(finalPosition);
        });
    }


    @Override
    public int getItemCount() {
        return Config.WEEK_NUM; // current 20
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView tvTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            tvTitle = itemView.findViewById(R.id.title);
        }
    }

    public interface OnWeekClickListener {
        void onClick(int week);
    }
}
