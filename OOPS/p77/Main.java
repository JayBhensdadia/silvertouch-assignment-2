import java.util.ArrayList;
import java.util.List;

class Ingredient {
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Recipe {
    private String name;
    private List<Ingredient> ingredients;
    private String category;

    public Recipe(String name, String category) {
        this.name = name;
        this.ingredients = new ArrayList<>();
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}

class Cookbook {
    private List<Recipe> recipes;

    public Cookbook() {
        this.recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public List<Recipe> searchByIngredient(Ingredient ingredient) {
        List<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (recipe.getIngredients().contains(ingredient)) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    public List<Recipe> searchByCategory(String category) {
        List<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (recipe.getCategory().equalsIgnoreCase(category)) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    public void displayRecipes() {
        System.out.println("Recipes in the Cookbook:");
        for (Recipe recipe : recipes) {
            System.out.println("Name: " + recipe.getName());
            System.out.println("Category: " + recipe.getCategory());
            System.out.println("Ingredients: " + recipe.getIngredients());
            System.out.println("------------------------------");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Ingredient ingredient1 = new Ingredient("Chicken");
        Ingredient ingredient2 = new Ingredient("Tomato");
        Ingredient ingredient3 = new Ingredient("Onion");

        Recipe recipe1 = new Recipe("Chicken Curry", "Curry");
        recipe1.addIngredient(ingredient1);
        recipe1.addIngredient(ingredient2);
        recipe1.addIngredient(ingredient3);

        Recipe recipe2 = new Recipe("Tomato Salad", "Salad");
        recipe2.addIngredient(ingredient2);
        recipe2.addIngredient(ingredient3);

        Cookbook cookbook = new Cookbook();

        cookbook.addRecipe(recipe1);
        cookbook.addRecipe(recipe2);

        System.out.println("Recipes with Tomato:");
        List<Recipe> recipesWithTomato = cookbook.searchByIngredient(ingredient2);
        for (Recipe recipe : recipesWithTomato) {
            System.out.println(recipe.getName());
        }

        System.out.println("\nSalad Recipes:");
        List<Recipe> saladRecipes = cookbook.searchByCategory("Salad");
        for (Recipe recipe : saladRecipes) {
            System.out.println(recipe.getName());
        }

        System.out.println("\nAll Recipes:");
        cookbook.displayRecipes();
    }
}
