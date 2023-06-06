package efs.task.collections.game;

import efs.task.collections.data.DataProvider;
import efs.task.collections.entity.Hero;
import efs.task.collections.entity.Town;

import java.util.*;

public class GameLobby {

    public static final String HERO_NOT_FOUND = "Nie ma takiego bohatera ";
    public static final String NO_SUCH_TOWN = "Nie ma takiego miasta ";

    private final DataProvider dataProvider;
    private Map<Town, List<Hero>> playableTownsWithHeroesList;

    public GameLobby() {
        this.dataProvider = new DataProvider();
        this.playableTownsWithHeroesList =
                mapHeroesToStartingTowns(dataProvider.getTownsList(), dataProvider.getHeroesSet());
    }

    public Map<Town, List<Hero>> getPlayableTownsWithHeroesList() {
        return playableTownsWithHeroesList;
    }

    // Dodać miasta i odpowiadających im bohaterów z DLC gry do mapy dostępnych
    // miast - playableTownsWithHeroesList, tylko jeżeli jeszcze się na niej nie znajdują.
    public void enableDLC() {
        Set<Town> dlcTowns = new HashSet<>(dataProvider.getDLCTownsList());

        for (Town dlcTown : dlcTowns) {
            if (!playableTownsWithHeroesList.containsKey(dlcTown)) {
                List<Hero> heroes = new ArrayList<>();
                for (Hero hero : dataProvider.getDLCHeroesSet()) {
                    if (dlcTown.getStartingHeroClasses().contains(hero.getHeroClass())) {
                        heroes.add(hero);
                    }
                }
                playableTownsWithHeroesList.put(dlcTown, heroes);
            }
        }
    }


    // Usunąć miasta i odpowiadających im bohaterów z DLC gry z mapy dostępnych
    // miast - playableTownsWithHeroesList.
    public void disableDLC() {
        Set<Town> dlcTowns = new HashSet<>(dataProvider.getDLCTownsList());

        for (Town dlcTown : dlcTowns) {
            playableTownsWithHeroesList.remove(dlcTown);
        }
    }

    // Sprawdza czy mapa playableCharactersByTown zawiera dane miasto.
    // Jeśli tak zwróć listę bohaterów z tego miasta.
    // Jeśli nie rzuć wyjątek NoSuchElementException z wiadomością NO_SUCH_TOWN + town.getName()
    public List<Hero> getHeroesFromTown(Town town) {
        if (playableTownsWithHeroesList.containsKey(town)) {
            return playableTownsWithHeroesList.get(town);
        } else {
            throw new NoSuchElementException(NO_SUCH_TOWN + town.getTownName());
        }
    }

    // Metoda powinna zwracać mapę miast w kolejności alfabetycznej z odpowiadającymi im bohaterami.
    // Każde z miast charakteryzuje się dwoma klasami bohaterów dostępnymi dla tego miasta - Town.startingHeroClass.
    // Mapa ma zawierać parę klucz-wartość gdzie klucz: miasto, wartość: lista bohaterów;
    public Map<Town, List<Hero>> mapHeroesToStartingTowns(List<Town> availableTowns, Set<Hero> availableHeroes) {
        Map<Town, List<Hero>> townHeroMap = new TreeMap<>();

        for (Town town : availableTowns) {
            List<Hero> heroes = new ArrayList<>();
            for (String heroClass : town.getStartingHeroClasses()) {
                for (Hero hero : availableHeroes) {
                    if (hero.getHeroClass().equals(heroClass)) {
                        heroes.add(hero);
                    }
                }
            }
            townHeroMap.put(town, heroes);
        }

        return townHeroMap;
    }

    // metoda zwraca wybranego bohatera na podstawie miasta z którego pochodzi i imienia.
    // Jeżeli istnieje, usuwa go z listy dostępnych bohaterów w danym mieście i zwraca bohatera.
    // Jeżeli nie ma go na liście dostępnych bohaterów, rzuca NoSuchElementException z wiadomością HERO_NOT_FOUND + name
    public Hero selectHeroByName(Town heroTown, String name) {
        List<Hero> heroes = playableTownsWithHeroesList.get(heroTown);
        if (heroes != null) {
            for (Hero hero : heroes) {
                if (hero.getName().equals(name)) {
                    heroes.remove(hero);
                    return hero;
                }
            }
        }
        throw new NoSuchElementException(HERO_NOT_FOUND + name);
    }
}