package site.easy.to.build.crm.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CsvUtil {

    public String reformatLine(String line) {
        char[] chars = line.toCharArray();
        String value = "";
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            boolean isIn = false;
            if (found){
                if (chars[i] == ','){
                    chars[i] = '.';
                }
            }
            if (chars[i] == '"' && !found && !isIn) {
                found = true;
                isIn = true;
            }
            if (chars[i] == '"' && found && !isIn) {
                found = false;
                isIn = true;
            }
            value += chars[i];
        }
        return value;
    }

    public List<HashMap<String,Object>> getDataFromCSV (String FileName, String separateur, HashMap<String,Class<?>> typeColonnesMap){
        List<HashMap<String,Object>> result = new ArrayList<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(FileName));
            String ligne;
            int nbLine = 0;
            List<String> entete = new ArrayList<>();
            // Convert CSV to list Objects
            while ((ligne = csvReader.readLine()) != null) {
                ligne = reformatLine(ligne);
                String [] valeur = ligne.split(separateur);
                if (nbLine>0){
                    HashMap<String,Object> ligneData = new HashMap<>();
                    for (int i = 0; i < valeur.length; i++) {
                        Class<?> valeurType = typeColonnesMap.get(entete.get(i));
//                        System.out.println("value : "+valeur[i]);

                        ligneData.put(entete.get(i), this.castValueOfParameter(valeur[i],valeurType));
                    }
                    result.add(ligneData);
                }
                else {
                    // initialize l'entete
                    for (String v:valeur){
//                        System.out.println("entete : "+v);
                        entete.add(v);
                    }
                }
                nbLine++;
//                System.out.println(ligne);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public String reformatDate (String date){
        date = date.replace("/","-");
        return date;
    }
    public String reformatDouble (String valeur){
        valeur = valeur.replace("\"","");
        return valeur;
    }

    public Object castValueOfParameter(String value,Class<?> clazz) throws Exception {
        Object result = null;
        try {
            if(clazz == String.class){
                result = value;
            }
            if(clazz == int.class){
                result = Integer.valueOf(value);
            }
            if(clazz == double.class){
                value = reformatDouble(value);
                result = Double.valueOf(value);
            }
            if(clazz == Date.class){
                value = reformatDate(value);
                result = Date.valueOf(value);
            }
            if(clazz == Timestamp.class){
                value = reformatDate(value);
                result = Timestamp.valueOf(value);
            }
            if(clazz == boolean.class){
                result = Boolean.valueOf(value);
            }

        }catch (Exception e){
            throw new Exception("Impossible de caster l'objet : "+value );
//            e.printStackTrace();
        }
        return result;
    }
}
