package p94;

import java.util.*;
import java.util.stream.IntStream;

class SkipGramWordEmbeddings {

    private static final int EMBEDDING_SIZE = 50;
    private static final double LEARNING_RATE = 0.01;
    private static final int WINDOW_SIZE = 2;
    private static final int EPOCHS = 5;

    private Map<String, double[]> wordEmbeddings = new HashMap<>();
    private Map<String, Integer> wordIndex = new HashMap<>();
    private List<String> words = new ArrayList<>();

    public void train(String[] corpus) {
        preprocessCorpus(corpus);

        int vocabSize = words.size();

        
        Random rand = new Random();
        for (String word : words) {
            double[] embedding = IntStream.range(0, EMBEDDING_SIZE)
                    .mapToDouble(i -> rand.nextDouble() - 0.5)
                    .toArray();
            wordEmbeddings.put(word, embedding);
        }

        
        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            for (int i = 0; i < corpus.length; i++) {
                String targetWord = corpus[i];
                for (int j = Math.max(0, i - WINDOW_SIZE); j < Math.min(corpus.length, i + WINDOW_SIZE + 1); j++) {
                    if (i != j) {
                        String contextWord = corpus[j];
                        trainPair(targetWord, contextWord);
                    }
                }
            }
        }
    }

    private void trainPair(String targetWord, String contextWord) {
        double[] targetEmbedding = wordEmbeddings.get(targetWord);
        double[] contextEmbedding = wordEmbeddings.get(contextWord);

        
        for (int i = 0; i < EMBEDDING_SIZE; i++) {
            targetEmbedding[i] -= LEARNING_RATE * contextEmbedding[i];
            contextEmbedding[i] -= LEARNING_RATE * targetEmbedding[i];
        }
    }

    private void preprocessCorpus(String[] corpus) {
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(corpus));
        words.addAll(uniqueWords);

        for (int i = 0; i < words.size(); i++) {
            wordIndex.put(words.get(i), i);
        }
    }

    public double[] getWordEmbedding(String word) {
        return wordEmbeddings.get(word);
    }

    public static void main(String[] args) {
        SkipGramWordEmbeddings skipGramModel = new SkipGramWordEmbeddings();

        
        String[] corpus = {"deep", "learning", "is", "fun", "and", "challenging"};

        
        skipGramModel.train(corpus);

        
        double[] embedding = skipGramModel.getWordEmbedding("learning");
        System.out.println("Word embedding for 'learning': " + Arrays.toString(embedding));
    }
}
