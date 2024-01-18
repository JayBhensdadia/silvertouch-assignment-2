import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class SuffixAutomaton {
    private static class State {
        Map<Character, State> transitions;
        State link;
        int length;

        public State(int length) {
            this.length = length;
            this.transitions = new HashMap<>();
            this.link = null;
        }
    }

    private State root;
    private State last;

    public SuffixAutomaton(String text) {
        root = new State(0);
        last = root;

        for (char c : text.toCharArray()) {
            extendAutomaton(c);
        }

        // Add a terminal state with link to itself
        extendAutomaton('$');
    }

    private void extendAutomaton(char c) {
        State newState = new State(last.length + 1);
        State current = last;

        while (current != null && !current.transitions.containsKey(c)) {
            current.transitions.put(c, newState);
            current = current.link;
        }

        if (current == null) {
            newState.link = root;
        } else {
            State next = current.transitions.get(c);
            if (current.length + 1 == next.length) {
                newState.link = next;
            } else {
                State split = new State(current.length + 1);
                split.transitions.putAll(next.transitions);
                split.link = next.link;
                next.link = split;
                newState.link = split;

                while (current != null && current.transitions.get(c) == next) {
                    current.transitions.put(c, split);
                    current = current.link;
                }
            }
        }

        last = newState;
    }

    public Set<String> getAllSubstrings() {
        Set<String> substrings = new HashSet<>();
        dfs(root, "", substrings);
        return substrings;
    }

    private void dfs(State state, String currentSubstring, Set<String> substrings) {
        substrings.add(currentSubstring);

        for (Map.Entry<Character, State> entry : state.transitions.entrySet()) {
            char transitionChar = entry.getKey();
            State nextState = entry.getValue();
            dfs(nextState, currentSubstring + transitionChar, substrings);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String text = "banana";
        SuffixAutomaton suffixAutomaton = new SuffixAutomaton(text);

        Set<String> substrings = suffixAutomaton.getAllSubstrings();

        System.out.println("All Substrings:");
        for (String substring : substrings) {
            System.out.println(substring);
        }
    }
}
