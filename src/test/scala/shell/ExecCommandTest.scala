package test.shell

import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.ShouldMatchersForJUnit
import org.junit.Test
import org.junit.Before
import shell.ExecCommand

class ExecCommandTestSuite extends JUnitSuite with ShouldMatchersForJUnit {
    @Before def initialize() {
        
    }
    @Test def stdoutTest() {
        ExecCommand.execCommand("echo a") match{
            case ExecCommand.Result(stdout, _, _) => stdout should be ("a\n")
        }
    }
    @Test def stderrTest(){
        ExecCommand.execCommand("ls fuga") match{
            case ExecCommand.Result(stdout, stderr, _) => stdout should be ("");stderr should not be ("")
        }        
    }
    @Test def argumentTest(){
        ExecCommand.execCommand("echo", "a") match{
            case ExecCommand.Result(stdout, stderr, _) => stdout should be ("a\n");stderr should be ("")
        }
    }
    @Test def systemTest(){
        ExecCommand.system("echo a")
    }
    @Test def multiCommandTest(){
        ExecCommand.system("echo $HOME")
    }
    
    
    
}

object ExecCommandTest {
	def func(a: Int) = 0
}