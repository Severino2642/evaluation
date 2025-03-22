package site.easy.to.build.crm.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CsvUtil {
    public List<HashMap<String,Object>> getDataFromCSV (String FileName, String separateur, HashMap<String,Class<?>> typeColonnesMap){
        List<HashMap<String,Object>> result = new ArrayList<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(FileName));
            String ligne;
            int nbLine = 0;
            List<String> entete = new ArrayList<>();
            // Convert CSV to list Objects
            while ((ligne = csvReader.readLine()) != null) {
                String [] valeur = ligne.split(separateur);
                if (nbLine>0){
                    HashMap<String,Object> ligneData = new HashMap<>();
                    for (int i = 0; i < valeur.length; i++) {
                        Class<?> valeurType = typeColonnesMap.get(entete.get(i));
                        ligneData.put(entete.get(i), this.castValueOfParameter(valeur[i],valeurType));
                    }
                    result.add(ligneData);
                }
                else {
                    // initialize l'entete
                    for (String v:valeur){
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
                result = Double.valueOf(value);
            }
            if(clazz == Date.class){
                result = Date.valueOf(value);
            }
            if(clazz == Timestamp.class){
                result = Timestamp.valueOf(value);
            }
            if(clazz == boolean.class){
                result = Boolean.valueOf(value);
            }

        }catch (Exception e){
            throw new Exception("Impossible de caster l'objet");
        }
        return result;
    }
}
