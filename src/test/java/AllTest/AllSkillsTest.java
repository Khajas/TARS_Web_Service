package AllTest;

import org.junit.Assert;

import org.junit.Test;

import com.khajas.resource.NQTD;
import com.khajas.service.ApiCall;
import com.khajas.service.Intents;
import com.khajas.service.currency.CurrencyApi;

public class AllSkillsTest {
	private NQTD qt;
	public AllSkillsTest(){
		qt=new NQTD("2601:242:4000:be10:1acf:5eff:fedc:9a68","");	// TEST IP that belongs to sycamore on 5/8/2017
	}
	/**
	 * JUnit Test for personal identification skills
	 */
	@Test
	public void personal_skill_test(){
		Intents i=ApiCall.searchIntent("Hello");
		Assert.assertEquals("Hi",i.getResponse());
	}
	/**
	 * JUnit test for maths_skills_test
	 */
	@Test
	public void maths_skills_test(){
		qt.setQuery("1+2 ");
		Assert.assertEquals(" 1+2 is 3",qt.detectServiceType());
		qt.setQuery("10 + 21 ");
		Assert.assertEquals(" 10+21 is 31",qt.detectServiceType());
		qt.setQuery("10 plus 21 ");
		Assert.assertEquals(" 10+21 is 31",qt.detectServiceType());
		qt.setQuery("1x2 ");
		Assert.assertEquals(" 1*2 is 2",qt.detectServiceType());
		qt.setQuery("1-2 ");
		Assert.assertEquals(" 1-2 is -1",qt.detectServiceType());
		qt.setQuery("1 X 2 ");
		Assert.assertEquals(" 1*2 is 2",qt.detectServiceType());
	}
	/**
	 * JUnit Test for Currency Skills
	 */
	@Test
	public void currency_skills_test(){
		qt.setQuery("1$");
		String actual=qt.detectServiceType();
		CurrencyApi ca=new CurrencyApi("USD");
<<<<<<< HEAD
		String expected=ca.serve("");
=======
<<<<<<< HEAD
		String expected=ca.serve("");
=======
		String expected=ca.serve("1 USD: ");
>>>>>>> origin/master
>>>>>>> origin/master
		Assert.assertEquals(expected, actual);
	}
}
