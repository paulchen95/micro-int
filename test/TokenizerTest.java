package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.chen.mint.Token;
import com.chen.mint.TokenType;
import com.chen.mint.Tokenizer;

public class TokenizerTest {
    @Test
    public void TestTest() {
        String test = "let x = 3 + 5";
        List<Token> expected = new ArrayList<Token>();
        expected.add(new Token(TokenType.LET));
        expected.add(new Token(TokenType.VAR, "x"));
        expected.add(new Token(TokenType.EQUAL));
        expected.add(new Token(TokenType.NUM, 3));
        expected.add(new Token(TokenType.PLUS));
        expected.add(new Token(TokenType.NUM, 5));
    }

    String assertTokenizer(String test, List<Token> expected) {
        List<Token> actual = Tokenizer.tokenize(test);
        return null;
    }

}
