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
    private Dialog dialog;

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
        holder.ayahbelajar.setText(list.get(position).getAyahbelajar());
        holder.ibubelajar.setText(list.get(position).getIbubelajar());
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
        TextView namabelajar, jeniskelaminbelajar, tanggallahirbelajar, agamabelajar, ayahbelajar, ibubelajar, alamatbelajar, nobelajar, emailbelajar, haritanggalbelajar, programbelajarskb;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            namabelajar = itemView.findViewById(R.id.namabelajar);
            jeniskelaminbelajar = itemView.findViewById(R.id.jeniskelaminbelajar);
            tanggallahirbelajar = itemView.findViewById(R.id.tanggallahirbelajar);
            agamabelajar = itemView.findViewById(R.id.agamabelajar);
            ayahbelajar = itemView.findViewById(R.id.ayahbelajar);
            ibubelajar = itemView.findViewById(R.id.ibubelajar);
            alamatbelajar = itemView.findViewById(R.id.alamatbelajar);
            nobelajar = itemView.findViewById(R.id.nobelajar);
            emailbelajar = itemView.findViewById(R.id.emailbelajar);
            haritanggalbelajar = itemView.findViewById(R.id.haritanggalbelajar);
            programbelajarskb= itemView.findViewById(R.id.programbelajarskb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null) {
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
