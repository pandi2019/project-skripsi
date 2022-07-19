package com.example.Projectpandi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Projectpandi.Model.Angsuran;
import com.example.Projectpandi.R;

import java.util.List;

public class AngsuranAdapter extends RecyclerView.Adapter<AngsuranAdapter.myViewHolder>{
    private Context context;
    private List<Angsuran> list;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int pos);

    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public AngsuranAdapter(Context context, List<Angsuran> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_angsuran, parent, false);
        return new AngsuranAdapter.myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nominalangsuran.setText(list.get(position).getNominalangsuran());
        holder.jumlahangsuran.setText(list.get(position).getJumlahangsuran());
        holder.tanggalangsuran.setText(list.get(position).getTanggalangsuran());
        holder.angkatan1.setText(list.get(position).getAngkatan1());
        holder.deskripsi1.setText(list.get(position).getDeskripsi1());
        holder.hasilangsuran.setText(list.get(position).getHasilangsuran());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        TextView nominalangsuran, jumlahangsuran, tanggalangsuran, angkatan1, deskripsi1, hasilangsuran;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nominalangsuran = itemView.findViewById(R.id.nominalangsuran);
            jumlahangsuran = itemView.findViewById(R.id.jumlahangsuran);
            tanggalangsuran = itemView.findViewById(R.id.tanggalangsuran);
            angkatan1 = itemView.findViewById(R.id.angkatan1);
            deskripsi1 = itemView.findViewById(R.id.deskripsi1);
            hasilangsuran = itemView.findViewById(R.id.hasilangsuran);

            itemView.setOnClickListener(view -> {
                if (dialog != null) {
                    dialog.onClick(getLayoutPosition());
                }
            });
        }
    }
}
