import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Recipe(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}

class User {
    private String username;
    private List<Ingredient> availableIngredients;
    private List<String> dietaryRestrictions;

    public User(String username, List<Ingredient> availableIngredients, List<String> dietaryRestrictions) {
        this.username = username;
        this.availableIngredients = availableIngredients;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getUsername() {
        return username;
    }

    public List<Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public Recipe recommendRecipe(List<Recipe> recipes) {
        // Simulate a simple recommendation algorithm
        List<Recipe> compatibleRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (isRecipeCompatible(recipe)) {
                compatibleRecipes.add(recipe);
            }
        }

        if (!compatibleRecipes.isEmpty()) {
            // Randomly select a recipe from the compatible ones
            Random random = new Random();
            int index = random.nextInt(compatibleRecipes.size());
            return compatibleRecipes.get(index);
        } else {
            System.out.println("No compatible recipes found for " + username);
            return null;
        }
    }

    private boolean isRecipeCompatible(Recipe recipe) {
        // Check if the recipe's ingredients match the user's available ingredients
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (!availableIngredients.contains(ingredient)) {
                return false;
            }
        }

        // Check if the recipe violates any dietary restrictions
        for (String restriction : dietaryRestrictions) {
            if (recipe.getName().toLowerCase().contains(restriction.toLowerCase())) {
                return false;
            }
        }

        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create ingredients
        Ingredient tomato = new Ingredient("Tomato");
        Ingredient onion = new Ingredient("Onion");
        Ingredient chicken = new Ingredient("Chicken");
        Ingredient rice = new Ingredient("Rice");
        Ingredient pasta = new Ingredient("Pasta");

        // Create recipes
        List<Ingredient> pastaRecipeIngredients = List.of(pasta, tomato, onion);
        List<Ingredient> chickenRiceRecipeIngredients = List.of(chicken, rice, tomato, onion);

        Recipe pastaRecipe = new Recipe("Pasta with Tomato Sauce", pastaRecipeIngredients);
        Recipe chickenRiceRecipe = new Recipe("Chicken Rice", chickenRiceRecipeIngredients);

        // Create a user
        List<Ingredient> userIngredients = List.of(tomato, onion, chicken);
        List<String> dietaryRestrictions = List.of("vegetarian");
        User user = new User("Amit", userIngredients, dietaryRestrictions);

        // Recommend a recipe to the user
        List<Recipe> recipes = List.of(pastaRecipe, chickenRiceRecipe);
        Recipe recommendedRecipe = user.recommendRecipe(recipes);

        if (recommendedRecipe != null) {
            System.out.println("Recommended Recipe for " + user.getUsername() + ": " + recommendedRecipe.getName());
        }
    }
}
