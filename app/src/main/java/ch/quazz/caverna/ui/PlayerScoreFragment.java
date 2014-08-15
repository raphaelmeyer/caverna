package ch.quazz.caverna.ui;

import android.app.Fragment;

import ch.quazz.caverna.score.PlayerScore;

public abstract class PlayerScoreFragment extends Fragment {

    protected PlayerScore playerScore;

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

}
