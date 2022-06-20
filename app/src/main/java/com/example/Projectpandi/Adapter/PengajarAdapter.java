package com.example.Projectpandi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Projectpandi.Model.Pengajar;
import com.example.Projectpandi.R;

import java.util.List;

public class PengajarAdapter extends RecyclerView.Adapter<PengajarAdapter.myViewHolder> {
    private Context context;
    private List<Pengajar> list;
    private PengajarAdapter.Dialog dialog;

    public interface Dialog {
        void onClick(int pos);

    }

    public void setDialog(PengajarAdapter.Dialog dialog) {
        this.dialog = dialog;
    }

    public PengajarAdapter(Context context, List<Pengajar> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PengajarAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_b, parent, false);
        return new PengajarAdapter.myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PengajarAdapter.myViewHolder holder, int position) {
        holder.namapengajar.setText(list.get(position).getNamapengajar());
        holder.nippegawai.setText(list.get(position).getNippegawai());
        holder.jabatan.setText(list.get(position).getJabatan());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        TextView namapengajar, nippegawai, jabatan;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            namapengajar = itemView.findViewById(R.id.namapengajar);
            nippegawai = itemView.findViewById(R.id.nippegawai);
            jabatan = itemView.findViewById(R.id.jabatan);

            itemView.setOnClickListener(view -> {
                if (dialog != null) {
                    dialog.onClick(getLayoutPosition());
                }
            });
        }
    }
}
