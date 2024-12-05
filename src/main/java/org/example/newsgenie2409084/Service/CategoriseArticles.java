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
            "surgery", "vaccine", "diagnosis", "therapy", "illness", "virus", "epidemic", "pandemic", "mental health",
            "dental", "nursing", "clinical", "healthcare", "recovery", "prevention", "first aid", "cardiology",
            "orthopedic", "pharmacy", "gynecology", "pediatrics", "neurology", "dermatology", "immunology", "oncology",
            "radiology", "allergy", "antibiotics", "diabetes", "hypertension", "vaccination", "nutritionist", "psychology",
            "public health", "infection", "surgeon", "transplant", "alternative medicine", "therapist", "rehabilitation",
            "well-being", "emergency", "medical research", "immunity", "clinical trials", "hospitalization",
            "infectious disease", "chronic illness", "general health", "respiratory", "gastroenterology",
            "therapeutic", "surgical", "anatomy", "patient care", "healthcare system", "pharmaceutical",
            "medical advancement", "reproductive health", "medical technology", "diagnostic tools",
            "primary care", "health policy", "mental wellness", "addiction recovery", "WHO"

        ));
        keywordMap.put("Technology", Arrays.asList(
            "technology", "AI", "software", "computer", "robot", "machine learning", "hardware", "innovation",
            "cybersecurity", "blockchain", "cloud", "data science", "quantum computing", "apps", "smartphone", "5G",
            "automation", "artificial intelligence", "virtual reality", "augmented reality", "internet of things",
            "digital transformation", "deep learning", "neural networks", "big data", "database", "information systems",
            "coding", "programming", "biotechnology", "tech startups", "wearables", "gadgets", "cryptography",
            "tech trends", "VR headset", "nanotechnology", "semiconductors", "AR games", "algorithm", "CPU",
            "AI ethics", "cyber attack", "technology innovation", "engineering", "digital privacy", "data mining",
            "tech review", "internet security", "tech ecosystem", "software development", "internet", "data", "VR",
            "AR", "development", "computing", "networking", "IoT", "web development", "UX", "encryption", "ubuntu",
            "wearable technology", "UI", "smart devices", "drones", "virtual assistant", "technology trends",
            "search engine", "server", "devops", "operating system", "samsung", "apple", "ios", "android", "linux"

        ));
        keywordMap.put("Politics", Arrays.asList(
            "politics", "election", "government", "president", "vote", "senate", "parliament", "campaign", "diplomacy",
            "policy", "political party", "congress", "debate", "legislation", "lawmaker", "referendum", "minister",
            "prime minister", "governor", "mayor", "council", "democracy", "dictatorship", "autocracy", "supreme court",
            "jurisdiction", "legislature", "nationalism", "liberalism", "conservatism", "socialism", "capitalism", "putin",
            "protest", "constitution", "treaty", "sanctions", "taxation", "foreign relations", "judiciary", "statecraft",
            "political science", "election fraud", "opposition", "ruling party", "manifesto", "coalition", "propaganda",
            "parliamentary debates", "voter turnout", "civil unrest", "human rights", "political ideology", "foreign policy",
            "social movements", "public policy", "executive order", "international relations", "electoral reform", "civil rights",
            "cabinet member", "political agenda", "bipartisanship", "state governor", "political leader", "policy analysis",
            "campaign rally", "global diplomacy", "social justice", "freedom of speech", "diplomatic ties", "trade agreement",
            "lobbying efforts", "judicial review", "treaty negotiation", "constitutional law", "voting rights", "trump", "biden"

        ));
        keywordMap.put("Business", Arrays.asList(
            "business", "market", "economy", "finance", "investment", "entrepreneur", "startup", "stock", "share",
            "revenue", "profit", "loss", "trading", "banking", "corporate", "merger", "acquisition", "sales",
            "marketing", "customer", "retail", "commerce", "strategy", "supply chain", "global trade", "e-commerce",
            "partnership", "business plan", "business development", "business model", "consumer behavior", "invoice",
            "negotiation", "small business", "business analysis", "economic growth", "franchise", "import", "export",
            "tariff", "logistics", "branding", "venture capital", "financial report", "investment fund", "cash flow",
            "business valuation", "productivity", "dividend", "business ethics", "global economy", "taxation policy",
            "financial market", "IPO launch", "market research", "startup growth", "equity funding", "trade balance",
            "corporate culture", "real estate", "business expansion", "capital market", "microfinance projects", "economic trends",
            "wealth management", "business merger", "foreign exchange", "revenue generation", "small enterprise", "cost analysis"

        ));
        keywordMap.put("Entertainment", Arrays.asList(
            "entertainment", "movie", "music", "celebrity", "theater", "actor", "actress", "show", "performance",
            "comedy", "drama", "action", "concert", "festival", "streaming", "series", "film", "award", "event",
            "production", "cinema", "director", "script", "reality show", "blockbuster", "soundtrack", "video",
            "movie premiere", "broadway", "musical", "visual effects", "animation", "movie trailer", "genre", "fiction",
            "documentary", "box office", "festival event", "red carpet", "celebrity gossip", "live performance", "stage design",
            "costume design", "sound design", "Hollywood actor", "Bollywood film", "trending music", "pop culture", "streaming platform",
            "TV series", "cinematic masterpiece", "entertainment news", "actor's interview", "music album", "singing competition",
            "artistic drama", "festival celebration", "movie production", "performance art", "theater production", "song playlist",
            "concert tour", "celebrity award", "movie director", "film festival", "entertainment trends"

        ));
        keywordMap.put("Environment", Arrays.asList(
            "environment", "climate", "pollution", "wildlife", "nature", "sustainability", "renewable", "ecosystem",
            "biodiversity", "conservation", "forest", "recycling", "waste", "carbon", "emissions", "green", "ozone",
            "global warming", "natural", "habitat", "solar", "energy", "organic", "clean", "deforestation",
            "fossil fuels", "eco-friendly", "sustainable development", "environmental impact", "composting",
            "reforestation", "species extinction", "habitat loss", "climate action", "air quality", "water pollution",
            "plastic waste", "environmental awareness", "sustainable agriculture", "ocean conservation", "rainforest",
            "climate justice", "ecotourism", "renewable energy", "climate solutions", "zero waste", "energy efficiency",
            "marine life", "natural resources", "greenhouse gases", "wildlife habitat", "tree planting", "nature reserve",
            "pollution control", "water conservation", "climate change", "eco-tourism", "plastic recycling", "environment protection",
            "carbon footprint", "solar energy", "eco-friendly living", "waste management", "wildlife conservation", "eco activism"

        ));
        keywordMap.put("Crime", Arrays.asList(
            "crime", "police", "arrest", "theft", "investigation", "murder", "robbery", "fraud", "cybercrime", "law",
            "assault", "prison", "violence", "witness", "evidence", "forensics", "justice", "court", "homicide",
            "trial", "burglary", "suspect", "victim", "sentence", "criminal", "prosecution", "misconduct", "conviction",
            "bail", "drug trafficking", "organized crime", "terrorism", "money laundering", "espionage", "kidnapping",
            "gang", "domestic violence", "juvenile crime", "parole", "rape", "sexual harassment", "vandalism",
            "embezzlement", "scam", "identity theft", "extortion", "blackmail", "arson", "smuggling", "criminal investigation",
            "white-collar crime", "cyber attack", "crime statistics", "forensic analysis", "fraud prevention", "gang violence",
            "criminal trial", "crime witness", "criminal behavior", "court evidence", "crime documentary", "online fraud"

        ));
        keywordMap.put("Education", Arrays.asList(
            "education", "school", "university", "student", "teacher", "learning", "curriculum", "classroom", "degree",
            "scholarship", "tuition", "study", "lecture", "assignment", "exam", "research", "academic", "college",
            "graduate", "faculty", "campus", "admission", "course", "training", "pedagogy", "teaching", "syllabus",
            "online learning", "higher education", "kindergarten", "preschool", "alumni", "library", "diploma",
            "assessment", "grading", "philosophy of education", "education policy", "distance learning", "educational",
            "extracurricular", "school board", "educational reform", "adult education", "lifelong learning",
            "special education", "public school", "private school", "vocational training", "STEM education", "e-learning platforms",
            "education tools", "school activities", "classroom technology", "student performance", "college degree"

        ));
        keywordMap.put("Weather", Arrays.asList(
            "weather", "rain", "storm", "temperature", "forecast", "climate", "snow", "hurricane", "wind", "flood",
            "heatwave", "thunderstorm", "cold", "fog", "drought", "humidity", "tornado", "monsoon", "lightning", "hail",
            "cloudy", "sunny", "freezing", "season", "winter", "summer", "autumn", "spring", "pressure", "atmosphere",
            "tropical", "polar", "cyclone", "typhoon", "meteorology", "blizzard", "weather patterns", "drizzle", "dew",
            "ice storm", "precipitation", "frost", "clear skies", "barometer", "air quality", "heat index", "UV index",
            "temperature drop", "storm surge", "weather conditions", "climate zones", "storm warning", "rain forecast",
            "extreme weather", "daily temperature", "cold front", "weather radar", "seasonal changes", "weather data"

        ));
        keywordMap.put("Science", Arrays.asList(
                "science", "research", "experiment", "physics", "biology", "chemistry", "astronomy", "ecology", "geology",
                "botany", "zoology", "genetics", "evolution", "microscope", "laboratory", "discovery", "analysis", "theory",
                "hypothesis", "scientist", "quantum", "space", "climate change", "energy", "nanotechnology", "particle physics",
                "biochemistry", "neuroscience", "anthropology", "marine biology", "astrophysics", "ecosystem", "geophysics",
                "meteorology", "scientific method", "scientific paper", "cell biology", "natural sciences", "bioinformatics",
                "environmental science", "materials science", "microbiology", "paleontology", "scientific innovation", "biophysics",
                "seismology", "environmental studies", "genome sequencing", "space exploration", "molecular biology",
                "organic chemistry", "biomedical research", "biotechnology tools", "scientific experiment", "science outreach",
                "applied sciences", "physical sciences", "theoretical physics", "DNA analysis", "genetic engineering", "data analysis",
                "laboratory techniques", "climate modeling", "oceanography", "research grant", "academic journal", "science fair",
                "planetary science", "earth science", "renewable energy", "science education", "chemical reactions", "ecological balance",
                "scientific discovery", "robotics research", "innovation hub", "space telescope", "solar system"
        ));
        keywordMap.put("Sports", Arrays.asList(
                "sports", "game", "football", "cricket", "athlete", "tennis", "basketball", "soccer", "rugby", "volleyball",
                "swimming", "track", "field", "olympics", "training", "coach", "stadium", "goal", "match", "tournament",
                "league", "championship", "marathon", "fitness", "hockey", "athletics", "gymnastics", "team", "scoring",
                "playoffs", "competition", "medal", "scoreboard", "sportsmanship", "cycling", "karate", "judo", "wrestling",
                "skiing", "golf", "triathlon", "badminton", "table tennis", "curling", "bowling", "doping", "esports", "F1",
                "teamwork", "basketball court", "goalkeeper", "football match", "track events", "field events", "sports coach",
                "national team", "fitness training", "athletic performance", "sports equipment", "game strategy", "team captain",
                "soccer league", "trophy ceremony", "game day", "baseball pitch", "tennis tournament", "hiking trail",
                "sports analyst", "physical training", "sports media", "sports club", "yoga training", "swimming competition",
                "extreme sports", "adventure sports", "winter sports", "Olympic medal", "match analysis", "stadium crowd",
                "record-breaking", "sports history", "f1", "formula 1", "Verstappen", "Hamilton", "test match"

        ));
    }

    public String categorise(String title, String preview) {
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