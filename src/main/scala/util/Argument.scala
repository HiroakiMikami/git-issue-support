package util

import java.io.File
import shell.ExecCommand
import settings.Settings
import scala.io.Source

/**
 * 引数(ないしOption)の取得に必要なメソッドをまとめたObject
 */
object Argument {
    /**
     * 一時ファイルとEditorを用いてArgumentを受け取る(行区切りのSeqとして返る)
     */
	def getArgsWithTemporaryFile(filePath: String): Seq[String] = {
	    val f = new File(filePath)
	    if(f.exists()){
	        System.err.println(s"temporary file(${filePath}) exists.")
	        exit
	    }
	    f.createNewFile()
	    ExecCommand.execCommand(s"${Settings.editor} ${filePath}")
	    val retval = Source.fromFile(filePath).getLines
	    f.delete()
	    retval.toSeq
	}
	/**
	 * 複数のargumentをechoをつかったscriptに変換する
	 */
	def multiArgsToArg(args: Seq[String]) = {
	    s"""${(args.foldLeft("")(_+"\n"+_)).tail}"""
	}
}