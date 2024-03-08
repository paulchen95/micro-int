package test;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.chen.mint.Nodes.OpNode;
import com.chen.mint.Nodes.OpNode;
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
        Assert.assertTrue("root->left->left must be NumNode", (num instanceof NumNode));
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
        Node num = exp.left;
        Assert.assertNull("let->right->left->left must be null", num.left);
        Assert.assertNull("let->right->left->right must be null", num.right);
        Assert.assertTrue("let->right->left must be ExpNode", (num instanceof NumNode));
        Assert.assertEquals("let->right->left->num must be 3", 3, num.num);
        Assert.assertNull("let->right->left->varName must be null", num.varName);
    }

    @Test
    public void testParseLet() {
        //SETUP
        //input = "let x = (3 * (x + y))";
        // List<Token> input = Arrays.asList(new Token[] {
        //     new Token(TokenType.LET),
        //     new Token(TokenType.VAR, "x"),
        //     new Token(TokenType.EQUAL),
        //     new Token(TokenType.LEFT_PARAN),
        //     new Token(TokenType.NUM, 3),
        //     new Token(TokenType.STAR),
        //     new Token(TokenType.LEFT_PARAN),
        //     new Token(TokenType.VAR, "x"),
        //     new Token(TokenType.PLUS),
        //     new Token(TokenType.VAR, "y"),
        //     new Token(TokenType.RIGHT_PARAN),
        //     new Token(TokenType.RIGHT_PARAN)
        // });

        List<Token> input = new ArrayList<Token>();
        input.add(new Token(TokenType.LET));
        input.add(new Token(TokenType.VAR, "x"));
        input.add(new Token(TokenType.EQUAL));
        input.add(new Token(TokenType.LEFT_PARAN));
        input.add(new Token(TokenType.NUM, 3));
        input.add(new Token(TokenType.STAR));
        input.add(new Token(TokenType.LEFT_PARAN));
        input.add(new Token(TokenType.VAR, "x"));
        input.add(new Token(TokenType.PLUS));
        input.add(new Token(TokenType.VAR, "y"));
        input.add(new Token(TokenType.RIGHT_PARAN));
        input.add(new Token(TokenType.RIGHT_PARAN));

        //expected node setup
        //                          LetNode
        //                          /       \
        //                  VarNode(x)       ExpNode(paran)
        //                                  /
        //                              OpNode(*)
        //                             /         \
        //                      ExpNode           ExpNode(paran)
        //                     /                 /
        //              NumNode(3)           OpNode(+)
        //                                  /         \
        //                           ExpNode           ExpNode
        //                          /                 /
        //                      VarNode(x)        VarNode(y)
        //
        Node expected = new LetNode();
        Node var = new VarNode();
        var.varName = "x";
        expected.left = var;
        Node exp = new ExpNode();
        expected.right = exp;
        Node opNodeStar = new OpNode();
        opNodeStar.type = TokenType.STAR;
        exp.left = opNodeStar;
        Node expNode1 = new ExpNode();
        Node numNode3 = new NumNode();
        numNode3.num = 3;
        expNode1.left = numNode3;
        Node expNodeParan = new ExpNode();
        opNodeStar.right = expNodeParan;
        opNodeStar.left = expNode1;
        Node opNodePlus = new OpNode();
        opNodePlus.type = TokenType.PLUS;
        expNodeParan.left = opNodePlus;
        Node expNode2 = new ExpNode();
        Node expNode3 = new ExpNode();
        Node varNodeX = new VarNode();
        varNodeX.varName = "x";
        Node varNodeY = new VarNode();
        varNodeY.varName = "y";
        opNodePlus.left = expNode2;
        opNodePlus.right = expNode3;
        expNode2.left = varNodeX;
        expNode3.left = varNodeY;

        //TEST
        Node let = Parser.parse(input);

        //VERIFY
        nodeCompare(expected, let, "root");
    }

    private void nodeCompare(Node expected, Node actual, String progress) {
        if (expected == null) {
            Assert.assertNull(progress + " must be null", actual);
            return;
        } else if (expected instanceof LetNode) {
            Assert.assertTrue(progress + " must be LetNode", (actual instanceof LetNode));
        } else if (expected instanceof ViewNode) {
            Assert.assertTrue(progress + " must be ViewNode", (actual instanceof ViewNode));
        } else if (expected instanceof ExpNode) {
            Assert.assertTrue(progress + " must be ExpNode", (actual instanceof ExpNode));
        } else if (expected instanceof VarNode) {
            Assert.assertTrue(progress + " must be VarNode", (actual instanceof VarNode));
            Assert.assertEquals(progress + ".varName must equal", expected.varName, actual.varName);
        } else if (expected instanceof NumNode) {
            Assert.assertTrue(progress + " must be NumNode", (actual instanceof NumNode));
            Assert.assertEquals(progress + ".num must equal", expected.num, actual.num);
        } else if (expected instanceof OpNode) {
            Assert.assertTrue(progress + " must be OpNode", (actual instanceof OpNode));
            Assert.assertEquals(progress + ".type must equal", expected.type, actual.type);
        }
        nodeCompare(expected.left, actual.left, progress + ".left");
        nodeCompare(expected.right, actual.right, progress + ".right");
    }

    @Test
    public void testParseLet2() {
    //SETUP
        //input = "let x = ((x + y) * 3)";

        List<Token> input = new ArrayList<Token>();
        input.add(new Token(TokenType.LET));
        input.add(new Token(TokenType.VAR, "x"));
        input.add(new Token(TokenType.EQUAL));
        input.add(new Token(TokenType.LEFT_PARAN));
        input.add(new Token(TokenType.LEFT_PARAN));
        input.add(new Token(TokenType.VAR, "x"));
        input.add(new Token(TokenType.PLUS));
        input.add(new Token(TokenType.VAR, "y"));
        input.add(new Token(TokenType.RIGHT_PARAN));
        input.add(new Token(TokenType.STAR));
        input.add(new Token(TokenType.NUM, 3));
        input.add(new Token(TokenType.RIGHT_PARAN));

        //expected node setup
        //                          LetNode
        //                          /       \
        //                  VarNode(x)       ExpNode(paran)
        //                                  /
        //                              OpNode(*)
        //                             /         \
        //                 ExpNode(paran)       ExpNode(paran)
        //                     /                 /
        //              OpNode(+)           NumNode(3)
        //             /       \        
        //      ExpNode     ExpNode    
        //       /             \     
        //   VarNode(x)     VarNode(y)    
        //
        Node expected = new LetNode();
        Node var = new VarNode();
        var.varName = "x";
        expected.left = var;
        Node exp = new ExpNode();
        expected.right = exp;
        Node opNodeStar = new OpNode();
        opNodeStar.type = TokenType.STAR;
        exp.left = opNodeStar;
        Node expNode1 = new ExpNode();
        Node numNode3 = new NumNode();
        numNode3.num = 3;
        expNode1.left = numNode3;
        Node expNodeParan = new ExpNode();
        opNodeStar.right = expNodeParan;
        opNodeStar.left = expNode1;
        Node opNodePlus = new OpNode();
        opNodePlus.type = TokenType.PLUS;
        expNodeParan.left = opNodePlus;
        Node expNode2 = new ExpNode();
        Node expNode3 = new ExpNode();
        Node varNodeX = new VarNode();
        varNodeX.varName = "x";
        Node varNodeY = new VarNode();
        varNodeY.varName = "y";
        opNodePlus.left = expNode2;
        opNodePlus.right = expNode3;
        expNode2.left = varNodeX;
        expNode3.left = varNodeY;

        //TEST
        Node let = Parser.parse(input);

        //VERIFY
        nodeCompare(expected, let, "root");
    }

}


