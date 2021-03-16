package ce101

import cats.effect._
import cats.syntax.all._

import scala.concurrent.duration._

import java.io.File

/**
  * small script to rename my OP files
  */

object RenameOP extends IOApp.Simple:

  def getListOfFiles(dir: String):IO[List[File]] =
    IO{
      val d = new File(dir)
      if (d.exists && d.isDirectory) then
        d.listFiles.filter(_.isFile).toList
      else
        List[File]()
    }  
    
  def newName(name : String, dir :String) : String =
    val extension = name.takeRight(3)
    val nameLength = "One Piece - XXX".length
    s"""|${dir}${name.take(nameLength)}.${extension}""".stripMargin
  
  val dir = "/media/renghen/Acer/Users/rengh/Downloads/torrent/One Piece - Season 11 Voyage 4/"
  override def run = 
    for
      files <- getListOfFiles(dir)
      _     <- IO{
                  files.map{file => 
                    val newNameFile = newName(file.getName,dir)
                    println(newNameFile)
                    file.renameTo(new File(newNameFile))
                  }
                }
    yield()