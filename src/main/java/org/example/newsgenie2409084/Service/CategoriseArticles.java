package org.example.newsgenie2409084.Service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriseArticles {

    private final Map<String, List<String>> keywordMap;

    public CategoriseArticles() {
        keywordMap = new HashMap<>();
        keywordMap.put("Health", Arrays.asList(
            "health", "medicine", "doctor", "hospital", "disease", "wellness", "nutrition", "fitness", "treatment",
            "surgery", "vaccine", "diagnosis", "therapy", "virus", "pandemic", "mental health", "emergency", "recovery",
            "public health", "medical research", "immunity", "epidemic", "clinical trials", "hospitalization",
            "infectious disease", "chronic illness", "cardiology", "orthopedic", "pediatrics", "dermatology", "neuro",
            "immunization", "general health", "respiratory", "gastroenterology", "oncology", "orthopedics",
            "psychology", "therapeutic", "surgical", "anatomy", "patient care", "healthcare system", "pharmaceutical",
            "medical advancement", "reproductive health", "nursing", "medical technology", "diagnostic tools",
            "primary care", "health policy", "dental care", "mental wellness", "addiction recovery"
        ));
        keywordMap.put("Technology", Arrays.asList(
            "technology", "AI", "software", "computer", "robot", "machine learning", "hardware", "innovation",
            "cybersecurity", "blockchain", "cloud", "data science", "quantum computing", "apps", "smartphone", "5G",
            "automation", "artificial intelligence", "virtual reality", "augmented reality", "internet of things",
            "digital transformation", "deep learning", "neural networks", "big data", "database", "information systems",
            "coding", "programming", "biotechnology", "tech startups", "wearables", "gadgets", "cryptography",
            "tech trends", "VR headset", "nanotechnology", "semiconductors", "AR games", "algorithm", "CPU",
            "AI ethics", "cyber attack", "technology innovation", "engineering", "digital privacy", "data mining",
            "tech review", "internet security", "tech ecosystem", "software development"
        ));
        keywordMap.put("Politics", Arrays.asList(
            "politics", "government", "election", "senate", "parliament", "president", "policy", "campaign",
            "diplomacy", "minister", "prime minister", "democracy", "law", "constitution", "protest", "public policy",
            "legislation", "civil rights", "political party", "congress", "judiciary", "governance",
            "parliamentary debates", "political agenda", "statecraft", "federalism", "coalition", "executive order",
            "treaty", "international relations", "social justice", "cabinet", "political leader", "caucus",
            "lobbying", "political campaign", "bipartisanship", "referendum", "electoral reform", "human rights",
            "political ideology", "foreign policy", "social movements", "voter turnout", "civil unrest",
            "nationalism", "policy analysis"
        ));
        keywordMap.put("Business", Arrays.asList(
            "business", "market", "economy", "finance", "investment", "entrepreneur", "stock", "profit", "trade",
            "banking", "revenue", "sales", "corporate", "commerce", "e-commerce", "growth", "merger", "strategy",
            "financial market", "startups", "business expansion", "shares", "fiscal policy", "monetary policy",
            "global economy", "wealth", "business model", "funding", "IPO", "dividends", "acquisitions", "logistics",
            "supply chain", "consumer behavior", "market trend", "economist", "business management", "taxation",
            "microfinance", "trade agreement", "real estate", "equity", "capital market", "recession", "inflation",
            "foreign exchange", "business innovation", "corporate culture", "business ethics", "retail"
        ));
        keywordMap.put("Entertainment", Arrays.asList(
            "entertainment", "movie", "music", "theater", "celebrity", "show", "performance", "concert", "festival",
            "cinema", "series", "award", "drama", "comedy", "animation", "soundtrack", "blockbuster", "streaming",
            "documentary", "reality TV", "trending", "pop culture", "video games", "binge-watching", "musical",
            "actor", "actress", "director", "artistic", "singing competition", "stage play", "box office", "album",
            "trailer", "premiere", "TV show", "production", "Hollywood", "Bollywood", "red carpet", "celebration"
        ));
        keywordMap.put("Environment", Arrays.asList(
            "environment", "climate", "pollution", "wildlife", "nature", "sustainability", "conservation", "recycling",
            "biodiversity", "forest", "green", "eco-friendly", "habitat", "solar", "deforestation", "carbon",
            "emissions", "global warming", "environmental protection", "renewable energy", "climate action",
            "clean energy", "waste management", "plastic pollution", "ecotourism", "reforestation", "natural resources",
            "eco-system", "energy efficiency", "water conservation", "marine life", "ocean pollution", "greenhouse gases"
        ));
        keywordMap.put("Crime", Arrays.asList(
            "crime", "police", "arrest", "theft", "murder", "fraud", "violence", "prison", "investigation",
            "cybercrime", "law", "homicide", "justice", "assault", "prosecution", "robbery", "gang", "terrorism",
            "forgery", "blackmail", "kidnapping", "money laundering", "corruption", "burglary", "smuggling",
            "domestic violence", "white-collar crime", "criminal investigation", "vandalism", "organized crime",
            "drug trafficking", "arson", "shoplifting", "illegal"
        ));
        keywordMap.put("Education", Arrays.asList(
            "education", "school", "university", "student", "teacher", "learning", "curriculum", "classroom",
            "research", "scholarship", "faculty", "campus", "admission", "academic", "teaching", "online learning",
            "e-learning", "education policy", "degree", "exams", "grades", "primary education", "secondary education",
            "higher education", "philosophy of education", "education reform", "tutoring", "homework", "educational tools",
            "STEM education", "extracurricular", "learning disabilities", "student loans"
        ));
        keywordMap.put("Weather", Arrays.asList(
            "weather", "rain", "storm", "temperature", "forecast", "climate", "snow", "hurricane", "flood",
            "heatwave", "thunderstorm", "humidity", "tornado", "monsoon", "drought", "fog", "winter", "season",
            "wind speed", "sunshine", "chilly", "cloudy", "precipitation", "meteorology", "extreme weather",
            "severe weather", "cold front", "heat advisory", "UV index", "tropical storm"
        ));
    }

    public String categorize(String title, String preview) {
        String content = (title + " " + preview).toLowerCase();
        Map<String, Integer> scores = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : keywordMap.entrySet()) {
            String category = entry.getKey();
            List<String> keywords = entry.getValue();
            int score = calculateKeywordScore(content, keywords);
            if (score > 0) {
                scores.put(category, score);
            }
        }

        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Other");
    }

    private int calculateKeywordScore(String content, List<String> keywords) {
        int score = 0;
        for (String keyword : keywords) {
            if (content.contains(keyword)) {
                score++;
            }
        }
        return score;
    }
}
