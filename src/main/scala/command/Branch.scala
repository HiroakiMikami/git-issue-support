package command

import shell.ExecCommand

class Branch extends Command{
	def apply(args: Array[String]): Unit = {
		/* idの取得*/
	    if(args.length == 0){
	        System.err.println("branch command must have one or more than argument");
	        
	        return 
	    }
	    val id = args(0)
	    /* argsの取得 */
	    val argument = args.tail.foldLeft("")(_+" "+_)
		ExecCommand.execCommand(s"git branch ticket/id/${id} ${argument}")
	}
	def help = "branch <id>"
}