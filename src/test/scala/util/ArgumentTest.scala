package test.util


import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.ShouldMatchersForJUnit
import org.junit.Test
import org.junit.Before
import shell.ExecCommand
import util.Argument

class ArgumentTestSuite extends JUnitSuite with ShouldMatchersForJUnit {
    @Before def initialize() {
        
    }
    @Test def tempFileTest() {
        //Argument.getArgsWithTemporaryFile("temporary").length should be (1)// 邪魔なので普段はcomment out
    }
    @Test def echoConvertTest(){
        Argument.multiArgsToArg(List("hoge", "fuga")) should be ("""echo "hoge\nfuga"""")
    }
    
}

object ArgumentTest {
	def func(a: Int) = 0
}