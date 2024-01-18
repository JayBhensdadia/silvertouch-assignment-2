import java.util.*;

class SuffixAutomaton {
    private static class State {
        int length, link;
        Map<Character, Integer> transitions;

        State(int length, int link) {
            this.length = length;
            this.link = link;
            this.transitions = new HashMap<>();
        }
    }

    private List<State> automaton;
    private int last;

    public SuffixAutomaton(String text) {
        automaton = new ArrayList<>();
        automaton.add(new State(0, -1));
        last = 0;

        for (char c : text.toCharArray()) {
            addCharacter(c);
        }
    }

    private void addCharacter(char c) {
        int newLast = automaton.size();
        automaton.add(new State(automaton.get(last).length + 1, 0));

        int p = last;
        while (p != -1 && !automaton.get(p).transitions.containsKey(c)) {
            automaton.get(p).transitions.put(c, newLast);
            p = automaton.get(p).link;
        }

        if (p == -1) {
            automaton.get(newLast).link = 0;
        } else {
            int q = automaton.get(p).transitions.get(c);
            if (automaton.get(p).length + 1 == automaton.get(q).length) {
                automaton.get(newLast).link = q;
            } else {
                int clone = automaton.size();
                automaton.add(new State(automaton.get(p).length + 1, automaton.get(q).link));
                automaton.get(clone).transitions.putAll(automaton.get(q).transitions);

                while (p != -1 && automaton.get(p).transitions.get(c) == q) {
                    automaton.get(p).transitions.put(c, clone);
                    p = automaton.get(p).link;
                }

                automaton.get(q).link = automaton.get(newLast).link = clone;
            }
        }

        last = newLast;
    }

    public List<Integer> findOccurrences(String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        int state = 0;

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (automaton.get(state).transitions.containsKey(c)) {
                state = automaton.get(state).transitions.get(c);
            } else {
                state = -1;
                break;
            }
        }

        if (state != -1) {
            int current = state;
            while (current != 0) {
                occurrences.add(current);
                current = automaton.get(current).link;
            }
            Collections.reverse(occurrences);
        }

        return occurrences;
    }
}

public class Main {
    public static void main(String[] args) {
        String text = "ababc";
        SuffixAutomaton suffixAutomaton = new SuffixAutomaton(text);

        String pattern = "ab";
        List<Integer> occurrences = suffixAutomaton.findOccurrences(pattern);

        if (occurrences.isEmpty()) {
            System.out.println("Pattern not found in the text.");
        } else {
            System.out.println("Pattern found at positions: " + occurrences);
        }
    }
}
