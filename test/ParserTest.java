package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.chen.mint.Parser;
import com.chen.mint.Token;
import com.chen.mint.TokenType;
import com.chen.mint.Nodes.ExpNode;
import com.chen.mint.Nodes.Node;
import com.chen.mint.Nodes.ViewNode;

public class ParserTest {
    @Test
    public void TestParseViewSimple() {
        //SETUP
        //input = "view 3";
        List<Token> input = new ArrayList<Token>();
        input.add(new Token(TokenType.VIEW));
        input.add(new Token(TokenType.NUM, 3));

        //TEST
        Node view = Parser.parse(input);

        //VERIFY
        Assert.assertNotNull("view must not be null", view);
        Assert.assertTrue("root must be ViewNode", (view instanceof ViewNode));
        Assert.assertNotNull("root->left must not be null", view.left);
        Assert.assertNull("root->right must be null", view.right);
        Node exp = view.left;
        Assert.assertNull("root->left->left must be null", exp.left);
        Assert.assertNull("root->left->right must be null", exp.right);
        Assert.assertTrue("root->left must be ExpNode", (exp instanceof ExpNode));
        Assert.assertEquals("root->left->num must be 3", 3, exp.num);
        Assert.assertNull("root->left->varName must be null", exp.varName);
    }

}
