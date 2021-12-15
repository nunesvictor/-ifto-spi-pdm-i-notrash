package pdm1.notrash.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import pdm1.notrash.R;
import pdm1.notrash.entity.Remessa;
import pdm1.notrash.entity.Residuo;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RemessaViewHolder> {
    protected List<Remessa> remessas;
    protected int mExpandedPosition = -1;
    protected int previousExpandedPosition = -1;

    public RecyclerViewAdapter(List<Remessa> remessas) {
        this.remessas = remessas;
    }

    public static class RemessaViewHolder extends RecyclerView.ViewHolder {
        CardView remessaCardView;
        TextView remessaTitleTextView;
        TextView remessaSubtitleTextView;
        TextView remessaTriviaTextView;
        LinearLayout remessaDetailsCardView;

        RemessaViewHolder(View view) {
            super(view);

            remessaCardView = view.findViewById(R.id.remessaCardView);
            remessaDetailsCardView = view.findViewById(R.id.remessaDetailsCardView);
            remessaTitleTextView = view.findViewById(R.id.remessaTitleTextView);
            remessaTriviaTextView = view.findViewById(R.id.remessaTriviaTextView);
            remessaSubtitleTextView = view.findViewById(R.id.remessaSubtitleTextView);
        }
    }

    @Override
    public int getItemCount() {
        return remessas.size();
    }

    @NonNull
    @Override
    public RemessaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_home_remessas, viewGroup, false);

        return new RemessaViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RemessaViewHolder holder, int position) {
        final boolean isExpanded = position == mExpandedPosition;
        Remessa remessa = remessas.get(position);

        if (isExpanded)
            previousExpandedPosition = holder.getBindingAdapterPosition();

        holder.remessaTitleTextView.setText(String.format("Remessa %02d", remessa.remessaId));
        holder.remessaSubtitleTextView.setText(String.join(", ", remessa.residuos));
        holder.remessaTriviaTextView.setText(getResiduoTrivia(remessa.residuos));

        holder.remessaDetailsCardView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.remessaCardView.setActivated(isExpanded);
        holder.remessaCardView.setOnClickListener(v -> {
            mExpandedPosition = isExpanded ? -1 : holder.getBindingAdapterPosition();
            notifyItemChanged(previousExpandedPosition);
            notifyItemChanged(holder.getBindingAdapterPosition());
        });
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private String getResiduoTrivia(ArrayList<String> keys) {
        String key = (String) getRandomElement(keys);

        for (Residuo r : Residuo.newResiduoList()) {
            if (r.getDescricao().equals(key)) {
                return (String) getRandomElement(r.getTrivias());
            }
        }

        return null;
    }

    private Object getRandomElement(ArrayList<?> list) {
        int index = ThreadLocalRandom.current().nextInt(0, list.size());
        return list.get(index);
    }
}

