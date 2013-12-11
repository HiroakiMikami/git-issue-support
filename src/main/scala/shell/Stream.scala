package shell

import scala.actors.Future
import scala.actors.Futures._
import java.io.InputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.IOException

object Stream {
	def getStream(in: InputStream): Future[String] = {
	    future {	
	    	val isr = new InputStreamReader(in);
	    	val br = new BufferedReader(isr);
	    	var line = "";
	    	var retval = ""
	    			try{
	    				while (line != null){
	    					line = br.readLine()
	    					if(line != null){
	    						retval += line + "\n"
	    					}
	    				}
	    			} catch {
	    			case exception: IOException => exception.printStackTrace()
	    			}
	    	retval
	    }
	}
}