//
    //@Test
    //public void testParseLetWithOp() {
    //    //SETUP
    //    //input = "let z = 3 + 7";
    //    List<Token> input = new ArrayList<Token>();
    //    input.add(new Token(TokenType.LET));
    //    input.add(new Token(TokenType.VAR, "z"));
    //    input.add(new Token(TokenType.EQUAL));
    //    input.add(new Token(TokenType.NUM, 3));
    //    input.add(new Token(TokenType.PLUS));
    //    input.add(new Token(TokenType.NUM, 7));
//
    //    Node letWithOp = Parser.parse(input);
//
    //    //VERIFY
    //    //VERIFY
    //    //                LetNode
    //    //               /       \
    //    //       VarNode(z)      ExpNode
    //    //                      /
    //    //                   OpNode(+)
    //    //                  /    \
    //    //         NumNode(3)   NumNode(7)
//
    //    Assert.assertNotNull("letWithOp must not be null", letWithOp);
    //    Assert.assertTrue("letWithOp must be LetNode", (letWithOp instanceof LetNode));
    //    Assert.assertNotNull("letWithOp->left must not be null", letWithOp.left);
    //    Assert.assertNotNull("letWithOp->right must not be null", letWithOp.right);
    //    Node var = letWithOp.left;
    //    Assert.assertNull("letWithOp->left->left must be null", var.left);
    //    Assert.assertNull("letWithOp->left->right must be null", var.right);
    //    Assert.assertTrue("letWithOp->left must be VarNode", (var instanceof VarNode));
    //    Assert.assertEquals("letWithOp->left->num must be 0", 0, var.num);
    //    Assert.assertEquals("letWithOp->left->varName must be z", "z", var.varName);
    //    Node exp = letWithOp.right;
    //    Assert.assertNotNull("letWithOp->right->left must not be null", exp.left);
    //    Assert.assertNull("letWithOp->right->right must be null", exp.right);
    //    Assert.assertTrue("letWithOp->right must be ExpNode", (exp instanceof ExpNode));
    //    Assert.assertEquals("letWithOp->right->num must be 0", 0, exp.num);
    //    Assert.assertNull("letWithOp->right->varName must be null", exp.varName);
    //    Node op = exp.left;
    //    Assert.assertNotNull("letWithOp->right->left->left must not be null", op.left);
    //    Assert.assertNotNull("letWithOp->right->left->right must not be null", op.right);
    //    Assert.assertTrue("letWithOp->right->left must be OpNode", (op instanceof OpNode));
    //    Assert.assertEquals("letWithOp->right->left->TokenType must be PLUS", TokenType.PLUS, op.type);
    //    Assert.assertEquals("letWithOp->right->left->num must be 0", 0, op.num);
    //    Assert.assertNull("letWithOp->right->left->varName must be null", op.varName);
    //    Node num3 = op.left;
    //    Assert.assertNull("letWithOp->right->left->left->left must be null", num3.left);
    //    Assert.assertNull("letWithOp->right->left->left->right must be null", num3.right);
    //    Assert.assertTrue("letWithOp->right->left->left must be NumNode", (op instanceof NumNode));
    //    Assert.assertEquals("letWithOp->right->left->left->num must be 3", 3, num3.num);
    //    Node num7 = op.right;
    //    Assert.assertNull("letWithOp->right->left->right->left must be null", num7.left);
    //    Assert.assertNull("letWithOp->right->left->right->right must be null", num7.right);
    //    Assert.assertTrue("letWithOp->right->left->right must be NumNode", (op instanceof NumNode));
    //    Assert.assertEquals("letWithOp->right->left->right->num must be 7", 7, num7.num);
//
    //}
    //// @Test
    // public void testParseLet() {
    //     //SETUP
    //     //input = "let x = (3 * (x + y))";
    //     List<Token> input = new ArrayList<Token>();
    //     input.add(new Token(TokenType.LET));
    //     input.add(new Token(TokenType.VAR, "x"));
    //     input.add(new Token(TokenType.EQUAL));
    //     input.add(new Token(TokenType.NUM, 3));
