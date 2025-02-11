package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    private final String bunName;
    private final IngredientType ingredientType;
    private final String ingridientName;
    private final float price;

    public BurgerTest(String bunName, IngredientType ingredientType, String ingridientName, float price) {
        this.bunName = bunName;
        this.ingredientType = ingredientType;
        this.ingridientName = ingridientName;
        this.price = price;
    }

    @Parameterized.Parameters
    public static Object[][] getBurgeData() {
        return new Object[][] {
                {"BunName", IngredientType.SAUCE, "ingridient name", 12.0f},
                {"BunName", IngredientType.FILLING, "ingridient name", 12.0f},
        };
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    Bun bun;
    @Mock
    Ingredient ingredientOne;
    @Mock
    Ingredient ingredientTwo;

    @Test
    public void setBuns() {
        Burger burger = new Burger();
        Mockito.when(bun.getName()).thenReturn(bunName);
        burger.setBuns(bun);
        assertEquals(bunName, burger.bun.getName());
    }

    @Test
    public void addIngredient() {
        Burger burger = new Burger();
        Mockito.when(ingredientOne.getType()).thenReturn(ingredientType);
        Mockito.when(ingredientOne.getName()).thenReturn(ingridientName);
        Mockito.when(ingredientOne.getPrice()).thenReturn(price);
        burger.addIngredient(ingredientOne);
        assertEquals(ingridientName, burger.ingredients.get(0).getName());
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void removeIngredient() {
        Burger burger = new Burger();
        Mockito.when(ingredientOne.getType()).thenReturn(ingredientType);
        Mockito.when(ingredientOne.getName()).thenReturn(ingridientName);
        Mockito.when(ingredientOne.getPrice()).thenReturn(price);
        burger.addIngredient(ingredientOne);
        burger.removeIngredient(0);
        burger.ingredients.get(0).getName();
    }

    @Test
    public void moveIngredient() {
        Burger burger = new Burger();
        Mockito.when(ingredientOne.getType()).thenReturn(ingredientType);
        Mockito.when(ingredientOne.getName()).thenReturn(ingridientName);
        Mockito.when(ingredientTwo.getName()).thenReturn("ingridient name two");
        Mockito.when(ingredientOne.getPrice()).thenReturn(price);
        burger.addIngredient(ingredientOne);
        burger.addIngredient(ingredientTwo);
        burger.moveIngredient(1, 0);
        assertEquals(ingredientTwo.getName(), burger.ingredients.get(0).getName());
    }

    @Test
    public void getPrice() {
        Burger burger = new Burger();
        float expectedPrice = 112.0f;
        Mockito.when(ingredientOne.getType()).thenReturn(ingredientType);
        Mockito.when(ingredientOne.getName()).thenReturn(ingridientName);
        Mockito.when(ingredientOne.getPrice()).thenReturn(price);
        Mockito.when(bun.getPrice()).thenReturn(50.0f);
        burger.setBuns(bun);
        burger.addIngredient(ingredientOne);
        float actualPrice = burger.getPrice();
        assertEquals(expectedPrice, actualPrice, 0);
    }

    @Test
    public void getReceipt() {
        Burger burger = new Burger();
        String expectedReceipt = "(==== " + bunName + " ====)\n" +
                "= " + ingredientType.toString().toLowerCase() + " " + ingridientName + " =\n" +
                "(==== " + bunName + " ====)\n" +
                "\n" +
                "Price: 12,000000" +
                "\n";
        Mockito.when(bun.getName()).thenReturn(bunName);
        Mockito.when(ingredientOne.getType()).thenReturn(ingredientType);
        Mockito.when(ingredientOne.getName()).thenReturn(ingridientName);
        Mockito.when(ingredientOne.getPrice()).thenReturn(price);
        burger.setBuns(bun);
        burger.addIngredient(ingredientOne);
        String actualReceipt = burger.getReceipt();
        assertEquals(expectedReceipt, actualReceipt);

    }
}
