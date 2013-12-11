package command

import settings.Mode._
import settings.ModeController
import shell.ExecCommand

/**
 * ファイルに動作をcacheしているようなコマンドのリスト
 */
abstract class CommandWithCache(cacheFile: String, defaultCommand: String) extends Command{
	def apply(args: Array[String]): Unit = {
	    ModeController.mode match {
	        case Local => {
	            ExecCommand.system(s"cat ${cacheFile}")
	        }
	        case _ => {
	            ExecCommand.system(defaultCommand + " " + args.foldLeft("")(_+" "+_))
	        }
	    }
	}
}