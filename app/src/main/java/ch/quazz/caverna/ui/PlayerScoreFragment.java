package ch.quazz.caverna.ui;

import android.app.Fragment;

import ch.quazz.caverna.score.PlayerScore;

abstract class PlayerScoreFragment extends Fragment {

    protected PlayerScore playerScore;

    void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

}
