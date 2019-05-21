package app.kuwas.android.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

import app.kuwas.android.R;

/**
 * Created by BrookMG on 5/20/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class TagsChipRecyclerAdapter extends RecyclerView.Adapter<TagsChipRecyclerAdapter.TagViewHolder> {

    private List<String> tags;

    public TagsChipRecyclerAdapter(List<String> tags) {
        this.tags = tags;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_chip_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.tag.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    class TagViewHolder extends RecyclerView.ViewHolder {
        Chip tag;

        TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tag_chip);
        }
    }

}
