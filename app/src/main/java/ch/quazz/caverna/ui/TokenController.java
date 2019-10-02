package ch.quazz.caverna.ui;

import android.app.Activity;

import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.score.Token;
import ch.quazz.caverna.widget.CountingInput;

class TokenController {

    static final class Item {
        final int id;
        final Token itemName;

        Item(int id, Token itemName) {
            this.id = id;
            this.itemName = itemName;
        }
    }

    private final Item[] items;

    TokenController(final Item[] items) {
        this.items = items;
    }

    void setup(final PlayerScore playerScore, Activity activity) {

        CountingInput.OnCountChangeListener listener = new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(CountingInput input, int count, boolean fromUser) {
                if (fromUser) {
                    playerScore.setCount((Token) input.getTag(), count);
                }
            }
        };

        for (final Item item : items) {
            CountingInput countingInput = activity.findViewById(item.id);
            countingInput.setTag(item.itemName);
            countingInput.setCount(playerScore.getCount(item.itemName));
            countingInput.setOnCountChangeListener(listener);
        }
    }
}