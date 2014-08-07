package ch.quazz.caverna.ui;

import android.app.Activity;

import ch.quazz.caverna.score.GameItem;
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.widget.CountingInput;

class ItemCountController {

    public final static class ItemCount {
        final int id;
        final GameItem item;

        public ItemCount(int id, GameItem item) {
            this.id = id;
            this.item = item;
        }
    }

    private final PlayerScore playerScore;

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
