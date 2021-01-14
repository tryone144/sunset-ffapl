package sunset.gui.search.advanced;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import sunset.gui.search.advanced.exception.*;
import sunset.gui.search.advanced.interfaces.IAdvancedSearchReplace;

public class AdvancedReplaceTest {

	static IAdvancedSearchReplace _searchReplace;

	@BeforeAll
	static void setUp() throws Exception {
		_searchReplace = new AdvancedSearchReplace();
	}

	@Test
	void testAllVariables() {
		try {
			_searchReplace.find("aabbcc", "%1", 0, false);
			Assert.assertEquals("aabbcc", _searchReplace.replaceVariables("%1", _searchReplace.getCaptures()));
			Assert.assertEquals("daabbccf", _searchReplace.replaceVariables("d%1f", _searchReplace.getCaptures()));
			Assert.assertEquals("aabbccf", _searchReplace.replaceVariables("%1f", _searchReplace.getCaptures()));
			Assert.assertEquals("daabbcc", _searchReplace.replaceVariables("d%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("abc", "a%1c", 0, false);
			Assert.assertEquals("b", _searchReplace.replaceVariables("%1", _searchReplace.getCaptures()));
			Assert.assertEquals("abc", _searchReplace.replaceVariables("a%1c", _searchReplace.getCaptures()));
			Assert.assertEquals("bbb", _searchReplace.replaceVariables("%1%1%1", _searchReplace.getCaptures()));
			Assert.assertEquals("bab", _searchReplace.replaceVariables("%1a%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("aaaaabcd", "%0b", 0, false);
			Assert.assertEquals("aaaaaaa", _searchReplace.replaceVariables("a%0a", _searchReplace.getCaptures()));
			
			_searchReplace.find("aaaaabcd", "b%9", 0, false);
			Assert.assertEquals("cd", _searchReplace.replaceVariables("%9", _searchReplace.getCaptures()));
			
			_searchReplace.find("aabbccdd", "%0c%1", 0, false);
			Assert.assertEquals("cddxaabb", _searchReplace.replaceVariables("%1x%0", _searchReplace.getCaptures()));
			Assert.assertEquals("cddaabbcddaabb", _searchReplace.replaceVariables("%1%0%1%0", _searchReplace.getCaptures()));
			
			_searchReplace.find("aabbcd", "a%1b%2c", 0, false);
			Assert.assertEquals("aabbc", _searchReplace.replaceVariables("a%1b%2c", _searchReplace.getCaptures()));
			Assert.assertEquals("babca", _searchReplace.replaceVariables("%2abc%1", _searchReplace.getCaptures()));
			Assert.assertEquals("aba", _searchReplace.replaceVariables("%1%2%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("aabccdeefgg", "%1b%2d%3f%4", 0, false);
			Assert.assertEquals("ggeeccaa", _searchReplace.replaceVariables("%4%3%2%1", _searchReplace.getCaptures()));
			Assert.assertEquals("aaaacccceeeegggg", _searchReplace.replaceVariables("%1%1%2%2%3%3%4%4", _searchReplace.getCaptures()));
			
			_searchReplace.find("ac", "a%7c", 0, false);
			Assert.assertEquals("ca", _searchReplace.replaceVariables("c%7%7%7a", _searchReplace.getCaptures()));
			
			_searchReplace.find("aabccdeefgg", "%8b%4d%0f%5", 0, false);
			Assert.assertEquals("eebggdccfaa", _searchReplace.replaceVariables("%0b%5d%4f%8", _searchReplace.getCaptures()));			
			
			_searchReplace.find("bdf", "%8b%4d%0f%5", 0, false);
			Assert.assertEquals("bdf", _searchReplace.replaceVariables("%8b%4d%0f%5", _searchReplace.getCaptures()));
			
			_searchReplace.find("abcdaabbccdde", "a%8b%4c%0d%5e", 0, false);
			Assert.assertEquals("eaabbccdddcba", _searchReplace.replaceVariables("e%5d%0c%4b%8a", _searchReplace.getCaptures()));
			
			_searchReplace.find("abcdababcdaabbccdde", "a%8b%4c%0d%5e", 0, false);
			Assert.assertEquals("ababcdaabbccddababcdaabbccdd", _searchReplace.replaceVariables("%5%0%4%5%8", _searchReplace.getCaptures()));
			
		} catch (Exception e) {
			Assert.fail();
		} 
	}
	
	@Test
	void testNotAllVariables() {
		try {
			_searchReplace.find("aabbccdd", "%0c%1", 0, false);
			Assert.assertEquals("", _searchReplace.replaceVariables("", _searchReplace.getCaptures()));
			Assert.assertEquals("x", _searchReplace.replaceVariables("x", _searchReplace.getCaptures()));
			Assert.assertEquals("cddx", _searchReplace.replaceVariables("%1x", _searchReplace.getCaptures()));
			Assert.assertEquals("xaabb", _searchReplace.replaceVariables("x%0", _searchReplace.getCaptures()));
			Assert.assertEquals("aabbaabb", _searchReplace.replaceVariables("%0%0", _searchReplace.getCaptures()));
			
			_searchReplace.find("aabbcd", "a%1b%2c", 0, false);
			Assert.assertEquals("aabca", _searchReplace.replaceVariables("%1abc%1", _searchReplace.getCaptures()));
			Assert.assertEquals("aaa", _searchReplace.replaceVariables("%1%1%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("aabccdeefgg", "%1b%2d%3f%4", 0, false);
			Assert.assertEquals("abc", _searchReplace.replaceVariables("abc", _searchReplace.getCaptures()));
			Assert.assertEquals("ggccggcc", _searchReplace.replaceVariables("%4%2%4%2", _searchReplace.getCaptures()));
			Assert.assertEquals("aaaacccc", _searchReplace.replaceVariables("%1%1%2%2", _searchReplace.getCaptures()));
			
			_searchReplace.find("aabccdeefgg", "%8b%4d%0f%5", 0, false);
			Assert.assertEquals("", _searchReplace.replaceVariables("", _searchReplace.getCaptures()));
			Assert.assertEquals("ee", _searchReplace.replaceVariables("%0", _searchReplace.getCaptures()));
			
			_searchReplace.find("bdf", "%8b%4d%0f%5", 0, false);
			Assert.assertEquals("bdf", _searchReplace.replaceVariables("bdf", _searchReplace.getCaptures()));
			Assert.assertEquals("", _searchReplace.replaceVariables("%8%8", _searchReplace.getCaptures()));
			
			_searchReplace.find("abcdaabbccdde", "a%8b%4c%0d%5e", 0, false);
			Assert.assertEquals("aaabbccddaaa", _searchReplace.replaceVariables("a%5a%8a%4a", _searchReplace.getCaptures()));
			
			_searchReplace.find("abcdababcdaabbccdde", "a%8b%4c%0d%5e", 0, false);
			Assert.assertEquals("abcababcdaabbccddxyz", _searchReplace.replaceVariables("abc%5xyz", _searchReplace.getCaptures()));
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	void testSpecialCharacters() {
		try {
			_searchReplace.find("%%%", "%1%", 0, false);
			Assert.assertEquals("", _searchReplace.replaceVariables("%1%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("$%�", "%1�", 0, false);
			Assert.assertEquals("$%$%", _searchReplace.replaceVariables("%1%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("!\"�$%&\\()=", "\"%1\\", 0, false);
			Assert.assertEquals("\\�$%&\\", _searchReplace.replaceVariables("\\%1\\", _searchReplace.getCaptures()));
			
			_searchReplace.find("!\"�$%&\\()=", "%4\"%1\\%6", 0, false);
			Assert.assertEquals("-�$%&-!-()=-", _searchReplace.replaceVariables("-%1-%4-%6-", _searchReplace.getCaptures()));
			
			_searchReplace.find("!\"�$%&\\()=", "!%0=", 0, false);
			Assert.assertEquals("=\"�$%&\\()!", _searchReplace.replaceVariables("=%0!", _searchReplace.getCaptures()));
			
			_searchReplace.find("!\"�$\n%&\\()=", "%0\n%1", 0, false);
			Assert.assertEquals("\n%&\\()=\t!\"�$\b", _searchReplace.replaceVariables("\n%1\t%0\b", _searchReplace.getCaptures()));
			
			_searchReplace.find("!\"�$%&\\()=", "%1!%2", 0, false);
			Assert.assertEquals("!\"�$%&\\()=\"�$%&\\()=!", _searchReplace.replaceVariables("!%2%1%2!", _searchReplace.getCaptures()));
			
			_searchReplace.find("!\"�$%&\\()=", "!%9", 0, false);
			Assert.assertEquals("\"�$%&\\()=!", _searchReplace.replaceVariables("%9!", _searchReplace.getCaptures()));
			
			_searchReplace.find("1%%%%1%%%1%%%%1", "%1%", 0, false);
			Assert.assertEquals("1%-1%", _searchReplace.replaceVariables("%1%-%1%", _searchReplace.getCaptures()));
			
			_searchReplace.find("$%%%%&%%%?%%%%!", "$%1&%2?%3!", 0, false);
			Assert.assertEquals("%%%%%%%%%%%", _searchReplace.replaceVariables("%3%2%1", _searchReplace.getCaptures()));
			Assert.assertEquals("%%%%-%%%-%%%%", _searchReplace.replaceVariables("%3-%2-%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("$%%%%&%%%?%%%%!", "%4$%1&%2?%3!%0", 0, false);
			Assert.assertEquals("%%%%%%%%%%%", _searchReplace.replaceVariables("%0%1%2%3%4", _searchReplace.getCaptures()));
			
			_searchReplace.find("$$%$$$%$$$%", "%1%$%2", 0, false);
			Assert.assertEquals("$$$$%$$$%", _searchReplace.replaceVariables("%1%2", _searchReplace.getCaptures()));
			Assert.assertEquals("$$%$$$%$$$%", _searchReplace.replaceVariables("%1%$%2", _searchReplace.getCaptures()));
			
			_searchReplace.find("$$%$$$%$?$%", "$$%1%$?%2", 0, false);
			Assert.assertEquals("%$$$%$$$$%", _searchReplace.replaceVariables("%1%1%2", _searchReplace.getCaptures()));

			_searchReplace.find("%5abc%6", "%0abc%1", 0, false);
			Assert.assertEquals("%5", _searchReplace.replaceVariables("%0", _searchReplace.getCaptures()));
			Assert.assertEquals("%6", _searchReplace.replaceVariables("%1", _searchReplace.getCaptures()));
			Assert.assertEquals("%5%6%5%6", _searchReplace.replaceVariables("%0%1%0%1", _searchReplace.getCaptures()));
			
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	void testEscapedVariables() {
		try {			
			_searchReplace.find("aab%1bcc", "%%1%1", 0, false);
			Assert.assertEquals("bcc", _searchReplace.replaceVariables("%1", _searchReplace.getCaptures()));
			Assert.assertEquals("%1", _searchReplace.replaceVariables("%%1", _searchReplace.getCaptures()));
			Assert.assertEquals("%1bcc%1", _searchReplace.replaceVariables("%%1%1%%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("aab%1bcc", "%1%%1", 0, false);
			Assert.assertEquals("%1aabaab%1", _searchReplace.replaceVariables("%%1%1%1%%1", _searchReplace.getCaptures()));
			Assert.assertEquals("aab%1%1aab", _searchReplace.replaceVariables("%1%%1%%1%1", _searchReplace.getCaptures()));
			Assert.assertEquals("aab%1aab%1", _searchReplace.replaceVariables("%1%%1%1%%1", _searchReplace.getCaptures()));
			Assert.assertEquals("%1aab%1aab", _searchReplace.replaceVariables("%%1%1%%1%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("aab%1bcc", "%1%%1%2", 0, false);
			Assert.assertEquals("aab%9bcc", _searchReplace.replaceVariables("%1%%9%2", _searchReplace.getCaptures()));
			Assert.assertEquals("aab%%9bcc", _searchReplace.replaceVariables("%1%%%9%2", _searchReplace.getCaptures()));
			
			_searchReplace.find("%1abc%1", "%%1a%1c%%1", 0, false);
			Assert.assertEquals("%1abc%1", _searchReplace.replaceVariables("%%1a%1c%%1", _searchReplace.getCaptures()));
			Assert.assertEquals("ba%1cb", _searchReplace.replaceVariables("%1a%%1c%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("%1abc%1", "%%1%1%%1", 0, false);
			Assert.assertEquals("%1abc%%1abc%1", _searchReplace.replaceVariables("%%1%1%%%1%1%%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("%1abc%1", "%0%%1%1%%1%2", 0, false);
			Assert.assertEquals("%0%1abc%2", _searchReplace.replaceVariables("%%0%0%%1%1%%2%2", _searchReplace.getCaptures()));
			
			_searchReplace.find("%1a%1c%1", "%%1%1%%1%2%%1", 0, false);
			Assert.assertEquals("%1ac%1", _searchReplace.replaceVariables("%%1%1%2%%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("aab%1%2%3%4bcc", "%8%%1%%2%%3%%4%9", 0, false);
			Assert.assertEquals("%8%9aabbcc%8%9", _searchReplace.replaceVariables("%%8%%9%8%9%%8%%9", _searchReplace.getCaptures()));
			
			_searchReplace.find("aab%1%2%4bcc", "%8%%1%2b%9", 0, false);
			Assert.assertEquals("%1%2%4%1", _searchReplace.replaceVariables("%%1%2%%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("aab%1%2%4bcc", "%8%%2%9", 0, false);
			Assert.assertEquals("%4bcc%3aab%1", _searchReplace.replaceVariables("%9%%3%8", _searchReplace.getCaptures()));
			
			_searchReplace.find("%1aaaaab", "%%1%1b", 0, false);
			Assert.assertEquals("aaaaa%1aaaaa", _searchReplace.replaceVariables("%1%%1%1", _searchReplace.getCaptures()));
			
			_searchReplace.find("aabbc%1d", "a%1%%1", 0, false);
			Assert.assertEquals("%1abbc%1", _searchReplace.replaceVariables("%%1%1%%1", _searchReplace.getCaptures()));
			
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	void testUndeclaredVariables() {
		UndeclaredVariableException e;
		String msg1 = "Variable %";
		String msg2 = " from replace text has not been used in search pattern!"; 
		
		e = Assert.assertThrows(UndeclaredVariableException.class, () -> {
			_searchReplace.find("abc", "%1b%2", 0, false);
			_searchReplace.replaceVariables("%3", _searchReplace.getCaptures());
		  });
		
		Assert.assertEquals(e.getMessage(), msg1 + "3" + msg2);
		
		e = Assert.assertThrows(UndeclaredVariableException.class, () -> {
			_searchReplace.find("abc", "a%1c%2", 0, false);
		  	_searchReplace.replaceVariables("%1%2%3", _searchReplace.getCaptures());
		  });
		
		Assert.assertEquals(e.getMessage(), msg1 + "3" + msg2);
		
		e = Assert.assertThrows(UndeclaredVariableException.class, () -> {
			_searchReplace.find("abc", "%1b%2", 0, false);
		  	_searchReplace.replaceVariables("%0%1%2", _searchReplace.getCaptures());
		  });
		
		Assert.assertEquals(e.getMessage(), msg1 + "0" + msg2);
		
		e = Assert.assertThrows(UndeclaredVariableException.class, () -> {
			_searchReplace.find("abc", "a%1b%2c%3", 0, false);
		  	_searchReplace.replaceVariables("%0", _searchReplace.getCaptures());
		  });
		
		Assert.assertEquals(e.getMessage(), msg1 + "0" + msg2);
		
		e = Assert.assertThrows(UndeclaredVariableException.class, () -> {
			_searchReplace.find("abc", "%0a%1c%2", 0, false);
		  	_searchReplace.replaceVariables("%1%9", _searchReplace.getCaptures());
		  });
		
		Assert.assertEquals(e.getMessage(), msg1 + "9" + msg2);
		
		e = Assert.assertThrows(UndeclaredVariableException.class, () -> {
			_searchReplace.find("abc", "%0b%1c%2", 0, false);
		  	_searchReplace.replaceVariables("%1%4", _searchReplace.getCaptures());
		  });
		
		Assert.assertEquals(e.getMessage(), msg1 + "4" + msg2);
		
		e = Assert.assertThrows(UndeclaredVariableException.class, () -> {
			_searchReplace.find("abcdefghi", "a%1c%2e%3g%4i", 0, false);
		  	_searchReplace.replaceVariables("%1%2%3%4%5%6%7%8%9%0", _searchReplace.getCaptures());
		  });
		
		Assert.assertEquals(e.getMessage(), msg1 + "5" + msg2);
	}
	
	@Test
	void testComplexReplacements() {
		try {
			/* ______________________________ TEST ______________________________ */
			
			String text = "Assert.assertTrue(_searchReplace.find(\"!\\\"�$%&\\\\()=\", \"!%0=\", 0, false));\n"
					+ "\t\t\tcheckResult(0,10,new String[] {\"\\\"�$%&\\\\()\", null, null, null, null, null, null, null, null, null});";
			String pattern = "Assert.assertTrue(%1);\n\t\t\tcheckResult(%2);";
			
			_searchReplace.find(text, pattern, 0, false);
			
			String newText = "_searchReplace.find(\"!\\\"�$%&\\\\()=\", \"!%0=\", 0, false);\n"
					+ "\t\t\tAssert.assertEquals(\"\", _searchReplace.getReplaceString(\"\"));";
			String replaceText = "%1;\n\t\t\tAssert.assertEquals(\"\", _searchReplace.getReplaceString(\"\"));";
			
			Assert.assertEquals(newText, _searchReplace.replaceVariables(replaceText, _searchReplace.getCaptures()));
			
			/* ______________________________ TEST ______________________________ */
			
			text = "e = Assert.assertThrows(InvalidPatternException.class, () -> {"
					+ "\n\t\t\t_searchReplace.find(\"abc\", \"a%1b%2%3\", 0, false);\n\t\t  });" 
					+ "\n\t\tAssert.assertEquals(e.getMessage(), msg + \"%2%3\");";
			
			pattern = "e = Assert.assertThrows(InvalidPatternException.class, () -> {%1});%3Assert.assertEquals(e.getMessage(), %2);";
			
			_searchReplace.find(text, pattern, 0, false);
			
			newText = "e = Assert.assertThrows(UndeclaredVariableException.class, () -> {"
					+ "\n\t\t\t_searchReplace.find(\"abc\", \"a%1b%2%3\", 0, false);\n\t\t  "
					+ "\n\t\t\t_searchReplace.getReplaceString(\"\")\n\t\t  });"
					+ "\n\t\tAssert.assertEquals(e.getMessage(), msg1 + \"\" + msg2);";
			replaceText = "e = Assert.assertThrows(UndeclaredVariableException.class, () -> {"
					+ "%1\n\t\t\t_searchReplace.getReplaceString(\"\")\n\t\t  });"
					+ "%3Assert.assertEquals(e.getMessage(), msg1 + \"\" + msg2);";
			
			Assert.assertEquals(newText, _searchReplace.replaceVariables(replaceText, _searchReplace.getCaptures()));
			
			/* ______________________________ TEST ______________________________ */
			
			text = "Assert.assertTrue(_logic.search(\"abcdefghi\", \"def\", 0, false, false));";
			
			pattern = "Assert.assert%0(_logic.search(%1));";
			
			_searchReplace.find(text, pattern, 0, false);
			
			newText = "Assert.assertTrue(_logic.searchRegex(\"abcdefghi\", \"def\", 0, false, false, false));";
			replaceText = "Assert.assert%0(_logic.searchRegex(%1, false));";
			
			Assert.assertEquals(newText, _searchReplace.replaceVariables(replaceText, _searchReplace.getCaptures()));
			
			/* ______________________________ TEST ______________________________ */
			
			text = "this is a text with an inline equation $a^2+b^2=c^2$ and a centered equation\n"
					+ "\\[\nx^2 \\geq 0\n\\]\nto be replaced";
			
			pattern = "$%1$%2\\[\n%3\n\\]";
			
			_searchReplace.find(text, pattern, 0, false);
			
			newText = "$x^2 \\geq 0$ and a centered equation\n"
					+ "\\[\na^2+b^2=c^2\n\\]";
			replaceText = "$%3$%2\\[\n%1\n\\]";
			
			Assert.assertEquals(newText, _searchReplace.replaceVariables(replaceText, _searchReplace.getCaptures()));
			
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@AfterAll
	static void tearDown() {
		_searchReplace = null;
	}
}
