package org.example.newsgenie2409084.Service;

import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Model.Article;
import org.example.newsgenie2409084.Database.DatabaseArticles;
import org.example.newsgenie2409084.Util.SessionManager;

import java.util.*;

public class RecommendArticles {
    private final DatabaseUsers databaseUsers = new DatabaseUsers();
    private final DatabaseArticles databaseArticles = new DatabaseArticles();

    private static final int K = 5;
    private String[] tempCategories = new String[K];
    private int[] categoryValues;
    private String[] allCategories;
    private int currentIndex = 0;

    public RecommendArticles() {
        initializeCategories();
        refreshTempCategories();
    }

    public Article getNextRecommendedArticle(String username) {
        if (currentIndex >= K) {
            refreshTempCategories();
            currentIndex = 0;
        }

        String category = tempCategories[currentIndex++];
        List<Article> articles = databaseArticles.getArticlesByCategory(category);

        List<Integer> skippedArticles = databaseUsers.getSkippedArticles(username);
        if (skippedArticles != null) {
            articles.removeIf(article -> skippedArticles.contains(article.getId()));
        }

        if (!articles.isEmpty()) {
            Random random = new Random();
            return articles.get(random.nextInt(articles.size()));
        }

        return getNextRecommendedArticle(username);
    }

    private void initializeCategories() {
        Map<String, Integer> preferredCategories = databaseUsers.getPreferredCategories(SessionManager.getUsername());

        if (preferredCategories == null || preferredCategories.isEmpty()) {
            allCategories = new String[]{"Default"};
            categoryValues = new int[]{1};
        } else {
            allCategories = preferredCategories.keySet().toArray(new String[0]);
            categoryValues = preferredCategories.values().stream().mapToInt(Integer::intValue).toArray();
        }
    }

    private void refreshTempCategories() {
        double[] cumulativeProbabilities = computeCumulativeProbabilities(categoryValues);

        Random random = new Random();
        for (int i = 0; i < K; i++) {
            double randomValue = random.nextDouble();
            for (int j = 0; j < cumulativeProbabilities.length; j++) {
                if (randomValue <= cumulativeProbabilities[j]) {
                    tempCategories[i] = allCategories[j];
                    break;
                }
            }
        }
    }

    private double[] computeCumulativeProbabilities(int[] frequencies) {
        int totalFrequency = Arrays.stream(frequencies).sum();
        double[] cumulativeProbabilities = new double[frequencies.length];
        double cumulativeSum = 0.0;

        for (int i = 0; i < frequencies.length; i++) {
            cumulativeSum += (double) frequencies[i] / totalFrequency;
            cumulativeProbabilities[i] = cumulativeSum;
        }

        return cumulativeProbabilities;
    }
}
