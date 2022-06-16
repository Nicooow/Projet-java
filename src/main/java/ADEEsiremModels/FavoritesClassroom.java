package ADEEsiremModels;

import ade.Classroom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Handler of the favorites classroom
 */
public class FavoritesClassroom {
    private ArrayList<String> classrooms;
    // Path to the favorites save-file
    final static String PATH_FILE = System.getProperty("user.dir") + "\\favorites.txt";

    public FavoritesClassroom() {
        classrooms = new ArrayList<>();

        try {
            File favoritesFile = new File(FavoritesClassroom.PATH_FILE);
            favoritesFile.createNewFile();

            String data = Files.readString(favoritesFile.toPath());

            for(String classroom : data.split("\\|")){
                if(!classroom.equals(""))
                    classrooms.add(classroom);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean containsClassroom(String classroom){
        return classrooms.contains(classroom);
    }

    public void addClassroom(String classroom){
        classrooms.add(classroom);
        save();
    }

    public void removeClassroom(String classroom){
        classrooms.remove(classroom);
        save();
    }

    public ArrayList<String> getClassrooms() {
        return classrooms;
    }

    /**
     * Save the favorites classroom on the file
     */
    private void save(){
        try {
            PrintWriter writer = new PrintWriter(FavoritesClassroom.PATH_FILE);
            String finalString = "";
            for(String classroom : classrooms){
                finalString += classroom + "|";
            }
            if(finalString.endsWith("|"))
                finalString = finalString.substring(0, finalString.length()-1);
            writer.print(finalString);
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
