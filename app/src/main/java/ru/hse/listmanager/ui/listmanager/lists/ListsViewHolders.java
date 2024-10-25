package ru.hse.listmanager.ui.listmanager.lists;

import androidx.recyclerview.widget.RecyclerView;
import lombok.Getter;
import ru.hse.listmanager.databinding.ItemPostTripBinding;

/**
 * Main FeedViewHolders class used as namespace for ViewHolders definition.
 */
public class ListsViewHolders {
  /**
   * Creates PostViewHolder to appearing Post in RecyclerView container.
   */
  @Getter
  public static class FeedPostViewHolder extends RecyclerView.ViewHolder {

    private final ItemPostTripBinding binding;

    FeedPostViewHolder(ItemPostTripBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
