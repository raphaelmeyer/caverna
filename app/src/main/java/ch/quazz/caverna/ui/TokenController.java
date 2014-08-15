package ch.quazz.caverna.ui;

import android.app.Activity;

import ch.quazz.caverna.score.Token;
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.widget.CountingInput;

public class TokenController {

    final static class Item {
        final int id;
        final Token item;

        Item(int id, Token item) {
            this.id = id;
            this.item = item;
        }
    }

    private final Item items[];

    TokenController(final Item items[]) {
        this.items = items;
    }

    void setup(final PlayerScore playerScore, Activity activity) {

        CountingInput.OnCountChangeListener listener = new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(CountingInput input, int count, boolean fromUser) {
                if (fromUser) {
                    playerScore.setCount((Token)input.getTag(), count);
                }
            }
        };

        for (final Item item : items) {
            CountingInput countingInput = (CountingInput)activity.findViewById(item.id);
            countingInput.setTag(item.item);
            countingInput.setCount(playerScore.getCount(item.item));
            countingInput.setOnCountChangeListener(listener);
        }
    }
}