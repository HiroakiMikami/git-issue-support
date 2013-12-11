package command

import settings.Settings
import util.Argument
import settings.ModeController

class Add extends Command{
	def apply(args: Array[String]): Unit = {
	    /* projectの取得 */
	    val project = if(args.length == 0) Settings.project else args(0)
	    /* argsの取得 */
	    val argument = if(args.length == 0) "" else args.tail.foldLeft("")(_+" "+_)
	    
	    /* subjectの取得 */
	    print("Input Subject:")
	    val subject = Console.readLine()
	    print("Input Description")
	    /* descriptionの取得 */
	    val description = Argument.getArgsWithTemporaryFile(".git-issue-support-description")
	    
	    /*scriptにまとめる*/
	    val script = s"""git issue add --project_id=${project} --subject=${subject} --description="${Argument.multiArgsToArg(description)}" ${argument}"""
	    ModeController.execCommand(script)
	}
	def help = "add [<project>] [<args>]"
}
