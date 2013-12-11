package command

/**
 * サブコマンドをあらわす特性
 */
trait Command {
    /**
     * コマンドの実行
     */
	def apply(args: Array[String]): Unit
	def help: String
}