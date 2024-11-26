package org.example.newsgenie2409084;

import java.util.Arrays;
import java.util.List;

public class CategoriseArticles {

    private final List<String> healthKeywords = Arrays.asList(
            "health", "medicine", "doctor", "hospital", "disease", "wellness", "nutrition", "fitness", "treatment",
            "surgery", "vaccine", "diagnosis", "therapy", "illness", "virus", "epidemic", "pandemic", "mental health",
            "dental", "nursing", "clinical", "healthcare", "recovery", "prevention", "first aid", "cardiology",
            "orthopedic", "pharmacy", "gynecology", "pediatrics", "neurology", "dermatology", "immunology", "oncology",
            "radiology", "allergy", "antibiotics", "diabetes", "hypertension", "vaccination", "nutritionist",
            "psychology", "public health", "infection", "surgeon", "transplant", "alternative medicine", "therapist",
            "rehabilitation", "well-being", "emergency"
    );

    private final List<String> technologyKeywords = Arrays.asList(
            "technology", "AI", "software", "computer", "robot", "machine learning", "hardware", "innovation", "gadget",
            "smartphone", "internet", "cybersecurity", "blockchain", "cloud", "programming", "coding", "data", "VR",
            "AR", "virtual reality", "artificial intelligence", "automation", "development", "computing", "networking",
            "engineering", "IoT", "quantum computing", "apps", "web development", "data science", "big data", "UX",
            "encryption", "algorithm", "5G", "wearable technology", "augmented reality", "UI", "smart devices",
            "drones", "nanotechnology", "virtual assistant", "technology trends", "search engine", "server", "database",
            "technology innovation", "devops", "operating system"
    );

    private final List<String> politicsKeywords = Arrays.asList(
            "politics", "election", "government", "president", "vote", "senate", "parliament", "campaign", "diplomacy",
            "policy", "political party", "congress", "debate", "legislation", "lawmaker", "referendum", "minister",
            "prime minister", "governor", "mayor", "council", "democracy", "dictatorship", "autocracy", "supreme court",
            "jurisdiction", "legislature", "nationalism", "liberalism", "conservatism", "socialism", "capitalism",
            "protest", "constitution", "treaty", "sanctions", "taxation", "foreign relations", "judiciary",
            "political science", "election fraud", "opposition", "ruling party", "manifesto", "coalition", "propaganda",
            "parliamentary", "voter turnout", "trump", "biden", "secretary"
    );

    private final List<String> businessKeywords = Arrays.asList(
            "business", "market", "economy", "finance", "investment", "entrepreneur", "startup", "stock", "share",
            "revenue", "profit", "loss", "trading", "banking", "corporate", "merger", "acquisition", "sales",
            "marketing", "customer", "retail", "commerce", "strategy", "supply chain", "global trade", "e-commerce",
            "partnership", "business plan", "business development", "business model", "consumer behavior", "invoice",
            "negotiation", "small business", "business analysis", "economic growth", "franchise", "import", "export",
            "tariff", "logistics", "branding", "venture capital", "financial report", "investment fund", "cash flow",
            "business valuation", "productivity", "dividend"
    );

    private final List<String> scienceKeywords = Arrays.asList(
            "science", "research", "experiment", "physics", "biology", "chemistry", "astronomy", "ecology", "geology",
            "botany", "zoology", "genetics", "evolution", "microscope", "laboratory", "discovery", "analysis", "theory",
            "hypothesis", "scientist", "quantum", "space", "climate change", "energy", "biology", "nanotechnology",
            "particle physics", "biochemistry", "neuroscience", "anthropology", "marine biology", "astrophysics",
            "ecosystem", "geophysics", "meteorology", "scientific method", "scientific paper", "cell biology",
            "natural sciences", "bioinformatics", "environmental science", "materials science", "microbiology",
            "paleontology", "scientific innovation", "biophysics", "seismology", "environmental studies"
    );

    private final List<String> sportsKeywords = Arrays.asList(
            "sports", "game", "football", "cricket", "athlete", "tennis", "basketball", "soccer", "rugby", "volleyball",
            "swimming", "track", "field", "olympics", "training", "coach", "stadium", "goal", "match", "tournament",
            "league", "championship", "marathon", "fitness", "hockey", "athletics", "gymnastics", "team", "scoring",
            "playoffs", "competition", "medal", "scoreboard", "sportsmanship", "cycling", "karate", "judo", "wrestling",
            "skiing", "golf", "triathlon", "badminton", "table tennis", "curling", "bowling", "doping", "esports", "F1"
    );

