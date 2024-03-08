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
import com.chen.mint.Nodes.LetNode;
import com.chen.mint.Nodes.Node;
import com.chen.mint.Nodes.VarNode;
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

    @Test
    public void TestParseLetSimple() {
        //SETUP
        //input = "let x = 3";
        List<Token> input = new ArrayList<Token>();
        input.add(new Token(TokenType.LET));
        input.add(new Token(TokenType.VAR, "x"));
        input.add(new Token(TokenType.EQUAL));
        input.add(new Token(TokenType.NUM, 3));

        //TEST
        Node let = Parser.parse(input);

        //VERIFY
        Assert.assertNotNull("let must not be null", let);
        Assert.assertTrue("let must be ViewNode", (let instanceof LetNode));
        Assert.assertNotNull("let->left must not be null", let.left);
        Assert.assertNotNull("let->right must not be null", let.right);
        Node var = let.left;
        Assert.assertNull("let->left->left must be null", var.left);
        Assert.assertNull("let->left->right must be null", var.right);
        Assert.assertTrue("let->left must be VarNode", (var instanceof VarNode));
        Assert.assertEquals("let->left->num must be 0", 0, var.num);
        Assert.assertEquals("let->left->varName must be x", "x", var.varName);
        Node exp = let.right;
        Assert.assertNull("let->left->left must be null", exp.left);
        Assert.assertNull("let->left->right must be null", exp.right);
        Assert.assertTrue("let->left must be ExpNode", (exp instanceof ExpNode));
        Assert.assertEquals("let->left->num must be 3", 3, exp.num);
        Assert.assertNull("let->left->varName must be null", exp.varName);
    }

}
