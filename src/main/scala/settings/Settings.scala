package settings

import shell.ExecCommand
import scala.concurrent.duration._

/**
 * プログラム全体で用いる設定を保存・管理するオブジェクト
 */
object Settings {
    /* gitの情報 */
    val editor = {
        val e = ExecCommand.execCommand("git config --get core.editor").stdout
        if(e == "") "vi" else e
    }
    val toplevel = ExecCommand.execCommand("git rev-parse --show-toplevel").stdout.init
    val homeDirectory = ExecCommand.execCommand("env").stdout.lines.find((str)=>{str.split("=")(0) == "HOME"}) match {
        case Some(str) => str.replaceFirst("HOME=", "")
        case None => toplevel
    }
    val project = ExecCommand.execCommand("git config --get issue.project").stdout.init
    val updateTime = try{
        (ExecCommand.execCommand("git config --get issue.support.update").stdout toFloat).hour
    }catch{
        case _ => 24.hour
    }
    val mineID = {
        val id = ExecCommand.execCommand("git config --get issue.support.mine").stdout.init
        if(id == "") "" else id
    }

    val scriptFile = s"${toplevel}/.git-issue-support-scripts"
    val listCache = s"${homeDirectory}/.git-issue-support-list"
    val projectCache= s"${homeDirectory}/.git-issue-support-project-${project}"
    val mineCache = s"${homeDirectory}/.git-issue-support-mine"
    case class Cache(file: String, updateCommand: String)
    /**
     * キャッシュするファイル名と更新方法のタプル
     */
    val cacheList = List(Cache(listCache,"git issue list"),
            		 	  Cache(projectCache,"git issue project"),
            		 	  Cache(mineCache,"git issue mine"))
}