    private final List<String> entertainmentKeywords = Arrays.asList(
            "entertainment", "movie", "music", "celebrity", "theater", "actor", "actress", "show", "performance",
            "comedy", "drama", "action", "concert", "festival", "streaming", "series", "film", "award", "event",
            "production", "cinema", "director", "script", "reality show", "blockbuster", "soundtrack", "video",
            "movie premiere", "broadway", "musical", "visual effects", "animation", "movie trailer", "genre", "fiction",
            "documentary", "box office", "festival", "red carpet", "celebrity", "gossip", "interview", "sound design",
            "live performance", "stage", "costume design", "theater production", "ticket sales"
    );

    private final List<String> environmentKeywords = Arrays.asList(
            "environment", "climate", "pollution", "wildlife", "nature", "sustainability", "renewable", "ecosystem",
            "biodiversity", "conservation", "forest", "recycling", "waste", "carbon", "emissions", "green", "ozone",
            "global warming", "natural", "habitat", "solar", "energy", "organic", "clean", "deforestation",
            "fossil fuels", "eco-friendly", "sustainable development", "environmental impact", "composting",
            "reforestation", "species extinction", "habitat loss", "climate action", "air quality", "water pollution",
            "plastic waste", "environmental awareness", "sustainable agriculture", "ocean conservation", "rainforest",
            "climate justice", "ecotourism", "renewable energy", "climate solutions", "zero waste"
    );

    private final List<String> crimeKeywords = Arrays.asList(
            "crime", "police", "arrest", "theft", "investigation", "murder", "robbery", "fraud", "cybercrime", "law",
            "assault", "prison", "violence", "witness", "evidence", "forensics", "justice", "court", "homicide",
            "trial", "burglary", "suspect", "victim", "sentence", "criminal", "prosecution", "misconduct", "conviction",
            "bail", "drug trafficking", "organized crime", "terrorism", "money laundering", "espionage", "kidnapping",
            "gang", "domestic violence", "juvenile crime", "parole", "rape", "sexual harassment", "vandalism",
            "embezzlement", "scam", "identity theft", "extortion", "blackmail", "arson", "smuggling"
    );

    private final List<String> educationKeywords = Arrays.asList(
            "education", "school", "university", "student", "teacher", "learning", "curriculum", "classroom", "degree",
            "scholarship", "tuition", "study", "lecture", "assignment", "exam", "research", "academic", "college",
            "graduate", "faculty", "campus", "admission", "course", "training", "pedagogy", "teaching", "syllabus",
            "online learning", "higher education", "kindergarten", "preschool", "alumni", "library", "diploma",
            "assessment", "grading", "philosophy of education", "education policy", "distance learning", "educational",
            "extracurricular", "school board", "educational reform", "adult education", "lifelong learning",
            "special education", "public school", "private school", "vocational training"
    );

    private final List<String> weatherKeywords = Arrays.asList(
            "weather", "rain", "storm", "temperature", "forecast", "climate", "snow", "hurricane", "wind", "flood",
            "heatwave", "thunderstorm", "cold", "fog", "drought", "humidity", "tornado", "monsoon", "lightning", "hail",
            "cloudy", "sunny", "freezing", "season", "winter", "summer", "autumn", "spring", "pressure", "atmosphere",
            "tropical", "polar", "cyclone", "typhoon", "meteorology", "blizzard", "weather patterns", "drizzle", "dew",
            "ice storm", "precipitation", "frost", "clear skies", "barometer", "air quality", "heat index", "UV index",
            "temperature drop", "storm surge"
    );


    public String categorize(String title, String preview) {
        String content = (title + " " + preview).toLowerCase();

        if (containsKeyword(content, healthKeywords)) return "Health";
        if (containsKeyword(content, technologyKeywords)) return "Technology";
        if (containsKeyword(content, politicsKeywords)) return "Politics";
        if (containsKeyword(content, businessKeywords)) return "Business";
        if (containsKeyword(content, scienceKeywords)) return "Science";
        if (containsKeyword(content, sportsKeywords)) return "Sports";
        if (containsKeyword(content, entertainmentKeywords)) return "Entertainment";
        if (containsKeyword(content, environmentKeywords)) return "Environment";
        if (containsKeyword(content, crimeKeywords)) return "Crime";
        if (containsKeyword(content, educationKeywords)) return "Education";
        if (containsKeyword(content, weatherKeywords)) return "Weather";

        return "Other";
    }

    private boolean containsKeyword(String content, List<String> keywords) {
        for (String keyword : keywords) {
            if (content.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}

