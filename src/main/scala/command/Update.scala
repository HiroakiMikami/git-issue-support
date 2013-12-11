package command

import util.Argument
import settings.ModeController
import settings.Settings

class Update extends Command{
	def apply(args: Array[String]): Unit = {
	    /*argumentの変換*/
	    val argument = args.map(_ match {
	        case ("-m"|"--mine") => {
	        	/*mineID*/
	            println(s"assigned to id to ${Settings.mineID}")
	            s"--assigned_to_id=${Settings.mineID}"
	        }
	        case ("-s"|"--status") => {
	            /*Statusを入力させて、それに変更する*/
	            println("Input Status(1:新規\t2:進行中\t5:解決済\t6:却下): ");
	            val status = Console.readLine
	            s"""--status=${status}"""
	        }
	        case ("-r"|"--ratio") => {
	            /*ratioを入力させて、それに変更する*/
	            println("Input Ratio(0-100[%]): ");
	            val ratio = Console.readLine
	            s"""--ratio=${ratio}"""
	        }
	        case ("-p"|"--priority") => {
	            /*priorityを入力させて、それに変更する*/
	            println("Input Priority(3:低め\t4:通常\t5:高め\t6:急いで\t7:緊急\t10:ゆっくり(ry): ");
	            val priority = Console.readLine
	            s"""--priority=${priority}"""
	        }
	        case ("-n"|"--notes") => {
	            /*notesをeditorから入力させて、それに変更する*/
	        	val notes = Argument.getArgsWithTemporaryFile(".git-issue-support-notes")
	        	s"""--notes=${Argument.multiArgsToArg(notes)}"""
	        }
	        case arg:String => arg //< その他の場合 
	    }).foldLeft("")(_+" "+_)
	    val script = s"""git issue update ${argument}"""
	    ModeController.execCommand(script)
	}
	def help = """update [options]
options list:
    -s|--status
    -r|--ratio
    -p|--priority
    -n|--notes
"""
}