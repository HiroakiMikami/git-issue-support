import commandline.CommandLine
import scala.collection.immutable.HashMap
import command._

object GitIssueSupportTool extends CommandLine(HashMap(
	        List("add", "a") -> new Add(),
	        List("branch", "br", "b")->new Branch(),
	        List("update", "u")->new Update(),
	        List("list", "l")->new command.List(),
	        List("project", "p", "pj")->new Project(),
	        List("mine", "m")->new Mine())) {
}