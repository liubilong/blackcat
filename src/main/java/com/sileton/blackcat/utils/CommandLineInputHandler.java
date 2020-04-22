package com.sileton.blackcat.utils;

import com.sileton.blackcat.model.CatItem;
import com.sileton.blackcat.repository.CatRepository;
import com.sileton.blackcat.repository.InMemoryCatRespository;

import java.util.Collection;
import java.util.Collections;

public class CommandLineInputHandler {
    private CatRepository catRepository = new InMemoryCatRespository();

    public void printOptions() {
        System.out.println("\n--- To Do Application ---");
        System.out.println("Please make a choice:");
        System.out.println("(a)ll items");
        System.out.println("(f)ind a specific item");
        System.out.println("(i)nsert a new item");
        System.out.println("(u)pdate an existing item");
        System.out.println("(d)elete an existing item");
        System.out.println("(e)xit");
    }

    public String readInput() {
        return System.console().readLine("> ");
    }

    public void processInput(CommandLineInput input) {
        if (input == null) {
            handleUnknownInput();
        } else {
            switch (input) {
                case FIND_ALL:
                    printAllCatItems();
                    break;
                case FIND_BY_ID:
                    printCatItem();
                    break;
                case INSERT:
                    insertCatItem();
                    break;
                case UPDATE:
                    updateCatItem();
                    break;
                case DELETE:
                    deleteCatItem();
                    break;
                case EXIT:
                    break;
                default:
                    handleUnknownInput();
            }
        }
    }

    private Long askForItemId() {
        System.out.println("Please enter the item ID:");
        String input = readInput();
        return Long.parseLong(input);
    }

    private CatItem askForNewCatAction() {
        CatItem item = new CatItem();
        System.out.println("Please enter the name of the item:");
        item.setName(readInput());
        return item;
    }

    private void printAllCatItems() {
        Collection<CatItem> catItems = catRepository.findAll();

        if (catItems.isEmpty()) {
            System.out.println("Nothing to do. Go relax!");
        } else {
            for (CatItem item : catItems) {
                System.out.println(item);
            }
        }
    }

    private void printCatItem() {
        CatItem item = findCatItem();

        if (item != null) {
            System.out.println(item);
        }
    }

    private CatItem findCatItem() {
        Long id = askForItemId();
        CatItem item = catRepository.findbyid(id);

        if (item == null) {
            System.err.println("To do item with ID " + id + " could not be found.");
        }

        return item;
    }

    private void insertCatItem() {
        CatItem item = askForNewCatAction();
        Long id = catRepository.insert(item);
        System.out.println("Successfully inserted to do item with ID " + id + ".");
    }

    private void updateCatItem() {
        CatItem item = findCatItem();

        if (item != null) {
            System.out.println(item);
            System.out.println("Please enter the name of the item:");
            item.setName(readInput());
            System.out.println("Please enter the done status the item:");
            item.setCompleted(Boolean.parseBoolean(readInput()));
            catRepository.update(item);
            System.out.println("Successfully updated to do item with ID " + item.getId() + ".");
        }
    }

    private void deleteCatItem() {
        CatItem item = findCatItem();

        if (item != null) {
            catRepository.delete(item);
            System.out.println("Successfully deleted to do item with ID " + item.getId() + ".");
        }
    }

    private void handleUnknownInput() {
        System.out.println("Please select a valid option!");
    }
}
