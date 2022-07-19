package com.example.Projectpandi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Projectpandi.Model.LaporanM;
import com.example.Projectpandi.R;

import java.util.List;

public class LaporanmasukAdapter extends RecyclerView.Adapter<LaporanmasukAdapter.myViewHolder> {
    private Context context;
    private List<LaporanM> list;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int pos);

    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public LaporanmasukAdapter(Context context, List<LaporanM> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_masuk, parent, false);
        return new LaporanmasukAdapter.myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nominalpembayaran.setText(list.get(position).getNominalpembayaran());
        holder.jumlahpembayar.setText(list.get(position).getJumlahpembayar());
        holder.tanggallaporan.setText(list.get(position).getTanggallaporan());
        holder.angkatan.setText(list.get(position).getAngkatan());
        holder.deskripsi.setText(list.get(position).getDeskripsi());
        holder.hasil.setText(list.get(position).getHasil());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        TextView nominalpembayaran, jumlahpembayar, tanggallaporan, angkatan, deskripsi, hasil;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nominalpembayaran = itemView.findViewById(R.id.nominalpembayaran);
            jumlahpembayar = itemView.findViewById(R.id.jumlahpembayar);
            tanggallaporan = itemView.findViewById(R.id.tanggallaporan);
            angkatan = itemView.findViewById(R.id.angkatan);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            hasil = itemView.findViewById(R.id.hasil);

            itemView.setOnClickListener(view -> {
                if (dialog != null) {
                    dialog.onClick(getLayoutPosition());
                }
            });
        }
    }
}
