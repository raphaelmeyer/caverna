package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Tile;
import ch.quazz.caverna.score.Token;

public class BonusFragment extends PlayerScoreFragment {


    private final TokenController.Item BonusItems[] = {
            new TokenController.Item(R.id.stone, Token.Stone),
            new TokenController.Item(R.id.ore, Token.Ore),
            new TokenController.Item(R.id.weapons, Token.Weapons),
            new TokenController.Item(R.id.adjacent_dwellings, Token.AdjacentDwellings),
    };

    private static final TileAdapter.Item BonusTiles[] = {
            new TileAdapter.Item(Tile.WeavingParlor, R.drawable.weaving_parlor),
            new TileAdapter.Item(Tile.MilkingParlor, R.drawable.milking_parlor),
            new TileAdapter.Item(Tile.StateParlor, R.drawable.state_parlor),
            new TileAdapter.Item(Tile.HuntingParlor, R.drawable.hunting_parlor),
            new TileAdapter.Item(Tile.BeerParlor, R.drawable.beer_parlor),
            new TileAdapter.Item(Tile.BlacksmithingParlor, R.drawable.blacksmithing_parlor),

            new TileAdapter.Item(Tile.StoneStorage, R.drawable.stone_storage),
            new TileAdapter.Item(Tile.OreStorage, R.drawable.ore_storage),
            new TileAdapter.Item(Tile.SparePartStorage, R.drawable.spare_part_storage),
            new TileAdapter.Item(Tile.MainStorage, R.drawable.main_storage),
            new TileAdapter.Item(Tile.WeaponStorage, R.drawable.weapon_storage),
            new TileAdapter.Item(Tile.SuppliesStorage, R.drawable.supplies_storage),

            new TileAdapter.Item(Tile.BroomChamber, R.drawable.broom_chamber),
            new TileAdapter.Item(Tile.TreasureChamber, R.drawable.treasure_chamber),
            new TileAdapter.Item(Tile.FoodChamber, R.drawable.food_chamber),
            new TileAdapter.Item(Tile.PrayerChamber, R.drawable.prayer_chamber),
            new TileAdapter.Item(Tile.WritingChamber, R.drawable.writing_chamber),
            new TileAdapter.Item(Tile.FodderChamber, R.drawable.fodder_chamber)
    };

    private final TileController tileController;
    private final TokenController tokenController;

    public BonusFragment() {
        tileController = new TileController(BonusTiles);
        tokenController = new TokenController(BonusItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bonus, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        optionalVisibility();

        tileController.setup(playerScore, getActivity(), R.id.bonus_tiles);
        tileController.setOnSelectionChangeListener(new TileController.OnSelectionChangeListener() {
            @Override
            public void onSelectionChanged() {
                optionalVisibility();
            }
        });

        tokenController.setup(playerScore, getActivity());
    }

    private void optionalVisibility() {
        View view = getActivity().findViewById(R.id.stone);
        if (playerScore.has(Tile.StoneStorage)) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }

        view = getActivity().findViewById(R.id.ore);
        if (playerScore.has(Tile.OreStorage)) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }

        boolean showWeapons = playerScore.has(Tile.WeaponStorage) ||
                playerScore.has(Tile.SuppliesStorage) ||
                playerScore.has(Tile.PrayerChamber);

        view = getActivity().findViewById(R.id.weapons);
        if (showWeapons) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }

        view = getActivity().findViewById(R.id.adjacent_dwellings);
        if (playerScore.has(Tile.StateParlor)) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
