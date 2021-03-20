package edu.ncsu.csc326.coffeemaker;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

public class CoffeeMakerTest {
	private CoffeeMaker maker;
	private Inventory inven;
	private RecipeBook book;
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	private Recipe r4;
	private Recipe r5;
	
	@Before
	public void setUp() throws Exception{
		maker = new CoffeeMaker();
		inven = new Inventory();
		book = new RecipeBook();
		
		r1 = new Recipe();
		r1.setName("americano");
		r1.setAmtChocolate("0");
		r1.setAmtCoffee("2");
		r1.setAmtMilk("0");
		r1.setAmtSugar("0");
		r1.setPrice("3000");
		
		r2 = new Recipe();
		r2.setName("cafelatte");
		r2.setAmtChocolate("0");
		r2.setAmtCoffee("2");
		r2.setAmtMilk("2");
		r2.setAmtSugar("1");
		r2.setPrice("4000");
		
		r3 = new Recipe();
		r3.setName("caramelmachiato");
		r3.setAmtChocolate("0");
		r3.setAmtCoffee("2");
		r3.setAmtMilk("3");
		r3.setAmtSugar("2");
		r3.setPrice("5000");
		
		r4 = new Recipe();
		r4.setName("mochalatte");
		r4.setAmtChocolate("1");
		r4.setAmtCoffee("1");
		r4.setAmtMilk("2");
		r4.setAmtSugar("2");
		r4.setPrice("4000");
		
		r5 = new Recipe();
		r5.setName("chocochoco");
		r5.setAmtChocolate("5");
		r5.setAmtCoffee("0");
		r5.setAmtMilk("3");
		r5.setAmtSugar("3");
		r5.setPrice("6000");

	}
	@After
	public void tearDown() throws Exception {
		//���� �׽�Ʈ �� �ؾ��� �۾� ����
	}
	@Test
	public void testCoffeeMaker() {
		//������(��ŵ)
		Assert.assertTrue(true);
	}

	@Test
	public void testAddRecipe() {
		//1. �������� ������ �Է�(���� ���̽�)
		Assert.assertTrue(maker.addRecipe(r1));
		Assert.assertTrue(maker.addRecipe(r2));
		//2. ������ �ִ� ������ �ߺ� �Է�(���� ���̽�)
		Assert.assertFalse(maker.addRecipe(r2));
		//3. �������� ������ �Է�(������ ���� �뷮�� 4������/���� ���̽�)
		Assert.assertTrue(maker.addRecipe(r3));
		Assert.assertTrue(maker.addRecipe(r4));
		//4. ������ ���� �뷮�� �ʰ��Ͽ� �Է�(���� ���̽�)
		Assert.assertFalse(maker.addRecipe(r5));
	}

	@Test
	public void testDeleteRecipe() {
		//������ �Ͽ� �޴� �Է�
		Assert.assertTrue(maker.addRecipe(r1));
		Assert.assertTrue(maker.addRecipe(r2));
		Assert.assertTrue(maker.addRecipe(r3));
		Assert.assertTrue(maker.addRecipe(r4));
		
		//1. �������� �޴� ����(���� ���̽�)
		Assert.assertEquals("americano", maker.deleteRecipe(0));
		Assert.assertEquals("cafelatte", maker.deleteRecipe(1));
		//2. �޴� �ߺ� ����(���� ��Ȳ)
		Assert.assertEquals("", maker.deleteRecipe(1));
		//3. �������� �޴� ����(���� ���̽�)
		Assert.assertEquals("caramelmachiato", maker.deleteRecipe(2));
		Assert.assertEquals("mochalatte", maker.deleteRecipe(3));
	}

	@Test
	//RecipeBook.java�� editRecipe �����ڵ� ���� ����
	//���� �߰� : ������ ������ �����Ǹ� �ٲٰ� �ٲ� �������� �̸��� ����ؾ� ��.
	//String recipeName = recipeArray[recipeToEdit].getName();
	//newRecipe.setName("");
	//recipeArray[recipeToEdit] = newRecipe;
	//���� ���� : ������ ������ �����Ǹ� �ٲٰ� ����� ��Ʈ���� �ٲ� �������� �̸��� �Ҵ� �� ��ȯ����
	public void testEditRecipe() {
		Assert.assertTrue(book.addRecipe(r1));
		Assert.assertTrue(book.addRecipe(r4));
		//1. �������� �޴� ����(���� ���̽�)
		Assert.assertEquals("mochalatte", book.editRecipe(0, r4));
		//2. ������ �Ͽ� ���� �޴� ����(���� ���̽�)
		Assert.assertNull(book.editRecipe(2, r1));
	}

