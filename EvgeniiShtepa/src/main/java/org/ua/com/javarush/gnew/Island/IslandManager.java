package org.ua.com.javarush.gnew.Island;

import org.ua.com.javarush.gnew.model.Animals.Intarfaces.Organism;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class IslandManager {
    private IslandMap islandMap;
    private ScheduledExecutorService scheduled;

    public IslandManager() {
        this.islandMap = IslandMap.getInstance();
        this.scheduled = Executors.newScheduledThreadPool(4);
    }

    public void startSimulation() {
        Cell[][] cells = islandMap.getCells(); //Двоичный массив клеток острова
        for (Cell[] cell : cells) { //Массив клеток острова
            for (Cell currentCell : cell) { // Конкретная клетка острова
                Set<Class<? extends Organism>> classes = currentCell.getResidents().keySet(); // Сет классов в клетке
                for (Class<? extends Organism> clazz : classes) { //Конкретный класс
                    List<Organism> organisms = currentCell.getResidents().get(clazz); //Коллекция животных выбранного класса
                    for (Organism organism : organisms) { // конкретное животное
                        processCell(currentCell, organism); //Запуск последовательности действий конкретного животного
                    }
                }
            }
        }
    }

    private void processCell(Cell currentCell, Organism organism) {
        synchronized (currentCell) {
            int maxStepsCount = organism.getMAX_STEPS_COUNT();

            for (int i = 0; i < maxStepsCount; i++) {
                organism.move(islandMap, currentCell);
            }
            organism.eat(currentCell);


            organism.reproduce(currentCell);

        }
    }
}
