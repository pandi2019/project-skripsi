package com.example.Projectpandi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Projectpandi.Model.Bulanan14;
import com.example.Projectpandi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BulananAdapter extends RecyclerView.Adapter<BulananAdapter.myViewHolder> {
    private Context context;
    private List<Bulanan14> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);

    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public BulananAdapter(Context context, List<Bulanan14> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bulanan, parent, false);
        return new BulananAdapter.myViewHolder(itemView);
    }

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.namabln.setText(list.get(position).getNamabln());
        holder.nisbln.setText(list.get(position).getNisbln());
        holder.kelasbln.setText(list.get(position).getKelasbln());
        holder.tanggalbln.setText(list.get(position).getTanggalbln());
        holder.bulanan.setText(list.get(position).getBulanan());

        //Problem
        Picasso.get().load(list.get(position).getUploadbulanan())
                .centerCrop()
                .fit()
                .into(holder.uploadbulanan);

//        Glide.with(context).load(list.get(position).getUploadpendaftaran()).into(holder.uploadpendaftaran);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{
        TextView namabln, nisbln, kelasbln, tanggalbln, bulanan;
        ImageView uploadbulanan;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            namabln = itemView.findViewById(R.id.namabln);
            nisbln = itemView.findViewById(R.id.nisbln);
            kelasbln = itemView.findViewById(R.id.kelasbln);
            tanggalbln = itemView.findViewById(R.id.tanggalbln);
            bulanan = itemView.findViewById(R.id.bulanan);
            uploadbulanan = itemView.findViewById(R.id.uploadbulanan);
            itemView.setOnClickListener(view -> {
                if(dialog!=null){
                    dialog.onClick(getLayoutPosition());
                }
            });
        }
    }

}