	@Test
	//CoffeeMaker.java�� addInventory �����ڵ� ���� ����
	//���� ���� : ���� ���̾�׷� ���� �䱸���װ� ���� �ڵ尡 �޶� ��������(return�� void -> boolean)
	//Inventory.java�� addSugar �����ڵ� ���� ����
	//���� ���� : ù��° ���ǹ��� �ε�ȣ�� ������ �߸� �Ǿ� ���� -> ���� if (amtSugar <= 0) -> �ε�ȣ ���� ����
	public void testAddInventory() throws InventoryException {
		//1. �κ��丮 �߰�(���� ���̽�)
		Assert.assertTrue(maker.addInventory("27", "23", "7", "13"));
		//2. �κ��丮 ���� ���� Ȯ��(���� ���̽�)
		Assert.assertEquals(42, inven.getCoffee());
		Assert.assertEquals(38, inven.getMilk());
		Assert.assertEquals(22, inven.getSugar());
		Assert.assertEquals(28, inven.getChocolate());
		//3. �κ��丮 �߰� �� ����(���� ���̽�)
		try {
			Assert.assertTrue(maker.addInventory("-1", "1", "1", "1"));
		}
		catch(InventoryException e) {
			String expected = "Units of coffee must be a positive integer";
			String actual = e.getMessage();
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public void testCheckInventory() throws InventoryException {
		//inventory default sets are "15"
		//�κ��丮 �ʱⰪ Ȯ��(���� ���̽�)
		Assert.assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", maker.checkInventory());
		//�κ��丮 ���� �� ��ȭ
		Assert.assertTrue(maker.addInventory("1", "2", "3", "4"));
		//�κ��丮 ��ȭ �� Ȯ��(���� ���̽�-���� ���̽� ��)
		Assert.assertNotEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", maker.checkInventory());
		Assert.assertEquals("Coffee: 16\nMilk: 17\nSugar: 18\nChocolate: 19\n", maker.checkInventory());
	}

	@Test
	public void testMakeCoffee() throws RecipeException {
		//�Ƹ޸�ī�� �ֹ��� ��츦 �׽�Ʈ ������� ����(�Ҵ�)
		Assert.assertTrue(maker.addRecipe(r1));
		//���� �ݾ� ����� �� ����(����, ��Ȯ, ����)
		int purchaseLack = 2000;
		int purchaseCost = 3000;
		int purchaseOver = 4000;
		//�ݾ��� ����, ��Ȯ, ������ ���� �޴��� ���� �ֹ��� ��� Ȯ��
		//�ݾ��� ������ ��� ������ �ݾ��� ���θ� �ǵ��� ��
		Assert.assertEquals(purchaseLack, maker.makeCoffee(0, purchaseLack));
		//�ݾ��� ��Ȯ�� ��� 0���� �ǵ��� ��
		Assert.assertEquals(purchaseCost-purchaseCost, maker.makeCoffee(0, purchaseCost));
		//�ݾ��� ��ġ�� ��� ������ �ǵ��� ��
		Assert.assertEquals(purchaseOver-purchaseCost, maker.makeCoffee(0, purchaseOver));
		//�޴��� ���� �ֹ�(3�� �޴�)�� ��� ������ �ݾ��� ���� �ǵ��� ��
		Assert.assertEquals(purchaseCost, maker.makeCoffee(2, purchaseCost));
	}

	@Test
	public void testGetRecipes() throws RecipeException {
		Recipe[] R_arr;
		//�޴� �Ҵ�
		Assert.assertTrue(maker.addRecipe(r1));
		Assert.assertTrue(maker.addRecipe(r2));
		Assert.assertTrue(maker.addRecipe(r3));
		Assert.assertTrue(maker.addRecipe(r4));
		//�迭�� �Ҵ��� �޴��� �޾ƿ�
		R_arr = maker.getRecipes();
		//��� Ȯ��(����� ���ư��ٸ� ���� ���� ���̽��� ���� ���� ����)
		Assert.assertEquals(r1, R_arr[0]);
		Assert.assertEquals(r2, R_arr[1]);
		Assert.assertEquals(r3, R_arr[2]);
		Assert.assertEquals(r4, R_arr[3]);
		//�ٸ� ��ġ�� �ٸ� ���� ����ִ��� ����(���� ���̽� ���� 1)
		Assert.assertNotEquals(r2, R_arr[0]);
		Assert.assertEquals(r2, R_arr[1]);
		Assert.assertNotEquals(r2, R_arr[2]);
		Assert.assertNotEquals(r2, R_arr[3]);

		//���� ��ȯ�� ����(�ϳ��� ������ �������� �ּ�)
		Assert.assertEquals("americano", R_arr[0].getName());
		Assert.assertEquals(0, R_arr[0].getAmtChocolate());
		Assert.assertEquals(2, R_arr[0].getAmtCoffee());
		Assert.assertEquals(0, R_arr[0].getAmtMilk());
		Assert.assertEquals(0, R_arr[0].getAmtSugar());
		Assert.assertEquals(3000, R_arr[0].getPrice());
		/*
		Assert.assertEquals("cafelatte", R_arr[1].getName());
		Assert.assertEquals(0, R_arr[1].getAmtChocolate());
		Assert.assertEquals(2, R_arr[1].getAmtCoffee());
		Assert.assertEquals(2, R_arr[1].getAmtMilk());
		Assert.assertEquals(1, R_arr[1].getAmtSugar());
		Assert.assertEquals(4000, R_arr[1].getPrice());
		
		Assert.assertEquals("caramelmachiato", R_arr[2].getName());
		Assert.assertEquals(0, R_arr[2].getAmtChocolate());
		Assert.assertEquals(2, R_arr[2].getAmtCoffee());
		Assert.assertEquals(3, R_arr[2].getAmtMilk());
		Assert.assertEquals(2, R_arr[2].getAmtSugar());
		Assert.assertEquals(5000, R_arr[2].getPrice());
		
		Assert.assertEquals("mochalatte", R_arr[3].getName());
		Assert.assertEquals(1, R_arr[3].getAmtChocolate());
		Assert.assertEquals(1, R_arr[3].getAmtCoffee());
		Assert.assertEquals(2, R_arr[3].getAmtMilk());
		Assert.assertEquals(2, R_arr[3].getAmtSugar());
		Assert.assertEquals(4000, R_arr[3].getPrice());
		*/
	}
}

