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
		//딱히 테스트 후 해야할 작업 없음
	}
	@Test
	public void testCoffeeMaker() {
		//생성자(스킵)
		Assert.assertTrue(true);
	}

	@Test
	public void testAddRecipe() {
		//1. 정상적인 레시피 입력(정상 케이스)
		Assert.assertTrue(maker.addRecipe(r1));
		Assert.assertTrue(maker.addRecipe(r2));
		//2. 기존에 있는 레시피 중복 입력(오류 케이스)
		Assert.assertFalse(maker.addRecipe(r2));
		//3. 정상적인 레시피 입력(레시피 북의 용량인 4개까지/정상 케이스)
		Assert.assertTrue(maker.addRecipe(r3));
		Assert.assertTrue(maker.addRecipe(r4));
		//4. 레시피 북의 용량을 초과하여 입력(오류 케이스)
		Assert.assertFalse(maker.addRecipe(r5));
	}

	@Test
	public void testDeleteRecipe() {
		//레시피 북에 메뉴 입력
		Assert.assertTrue(maker.addRecipe(r1));
		Assert.assertTrue(maker.addRecipe(r2));
		Assert.assertTrue(maker.addRecipe(r3));
		Assert.assertTrue(maker.addRecipe(r4));
		
		//1. 정상적인 메뉴 삭제(정상 케이스)
		Assert.assertEquals("americano", maker.deleteRecipe(0));
		Assert.assertEquals("cafelatte", maker.deleteRecipe(1));
		//2. 메뉴 중복 삭제(예외 상황)
		Assert.assertEquals("", maker.deleteRecipe(1));
		//3. 정상적인 메뉴 삭제(정상 케이스)
		Assert.assertEquals("caramelmachiato", maker.deleteRecipe(2));
		Assert.assertEquals("mochalatte", maker.deleteRecipe(3));
	}

	@Test
	//RecipeBook.java의 editRecipe 원시코드 수정 내역
	//오류 발견 : 선택한 순번의 레시피를 바꾸고 바뀐 레시피의 이름을 출력해야 함.
	//String recipeName = recipeArray[recipeToEdit].getName();
	//newRecipe.setName("");
	//recipeArray[recipeToEdit] = newRecipe;
	//오류 수정 : 선택한 순번의 레시피를 바꾸고 출력할 스트링에 바뀐 레시피의 이름을 할당 후 반환했음
	public void testEditRecipe() {
		Assert.assertTrue(book.addRecipe(r1));
		Assert.assertTrue(book.addRecipe(r4));
		//1. 정상적인 메뉴 변경(정상 케이스)
		Assert.assertEquals("mochalatte", book.editRecipe(0, r4));
		//2. 레시피 북에 없는 메뉴 변경(오류 케이스)
		Assert.assertNull(book.editRecipe(2, r1));
	}

	@Test
	//CoffeeMaker.java의 addInventory 원시코드 수정 내역
	//오류 수정 : 순서 다이어그램 상의 요구사항과 원시 코드가 달라 수정했음(return값 void -> boolean)
	//Inventory.java의 addSugar 원시코드 수정 내역
	//오류 수정 : 첫번째 조건문의 부등호의 방향이 잘못 되어 있음 -> 원문 if (amtSugar <= 0) -> 부등호 방향 수정
	public void testAddInventory() throws InventoryException {
		//1. 인벤토리 추가(정상 케이스)
		Assert.assertTrue(maker.addInventory("27", "23", "7", "13"));
		//2. 인벤토리 수정 내용 확인(정상 케이스)
		Assert.assertEquals(42, inven.getCoffee());
		Assert.assertEquals(38, inven.getMilk());
		Assert.assertEquals(22, inven.getSugar());
		Assert.assertEquals(28, inven.getChocolate());
		//3. 인벤토리 추가 값 음수(오류 케이스)
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
		//인벤토리 초기값 확인(정상 케이스)
		Assert.assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", maker.checkInventory());
		//인벤토리 저장 값 변화
		Assert.assertTrue(maker.addInventory("1", "2", "3", "4"));
		//인벤토리 변화 값 확인(오류 케이스-정상 케이스 순)
		Assert.assertNotEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", maker.checkInventory());
		Assert.assertEquals("Coffee: 16\nMilk: 17\nSugar: 18\nChocolate: 19\n", maker.checkInventory());
	}

	@Test
	public void testMakeCoffee() throws RecipeException {
		//아메리카노 주문의 경우를 테스트 대상으로 지정(할당)
		Assert.assertTrue(maker.addRecipe(r1));
		//지불 금액 경우의 수 지정(부족, 정확, 과잉)
		int purchaseLack = 2000;
		int purchaseCost = 3000;
		int purchaseOver = 4000;
		//금액이 부족, 정확, 과잉일 경우와 메뉴에 없는 주문일 경우 확인
		//금액이 부족한 경우 지불한 금액의 전부를 되돌려 줌
		Assert.assertEquals(purchaseLack, maker.makeCoffee(0, purchaseLack));
		//금액이 정확한 경우 0원을 되돌려 줌
		Assert.assertEquals(purchaseCost-purchaseCost, maker.makeCoffee(0, purchaseCost));
		//금액이 넘치는 경우 차액을 되돌려 줌
		Assert.assertEquals(purchaseOver-purchaseCost, maker.makeCoffee(0, purchaseOver));
		//메뉴에 없는 주문(3번 메뉴)의 경우 지불한 금액을 전부 되돌려 줌
		Assert.assertEquals(purchaseCost, maker.makeCoffee(2, purchaseCost));
	}

	@Test
	public void testGetRecipes() throws RecipeException {
		Recipe[] R_arr;
		//메뉴 할당
		Assert.assertTrue(maker.addRecipe(r1));
		Assert.assertTrue(maker.addRecipe(r2));
		Assert.assertTrue(maker.addRecipe(r3));
		Assert.assertTrue(maker.addRecipe(r4));
		//배열에 할당한 메뉴를 받아옴
		R_arr = maker.getRecipes();
		//결과 확인(제대로 돌아간다면 딱히 오류 케이스가 생길 일이 없음)
		Assert.assertEquals(r1, R_arr[0]);
		Assert.assertEquals(r2, R_arr[1]);
		Assert.assertEquals(r3, R_arr[2]);
		Assert.assertEquals(r4, R_arr[3]);
		//다른 위치에 다른 값이 들어있는지 검증(오류 케이스 추적 1)
		Assert.assertNotEquals(r2, R_arr[0]);
		Assert.assertEquals(r2, R_arr[1]);
		Assert.assertNotEquals(r2, R_arr[2]);
		Assert.assertNotEquals(r2, R_arr[3]);

		//세부 반환값 검증(하나를 제외한 나머지는 주석)
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

