package settings

import shell.ExecCommand
import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import java.io.File
import java.io.PrintWriter
import java.io.BufferedWriter
import java.io.FileWriter
import scala.io.Source

/**
 * プログラムの実行モードをあらわす。
 * @todo not tested yet
 */
object Mode extends Enumeration {
    type Mode = Value
	val Remote, Local, Test = Value
}
object ModeController {
    private val scriptSeparator = "###finish###"
    
    private var mode_ : Mode.Value = Mode.Test
    private def updateFile(file: File, duration: Duration, update: ()=>String): Unit ={
        val now = System.currentTimeMillis() millis
        val lastModified = file.lastModified().millis
        
        if(now - lastModified > duration){
            val pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(update())
            pw.flush
            pw.close
        }
    }
    def mode: Mode.Value = mode_
	/**
     * modeのSetter
     * 同時に、起動時処理を行う
     */
    def mode_=(mode: Mode.Value): Unit = {
        mode_ = mode
        mode match {
            case Mode.Remote => {
                /*
                 * Remoteの際の起動時処理 
                 */
                // (1) スクリプト保存ファイルを見て、存在すれば実行
                if(new File(Settings.scriptFile).length() != 0){
                    val s = Source.fromFile(Settings.scriptFile)
                    try {
                        var command = ""
                    	for (line <- s.getLines) {
                    	    if(line == scriptSeparator){
                    	        println(command+"#command")
                    	    	val r = ExecCommand.execCommand(command)
                    	    	Console.println(r.stdout)
                    	    	Console.err.println(r.stderr)
                    	    	command = ""
                    	    }else{
                    	    	command += line + "\n"
                    	    }
                    	}
                    } finally {
                    	s.close
                    }
                    // 削除
                    new File(Settings.scriptFile) delete
                }
                
                // (2) ファイル更新日時がupdate周期を超えていれば、最新のに変更
                for(c <- Settings.cacheList){
                    updateFile(new File(c.file), Settings.updateTime, () => ExecCommand.execCommand(c.updateCommand).stdout)
                }
            }
            case _ => {}
        }
    }
    def execCommand(command: String): Unit = {
    	mode_ match {
    	    case Mode.Remote => ExecCommand.system(command) //< スクリプトの実行
    	    case Mode.Local => { /* スクリプト保存ファイルに保存 */
    	        // JavaのFileOutputStreamクラスとOutputStreamWriterを別名でimport
    	    	import java.io.{ FileOutputStream=>FileStream, OutputStreamWriter=>StreamWriter };
    	    	
    	    	val fileName = "test.txt"
    	    	val encode = "UTF-8"
         
    	    	// 書き込み処理
     	    	val writer = new StreamWriter( new FileStream(Settings.scriptFile, true), "UTF-8" )
    	    	writer.write(s"${command}\n")
    	    	writer.write(s"${scriptSeparator}\n")
    	    	writer.close
    	    	println(command)
     	    }
    	    case Mode.Test => ExecCommand.execCommand(s"echo ${command}") //< echo
    	}
    }
}
