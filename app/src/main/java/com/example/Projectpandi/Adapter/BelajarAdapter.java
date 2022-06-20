package com.example.Projectpandi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Projectpandi.Model.Belajarskb;
import com.example.Projectpandi.R;

import java.util.List;

public class BelajarAdapter extends RecyclerView.Adapter<BelajarAdapter.myViewHolder>{
    private Context context;
    private List<Belajarskb> list;
    private BelajarAdapter.Dialog dialog;

    public interface Dialog {
        void onClick(int pos);
    }

    public void setDialog(BelajarAdapter.Dialog dialog) {
        this.dialog = dialog;
    }

    public BelajarAdapter(Context context, List<Belajarskb> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BelajarAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pendaftaran, parent, false);
        return new BelajarAdapter.myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BelajarAdapter.myViewHolder holder, int position) {
        holder.namabelajar.setText(list.get(position).getNamabelajar());
        holder.jeniskelaminbelajar.setText(list.get(position).getJeniskelaminbelajar());
        holder.tanggallahirbelajar.setText(list.get(position).getTanggallahirbelajar());
        holder.agamabelajar.setText(list.get(position).getAgamabelajar());
        holder.orangtuabelajar.setText(list.get(position).getOrangtuabelajar());
        holder.alamatbelajar.setText(list.get(position).getAlamatbelajar());
        holder.nobelajar.setText(list.get(position).getNobelajar());
        holder.emailbelajar.setText(list.get(position).getEmailbelajar());
        holder.haritanggalbelajar.setText(list.get(position).getHaritanggalbelajar());
        holder.programbelajarskb.setText(list.get(position).getProgrambelajarskb());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        TextView namabelajar, jeniskelaminbelajar, tanggallahirbelajar, agamabelajar, programbelajarskb,
                orangtuabelajar, alamatbelajar, nobelajar, emailbelajar, haritanggalbelajar;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            namabelajar = itemView.findViewById(R.id.row_nama);
            jeniskelaminbelajar = itemView.findViewById(R.id.row_jenisk);
            tanggallahirbelajar = itemView.findViewById(R.id.row_tanggallahir);
            agamabelajar = itemView.findViewById(R.id.row_agama);
            orangtuabelajar = itemView.findViewById(R.id.row_ortu);
            alamatbelajar = itemView.findViewById(R.id.row_alamat_rumah);
            nobelajar = itemView.findViewById(R.id.row_no);
            emailbelajar = itemView.findViewById(R.id.row_email);
            haritanggalbelajar = itemView.findViewById(R.id.row_tanggal);
            programbelajarskb= itemView.findViewById(R.id.row_programbelajar);

            itemView.setOnClickListener(view -> {
                if (dialog != null) {
                    dialog.onClick(getLayoutPosition());
                }
            });
        }
    }
}
