package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.chen.mint.Token;
import com.chen.mint.TokenType;
import com.chen.mint.Tokenizer;

public class TokenizerTest {
    @Test
    public void TestViewSimple() {
        String test = "view 3";
        List<Token> expected = new ArrayList<Token>();
        expected.add(new Token(TokenType.VIEW));
        expected.add(new Token(TokenType.NUM, 3));
        String error = assertTokenizer(test, expected);
        if (error != null) {
            assertTrue(error, false);
        }
    }

    @Test
    public void TestLetSimple() {
        String test = "let x = 3 + 5";
        List<Token> expected = new ArrayList<Token>();
        expected.add(new Token(TokenType.LET));
        expected.add(new Token(TokenType.VAR, "x"));
        expected.add(new Token(TokenType.EQUAL));
        expected.add(new Token(TokenType.NUM, 3));
        expected.add(new Token(TokenType.PLUS));
        expected.add(new Token(TokenType.NUM, 5));
        String error = assertTokenizer(test, expected);
        if (error != null) {
            assertTrue(error, false);
        }
    }

    String assertTokenizer(String test, List<Token> expected) {
        String error = null;
        List<Token> actual = Tokenizer.tokenize(test);
        if (actual.size() != expected.size()) {
            error = "Actual list of Tokens are not expected.";
        } else {
            for (int index = 0; index < expected.size(); index++) {
                if (!expected.get(index).equals(actual.get(index))) {
                    error = "Diff found at index = " + index + ".";
                    break;
                }
            }
        }

        if (error != null) {
            error += "\nExpected:\nSize = " + expected.size() + "\n";
            for(Token token : expected) {
                String tokenTypeName = token.type.name();
                error += (tokenTypeName + "\n");
            }
            error += "\nActual:\n";
            error += "Size = " + actual.size() + "\n";
            for(Token token : actual) {
                String tokenTypeName = token.type.name();
                error += (tokenTypeName + "\n");
            }
        }
        return error;
    }
}
