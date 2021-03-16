package myfp

trait Functor[F[_]]:
  extension[A](fa: F[A])
    def map[B](f: A => B): F[B]
    def lift[B](f: A => B): F[A] => F[B] = (fa: F[A]) => fa.map(f)   

trait Apply[F[_]] extends Functor[F]:
  def pure[A](a : A) : F[A]

trait Applicative[F[_]] extends Apply[F]:
  def ap[A,B](fa : F[A])(f : F[A => B]) : F[B]
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]

trait Monad[F[_]] extends Applicative[F]:
  def flatMap[A,B](fa:F[A])(f: A => F[B]) : F[B]

trait Traverse[F[_]] extends Functor[F]:
  def traverse[G[_],A,B](fa: F[A])(f : A => G[B]) : G[F[B]]

  extension[G[_], A](fa : F[G[A]])
    def sequence() : G[F[A]] = traverse(fa)(identity)




