package com.ahmadawalludin.sholatku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/** Menampilkan daftar riwayat salat di RecyclerView. */
public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.ViewHolder> {

    public interface OnHapusListener {
        void onHapus(Riwayat riwayat);
    }

    private final List<Riwayat> data = new ArrayList<>();
    private final OnHapusListener listener;

    public RiwayatAdapter(OnHapusListener listener) {
        this.listener = listener;
    }

    public void setData(List<Riwayat> dataBaru) {
        data.clear();
        data.addAll(dataBaru);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_riwayat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Riwayat item = data.get(position);
        holder.tvNamaSalat.setText(item.getNamaSalat());
        holder.tvTanggal.setText(item.getTanggal());

        if (item.isSudahDikerjakan()) {
            holder.tvStatus.setText(R.string.status_sudah);
            holder.tvStatus.setTextColor(
                    holder.itemView.getContext().getColor(R.color.green_primary));
        } else {
            holder.tvStatus.setText(R.string.status_belum);
            holder.tvStatus.setTextColor(
                    holder.itemView.getContext().getColor(R.color.text_secondary));
        }

        holder.btnHapus.setOnClickListener(v -> listener.onHapus(item));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNamaSalat;
        final TextView tvTanggal;
        final TextView tvStatus;
        final ImageButton btnHapus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaSalat = itemView.findViewById(R.id.tvNamaSalat);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}
