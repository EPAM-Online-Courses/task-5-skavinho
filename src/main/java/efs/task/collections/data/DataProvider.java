package efs.task.collections.data;

import efs.task.collections.entity.Hero;
import efs.task.collections.entity.Town;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataProvider {

    public static final String DATA_SEPARATOR = ",";

    public List<Town> getTownsList() {
        List<Town> towns = new ArrayList<>();

        for (String townString : Data.baseTownsArray) {
            String[] townData = townString.split(DATA_SEPARATOR);
            String townName = townData[0].trim();
            List<String> startingHeroClasses = getStartingHeroClasses(townData);

            Town town = new Town(townName, startingHeroClasses);
            towns.add(town);
        }

        return towns;
    }

    public List<Town> getDLCTownsList() {
        List<Town> dlcTowns = new ArrayList<>();

        for (String townString : Data.dlcTownsArray) {
            String[] townData = townString.split(DATA_SEPARATOR);
            String townName = townData[0].trim();
            List<String> startingHeroClasses = getStartingHeroClasses(townData);

            Town town = new Town(townName, startingHeroClasses);
            dlcTowns.add(town);
        }

        return dlcTowns;
    }

    public Set<Hero> getHeroesSet() {
        Set<Hero> heroes = new HashSet<>();

        for (String heroString : Data.baseCharactersArray) {
            String[] heroData = heroString.split(DATA_SEPARATOR);
            String heroName = heroData[0].trim();
            String heroClass = heroData[1].trim();

            Hero hero = new Hero(heroName, heroClass);
            heroes.add(hero);
        }

        return heroes;
    }

    public Set<Hero> getDLCHeroesSet() {
        Set<Hero> dlcHeroes = new HashSet<>();

        for (String heroString : Data.dlcCharactersArray) {
            String[] heroData = heroString.split(DATA_SEPARATOR);
            String heroName = heroData[0].trim();
            String heroClass = heroData[1].trim();

            Hero hero = new Hero(heroName, heroClass);
            dlcHeroes.add(hero);
        }

        return dlcHeroes;
    }

    private List<String> getStartingHeroClasses(String[] townData) {
        List<String> startingHeroClasses = new ArrayList<>();

        for (int i = 1; i < townData.length; i++) {
            startingHeroClasses.add(townData[i].trim());
        }

        return startingHeroClasses;
    }
}