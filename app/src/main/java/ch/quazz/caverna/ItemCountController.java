package ch.quazz.caverna;

import android.app.Activity;

import ch.quazz.caverna.widget.CountingInput;

public class ItemCountController {

    public final static class ItemCount {
        protected final int id;
        protected final PlayerScore.Item item;

        public ItemCount(int id, PlayerScore.Item item) {
            this.id = id;
            this.item = item;
        }
    };

    private PlayerScore playerScore;

    public ItemCountController(PlayerScore playerScore, ItemCount[] itemCounts, Activity activity) {
        this.playerScore = playerScore;

        setup(itemCounts, activity);
    }

    private void setup(ItemCount[] itemCounts, Activity activity) {
        for (final ItemCount itemCount : itemCounts) {
            CountingInput inputView = (CountingInput)activity.findViewById(itemCount.id);
            inputView.addOnCountChangeListener(new CountingInput.OnCountChangeListener() {
                @Override
                public void onCountChanged(int count) {
                    playerScore.setCount(itemCount.item, count);
                }
            });
        }

        for (final ItemCount itemCount : itemCounts) {
            CountingInput inputView = (CountingInput)activity.findViewById(itemCount.id);
            inputView.setCount(playerScore.getCount(itemCount.item));
        }
    }
}
