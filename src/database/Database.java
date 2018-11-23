package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Database {

    public Database(){
        initDatabase();
    }
	private List<Category> category = new ArrayList<>();

    private void initDatabase(){
        category.add(
                new Category("Sport", Arrays.asList(
                        new Question("Vem vann VM 1994?", "Brasilien", "Sverige", "Spanien", "Gr�nland", 1),
                        new Question("Vad �r Sydkoreas nationalsport?", "Schack", "Tetris", "Starcraft", "Fotboll", 3),
                        new Question("Hur m�nga guldmedaljer totalt vann Jan-Ove Waldner under sin karri�r?", "45", "29", "24", "19", 2),
                        new Question("Vad heter sporten som innefattar en h�st och en klubba samt en boll?", "Clop", "Cricket", "Crocket", "Polo", 4)
                        ))
        );
        category.add(
                new Category("Sport", Arrays.asList(
                        new Question("Vem vann VM 1994?", "Brasilien", "Sverige", "Spanien", "Gr�nland", 1),
                        new Question("Vad �r Sydkoreas nationalsport?", "Schack", "Tetris", "Starcraft", "Fotboll", 3),
                        new Question("Hur m�nga guldmedaljer totalt vann Jan-Ove Waldner under sin karri�r?", "45", "29", "24", "19", 2),
                        new Question("Vad heter sporten som innefattar en h�st och en klubba samt en boll?", "Clop", "Cricket", "Crocket", "Polo", 4)
                        ))
        );
        category.add(
                new Category("Sport", Arrays.asList(
                        new Question("Vem vann VM 1994?", "Brasilien", "Sverige", "Spanien", "Gr�nland", 1),
                        new Question("Vad �r Sydkoreas nationalsport?", "Schack", "Tetris", "Starcraft", "Fotboll", 3),
                        new Question("Hur m�nga guldmedaljer totalt vann Jan-Ove Waldner under sin karri�r?", "45", "29", "24", "19", 2),
                        new Question("Vad heter sporten som innefattar en h�st och en klubba samt en boll?", "Clop", "Cricket", "Crocket", "Polo", 4)
                        ))
        );
        category.add(
                new Category("Sport", Arrays.asList(
                        new Question("Vem vann VM 1994?", "Brasilien", "Sverige", "Spanien", "Gr�nland", 1),
                        new Question("Vad �r Sydkoreas nationalsport?", "Schack", "Tetris", "Starcraft", "Fotboll", 3),
                        new Question("Hur m�nga guldmedaljer totalt vann Jan-Ove Waldner under sin karri�r?", "45", "29", "24", "19", 2),
                        new Question("Vad heter sporten som innefattar en h�st och en klubba samt en boll?", "Clop", "Cricket", "Crocket", "Polo", 4)
                        ))
        );
    }

	private String[] categoryChoice = new String[3];
    
    public List<Question> getQuestionFromCat(String catName){
        
        List<Category> category1 = category.stream().filter( cat -> cat.getName().equalsIgnoreCase(catName)).distinct().limit(1).collect(toList()); // distinct unik category name och limit , returnera bara en
        return category1.get(0).getQuestions();
    }
    
    public String[] getCategories() {
        categoryChoice = category.stream().map(Category::getName).toArray(String[]::new);
		return categoryChoice;
    }
    
}
