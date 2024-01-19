

import java.util.*;

class TextEditor {
    private String content;

    public void write(String text) {
        content = text;
    }

    public String getContent() {
        return content;
    }

    public Memento save() {
        return new Memento(content);
    }

    public void restore(Memento memento) {
        content = memento.getSavedContent();
    }
}


class Memento {
    private final String savedContent;

    public Memento(String contentToSave) {
        savedContent = contentToSave;
    }

    public String getSavedContent() {
        return savedContent;
    }
}


class History {
    private final List<Memento> mementos = new ArrayList<>();

    public void addMemento(Memento memento) {
        mementos.add(memento);
    }

    public Memento getMemento(int index) {
        return mementos.get(index);
    }
}


public class Main {
    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
        History history = new History();

        
        textEditor.write("Hello, World!");
        history.addMemento(textEditor.save());

        
        textEditor.write("Updated content");
        history.addMemento(textEditor.save());

        
        textEditor.write("Final content");
        history.addMemento(textEditor.save());

        
        textEditor.restore(history.getMemento(1));

        System.out.println("Current content: " + textEditor.getContent());
    }
}
