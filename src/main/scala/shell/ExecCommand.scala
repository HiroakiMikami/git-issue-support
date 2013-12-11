package shell

object ExecCommand {
    case class Result(stdout: String, stderr: String, exitCode: Int)
	def execCommand(commands: String*): Result = {
        val command = commands.foldLeft("")((str:String, command:String)=>str+" "+command)
        execCommand(command)
    }
	def execCommand(command: String): Result = {
    	val proc = Runtime.getRuntime().exec(command)
    	// 標準出力Streamの読み込みスレッド開始
    	val stdout = Stream.getStream(proc.getInputStream())
    	// エラー出力Streamの読み込みスレッド開始
    	val stderr = Stream.getStream(proc.getErrorStream())
    	// すべての処理が終わるまで待機
    	val exitCode = proc.waitFor()
    	Result(stdout(), stderr(), exitCode)
    }
	def system(command: String): Unit = {
    	execCommand(command) match {
    	    case Result(stdout, stderr, _) => {
    	        print(stdout)
    	        System.err.print(stderr)
    	    }
    	}
	}
}