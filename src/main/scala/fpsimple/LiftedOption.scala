package fpsimple

import cats._
import cats.effect._
import cats.syntax.all._
import cats.data._

object LiftedOption extends IOApp.Simple:  
  val option3 = 3.some
  val inc = (x: Int) => x + 1
  val lifted = Functor[Option].lift(inc)
    
  override def run = 
     for
       _ <- IO.println(lifted(option3))
     yield()