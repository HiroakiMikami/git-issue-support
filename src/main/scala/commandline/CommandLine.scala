package commandline

import scala.collection.immutable.HashMap
import command._
import settings.Mode
import settings.ModeController
import shell.ExecCommand
import settings.Settings

class CommandLine(commandMap: HashMap[scala.collection.immutable.List[String], Command]) {
    private def usage: Unit = {
        println("#usage [<options>] <command> [<args>]")
        println("""options list:
    -l execute with Local Mode""")
    	exit        
    }
    def main(args:Array[String]): Unit = {
        if(args.length < 1){
            usage
        }
        
        var subcommand = ""
        var argument = List[String]()
        if(args(0) == "-l"){
            ModeController.mode = Mode.Local
            if(args.length < 2){
                usage
            }
            subcommand = args(1)
            argument = args.tail.tail.toList
        }else{
        	ModeController.mode = Mode.Remote
        	subcommand = args(0)
        	argument = args.tail.toList
        }
        
        if(subcommand == "help"){
            if(argument.length == 0){
                usage
            }else{
            	for(command <- commandMap){
            		if(command._1.contains(argument(0))){
            			println(command._2.help)
            		}
            	}    
            }
        }else{
        
        	var flag = false
        	for(command <- commandMap){
            	if(command._1.contains(subcommand)){
                	command._2.apply(argument.toArray)
                	flag = true
            	}
        	}
        
        	if(!flag){
        		// 当てはまるコマンド無し
            	ExecCommand.execCommand(s"git issue ${subcommand} ${argument.foldLeft("")(_+" "+_)}")
        	}
        }
    }
	
}