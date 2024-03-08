package test;

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
import com.chen.mint.Nodes.NumNode;
import com.chen.mint.Nodes.VarNode;
import com.chen.mint.Nodes.ViewNode;

public class ParserTest {
    @Test
    public void testParseViewSimple() {
        //SETUP
        //input = "view 3";
        List<Token> input = new ArrayList<Token>();
        input.add(new Token(TokenType.VIEW));
        input.add(new Token(TokenType.NUM, 3));

        //TEST
        Node view = Parser.parse(input);

        //VERIFY
        //               ViewNode
        //              /
        //          ExpNode
        //         /
        //     NumNode(3)
        Assert.assertNotNull("view must not be null", view);
        Assert.assertTrue("root must be ViewNode", (view instanceof ViewNode));
        Assert.assertNotNull("root->left must not be null", view.left);
        Assert.assertNull("root->right must be null", view.right);
        Node exp = view.left;
        Assert.assertNotNull("root->left->left must not be null", exp.left);
        Assert.assertNull("root->left->right must be null", exp.right);
        Assert.assertTrue("root->left must be ExpNode", (exp instanceof ExpNode));
        Assert.assertEquals("root->left->num must be 0", 0, exp.num);
        Assert.assertNull("root->left->varName must be null", exp.varName);
        Node num = exp.left;
        Assert.assertNull("root->left->left->left must be null", num.left);
        Assert.assertNull("root->left->left->right must be null", num.right);
        Assert.assertTrue("root->left->left must be ExpNode", (num instanceof ExpNode));
        Assert.assertEquals("root->left->left->num must be 3", 3, num.num);
        Assert.assertNull("root->left->left->varName must be null", num.varName);
    }

    @Test
    public void testParseLetSimple() {
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
        //VERIFY
        //                LetNode
        //               /       \
        //       VarNode(x)      ExpNode
        //                      /
        //                   NumNode(3)

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
        Assert.assertNotNull("let->right->left must not be null", exp.left);
        Assert.assertNull("let->right->right must be null", exp.right);
        Assert.assertTrue("let->right must be ExpNode", (exp instanceof ExpNode));
        Assert.assertEquals("let->right->num must be 0", 0, exp.num);
        Assert.assertNull("let->right->varName must be null", exp.varName);
        Node num = exp.right;
        Assert.assertNull("let->right->left->left must be null", num.left);
        Assert.assertNull("let->right->left->right must be null", num.right);
        Assert.assertTrue("let->right->left must be ExpNode", (num instanceof NumNode));
        Assert.assertEquals("let->right->left->num must be 3", 3, num.num);
        Assert.assertNull("let->right->left->varName must be null", num.varName);
    }

    // @Test
    // public void testParseLet() {
    //     //SETUP
    //     //input = "let x = (3 * (x + y))";
    //     List<Token> input = new ArrayList<Token>();
    //     input.add(new Token(TokenType.LET));
    //     input.add(new Token(TokenType.VAR, "x"));
    //     input.add(new Token(TokenType.EQUAL));
    //     input.add(new Token(TokenType.NUM, 3));

    //     //expected node setup
    //     Node root = new LetNode();
    //     Node var = new VarNode();
    //     var.varName = "x";
    //     root.left = var;
    //     Node exp = new ExpNode();
    //     exp.num = 3;
    //     root.right = exp;

    //     //TEST
    //     Node let = Parser.parse(input);

    //     //VERIFY
    //     nodeCompare(root, let, "root");
    //     Assert.assertNotNull("let must not be null", let);
    //     Assert.assertTrue("let must be ViewNode", (let instanceof LetNode));
    //     Assert.assertNotNull("let->left must not be null", let.left);
    //     Assert.assertNotNull("let->right must not be null", let.right);
    //     Node var = let.left;
    //     Assert.assertNull("let->left->left must be null", var.left);
    //     Assert.assertNull("let->left->right must be null", var.right);
    //     Assert.assertTrue("let->left must be VarNode", (var instanceof VarNode));
    //     Assert.assertEquals("let->left->num must be 0", 0, var.num);
    //     Assert.assertEquals("let->left->varName must be x", "x", var.varName);
    //     Node exp = let.right;
    //     Assert.assertNull("let->left->left must be null", exp.left);
    //     Assert.assertNull("let->left->right must be null", exp.right);
    //     Assert.assertTrue("let->left must be ExpNode", (exp instanceof ExpNode));
    //     Assert.assertEquals("let->left->num must be 3", 3, exp.num);
    //     Assert.assertNull("let->left->varName must be null", exp.varName);
    // }

    // private String nodeCompare(Node expected, Node actual, String progress) {
    //     if (expected == null) {
    //         Assert.assertNull(progress + " must be null", actual);
    //     } else if (expected instanceof LetNode) {
    //         Assert.assertTrue(progress + " must be LetNode", (expected instanceof LetNode));
    //     } else if (expected instanceof ViewNode) {
    //         Assert.assertTrue(progress + " must be ViewNode", (expected instanceof ViewNode));
    //     } else if (expected instanceof ExpNode) {
    //         Assert.assertTrue(progress + " must be ExpNode", (expected instanceof ExpNode));
    //     } else if (expected instanceof VarNode) {
    //         Assert.assertTrue(progress + " must be VarNode", (expected instanceof VarNode));
    //     } else if (expected instanceof LetNode) {
    //         Assert.assertTrue(progress + " must be LetNode", (expected instanceof LetNode));
    //     } else if (expected instanceof LetNode) {
    //         Assert.assertTrue(progress + " must be LetNode", (expected instanceof LetNode));
    //     }
    // }
}
