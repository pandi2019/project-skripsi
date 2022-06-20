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

import com.example.Projectpandi.Model.User;
import com.example.Projectpandi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.myViewHolder> {
    private Context context;
    private List<User> list;
    private Dialog dialog;


    public interface Dialog {
        void onClick(int pos);

    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new UserAdapter.myViewHolder(itemView);
    }

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nama.setText(list.get(position).getNama());
        holder.namaortu.setText(list.get(position).getNamaortu());
        holder.kelas.setText(list.get(position).getKelas());
        holder.tanggal.setText(list.get(position).getTanggal());
        holder.nominal.setText(list.get(position).getNominal());

        //Problem
        Picasso.get().load(list.get(position).getUploadpendaftaran())
                .centerCrop()
                .fit()
                .into(holder.uploadpendaftaran);

//        Glide.with(context).load(list.get(position).getUploadpendaftaran()).into(holder.uploadpendaftaran);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        TextView nama, namaortu, kelas, tanggal, nominal;
        ImageView uploadpendaftaran;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            namaortu = itemView.findViewById(R.id.namaortu);
            kelas = itemView.findViewById(R.id.kelas);
            tanggal = itemView.findViewById(R.id.tanggal);
            nominal = itemView.findViewById(R.id.nominal);
            uploadpendaftaran = itemView.findViewById(R.id.uploadpendaftaran);
            itemView.setOnClickListener(view -> {
                if (dialog != null) {
                    dialog.onClick(getLayoutPosition());
                }
            });
        }
    }
}
