package site.easy.to.build.crm.csv;

import java.util.*;

public class Generateur {
    private static final Map<String, Map<String, Map<String, List<String>>>> locations = new HashMap<>();
    private static final Random random = new Random();
    static {
        locations.put("France", Map.of(
                "Île-de-France", Map.of(
                        "Paris", List.of("12 Rue de Rivoli", "8 Avenue des Champs-Élysées", "99 Boulevard Haussmann"),
                        "Versailles", List.of("25 Rue de la Paroisse", "78 Avenue de Paris", "10 Rue du Vieux Versailles"),
                        "Boulogne-Billancourt", List.of("3 Rue de Sèvres", "15 Quai du Point du Jour", "50 Rue Gallieni")
                ),
                "Provence-Alpes-Côte d'Azur", Map.of(
                        "Marseille", List.of("1 Rue de la République", "130 Boulevard Chave", "45 Avenue du Prado"),
                        "Nice", List.of("20 Promenade des Anglais", "5 Place Masséna", "15 Rue de France"),
                        "Toulon", List.of("8 Boulevard de Strasbourg", "25 Rue Jean Jaurès", "60 Avenue de la Victoire")
                )
        ));

        locations.put("USA", Map.of(
                "California", Map.of(
                        "Los Angeles", List.of("100 Hollywood Blvd", "200 Sunset Blvd", "500 Rodeo Dr"),
                        "San Francisco", List.of("1 Market St", "50 Embarcadero", "300 Lombard St"),
                        "San Diego", List.of("10 Gaslamp Quarter", "25 Coronado Ave", "99 La Jolla Blvd")
                ),
                "Texas", Map.of(
                        "Houston", List.of("15 Main St", "99 Westheimer Rd", "220 Post Oak Blvd"),
                        "Dallas", List.of("25 Elm St", "78 Ross Ave", "4100 McKinney Ave"),
                        "Austin", List.of("7 Congress Ave", "33 Barton Springs Rd", "55 6th St")
                )
        ));

        locations.put("Madagascar", Map.of(
                "Analamanga", Map.of(
                        "Antananarivo", List.of("101 Avenue de l'Indépendance", "50 Rue Ravelojaona", "7 Rue Andrianary Ratianarivo"),
                        "Ambohidratrimo", List.of("12 Rue Andranomena", "8 Lotissement Andranobevava", "99 Boulevard Ratsimandrava"),
                        "Ankazobe", List.of("5 Route RN4", "3 Rue du Marché", "15 Avenue Mahafaly")
                ),
                "Atsinanana", Map.of(
                        "Toamasina", List.of("5 Avenue de l’Indépendance", "3 Boulevard Ratsimilaho", "12 Rue du Port"),
                        "Brickaville", List.of("10 Route Nationale 2", "7 Rue du Marché", "21 Rue des Palmiers"),
                        "Vatomandry", List.of("11 Rue des Cocotiers", "88 Allée des Baobabs", "42 Route de la Plage")
                )
        ));

        locations.put("Germany", Map.of(
                "Bavaria", Map.of(
                        "Munich", List.of("1 Marienplatz", "8 Sendlinger Str.", "99 Leopoldstraße"),
                        "Nuremberg", List.of("5 Hauptmarkt", "12 Königstraße", "60 Plärrer"),
                        "Augsburg", List.of("3 Maximilianstraße", "15 Fuggerstraße", "25 Bahnhofstraße")
                ),
                "Berlin", Map.of(
                        "Berlin", List.of("1 Alexanderplatz", "7 Unter den Linden", "99 Kurfürstendamm")
                ),
                "North Rhine-Westphalia", Map.of(
                        "Cologne", List.of("8 Domplatte", "22 Hohe Straße", "45 Friesenplatz"),
                        "Düsseldorf", List.of("10 Königsallee", "5 Immermannstraße", "30 Schadowstraße"),
                        "Dortmund", List.of("15 Westenhellweg", "33 Hansastraße", "50 Kampstraße")
                )
        ));

        locations.put("Japan", Map.of(
                "Tokyo", Map.of(
                        "Tokyo", List.of("1 Shibuya Crossing", "5 Akihabara St.", "99 Ginza Blvd"),
                        "Shinjuku", List.of("10 Kabukicho", "7 Yasukuni-dori", "88 Shinjuku Sanchome"),
                        "Shibuya", List.of("12 Center Gai", "25 Koen-dori", "45 Meiji-dori")
                ),
                "Osaka", Map.of(
                        "Osaka", List.of("1 Dotonbori", "8 Shinsekai", "77 Umeda"),
                        "Sakai", List.of("5 Mozu Tumulus", "12 Sakai Station", "22 Higashi-ku"),
                        "Hirakata", List.of("3 Hirakata Park", "9 Keihan Hirakata", "30 Banpaku Memorial Park")
                ),
                "Hokkaido", Map.of(
                        "Sapporo", List.of("8 Odori Park", "50 Susukino", "99 JR Tower"),
                        "Asahikawa", List.of("5 Zoo Street", "10 Heiwa-dori", "42 Taisetsu Ave"),
                        "Hakodate", List.of("7 Goryokaku", "33 Bay Area", "88 Hakodate Station")
                )
        ));
    }

    private static String randomSuffix(int limit, int max) {
        Random random = new Random();
        StringBuilder suffix = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            suffix.append(random.nextInt(max + 1));
        }
        return suffix.toString();
    }

    public static String getRandomPhoneNumber() {
        int[] number = {2,3,4,7,8};
        String prefix = "03" + 2 + number[random.nextInt(number.length)];
        String suffix = randomSuffix(7, 9);

        return prefix+suffix;
    }

    public static String getRandomCountry() {
        List<String> countries = new ArrayList<>(locations.keySet());
        return countries.get(random.nextInt(countries.size()));
    }

    public static String getRandomState(String country) {
        if (!locations.containsKey(country)) return null;
        List<String> states = new ArrayList<>(locations.get(country).keySet());
        return states.get(random.nextInt(states.size()));
    }

    public static String getRandomCity(String country, String state) {
        if (!locations.containsKey(country) || !locations.get(country).containsKey(state)) return null;
        List<String> cities = new ArrayList<>(locations.get(country).get(state).keySet());
        return cities.get(random.nextInt(cities.size()));
    }

    public static String getRandomAddress(String country, String state, String city) {
        if (!locations.containsKey(country) || !locations.get(country).get(state).containsKey(city)) return null;
        List<String> addresses = locations.get(country).get(state).get(city);
        return addresses.get(random.nextInt(addresses.size()));
    }

    private static char getRandomLowercaseLetter() {
        return (char) ('a' + random.nextInt(26));
    }

    private static char getRandomUppercaseLetter() {
        return (char) ('A' + random.nextInt(26));
    }

    public static String getRandomDescription(int limit) {
        StringBuilder description = new StringBuilder(getRandomUppercaseLetter() + "");
        for (int i = 0; i < limit; i++) {
            description.append(getRandomLowercaseLetter());
        }
        return description.toString();
    }

    public static int getRandomIndex(int length) {
        return random.nextInt(length);
    }

    public static void main(String[] args) {
        System.out.println(getRandomPhoneNumber());
    }
}
