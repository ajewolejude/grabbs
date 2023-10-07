package com.example.grabbs.util;

import com.example.grabbs.model.Action;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActionSorter {

    public static void sortByCreatedDate(List<Action> actions) {
        // Use Collections.sort with a custom comparator
        Collections.sort(actions, Comparator.comparing(Action::getCreatedDate));
    }
}
